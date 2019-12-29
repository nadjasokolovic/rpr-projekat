package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SignupController {
    SignupModel model;

    public SignupController(SignupModel model) {
        this.model = model;
    }

    public void createAccount(ActionEvent actionEvent) {
        //upisati novog korisnika u bazu

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();

    }

    public void closeWindow(ActionEvent actionEvent) {
        //korisnik je odustao od uredjivanja profila i nema potrebe za upisivanjem u bazu
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
