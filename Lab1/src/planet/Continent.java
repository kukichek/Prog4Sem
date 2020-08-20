package planet;

import java.util.List;
import java.util.Objects;

public class Continent extends GeoObject{
    List<Isle> isles;

    public Continent(String name, int area) {
        super(name, area);
    }

    public Continent(String name, int area, List<Isle> isles) {
        super(name, area);
        this.isles = isles;
    }

    @Override
    public String toString() {
        if (isles != null && !isles.isEmpty() ) {
            return "Continent " + super.toString() +
                    ", isles=" + isles;
        } else {
            return "Continent " + super.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Continent continent = (Continent) o;
        return Objects.equals(isles, continent.isles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isles);
    }
}
