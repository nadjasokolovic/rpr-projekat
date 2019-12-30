package ba.unsa.etf.rpr.projekat;

public class LoginModel {

    private Validacija validacija;

    public LoginModel() {
        validacija = new Validacija();
    }

    public Validacija getValidacija() {
        return validacija;
    }

    public void setValidacija(Validacija validacija) {
        this.validacija = validacija;
    }

    public boolean validiraj(String username, String password) {
        if(username.length() == 0 || password.length() == 0)
            return false;
        //ova metoda ce ucitavati da li username i password postoje u bazi
        //ako postoje provjeravat ce da li pripadaju istom korisniku
        return true;
    }
}
