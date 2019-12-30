package ba.unsa.etf.rpr.projekat;

public class RateObjectModel {
    private Validacija validacija;

    public Validacija getValidacija() {
        return validacija;
    }

    public void setValidacija(Validacija validacija) {
        this.validacija = validacija;
    }

    public RateObjectModel() {
        validacija = new Validacija();
    }
}
