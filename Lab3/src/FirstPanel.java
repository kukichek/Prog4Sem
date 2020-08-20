import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class FirstPanel extends JPanel {
    private static final int PANEL_WIDTH = 550;
    private static final int PANEL_HEIGHT = 450;

    public FirstPanel(Map<String, String> countryToCapital, Map<String, ImageIcon> countryToFlag, List<Country> countries) {
        JLabel label = new JLabel();

        JList<Country> list = new JList<>();
        DefaultListModel<Country> listModel = new DefaultListModel<>();

        for (Country country : countries) {
            listModel.addElement(country);
        }

        list.setModel(listModel);
        list.setCellRenderer(new CountryLabel());
        list.setBounds(100, 100, 150, 75);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                Country country = list.getSelectedValue();
                label.setIcon(country.getFlag());
                label.setText(country.getName() + " - " + country.getCapital());
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        scrollPane.setPreferredSize(new Dimension(470, 350));

        this.add(scrollPane);
        this.add(label);
        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }
}
