package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    //tab korisnici
    public TabPane adminTabPane;
    public TextField nameFld;
    public TextField surnameFld;
    public TextField usernameFld;
    public Button checkActivityBtn;
    public Button membershipFeeBtn;
    public PasswordField passwordFld;
    //tab objekti
    public TextField objectNameFld;
    public TextField objectLocationFld;
    public TextField objectRateFld;
    public Button deleteBtn;
    public Button addBtn;
    //tab discipline
    public TextField disciplineNameFld;
    public Button deleteDisciplineBtn;
    public Button addDisciplineBtn;

    AdminModel model;

    public AdminController(AdminModel model) {
        this.model = model;
    }

    @FXML
    public void initialize() {
        nameFld.textProperty().addListener((obs, oldName, newName) -> {
            if (model.getValidacija().validirajImeIPrezime(newName)) {
                nameFld.getStyleClass().removeAll("poljeNijeIspravno");
                nameFld.getStyleClass().add("poljeIspravno");
            } else {
                nameFld.getStyleClass().removeAll("poljeIspravno");
                nameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        surnameFld.textProperty().addListener((obs, oldSurname, newSurname) -> {
            if (model.getValidacija().validirajImeIPrezime(newSurname)) {
                surnameFld.getStyleClass().removeAll("poljeNijeIspravno");
                surnameFld.getStyleClass().add("poljeIspravno");
            } else {
                surnameFld.getStyleClass().removeAll("poljeIspravno");
                surnameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        usernameFld.textProperty().addListener((obs, oldUsername, newUsername) -> {
            if (model.getValidacija().validirajUsername(newUsername)) {
                usernameFld.getStyleClass().removeAll("poljeNijeIspravno");
                usernameFld.getStyleClass().add("poljeIspravno");
            } else {
                usernameFld.getStyleClass().removeAll("poljeIspravno");
                usernameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        passwordFld.textProperty().addListener((obs, oldPassword, newPassword) -> {
            if (model.getValidacija().validirajPassword(newPassword)) {
                passwordFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordFld.getStyleClass().add("poljeIspravno");
            } else {
                passwordFld.getStyleClass().removeAll("poljeIspravno");
                passwordFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        objectNameFld.textProperty().addListener((obs, oldObjectName, newObjectName) -> {
            if (model.getValidacija().validirajNazivObjekta(newObjectName)) {
                objectNameFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectNameFld.getStyleClass().add("poljeIspravno");
            } else {
                objectNameFld.getStyleClass().removeAll("poljeIspravno");
                objectNameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        objectLocationFld.textProperty().addListener((obs, oldObjectLocation, newObjectLocation) -> {
            if (model.getValidacija().validirajNazivLokacije(newObjectLocation)) {
                objectLocationFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectLocationFld.getStyleClass().add("poljeIspravno");
            } else {
                objectLocationFld.getStyleClass().removeAll("poljeIspravno");
                objectLocationFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        objectRateFld.textProperty().addListener((obs, oldObjectRate, newObjectRate) -> {
            if (model.getValidacija().validirajOcjenu(Integer.parseInt(newObjectRate))) {
                objectRateFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectRateFld.getStyleClass().add("poljeIspravno");
            } else {
                objectRateFld.getStyleClass().removeAll("poljeIspravno");
                objectRateFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        disciplineNameFld.textProperty().addListener((obs, oldDisciplineName, newDisciplineName) -> {
            if (model.getValidacija().validirajNazivDiscipline(newDisciplineName)) {
                disciplineNameFld.getStyleClass().removeAll("poljeNijeIspravno");
                disciplineNameFld.getStyleClass().add("poljeIspravno");
            } else {
                disciplineNameFld.getStyleClass().removeAll("poljeIspravno");
                disciplineNameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void deleteObject(ActionEvent actionEvent) {
        //treba izbrisati objekat iz baze
    }

    public void addObject(ActionEvent actionEvent) {
        //treba dodati objekat u bazu
    }

    public void checkActivity(ActionEvent actionEvent) {
        //treba ucitati podatke i provjeiti je li korisnik uplacivao clanarinu u proteklih 3 mjeseca
        //otvoriti confirm prozor gdje ce se pitati admina da li zeli dodijeliti dodatne termine

        //ako admin dodijeli treba upisati u listu obavijesti korisnika da je dobio nove termine
    }

    public void extendMembershipFee(ActionEvent actionEvent) {
        //samo u bazi datim isteka i uplate clanarine produziti
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

    public void deleteDiscipline(ActionEvent actionEvent) {
        //brisat ce se ta disciplina iz baze
    }

    public void addDiscipline(ActionEvent actionEvent) {
        //dodavat ce disciplinu u bazu
    }
}
