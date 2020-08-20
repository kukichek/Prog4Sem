import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstTask extends JPanel {
    private Timer timer;

    private int width, height;

    private int delay;
    private int angle;

    public FirstTask(int width, int height) {
        this.width = width;
        this.height = height;
        angle = 0;
        delay = 1000;

        setPreferredSize(new Dimension(width, height));

        ActionListener clockPainter = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                repaint();

                angle += 6;
                angle %= 360;
            }
        };

        timer = new Timer(delay, clockPainter);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, width, height);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(2));

        int circleRadius = Math.min(width, height) / 2 - 5;
        int x, y;

        x = (int) (width / 2 + circleRadius * Math.cos(Math.toRadians(angle)));
        y = (int) (height / 2 + circleRadius * Math.sin(Math.toRadians(angle)));

        g2D.drawLine(width / 2, height / 2, x, y);
        g2D.drawOval(width / 2 - circleRadius, height / 2 - circleRadius, 2 * circleRadius, 2 * circleRadius);
    }
}
