import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Dates in text");
        String startText = "Sample text29/02/1916 sample_textSAMPLE_TEXT029752,232\n23872,2-3i42-.3-2r_33-13-0242SAMPLETEXT\nSampleText13.05.2013";
        Pattern datePattern = getDatePattern();

        TextArea text = new TextArea(startText);
        text.setPrefRowCount(10);
        TextArea dates = new TextArea();
        dates.setPrefRowCount(10);

        text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                dates.setText("");
                Matcher matcher = datePattern.matcher(text.getText());
                while (matcher.find()) {
                    dates.appendText(matcher.group());
                    dates.appendText("\n");
                }
            }
        });

        FlowPane root = new FlowPane(10, 10);
        root.getChildren().add(text);
        root.getChildren().add(dates);

        stage.setScene(new Scene(root, 490, 380));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Pattern getDatePattern() {
        String delim = "[./-]";
        String anyYear = "([1-9]\\d*|0)";
        String leapYears = "(29" + delim + "0?2" + delim + "((([1-9]\\d|[1-9])(0[48]|[2468][048]|[13579][26]))|[48]|([2468][048])|([13579][26])))";
        String day31 = "31" + delim + "(0?[13578]|1[02])";
        String day29or30 = "(30|29)" + delim + "(0?[13-9]|1[012])";
        String otherDays = "((2[0-8])|(1\\d)|(0?[1-9]))" + delim + "(0?[1-9]|1[012])";

        Pattern datePattern = Pattern.compile(leapYears + "|" + "(" + "(" + day31 + "|" + day29or30 + "|" + otherDays + ")" + delim + anyYear + ")");

        return datePattern;
    }
}
