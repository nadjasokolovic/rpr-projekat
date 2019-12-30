package ba.unsa.etf.rpr.projekat;

public class ObjectModel {
    private Validacija validacija;

    public Validacija getValidacija() {
        return validacija;
    }

    public void setValidacija(Validacija validacija) {
        this.validacija = validacija;
    }

    public ObjectModel() {
        validacija = new Validacija();
    }
}
