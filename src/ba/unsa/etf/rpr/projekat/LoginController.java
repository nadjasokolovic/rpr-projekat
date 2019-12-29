package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    public CheckBox adminCheckbox;
    public TextField usernameFld;
    public TextField passwordFld;
    public Hyperlink changePasswordUrl;
    public Button loginBtn;
    public Button signupBtn;

    private LoginModel model;

    public LoginController(LoginModel model) {
        this.model = model;
    }


    @FXML
    public void initialize() {
        usernameFld.textProperty().addListener((obs, oldUsername, newUsername) -> {
            if (model.validirajUsername(newUsername)) {
                usernameFld.getStyleClass().removeAll("poljeNijeIspravno");
                usernameFld.getStyleClass().add("poljeIspravno");
            } else {
                usernameFld.getStyleClass().removeAll("poljeIspravno");
                usernameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        passwordFld.textProperty().addListener((obs, oldPasword, newPassword) -> {
            if (model.validirajPassword(newPassword)) {
                passwordFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordFld.getStyleClass().add("poljeIspravno");
            } else {
                passwordFld.getStyleClass().removeAll("poljeIspravno");
                passwordFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

    }

        public void loginAction(ActionEvent actionEvent) {
        Stage myStage = new Stage();
        //prilikom klika na dugme potrebno je provjeriti da li username i password pripadaju korisniku
        if(model.validiraj(usernameFld.getText(), passwordFld.getText())) {
            try {
                if (adminCheckbox.isSelected()) {
                    AdminModel model = new AdminModel();
                    AdminController ctrl = new AdminController(model);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin.fxml"));
                    loader.setController(ctrl);
                    Parent root = loader.load();
                    myStage.setTitle("Fitpass Sarajevo");
                    myStage.setScene(new Scene(root, 700, 500));
                    myStage.show();
                }
                else {
                    UserModel model = new UserModel();
                    UserController ctrl = new UserController(model);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
                    loader.setController(ctrl);
                    Parent root = loader.load();
                    myStage.setTitle("Fitpass Sarajevo");
                    myStage.setScene(new Scene(root, 700, 500));
                    myStage.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška!");
            alert.setHeaderText("Pogrešni pristupni podaci!");
            alert.setContentText("Ponovite Vaš unos.");
            alert.showAndWait();

            //restartujemo polja za unos
            usernameFld.setText("");
            passwordFld.setText("");
        }
    }

    public void signUpAction(ActionEvent actionEvent) {
        Stage myStage = new Stage();
        SignupModel model = new SignupModel();
        SignupController ctrl = new SignupController(model);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
        loader.setController(ctrl);
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

    public void changePassword(ActionEvent actionEvent) {
        Stage myStage = new Stage();
        ChangePasswordModel model = new ChangePasswordModel();
        ChangePasswordController ctrl = new ChangePasswordController(model);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/changePassword.fxml"));
        loader.setController(ctrl);
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
