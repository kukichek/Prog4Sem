import javafx.scene.control.TextArea;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggerField extends TextArea implements PropertyChangeListener {
    public LoggerField() {
        editableProperty().setValue(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        String text = getText();
        text = text.concat(String.format("Key pressed: %s\n", propertyChangeEvent.getNewValue()));
        setText(text);
        selectEnd();
        deselect();
    }
}
