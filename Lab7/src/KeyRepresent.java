import javafx.scene.input.KeyCode;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class KeyRepresent {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String keyRepresent;

    void setKeyCode(KeyCode keyCode) {
        support.firePropertyChange("", keyRepresent, keyCode.getName());
        keyRepresent = keyCode.getName();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
