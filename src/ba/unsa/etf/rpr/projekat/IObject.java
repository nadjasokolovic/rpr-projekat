package ba.unsa.etf.rpr.projekat;

public interface IObject {

    //metoda koja dodaje novi objekat u bazu podataka
    void addObject();

    //metoda koja vrsi izmjene nad postojecim objektom u bazi
    void editObject();

    //metoda koja brise neki objekat iz baze
    void deleteObject();
}
