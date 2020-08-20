import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ImageWindow extends JPanel {
    private int width, height;
    private double angle;
    private BufferedImage image;

    ImageWindow(MainWindow parent, int w, int h) {
        int delay = 1;
        angle = 0;
        width = w;
        height = h;

        setPreferredSize(new Dimension(width, height));

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                width = getWidth();
                height = getHeight();

                double circleRadius = Math.min(width, height) * 1. / 2;

                angle += parent.getDirection() * parent.getSpeed() * 20 / Math.max(circleRadius, 1);
                angle %= 360;

                repaint();
            }
        };

        Timer timer = new Timer(delay, taskPerformer);
        timer.start();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, width, height);

        int circleRadius = Math.min(width, height) / 2;
        if (image != null) {
            int xImage = (width - image.getWidth()) / 2 - (int) (circleRadius * Math.sin(Math.toRadians(angle)));
            int yImage = (height - image.getHeight()) / 2 - (int) (circleRadius * Math.cos(Math.toRadians(angle)));
            g.drawImage(image, xImage, yImage, null);
        }
    }
}