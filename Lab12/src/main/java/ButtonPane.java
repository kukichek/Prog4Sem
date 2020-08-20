package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ButtonPane extends JPanel {
    public ButtonPane(Rectangle r) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBounds(r);

        JPanel nameTypePanel = new JPanel();
        nameTypePanel.setLayout(new BoxLayout(nameTypePanel, BoxLayout.PAGE_AXIS));
        nameTypePanel.setMaximumSize(new Dimension(340, 200));

        JPanel continentsPanel = new JPanel();
        continentsPanel.setLayout(new BoxLayout(continentsPanel, BoxLayout.PAGE_AXIS));

        nameField = new JTextField();
        typeComboBox = new JComboBox<>(Waterbody.Type.values());
        valueSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        continentList = new JList<>(Waterbody.Continent.values());
        continentList.setPreferredSize(new Dimension(300, 130));

        nameTypePanel.add(new JLabel("Enter name of water body"));
        nameTypePanel.add(nameField);
        nameTypePanel.add(new JLabel("Choose type of water body"));
        nameTypePanel.add(typeComboBox);
        nameTypePanel.add(new JLabel("Set height/length/surface area"));
        nameTypePanel.add(valueSpinner);

        continentsPanel.add(new JLabel("Set continents"));
        continentsPanel.add(continentList);

        add(nameTypePanel);
        add(continentsPanel);
    }

    public String getName() {
        return nameField.getText();
    }

    public Waterbody.Type getType() {
        return (Waterbody.Type) typeComboBox.getSelectedItem();
    }

    public int getValue() {
        return (int) valueSpinner.getValue();
    }

    public List<Waterbody.Continent> getContinents() {
        return continentList.getSelectedValuesList();
    }

    private JTextField nameField;
    private JComboBox<Waterbody.Type> typeComboBox;
    private JSpinner valueSpinner;
    private JList<Waterbody.Continent> continentList;
}
