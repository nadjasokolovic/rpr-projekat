package ba.unsa.etf.rpr.projekat;

public interface IReservation {

    //metoda koja trening oznacava kao rezervisan u bazi podataka
    void makeTrainingReservarion(Training training);

    //metoda koja upisuje novu ocjenu u niz ocjena
    void rateObject(int ocjena);
}
