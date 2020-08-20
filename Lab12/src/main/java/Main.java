package main.java;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Main extends JFrame {
    public Main() throws HeadlessException, MalformedURLException {
        super("XML Table");
        setBounds(400, 100, FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        table = new JTable();
        tableModel = null;
        buttonPane = new ButtonPane(new Rectangle(0, 0, FRAME_WIDTH, 200));
        add(table, BorderLayout.NORTH);
        add(buttonPane, BorderLayout.SOUTH);

        setSplashScreenByJWindow();
        setMenuBar();

        setVisible(true);
        toFront();
    }

    public static void main(String[] args) throws MalformedURLException {
        new Main();
    }

    private void setSplashScreenByJWindow() throws MalformedURLException {
        JWindow window = new JWindow();
        window.setLayout(null);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBounds(0, 0, IMAGE_WIDTH_HEIGHT, PROGRESSBAR_HEIGHT);

        // Thread.currentThread().getContextClassLoader().getResource(path)
        JLabel image = new JLabel("", new ImageIcon(new URL(ICON_URI)), SwingConstants.CENTER);

        image.setBounds(0, PROGRESSBAR_HEIGHT, IMAGE_WIDTH_HEIGHT, IMAGE_WIDTH_HEIGHT);

        window.setBounds(500, 100, IMAGE_WIDTH_HEIGHT, IMAGE_WIDTH_HEIGHT + PROGRESSBAR_HEIGHT);
        window.add(progressBar);
        window.add(image);
        window.setVisible(true);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        window.setVisible(false);
    }

    private void setMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu loadMenu = new JMenu("Load");
        JMenu saveMenu = new JMenu("Save");
        JMenu editMenu = new JMenu("Edit");
        JMenu countMenu = new JMenu("Count");

        JMenuItem loadXMLItem = new JMenuItem("Load XML");
        JMenuItem loadBinaryItem = new JMenuItem("Load binary");
        JMenuItem saveXMLItem = new JMenuItem("Save XML");
        JMenuItem saveBinaryItem = new JMenuItem("Save binary");
        JMenuItem addRowItem = new JMenuItem("Add row");
        JMenuItem deleteRowsItem = new JMenuItem("Delete row(s)");
        JMenuItem countSAXItem = new JMenuItem("Count SAX");

        loadXMLItem.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setCurrentDirectory(new File(DEFAULT_DIRECTORY_PATH));
            int option = chooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                tableModel = new WaterbodyTableModel(chooser.getSelectedFile());
                table.setModel(tableModel);
            }

            setTableColumnsWidth();
        });

        loadBinaryItem.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            chooser.setCurrentDirectory(new File(DEFAULT_DIRECTORY_PATH));
            int option = chooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    tableModel = WaterbodyTableModel.getBinary(chooser.getSelectedFile());
                    table.setModel(tableModel);
                    table.updateUI();

                    setTableColumnsWidth();
                } catch (IOException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        });

        saveXMLItem.addActionListener(event -> {
            if (tableModel != null) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                chooser.setCurrentDirectory(new File(DEFAULT_DIRECTORY_PATH));
                int option = chooser.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    try {
                        tableModel.saveXml(chooser.getSelectedFile());
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "nothing to save");
            }
        });

        saveBinaryItem.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            chooser.setCurrentDirectory(new File(DEFAULT_DIRECTORY_PATH));
            int option = chooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    tableModel.saveBinary(chooser.getSelectedFile());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        });

        addRowItem.addActionListener(event -> {
            if (tableModel != null) {
                String name = buttonPane.getName();
                Waterbody.Type type = buttonPane.getType();
                int val = buttonPane.getValue();
                List<Waterbody.Continent> continentList = buttonPane.getContinents();
                Waterbody.Property property;

                if (name != null && !continentList.isEmpty()) {
                    switch (type) {
                        case Waterfall:
                            property = Waterbody.Property.height;
                            break;
                        case River:
                            property = Waterbody.Property.length;
                            break;
                        default:
                            property = Waterbody.Property.surfaceArea;
                    }

                    tableModel.getWaterObjects().add(new Waterbody(name, type, property, val, continentList));
                    tableModel.fireTableDataChanged();
                    table.repaint();
                    table.revalidate();
                } else {
                    JOptionPane.showMessageDialog(this, "please set all fields");
                }
            } else {
                JOptionPane.showMessageDialog(this, "tableModel is not initialized");
            }
        });

        deleteRowsItem.addActionListener(event -> {
            if (tableModel != null) {
                tableModel.deleteRows(table.getSelectedRows());
                table.repaint();
                table.revalidate();
            } else {
                JOptionPane.showMessageDialog(this, "tableModel is not initialized");
            }
        });

        countSAXItem.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            chooser.setCurrentDirectory(new File(DEFAULT_DIRECTORY_PATH));
            int option = chooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser parser = factory.newSAXParser();
                    SAXCounter handler = new SAXCounter();
                    parser.parse(chooser.getSelectedFile(), handler);
                    JOptionPane.showMessageDialog(null, String.format(FORMAT_STRING,
                            handler.getLakeCount(),
                            handler.getRiverCount(),
                            handler.getWaterfallCount(),
                            handler.getSeaCount(),
                            handler.getOceanCount(),
                            handler.getMaxSurfaceArea()));
                } catch (IOException | SAXException | ParserConfigurationException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
        });

        loadMenu.add(loadXMLItem);
        loadMenu.add(loadBinaryItem);
        saveMenu.add(saveXMLItem);
        saveMenu.add(saveBinaryItem);
        editMenu.add(addRowItem);
        editMenu.add(deleteRowsItem);
        countMenu.add(countSAXItem);

        bar.add(loadMenu);
        bar.add(saveMenu);
        bar.add(editMenu);
        bar.add(countMenu);

        setJMenuBar(bar);
    }

    private void setTableColumnsWidth() {
        table.getColumnModel().getColumn(1).setMaxWidth(100);
        table.getColumnModel().getColumn(2).setMaxWidth(200);
        table.getColumnModel().getColumn(3).setMaxWidth(200);
    }

    private JTable table;
    private ButtonPane buttonPane;
    private WaterbodyTableModel tableModel;

    private static final int PROGRESSBAR_HEIGHT = 15;
    private static final int IMAGE_WIDTH_HEIGHT = 500;

    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;
    private static final String ICON_URI = "https://data.whicdn.com/images/316637523/original.jpg";
    private static final String FORMAT_STRING = "Lake count: %s;\n River count: %s;\n Waterfall count: %s;\n Sea count: %s;\n Ocean count: %s;\n Max surface area: %s";
    private static final String DEFAULT_DIRECTORY_PATH = "C:\\Users\\acer\\Downloads\\xml";
}
