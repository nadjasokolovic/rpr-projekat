package ba.unsa.etf.rpr.projekat;

public class LoginModel {

    public LoginModel() {}

    //validacije
    public boolean validirajUsername(String newUsername) {
        //validan username mora imati barem 4 znaka
        if(newUsername.length() < 4) return false;
        //validan username moze sadrzavati samo velika i mala slova i cifre 0-9
        for(char c : newUsername.toCharArray())
            if(!Character.isLetter(c) && !Character.isDigit(c))
                return false;

        return true;
    }

    public boolean validirajPassword(String newPassword) {
        //validan password mora imati barem 4 znaka
        if(newPassword.length() < 4) return false;

        return true;
    }

    public boolean validiraj(String username, String password) {
        if(username.length() == 0 || password.length() == 0)
            return false;
        //ova metoda ce ucitavati da li username i password postoje u bazi
        //ako postoje provjeravat ce da li pripadaju istom korisniku
        return true;
    }
}
