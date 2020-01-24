package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalTime;

public class Training {
    private SimpleObjectProperty<LocalTime> startOfTraining = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalTime> endOfTraining = new SimpleObjectProperty<>();
    private SimpleObjectProperty<User> user = new SimpleObjectProperty<>();
    private SimpleStringProperty day = new SimpleStringProperty();
    private SimpleIntegerProperty id = new SimpleIntegerProperty();

    public Training() {}

    public Training(int id, LocalTime startOfTraining, LocalTime endOfTraining, User user, String day) {
        this.id = new SimpleIntegerProperty(id);
        this.startOfTraining = new SimpleObjectProperty<>(startOfTraining);
        this.endOfTraining = new SimpleObjectProperty<>(endOfTraining);
        this.user = new SimpleObjectProperty<>(user);
        this.day = new SimpleStringProperty(day);
    }

    public LocalTime getStartOfTraining() {
        return startOfTraining.get();
    }

    public SimpleObjectProperty<LocalTime> startOfTrainingProperty() {
        return startOfTraining;
    }

    public void setStartOfTraining(LocalTime startOfTraining) {
        this.startOfTraining.set(startOfTraining);
    }

    public LocalTime getEndOfTraining() {
        return endOfTraining.get();
    }

    public SimpleObjectProperty<LocalTime> endOfTrainingProperty() {
        return endOfTraining;
    }

    public void setEndOfTraining(LocalTime endOfTraining) {
        this.endOfTraining.set(endOfTraining);
    }

    public User getUser() {
        return user.get();
    }

    public SimpleObjectProperty<User> userProperty() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public String getDay() { return day.get();
    }

    public SimpleStringProperty dayProperty() { return day;}

    public void setDay(String day) { this.day.set(day);}

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    //napraviti compareTo da poredi treninge po vremenu pocetka
}
