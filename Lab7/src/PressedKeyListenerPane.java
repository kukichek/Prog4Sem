import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import javax.imageio.ImageIO;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class PressedKeyListenerPane extends StackPane implements PropertyChangeListener {
    Label keyText;

    public PressedKeyListenerPane() throws IOException {
        keyText = new Label();
        keyText.setFont(new Font("Arial", 38));
        ImageView backgroundImage = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(new File("blankKey.png")), null));
        backgroundImage.setPreserveRatio(true);
        backgroundImage.setFitHeight(400);
        getChildren().add(backgroundImage);
        getChildren().add(keyText);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        keyText.setText((String) propertyChangeEvent.getNewValue());
    }
}
