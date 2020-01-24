package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.TreeSet;

public class Object {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty municipality = new SimpleStringProperty(); //moci ce se sortirati objekti po opcinama
    private SimpleStringProperty adress = new SimpleStringProperty();
    private SimpleDoubleProperty averageRate = new SimpleDoubleProperty();
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private ObservableSet<Training> trainingSchedule = FXCollections.observableSet();
    //treninzi ce biti sortirani po vremenu pocetka od onog sto najranije pocinje do onog koji najkasnije pocinje, MOZDA NECE
    //u ovaj skup ce se ucitavati iz baze treninzi za konkretan dan

    private int[] gradesFromUsers = new int[10000];
    private int numberOfGrades = 0;

    public Object() {}

    public Object(int id, String name, String municipality, String adress, Double averageRate) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.municipality = new SimpleStringProperty(municipality);
        this.adress = new SimpleStringProperty(adress);
        this.averageRate = new SimpleDoubleProperty(averageRate);
        //bit ce potrebno citati termine iz baze i dodavati ih jedan po jedan u mapu
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

    public double getAverageRate() {
        return averageRate.get();
    }

    public SimpleDoubleProperty averageRateProperty() {
        return averageRate;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate.set(averageRate);
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

    //uraditi hashCode


    @Override
    public String toString() {
        return this.getName();
    }
}
