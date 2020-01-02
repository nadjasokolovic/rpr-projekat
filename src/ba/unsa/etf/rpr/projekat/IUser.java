package ba.unsa.etf.rpr.projekat;

public interface IUser {

    //metoda koja dodaje novog korisnika u bazu podataka
    void addUser();

    //metoda koja vrsi izmjene nad postojecim korisnikom
    void editUser();

    //metoda koja brise nekog korisnika iz baze
    void deleteUser();
}
