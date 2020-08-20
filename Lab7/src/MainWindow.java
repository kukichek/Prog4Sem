import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pressed Key");
        stage.setResizable(false);

        BorderPane root = new BorderPane();

        KeyRepresent represent = new KeyRepresent();
        LoggerField logger = new LoggerField();
        PressedKeyListenerPane imagePane = new PressedKeyListenerPane();
        logger.setPrefWidth(200);
        logger.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue){
                root.requestFocus(); // Delegate the focus to container
            }
        });
        represent.addPropertyChangeListener(logger);
        represent.addPropertyChangeListener(imagePane);

        root.setLeft(imagePane);
        root.setRight(logger);

        root.setOnKeyPressed(e -> { represent.setKeyCode(e.getCode()); });

        stage.setScene(new Scene(root));
        root.requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
