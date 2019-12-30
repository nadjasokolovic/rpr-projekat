package ba.unsa.etf.rpr.projekat;

public class ChangePasswordModel {

    private Validacija validacija;

    public Validacija getValidacija() {
        return validacija;
    }

    public void setValidacija(Validacija validacija) {
        this.validacija = validacija;
    }

    public ChangePasswordModel() {
        validacija = new Validacija();
    }
}
