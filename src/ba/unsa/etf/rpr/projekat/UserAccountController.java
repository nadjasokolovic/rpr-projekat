package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserAccountController {

    private String username;
    private int numberOfTrainings, numberOfTrainingsUsed;
    private ArrayList<String> obavijesti;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumberOfTrainings() {
        return numberOfTrainings;
    }

    public void setNumberOfTrainings(int numberOfTrainings) {
        this.numberOfTrainings = numberOfTrainings;
    }

    public int getNumberOfTrainingsUsed() {
        return numberOfTrainingsUsed;
    }

    public void setNumberOfTrainingsUsed(int numberOfTrainingsUsed) {
        this.numberOfTrainingsUsed = numberOfTrainingsUsed;
    }

    public ArrayList<String> getObavijesti() {
        return obavijesti;
    }

    public void setObavijesti(ArrayList<String> obavijesti) {
        this.obavijesti = obavijesti;
    }

    public Label usernameLabel;
    public Label iskoristenoTermina;
    public Label preostaloTermina;
    public ListView notificationsList;

    FitpassDAO dao = FitpassDAO.getInstance();

    public UserAccountController(FitpassDAO dao) {
        this.dao = dao;
    }

    public void editProfil(ActionEvent actionEvent) {
        FitpassDAO dao = FitpassDAO.getInstance();
        EditProfilController ctrl = new EditProfilController(dao);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editprofil.fxml"), bundle);
        loader.setController(ctrl);
        //prosljedjivanje korisnika u EditProfilCOntroller
        ctrl.setUser(this.getUsername());
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

    public void rateObject(ActionEvent actionEvent) {
        FitpassDAO dao = FitpassDAO.getInstance();
        RateObjectController ctrl = new RateObjectController(dao);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/rateObject.fxml"), bundle);
        loader.setController(ctrl);
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

    @FXML
    public void initialize() {
        this.usernameLabel.setText(this.getUsername());
        this.iskoristenoTermina.setText(Integer.toString(this.getNumberOfTrainingsUsed()));
        this.preostaloTermina.setText(Integer.toString(this.getNumberOfTrainings()));
        notificationsList.setItems(FXCollections.observableArrayList(this.getObavijesti()));
    }
}
