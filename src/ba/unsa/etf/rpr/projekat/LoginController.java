package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    public CheckBox adminCheckbox;
    public TextField usernameFld;
    public TextField passwordFld;
    public Hyperlink changePasswordUrl;
    public Button loginBtn;
    public Button signupBtn;

    public void loginAction(ActionEvent actionEvent) {
        Parent root = null;
        Stage myStage = new Stage();
        try {
            if(adminCheckbox.isSelected())
                root = FXMLLoader.load(getClass().getResource("/fxml/admin.fxml"));
            else
                root = FXMLLoader.load(getClass().getResource("/fxml/user.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myStage.setTitle("Fitpass Sarajevo");
        myStage.setScene(new Scene(root, 700, 500));
        myStage.show();

    }

    public void signUpAction(ActionEvent actionEvent) {
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
}
