package ba.unsa.etf.rpr.projekat;

public class UserAccountModel {
    private Validacija validacija;

    public Validacija getValidacija() {
        return validacija;
    }

    public void setValidacija(Validacija validacija) {
        this.validacija = validacija;
    }

    public UserAccountModel() {
        validacija = new Validacija();
    }
}
