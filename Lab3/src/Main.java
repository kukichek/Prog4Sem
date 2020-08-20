import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Main {
    private static final int FRAME_WIDTH = 560;
    private static final int FRAME_HEIGHT = 500;

    public static void main(String[] args) throws IOException {
        Map<String, String> countryToCapital = new HashMap<>();
        Map<String, ImageIcon> countryToFlag = new HashMap<>();
        List<Country> countries = new ArrayList<>();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 200, FRAME_WIDTH, FRAME_HEIGHT);

        getCountries(countryToCapital, countryToFlag, countries);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("First Task", new FirstPanel(countryToCapital, countryToFlag, countries));
        tabbedPane.addTab("Second Task", new SecondPanel(countryToFlag));

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public static void getCountries(Map<String, String> countryToCapital, Map<String, ImageIcon> countryToFlag, List<Country> countries) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("countryToCapital.txt"));

        String line;
        String[] countryAndCapital;

        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            countryAndCapital = line.split("[\",]");
            countryToCapital.put(countryAndCapital[1], countryAndCapital[4]);
        }

        Path folderPath = Paths.get("flags-all-world\\plain");
        File[] files = folderPath.toFile().listFiles();

        for (File file : files) {
            Country country = new Country(Paths.get(file.toString()));
            country.setCapital(countryToCapital.get(country.getName()));
            countryToFlag.put(country.getName(), country.getFlag());
            countries.add(country);
        }
    }
}
