package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserAccountController {
    UserAccountModel model;

    public UserAccountController(UserAccountModel model) {
        this.model = model;
    }

    public void editProfil(ActionEvent actionEvent) {
        SignupModel model = new SignupModel();
        SignupController ctrl = new SignupController(model);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
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

    public void rateObject(ActionEvent actionEvent) {
        RateObjectModel model = new RateObjectModel();
        RateObjectController ctrl = new RateObjectController(model);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/rateObject.fxml"));
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
