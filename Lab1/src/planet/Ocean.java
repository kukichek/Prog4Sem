package planet;

import java.util.Objects;

public class Ocean extends GeoObject{
    private double maxDepth; // in km

    public Ocean(String name, int area, double maxDepth) {
        super(name, area);
        this.maxDepth = maxDepth;
    }

    public double getMaxDepth() {
        return maxDepth;
    }

    @Override
    public String toString() {
        return "Ocean " + super.toString() + ", max depth = " + maxDepth + " km";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ocean)) return false;
        if (!super.equals(o)) return false;
        Ocean ocean = (Ocean) o;
        return Double.compare(ocean.maxDepth, maxDepth) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxDepth);
    }
}
