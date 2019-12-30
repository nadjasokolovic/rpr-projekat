package ba.unsa.etf.rpr.projekat;

public class AdminModel {
    Validacija validacija;

    public Validacija getValidacija() {
        return validacija;
    }

    public void setValidacija(Validacija validacija) {
        this.validacija = validacija;
    }

    public AdminModel() {
        validacija = new Validacija();
    }
}
