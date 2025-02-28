package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class SignupController {

    public PasswordField passwordFld;
    public PasswordField passwordRepeatFld;
    public TextField namefld;
    public TextField surnameFld;
    public TextField usernameFld;
    public Button createAccountBtn;
    public Button exitBtn;

    FitpassDAO dao = FitpassDAO.getInstance();

    public SignupController(FitpassDAO dao) {
        this.dao = dao;
    }

    @FXML
    public void initialize() {
        namefld.textProperty().addListener((obs, oldName, newName) -> {
            if (dao.getValidation().validateNameAndSurname(newName)) {
                namefld.getStyleClass().removeAll("poljeNijeIspravno");
                namefld.getStyleClass().add("poljeIspravno");
            } else {
                namefld.getStyleClass().removeAll("poljeIspravno");
                namefld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        surnameFld.textProperty().addListener((obs, oldSurname, newSurname) -> {
            if (dao.getValidation().validateNameAndSurname(newSurname)) {
                surnameFld.getStyleClass().removeAll("poljeNijeIspravno");
                surnameFld.getStyleClass().add("poljeIspravno");
            } else {
                surnameFld.getStyleClass().removeAll("poljeIspravno");
                surnameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        usernameFld.textProperty().addListener((obs, oldUsername, newUsername) -> {
            if (dao.getValidation().validateUsername(newUsername)) {
                usernameFld.getStyleClass().removeAll("poljeNijeIspravno");
                usernameFld.getStyleClass().add("poljeIspravno");
            } else {
                usernameFld.getStyleClass().removeAll("poljeIspravno");
                usernameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

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

    public void createAccount(ActionEvent actionEvent) {
        //treba prvo provjeriti da li je username vec zauzet, ako jeste staviti da nije validan i prikazati alert
        if(dao.checkUsername(usernameFld.getText())) {
            //otvaranje upozorenja da je username vec zauzet
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška!");
            alert.setHeaderText("Izabrani username se već koristi!");
            alert.setContentText("Ponovite Vaš unos.");
            alert.showAndWait();

            usernameFld.setText("");
        }
        //upisati novog korisnika u bazu ako su sva polja validna
        else if(dao.getValidation().validateNameAndSurname(namefld.getText()) && dao.getValidation().validateNameAndSurname(surnameFld.getText()) &&
           dao.getValidation().validateUsername(usernameFld.getText()) && dao.getValidation().validatePassword(passwordFld.getText())) {

            User tmp = new User();
            tmp.setId(0);
            tmp.setName(namefld.getText());
            tmp.setSurname(surnameFld.getText());
            tmp.setUsername(usernameFld.getText());
            tmp.setPassword(passwordFld.getText());
            dao.addUser(tmp);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("Uspješno ste kreirali Vaš račun!");
            alert.setContentText("");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Node n = (Node) actionEvent.getSource();
                Stage stage = (Stage) n.getScene().getWindow();
                stage.close();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška!");
            alert.setHeaderText("Neispravni podaci!");
            alert.setContentText("Ponovite Vaš unos.");
            alert.showAndWait();

            //restartujemo polja za unos
            namefld.setText("");
            surnameFld.setText("");
            usernameFld.setText("");
            passwordFld.setText("");
            passwordRepeatFld.setText("");
        }

    }

    public void closeWindow(ActionEvent actionEvent) {
        //korisnik je odustao od uredjivanja profila i nema potrebe za upisivanjem u bazu
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
