package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginController {

    public CheckBox adminCheckbox;
    public TextField usernameFld;
    public TextField passwordFld;
    public Hyperlink changePasswordUrl;
    public Button loginBtn;
    public Button signupBtn;
    public Button bsBtn;
    public Button usBtn;

    FitpassDAO dao = FitpassDAO.getInstance();

    public LoginController(FitpassDAO dao) {
        this.dao = dao;
    }


    @FXML
    public void initialize() {
        usernameFld.textProperty().addListener((obs, oldUsername, newUsername) -> {
            if (dao.getValidation().validateUsername(newUsername)) {
                usernameFld.getStyleClass().removeAll("poljeNijeIspravno");
                usernameFld.getStyleClass().add("poljeIspravno");
            } else {
                usernameFld.getStyleClass().removeAll("poljeIspravno");
                usernameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        passwordFld.textProperty().addListener((obs, oldPasword, newPassword) -> {
            if (dao.getValidation().validatePassword(newPassword)) {
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
        if(dao.checkUser(usernameFld.getText(), passwordFld.getText())) {
            try {
                if (adminCheckbox.isSelected()) {
                    FitpassDAO dao = FitpassDAO.getInstance();
                    AdminController ctrl = new AdminController(dao);

                    ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin.fxml"), bundle);
                    loader.setController(ctrl);
                    Parent root = loader.load();
                    myStage.setTitle("Fitpass Sarajevo");
                    myStage.setScene(new Scene(root, 700, 500));
                    myStage.show();
                }
                else {
                    FitpassDAO dao = FitpassDAO.getInstance();
                    UserController ctrl = new UserController(dao);
                    ResourceBundle bundle = ResourceBundle.getBundle("Translation");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"), bundle);
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
        FitpassDAO dao = FitpassDAO.getInstance();
        SignupController ctrl = new SignupController(dao);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"), bundle);
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
        FitpassDAO dao = FitpassDAO.getInstance();
        ChangePasswordController ctrl = new ChangePasswordController(dao);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/changePassword.fxml"), bundle);
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

    public void setBosnian(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("bs", "BA"));
        Stage myStage = (Stage) usernameFld.getScene().getWindow();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_bs");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        loader.setController(new LoginController(this.dao));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myStage.setScene(new Scene(root, PopupControl.USE_COMPUTED_SIZE, PopupControl.USE_COMPUTED_SIZE));
        myStage.show();
    }

    public void setEnglish(ActionEvent actionEvent) {
        Locale.setDefault(new Locale("en", "US"));
        Stage myStage = (Stage) usernameFld.getScene().getWindow();
        ResourceBundle bundle = ResourceBundle.getBundle("Translation_en_US");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        loader.setController(new LoginController(this.dao));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myStage.setScene(new Scene(root, PopupControl.USE_COMPUTED_SIZE, PopupControl.USE_COMPUTED_SIZE));
        myStage.show();
    }
}
