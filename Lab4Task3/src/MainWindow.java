import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

public class MainWindow extends JFrame {
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 400;
    private static final int HORIZONTAL_OFFSET = 150;
    private static final int VERTICAL_OFFSET = 100;

    MainWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(HORIZONTAL_OFFSET, VERTICAL_OFFSET, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);

        try {
            Object object = new JSONParser().parse(new FileReader("data.json"));
            JSONObject jsonObject = (JSONObject) object;
            JSONArray toysJSONArray = (JSONArray) jsonObject.get("Toys");

            PieDataset pieDataset = createDataset(toysJSONArray);
            JFreeChart chart = createChart(pieDataset);
            PiePlot plot = (PiePlot) chart.getPlot();
            PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                    "{0}: {1} box/es, {2}");
            plot.setLabelGenerator(gen);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new ChartPanel(chart), BorderLayout.CENTER);
            add(panel);
            panel.validate();
        } catch (IOException | ParseException | NumberFormatException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
        pack();
    }

    public static void main(String[] args) {
        new MainWindow();
    }

    private JFreeChart createChart(final PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart("Toy Shop Diagram",
                dataset, true, true, false);
        return chart;
    }

    private PieDataset createDataset(JSONArray toysJSONArray) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Object o : toysJSONArray) {
            try {
                JSONObject toy = (JSONObject) o;

                dataset.setValue((String) toy.get("type"), ((Long) toy.get("amount")).intValue());
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
        return dataset;
    }

}