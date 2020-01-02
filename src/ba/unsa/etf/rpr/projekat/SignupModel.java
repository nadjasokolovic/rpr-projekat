package ba.unsa.etf.rpr.projekat;

public class SignupModel {

    private Validacija validacija;

    public Validacija getValidacija() {
        return validacija;
    }

    public void setValidacija(Validacija validacija) {
        this.validacija = validacija;
    }

    public SignupModel() {
        validacija = new Validacija();
    }

}
