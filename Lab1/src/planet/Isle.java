package planet;

public class Isle extends GeoObject{
    public Isle(String name, int area) {
        super(name, area);
    }

    @Override
    public String toString() {
        return "Isle " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && this.getClass().equals(o.getClass());
    }
}
