import javax.swing.*;
import java.awt.*;

public class CountryLabel extends JLabel implements ListCellRenderer<Country> {
    public CountryLabel() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Country> jList, Country country, int i, boolean isSelected, boolean b1) {
        setIcon(country.getFlag());
        setText(country.getName());

        if (isSelected) {
            setBackground(jList.getSelectionBackground());
        } else {
            setBackground(jList.getBackground());
        }

        return this;
    }
}
