import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class SecondPanel extends JPanel {
    private static final int PANEL_WIDTH = 550;
    private static final int PANEL_HEIGHT = 450;

    private JTextField countryField = new JTextField();
    private JTextField descriptField = new JTextField();
    private JTextField priceField = new JTextField();

    private Map<String, ImageIcon> countryToFlag;

    SecondPanel( Map<String, ImageIcon> countryToFlag) {
        this.countryToFlag = countryToFlag;

        String[] columnNames = {"Country", "Description", "Price", "Chosen Trips"};
        JPanel panelForTextFields = new JPanel(new GridLayout(3, 4));

        JTable table = new JTable();
        DefaultTableModel tableModel;

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);
        scrollPane.setPreferredSize(new Dimension(540, 300));

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class getColumnClass(int columnIndex) {
                return getValueAt(0, columnIndex).getClass();
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return (row != 0) && (column != 0);
            }
        };

        tableModel.addRow(new Object[]{new ImageIcon(), "Total cost: ", 0, false});

        tableModel.addTableModelListener(new TableModelListener() {
            boolean hasChanged = false;

            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {

                if (!hasChanged) {
                    int sum = 0;
                    for (int i = 1; i < tableModel.getRowCount(); ++i) {
                        if ((boolean) tableModel.getValueAt(i, 3)) {
                            sum += (int) tableModel.getValueAt(i, 2);
                        }
                    }
                    hasChanged = true;
                    tableModel.setValueAt(sum, 0, 2);
                    hasChanged = false;
                }
            }
        });

        Country england = new Country("England");
        england.setFlag(countryToFlag.get(england.getName()));
        Country vietnam = new Country("Vietnam");
        vietnam.setFlag(countryToFlag.get(vietnam.getName()));

        tableModel.addRow(new Object[]{england.getFlag(), "3 дня в Лондоне с проживанием в центре города.", 456, false});
        tableModel.addRow(new Object[]{vietnam.getFlag(), "Джонни! Они на деревьях!", 243, false});

        panelForTextFields.add(new JLabel("Country:"));
        panelForTextFields.add(new JLabel("Description:"));
        panelForTextFields.add(new JLabel("Price:"));
        panelForTextFields.add(countryField);
        panelForTextFields.add(descriptField);
        panelForTextFields.add(priceField);
        panelForTextFields.add(initAddButton(tableModel));

        table.setRowHeight(60);
        table.setModel(tableModel);
        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.add(panelForTextFields);
        this.add(scrollPane);
    }

    private JButton initAddButton(DefaultTableModel tableModel) {
        JButton addTourButton = new JButton("Add new tour");
        addTourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int price = Integer.valueOf(priceField.getText());
                    String countryName = countryField.getText();

                    if (countryToFlag.get(countryName) == null) {
                        throw new IllegalArgumentException("Entered country doesn't exist.");
                    }

                    Country country = new Country(countryField.getText());
                    country.setFlag(countryToFlag.get(country.getName()));
                    Object[] tourRow = new Object[]{country.getFlag(), descriptField.getText(), price, false};
                    tableModel.addRow(tourRow);
                }
                catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Incorrect price, reenter please.");
                }
                catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });

        return addTourButton;
    }
}
