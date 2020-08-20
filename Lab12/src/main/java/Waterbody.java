package main.java;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Waterbody implements Serializable {
    public Waterbody (String name, Type type, Property property, int value, List<Continent> continents) throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException("value should be greater than zero");
        }

        this.name = name;
        this.type = type;
        this.property = property;
        this.value = value;
        this.continents = new Continents(continents);
    }

    public Waterbody(String name, String type, String propertyName, int value, List<String> continentsNames) throws IllegalArgumentException {
        this(name, Type.valueOf(type), Property.valueOf(propertyName), value, continentsNames.stream().map(continentName -> Continent.valueOf(continentName)).collect(Collectors.toCollection(() -> new ArrayList<>())));
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Property getProperty() {
        return property;
    }

    public int getValue() {
        return value;
    }

    public Continents getContinents() {
        return continents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setContinents(Continents continents) {
        this.continents = continents;
    }

    public enum Type {
        Lake,
        Ocean,
        River,
        Sea,
        Waterfall
    }

    public enum Property {
        length,
        height,
        surfaceArea
    }

    public enum Continent {
        Africa,
        America,
        Antarctica,
        Asia,
        Australia,
        Europe
    }

    public static class Continents implements Serializable {
        public Continents(List<Continent> continentList) throws IllegalArgumentException {
            this.continentList = continentList;
        }

        public List<Continent> getContinentList() {
            return continentList;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(continentList.get(0).toString());

            for (int i = 1; i < continentList.size(); ++i) {
                builder.append(", " + continentList.get(i).toString());
            }

            return builder.toString();
        }

        private List<Continent> continentList;
    }

    private String name;
    private Type type;
    private Property property;
    private int value;
    private Continents continents;
}
