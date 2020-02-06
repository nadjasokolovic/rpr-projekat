package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProfilController {
    public PasswordField passwordFld;
    public PasswordField passwordRepeatFld;
    public TextField namefld;
    public TextField surnameFld;
    public TextField usernameFld;
    public Button editProfilBtn;
    public Button exitBtn;

    FitpassDAO dao = FitpassDAO.getInstance();

    public EditProfilController(FitpassDAO dao) {
        this.dao = dao;
    }

    //sluzit ce za pristup useru kojem profil pripada
    //ovaj podatak cu postavljati pri ostvaranju forme za uredjivanje i pomocu ovog atributa cu pristupati korisniku u bazi
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private boolean validForm = false;
    @FXML
    public void initialize() {
        namefld.textProperty().addListener((obs, oldName, newName) -> {
            if (dao.getValidation().validateNameAndSurname(newName)) {
                namefld.getStyleClass().removeAll("poljeNijeIspravno");
                namefld.getStyleClass().add("poljeIspravno");
                validForm = true;
            } else {
                namefld.getStyleClass().removeAll("poljeIspravno");
                namefld.getStyleClass().add("poljeNijeIspravno");
                validForm = false;
            }
        });

        surnameFld.textProperty().addListener((obs, oldSurname, newSurname) -> {
            if (dao.getValidation().validateNameAndSurname(newSurname)) {
                surnameFld.getStyleClass().removeAll("poljeNijeIspravno");
                surnameFld.getStyleClass().add("poljeIspravno");
                validForm = true;
            } else {
                surnameFld.getStyleClass().removeAll("poljeIspravno");
                surnameFld.getStyleClass().add("poljeNijeIspravno");
                validForm = false;
            }
        });

        usernameFld.textProperty().addListener((obs, oldUsername, newUsername) -> {
            if (dao.getValidation().validateUsername(newUsername)) {
                usernameFld.getStyleClass().removeAll("poljeNijeIspravno");
                usernameFld.getStyleClass().add("poljeIspravno");
                validForm = true;
            } else {
                usernameFld.getStyleClass().removeAll("poljeIspravno");
                usernameFld.getStyleClass().add("poljeNijeIspravno");
                validForm = false;
            }
        });

        passwordFld.textProperty().addListener((obs, oldPassword, newPassword) -> {
            if (dao.getValidation().validatePassword(newPassword) && newPassword.equals(passwordRepeatFld.getText())) {
                passwordFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordFld.getStyleClass().add("poljeIspravno");
                passwordRepeatFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordRepeatFld.getStyleClass().add("poljeIspravno");
                validForm = true;
            } else {
                passwordFld.getStyleClass().removeAll("poljeIspravno");
                passwordFld.getStyleClass().add("poljeNijeIspravno");
                passwordRepeatFld.getStyleClass().removeAll("poljeIspravno");
                passwordRepeatFld.getStyleClass().add("poljeNijeIspravno");
                validForm = false;
            }
        });

        passwordRepeatFld.textProperty().addListener((obs, oldPasswordRepeat, newPasswordRepeat) -> {
            if (dao.getValidation().validatePassword(newPasswordRepeat) && newPasswordRepeat.equals(passwordFld.getText())) {
                passwordRepeatFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordRepeatFld.getStyleClass().add("poljeIspravno");
                passwordFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordFld.getStyleClass().add("poljeIspravno");
                validForm = true;
            } else {
                passwordRepeatFld.getStyleClass().removeAll("poljeIspravno");
                passwordRepeatFld.getStyleClass().add("poljeNijeIspravno");
                passwordFld.getStyleClass().removeAll("poljeIspravno");
                passwordFld.getStyleClass().add("poljeNijeIspravno");
                validForm = false;
            }
        });
    }

    public void editProfil(ActionEvent actionEvent) {
        //ako je forma validna potrebno je izvrsiti azuriranje u bazi
        if(validForm) {
            dao.editProfil(user, namefld.getText(), surnameFld.getText(), usernameFld.getText(), passwordFld.getText());

            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uredjivanje profila");
            alert.setHeaderText("Podaci su neispravni!");
            alert.setContentText("Molimo Vas, izvršite ispravku unosa.");

            alert.showAndWait();
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        //korisnik je odustao od uredjivanja profila i nema potrebe za upisivanjem u bazu
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
