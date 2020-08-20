import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static int HORIZONTAL_OFFSET = 150;
    private static int VERTICAL_OFFSET = 100;

    MainWindow() {
        super("Clock");

        setBounds(HORIZONTAL_OFFSET, VERTICAL_OFFSET, 500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        add(new FirstTask(400, 400));
        pack();
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
