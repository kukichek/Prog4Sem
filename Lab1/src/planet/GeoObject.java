package planet;

import java.util.Objects;

abstract class GeoObject {
    protected String name;
    protected int area; // in km squared

    public GeoObject(String name, int area) {
        this.name = name;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public int getArea() {
        return area;
    }

    @Override
    public String toString() {
        return name + ", area = " + area + " km squared";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoObject)) return false;
        GeoObject geoObject = (GeoObject) o;
        return area == geoObject.area &&
                name.equals(geoObject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, area);
    }
}
