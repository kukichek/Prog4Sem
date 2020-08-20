package planet;

import java.util.List;
import java.util.Objects;

public class Planet {
    private String name;
    private List<Continent> continents;
    private List<Ocean> oceans;

    public Planet(String name, List<Continent> continents, List<Ocean> oceans) {
        this.name = name;
        this.continents = continents;
        this.oceans = oceans;
    }

    public String getName() {
        return name;
    }

    public String getContinentName(int index) throws IndexOutOfBoundsException {
        return continents.get(index).getName();
    }

    public int getContinentAmount() {
        return continents.size();
    }

    @Override
    public String toString() {
        return "Planet " + name +
                ", \ncontinents=" + continents +
                ", \noceans=" + oceans;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return Objects.equals(name, planet.name) &&
                Objects.equals(continents, planet.continents) &&
                Objects.equals(oceans, planet.oceans);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, continents, oceans);
    }
}
