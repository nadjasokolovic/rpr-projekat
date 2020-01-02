package ba.unsa.etf.rpr.projekat;

public interface IActivity {

    //metoda koja ce konkretnom korisniku produziti clanarinu, tj. azurirati pocetak i kraj clanarine
    void extendMembershipFee(User user);

    //metoda koja ce provjeravati da li je korisnik uplacivao clanarinu u pretkodna 3 mjeseca uzastopno
    boolean checkActivity(User user);

    //metoda koja ce dodavati korisniku termine gratis, tj. vrsiti azuriranje broja ukupnih termina
    void addFreeTrainings(User user);
}
