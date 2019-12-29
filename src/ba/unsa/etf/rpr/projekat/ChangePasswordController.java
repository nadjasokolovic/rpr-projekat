package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordController {

    ChangePasswordModel model = new ChangePasswordModel();

    public ChangePasswordController(ChangePasswordModel model) {
        this.model = model;
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

    public void rememberNewPassword(ActionEvent actionEvent) {
        //azurirati password korisniku
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
