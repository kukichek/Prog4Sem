import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Type checker");

        String[] dataTypes = {
                "Natural number",
                "Integer",
                "Real number (15.5, 1.55e1)",
                "Date (DD/MM/YYYY)",
                "Time (HH:MM)",
                "E-Mail (abc@secondLevelDomain.firstLevelDomain)"};
        Pattern[] patterns = getPatterns();

        Circle circle = new Circle(40, 100, 5);
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(dataTypes));
        comboBox.getSelectionModel().select(0);
        TextField textField = new TextField();

        comboBox.valueProperty().addListener((ObservableValue<? extends String> observableValue, String s, String t1) -> {
            changeCircleColor(t1, dataTypes, patterns, circle, textField.getText());
        });
        textField.textProperty().addListener((ObservableValue<? extends String> observableValue, String s, String t1) -> {
            changeCircleColor(comboBox.getValue(), dataTypes, patterns, circle, t1);
        });

        FlowPane root = new FlowPane(10, 10, comboBox);
        root.getChildren().add(textField);
        root.getChildren().add(circle);
        circle.setFill(Color.RED);

        stage.setScene(new Scene(root, 300, 200));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Pattern[] getPatterns() {
        String nPattern = "[1-9]\\d*";
        String zPattern = "[-+]?" + "(" + nPattern + "|0)";
//        String numberWithFloatingPointPattern = "(" + zPattern + "([,.]" + "0*[1-9]\\d*)?" + ")|(" + zPattern + "[.])|(" + "[,.]" + "0*[1-9]\\d*)";
        String numberWithFloatingPointPattern = "((" + zPattern + "([,.]" + "\\d*)?" + ")|(" + zPattern + "[.])|(" + "[,.]" + "\\d+))";

//        double k = .00000;

        String delim = "[./-]";
        String anyYear = "([1-9]\\d*|0)";
        String leapYears = "(29" + delim + "0?2" + delim + "((([1-9]\\d|[1-9])(0[48]|[2468][048]|[13579][26]))|[48]|([2468][048])|([13579][26])))";
        String day31 = "31" + delim + "(0?[13578]|1[02])";
        String day29or30 = "(30|29)" + delim + "(0?[13-9]|1[012])";
        String otherDays = "((2[0-8])|(1\\d)|(0?[1-9]))" + delim + "(0?[1-9]|1[012])";

        Pattern N = Pattern.compile(nPattern);
        Pattern Z = Pattern.compile(zPattern);
        Pattern R = Pattern.compile(numberWithFloatingPointPattern + "(e" + zPattern + ")?");
        Pattern date = Pattern.compile(leapYears + "|" + "(" + "(" + day31 + "|" + day29or30 + "|" + otherDays + ")" + delim + anyYear + ")");
        Pattern time = Pattern.compile("((2[0-3])|([01]?\\d)):(([1-5]|0?)\\d)");
        Pattern eMail = Pattern.compile("([\\w-]+[.])*[\\w-]+@[\\w-]+[.][\\w-]+");

        Pattern[] patterns = new Pattern[6];
        patterns[0] = N;
        patterns[1] = Z;
        patterns[2] = R;
        patterns[3] = date;
        patterns[4] = time;
        patterns[5] = eMail;

        return patterns;
    }

    private void changeCircleColor(String curType, String[] types, Pattern[] patterns, Circle circle, String text) {
        Matcher matcher;
        for (int i = 0; i < 6; ++i) {
            if (curType.equals(types[i])) {
                matcher = patterns[i].matcher(text);
                if (matcher.matches()) {
                    circle.setFill(Color.CHARTREUSE);
                } else {
                    circle.setFill(Color.RED);
                }
                break;
            }
        }
    }
}
