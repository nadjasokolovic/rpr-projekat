package ba.unsa.etf.rpr.projekat;

public class UserModel {
    private Validacija validacija;

    public Validacija getValidacija() {
        return validacija;
    }

    public void setValidacija(Validacija validacija) {
        this.validacija = validacija;
    }

    public UserModel() {
        validacija = new Validacija();
    }
}
