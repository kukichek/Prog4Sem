import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 10;
    private static final int INITIAL_SPEED = 1;

    private static final int HORIZONTAL_OFFSET = 150;
    private static final int VERTICAL_OFFSET = 100;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 500;
    private static final int CONTROL_PANEL_HEIGHT = 60;
    private static final int IMAGE_PANEL_WIDTH = 550;
    private static final int IMAGE_PANEL_HEIGHT = 400;

    private JSlider slider;
    private int direction;

    MainWindow() {
        super("Circling image");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(HORIZONTAL_OFFSET, VERTICAL_OFFSET, FRAME_WIDTH, FRAME_HEIGHT);
        setLayout(new BorderLayout());
        setVisible(true);

        direction = 1;
        slider = initSlider();

        ImageWindow imagePanel = new ImageWindow(this, IMAGE_PANEL_WIDTH, IMAGE_PANEL_HEIGHT);

        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(FRAME_WIDTH, CONTROL_PANEL_HEIGHT));
        controlPanel.add(initOpenButton(imagePanel));
        controlPanel.add(slider);
        controlPanel.add(initButtonPanel());

        add(imagePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
    }

    private JPanel initButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout());

        JRadioButton clockwise = new JRadioButton("По часовой стрелке", false);
        JRadioButton counterclockwise = new JRadioButton("Против часовой стрелки", true);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(clockwise);
        buttonGroup.add(counterclockwise);

        clockwise.addActionListener(actionEvent -> {direction = -1;});
        counterclockwise.addActionListener(actionEvent -> {direction = 1;});

        buttonPanel.add(clockwise, BorderLayout.NORTH);
        buttonPanel.add(counterclockwise, BorderLayout.SOUTH);

        return buttonPanel;
    }

    private JButton initOpenButton(ImageWindow imagePanel) {
        JButton openImageButton = new JButton("Open File");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image Files (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(imageFilter);

        openImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showDialog(null, "Открыть файл");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        imagePanel.setImage(ImageIO.read(file));
                    } catch (IOException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
            }
        });

        return openImageButton;
    }

    private JSlider initSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, MIN_SPEED, MAX_SPEED, INITIAL_SPEED);
        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        return slider;
    }

    public int getDirection() {
        return direction;
    }

    public double getSpeed() {
        return slider.getValue();
    }

    public static void main(String args[]) {
        new MainWindow();
    }

}