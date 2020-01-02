package ba.unsa.etf.rpr.projekat;

public interface IAccount {

    //metoda koja ce upisivati novog korisnika u bazu podataka
    void createAccount();

    //metoda koja ce vrsiti izmjenu za nekog vec postojeceg korisnika u bazi
    void editAccount();
}
