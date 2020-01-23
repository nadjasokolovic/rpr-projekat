package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

public class User extends Person {

    private enum Months {
        JANUAR(),
        FEBRUAR(),
        MART(),
        APRIL(),
        MAJ(),
        JUNI(),
        JULI(),
        AUGUST(),
        SEPTEMBAR(),
        OKTOBAR(),
        NOVEMBAR(),
        DECEMBAR();

        private boolean paidMembershipFee;

        Months() {
            paidMembershipFee = false;
        }

        public boolean isPaidMembershipFee() {
            return paidMembershipFee;
        }

        public void setPaidMembershipFee(boolean paidMembershipFee) {
            this.paidMembershipFee = paidMembershipFee;
        }

        //moci ce se pristupati svakom mjesecu sa npr. Months.JANUAR.setPaidMembershipFee(true)
    }

    private ObservableList<String> notifications = FXCollections.observableArrayList();
    private SimpleObjectProperty<LocalDate> startOfMembershipFee = new SimpleObjectProperty<>();
    private SimpleObjectProperty<LocalDate> endOfMembershipFee = new SimpleObjectProperty<>();
    private SimpleIntegerProperty totalNumberOfTrainings = new SimpleIntegerProperty();
    private SimpleIntegerProperty numberOfUsedTrainings = new SimpleIntegerProperty();
    private ObservableList<Months> activity = FXCollections.observableArrayList();

    public User(String name, String surname, String username, String password, ArrayList<String> notifications, LocalDate startOfMembershipFee, LocalDate endOfMembershipFee, int totalNumberOfTrainings, int numberOfUsedTrainings, ArrayList<Months> activity) {
        super(name, surname, username, password);
        this.notifications = FXCollections.observableArrayList(notifications);
        this.startOfMembershipFee = new SimpleObjectProperty<>(startOfMembershipFee);
        this.endOfMembershipFee = new SimpleObjectProperty<>(endOfMembershipFee);
        this.totalNumberOfTrainings = new SimpleIntegerProperty(totalNumberOfTrainings);
        this.numberOfUsedTrainings = new SimpleIntegerProperty(numberOfUsedTrainings);
        this.activity = FXCollections.observableArrayList(activity);

    }

    public User(){}

    public User(String name, String surname, String username, String password) {
        super(name, surname, username, password);
    }

    public ObservableList<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(ObservableList<String> notifications) {
        this.notifications = notifications;
    }

    public LocalDate getStartOfMembershipFee() {
        return startOfMembershipFee.get();
    }

    public SimpleObjectProperty<LocalDate> startOfMembershipFeeProperty() {
        return startOfMembershipFee;
    }

    public void setStartOfMembershipFee(LocalDate startOfMembershipFee) {
        this.startOfMembershipFee.set(startOfMembershipFee);
    }

    public LocalDate getEndOfMembershipFee() {
        return endOfMembershipFee.get();
    }

    public SimpleObjectProperty<LocalDate> endOfMembershipFeeProperty() {
        return endOfMembershipFee;
    }

    public void setEndOfMembershipFee(LocalDate endOfMembershipFee) {
        this.endOfMembershipFee.set(endOfMembershipFee);
    }

    public int getTotalNumberOfTrainings() {
        return totalNumberOfTrainings.get();
    }

    public SimpleIntegerProperty totalNumberOfTrainingsProperty() {
        return totalNumberOfTrainings;
    }

    public void setTotalNumberOfTrainings(int totalNumberOfTrainings) {
        this.totalNumberOfTrainings.set(totalNumberOfTrainings);
    }

    public int getNumberOfUsedTrainings() {
        return numberOfUsedTrainings.get();
    }

    public SimpleIntegerProperty numberOfUsedTrainingsProperty() {
        return numberOfUsedTrainings;
    }

    public void setNumberOfUsedTrainings(int numberOfUsedTrainings) {
        this.numberOfUsedTrainings.set(numberOfUsedTrainings);
    }

    public ObservableList<Months> getActivity() {
        return activity;
    }

    public void setActivity(ObservableList<Months> activity) {
        this.activity = activity;
    }


}
