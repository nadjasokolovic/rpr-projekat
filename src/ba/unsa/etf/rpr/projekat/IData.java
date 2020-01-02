package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;

public interface IData {

    //metode koje ucitavaju sve korisnike, objekte, discipline i rezervisane treninge iz baze
    ArrayList<User> allUsers();
    ArrayList<Object> allObjects();
    ArrayList<String> addDisciplines();
    ArrayList<Training> allTrainings();

}
