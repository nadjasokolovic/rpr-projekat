package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Object {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty municipality = new SimpleStringProperty(); //moci ce se sortirati objekti po opcinama
    private SimpleStringProperty adress = new SimpleStringProperty();
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private ObservableSet<Training> trainingSchedule = FXCollections.observableSet();
    private ObservableList<Discipline> disciplinesInObject = FXCollections.observableArrayList();

    private int[] gradesFromUsers = new int[10000];
    private int numberOfGrades = 0;

    public Object() {}

    public Object(int id, String name, String municipality, String adress) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.municipality = new SimpleStringProperty(municipality);
        this.adress = new SimpleStringProperty(adress);
        //bit ce potrebno citati termine iz baze i dodavati ih jedan po jedan u mapu
        //isto to i za discipline
    }

    public Object(int id, String name, String municipality, String adress, Set<Training> trainings, ArrayList<Discipline> disciplines) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.municipality = new SimpleStringProperty(municipality);
        this.adress = new SimpleStringProperty(adress);
        this.trainingSchedule = FXCollections.observableSet(trainings);
        this.disciplinesInObject = FXCollections.observableArrayList(disciplines);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMunicipality() {
        return municipality.get();
    }

    public SimpleStringProperty municipalityProperty() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality.set(municipality);
    }

    public String getAdress() {
        return adress.get();
    }

    public SimpleStringProperty adressProperty() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress.set(adress);
    }

    public ObservableSet<Training> getTrainingSchedule() {
        return trainingSchedule;
    }

    public void setTrainingSchedule(ObservableSet<Training> trainingSchedule) {
        this.trainingSchedule = trainingSchedule;
    }

    public int[] getGradesFromUsers() {
        return gradesFromUsers;
    }

    public void setGradesFromUsers(int[] gradesFromUsers) {
        this.gradesFromUsers = gradesFromUsers;
    }

    public int getNumberOfGrades() {
        return numberOfGrades;
    }

    public void setNumberOfGrades(int numberOfGrades) {
        this.numberOfGrades = numberOfGrades;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
