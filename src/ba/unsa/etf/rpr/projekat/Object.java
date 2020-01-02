package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import java.util.TreeSet;

public class Object {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty location = new SimpleStringProperty();
    private ObservableMap<String, TreeSet<Training>> trainingSchedule = FXCollections.observableHashMap();
    //treninzi ce biti sortirani po vremenu pocetka od onog sto najranije pocinje do onog koji najkasnije pocinje

    private int[] gradesFromUsers = new int[10000];
    private int numberOfGrades = 0;

    public Object() {}

    public Object(String name, String location) {
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleStringProperty(location);
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

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public ObservableMap<String, TreeSet<Training>> getTrainingSchedule() {
        return trainingSchedule;
    }

    public void setTrainingSchedule(ObservableMap<String, TreeSet<Training>> trainingSchedule) {
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

    //uraditi hashCode
}
