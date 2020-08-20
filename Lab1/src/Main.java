import org.w3c.dom.ls.LSOutput;
import planet.Continent;
import planet.Isle;
import planet.Ocean;
import planet.Planet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Isle cyprus = new Isle("Кипр", 1);
        Continent america = new Continent("Америка", 10, List.of());
        Continent eurasia = new Continent("Евразия", 13, List.of(cyprus));
        Ocean azov = new Ocean("Азовский", 8, 0.0135);
        Ocean calm = new Ocean("Тихий", 17, 11.024);

        Planet anthea = new Planet("Антея", List.of(america, eurasia), List.of(azov, calm));
        Planet venus = new Planet("Венера", List.of(america, eurasia), List.of(azov, calm));
        System.out.println(anthea);
        System.out.println();

        if (anthea.equals(venus)) {
            System.out.println("Planet " + anthea.getName() + " is equal to planet " + venus.getName());
        } else {
            System.out.println("Planet " + anthea.getName() + " is not equal to planet " + venus.getName());
        }

        System.out.println("Planet " + anthea.getName() + " has " + anthea.getContinentAmount() + " continents");
        System.out.println();

        Continent[] continents = new Continent[4];
        for (int i = 0; i < 4; ++i) {
            continents[i] = new Continent("Континент" + (i + 1), (int) Math.round(Math.random() * 100));
        }

        Ocean[] oceans = new Ocean[3];
        for (int i = 0; i < 3; ++i) {
            oceans[i] = new Ocean("Океан" + (i + 1), (int) Math.round(Math.random() * 100), Math.round(Math.random() * 100));
        }

        Planet planet = new Planet("Планета", Arrays.asList(continents), Arrays.asList(oceans));
        System.out.println(planet);
        System.out.println();

        try {
            System.out.println(venus.getContinentName(6));
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("There is no continent of that index");
        }
    }
}
