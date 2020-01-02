package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalTime;

public class Training {
    private SimpleObjectProperty<LocalTime> startOfTraining = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalTime> endOfTraining = new SimpleObjectProperty<>();
    private SimpleObjectProperty<User> user = new SimpleObjectProperty<>();

    public Training() {}

    public Training(LocalTime startOfTraining, LocalTime endOfTraining, User user) {
        this.startOfTraining = new SimpleObjectProperty<>(startOfTraining);
        this.endOfTraining = new SimpleObjectProperty<>(endOfTraining);
        this.user = new SimpleObjectProperty<>(user);
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

    //napraviti compareTo da poredi treninge po vremenu pocetka
}
