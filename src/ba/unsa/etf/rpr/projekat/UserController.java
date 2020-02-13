package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserController {

    public ListView objectsList;
    public ChoiceBox disciplineChoice;
    public SimpleObjectProperty<Object> selectedObject = new SimpleObjectProperty<>();

    FitpassDAO dao = FitpassDAO.getInstance();

    public UserController(FitpassDAO dao) {
        this.dao = dao;
    }

    private String username, pasword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    @FXML
    public void initialize() {
        disciplineChoice.setItems(FXCollections.observableArrayList(dao.getAllDisciplines()));
        //selectedObject.set(null);

        disciplineChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                ArrayList<Object> objects = dao.getObjectsForDiscipline(((Discipline)disciplineChoice.getItems().get((Integer) number2)).getId());
                objectsList.setItems(FXCollections.observableArrayList(objects));
            }
        });

        //postavljanje trenutnog objekta
        objectsList.getSelectionModel().selectedItemProperty().addListener((obs, oldObject, newObject) -> {
            selectedObject.set((Object)objectsList.getSelectionModel().getSelectedItem());
        });
    }

    public void goToProfil(ActionEvent actionEvent) {
        FitpassDAO dao = FitpassDAO.getInstance();
        UserAccountController ctrl = new UserAccountController(dao);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userAccount.fxml"), bundle);
        loader.setController(ctrl);
        sendData(ctrl);
        Stage myStage = new Stage();
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myStage.setTitle("Fitpass Sarajevo");
        myStage.setScene(new Scene(root, 700, 500));

        myStage.show();
    }

    private void sendData(UserAccountController ctrl) {
        //prvo na osnovu username koji je unique iz baze ucitavati id osobe
        int personId = dao.getIdForUsername(this.getUsername());
        //potrebno je pronaci idKorisnika na osnovu id osobe
        int userId = dao.getUserIdForPersonId(personId);

        //iz tabele korisnik dobijamo podatke o broju iskoristenih termina i broju preostalih
        int brojIskoristenihTermina = dao.getNumberTerminsUsed(userId);
        //iz baze ucitavamo ukupan broj termina da bismo dobili broj preostalih
        int ukupnoTermina = dao.getNumberOfTermins(userId);
        //iz baze ucitavamo sve obavijesti za korisnika
        ArrayList<String> obavijesti = dao.getNotifications(userId);

        //slanje ovih podataka u userAccountController
        ctrl.setUsername(this.getUsername());
        ctrl.setNumberOfTrainings(ukupnoTermina - brojIskoristenihTermina);
        ctrl.setNumberOfTrainingsUsed(brojIskoristenihTermina);
        ctrl.setNotifications(obavijesti);
    }

    private void sendDataToObjectController(ObjectController ctrl){
        if(selectedObject != null){
            //potrebno je naziv objekta i njegovu prosjecnu ocjenu poslati u ObjectController
            ctrl.setObjectName(selectedObject.getValue().getName());
            ctrl.setObjectRate(Double.toString(dao.averageRate(selectedObject.getValue().getId())));
        }
        //postavljanje podataka o trenutno prijavljenom korisniku
        ctrl.setUsername(username);
        ctrl.setPassword(pasword);
    }

    public void openObjectController(ActionEvent actionEvent) {
        FitpassDAO dao = FitpassDAO.getInstance();
        ObjectController ctrl = new ObjectController(dao);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/object.fxml"), bundle);
        loader.setController(ctrl);
        sendDataToObjectController(ctrl);
        Stage myStage = new Stage();
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myStage.setTitle("Fitpass Sarajevo");
        myStage.setScene(new Scene(root, 700, 500));
        myStage.show();    }
}
