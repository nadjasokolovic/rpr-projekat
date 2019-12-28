package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserAccountController {
    public void editProfil(ActionEvent actionEvent) {
        Parent root = null;
        Stage myStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/signup.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myStage.setTitle("Fitpass Sarajevo");
        myStage.setScene(new Scene(root, 700, 500));
        myStage.show();
    }

    public void rateObject(ActionEvent actionEvent) {
        Parent root = null;
        Stage myStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/rateObject.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myStage.setTitle("Fitpass Sarajevo");
        myStage.setScene(new Scene(root, 700, 500));
        myStage.show();
    }
}
