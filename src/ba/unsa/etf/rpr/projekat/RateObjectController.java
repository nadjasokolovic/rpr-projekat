package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RateObjectController {
    RateObjectModel model;

    public RateObjectController(RateObjectModel model) {
        this.model = model;
    }

    public void closeWindow(ActionEvent actionEvent) {
        //korisnik je odustao od ocjenjivanja objekta i nema potrebe za upisivanjem u bazu
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void rateObject(ActionEvent actionEvent) {
        //upisati novu ocjenu u bazu

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void goToProfil(ActionEvent actionEvent) {
        UserAccountModel model = new UserAccountModel();
        UserAccountController ctrl = new UserAccountController(model);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userAccount.fxml"));
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
