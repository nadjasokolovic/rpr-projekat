package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class ChangePasswordController {

    public PasswordField passwordFld;
    public PasswordField passwordRepeatFld;
    public Button confirmBtn;

    FitpassDAO dao = FitpassDAO.getInstance();

    public ChangePasswordController(FitpassDAO dao) {
        this.dao = dao;
    }

    @FXML
    public void initialize() {
        passwordFld.textProperty().addListener((obs, oldPassword, newPassword) -> {
            if (dao.getValidation().validatePassword(newPassword) && newPassword.equals(passwordRepeatFld.getText())) {
                passwordFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordFld.getStyleClass().add("poljeIspravno");
                passwordRepeatFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordRepeatFld.getStyleClass().add("poljeIspravno");
            } else {
                passwordFld.getStyleClass().removeAll("poljeIspravno");
                passwordFld.getStyleClass().add("poljeNijeIspravno");
                passwordRepeatFld.getStyleClass().removeAll("poljeIspravno");
                passwordRepeatFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        passwordRepeatFld.textProperty().addListener((obs, oldPasswordRepeat, newPasswordRepeat) -> {
            if (dao.getValidation().validatePassword(newPasswordRepeat) && newPasswordRepeat.equals(passwordFld.getText())) {
                passwordRepeatFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordRepeatFld.getStyleClass().add("poljeIspravno");
                passwordFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordFld.getStyleClass().add("poljeIspravno");
            } else {
                passwordRepeatFld.getStyleClass().removeAll("poljeIspravno");
                passwordRepeatFld.getStyleClass().add("poljeNijeIspravno");
                passwordFld.getStyleClass().removeAll("poljeIspravno");
                passwordFld.getStyleClass().add("poljeNijeIspravno");
            }
        });
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

    public void rememberNewPassword(ActionEvent actionEvent) {
        //azurirati password korisniku
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
