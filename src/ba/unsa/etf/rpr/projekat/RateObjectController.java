package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class RateObjectController {
    private Validation validation = new Validation();

    public ChoiceBox objectChoice;
    public Button exitBtn;
    public TextField objectRateFld;
    public Button confirmBtn;

    FitpassDAO dao = FitpassDAO.getInstance();

    public RateObjectController(FitpassDAO dao) {
        this.dao = dao;
    }


    @FXML
    public void initialize() {
        objectChoice.setItems(FXCollections.observableArrayList(dao.getAllObjects()));

        objectRateFld.textProperty().addListener((obs, oldRate, newRate) -> {
            if (dao.getValidation().validateGrade(newRate)) {
                objectRateFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectRateFld.getStyleClass().add("poljeIspravno");
            } else {
                objectRateFld.getStyleClass().removeAll("poljeIspravno");
                objectRateFld.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void closeWindow(ActionEvent actionEvent) {
        //korisnik je odustao od ocjenjivanja objekta i nema potrebe za upisivanjem u bazu
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void rateObject(ActionEvent actionEvent) {
        //upisivanje nove ocjene u bazu

        //prvo provjera da li je izabran konkretan objekat
        if(objectChoice.getSelectionModel().getSelectedItem() != null) {
            Object object = (Object)objectChoice.getSelectionModel().getSelectedItem();
            if(!validation.validateGrade(objectRateFld.getText()))
                throw new IllegalRateException("Neispravna cjena");

            //u suprotnom, ako se iuzetak nije desio, potrebno je dodati ocjenu za izabrani objekat
            dao.addObjectRate(object.getId(), Integer.parseInt(objectRateFld.getText()));
        }


        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void goToProfil(ActionEvent actionEvent) {
        FitpassDAO dao = FitpassDAO.getInstance();
        UserAccountController ctrl = new UserAccountController(dao);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userAccount.fxml"), bundle);
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
}
