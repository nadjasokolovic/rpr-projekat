package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserAccountController {
    FitpassDAO dao = FitpassDAO.getInstance();

    public UserAccountController(FitpassDAO dao) {
        this.dao = dao;
    }

    public void editProfil(ActionEvent actionEvent) {
        FitpassDAO dao = FitpassDAO.getInstance();
        SignupController ctrl = new SignupController(dao);

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
        FitpassDAO dao = FitpassDAO.getInstance();
        RateObjectController ctrl = new RateObjectController(dao);

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
