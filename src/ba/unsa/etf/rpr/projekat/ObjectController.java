package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class ObjectController {

    public TextField objectRatingFld;
    public TableView personTableView;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public Button reserveBtn;

    FitpassDAO dao = FitpassDAO.getInstance();

    public ObjectController(FitpassDAO dao) {
        this.dao = dao;
    }

    @FXML
    public void initialize() {
        objectRatingFld.textProperty().addListener((obs, oldRate, newRate) -> {
            if (dao.getValidation().validateGrade(newRate)) {
                objectRatingFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectRatingFld.getStyleClass().add("poljeIspravno");
            } else {
                objectRatingFld.getStyleClass().removeAll("poljeIspravno");
                objectRatingFld.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void makeReservation(ActionEvent actionEvent) {
        //treba upisati rezervaciju u bazu i otvorit alert na kojem pise uspjesno ste rezervisali
        //korisnik je odustao od uredjivanja profila i nema potrebe za upisivanjem u bazu
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
