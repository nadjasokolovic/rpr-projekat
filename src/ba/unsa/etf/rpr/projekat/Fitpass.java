package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.*;

public class Fitpass {
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableSet<Object> objects = FXCollections.observableSet();
    private ObservableMap<String, HashSet<Object>> disciplines = FXCollections.observableHashMap(); //kljuc: naziv discipline, vrijednost:skup objekata u kojima postoje treninzi te discipline
    private ObservableList<Training> trainings = FXCollections.observableArrayList();

    public Fitpass() {}

    public Fitpass(ArrayList<User> users, HashSet<Object> objects, HashMap<String, HashSet<Object>> disciplines, ArrayList<Training> trainings) {
        this.users = FXCollections.observableArrayList(users);
        this.objects = FXCollections.observableSet(objects);
        //this.disciplines = FXCollections.observableHashMap(disciplines);
        //kreirat ce se prazna ova mapa disciplina, pa ce se dodavati u nju jedan po jedan kako se citaju iz baze
        this.trainings = FXCollections.observableArrayList(trainings);
    }
}
