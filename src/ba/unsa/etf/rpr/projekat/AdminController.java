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
    public Button addUserBtn;
    public Button editUserBtn;
    public Button deleteUserbtn;
    public Button checkActivityBtn;
    public Button membershipFeeBtn;
    public PasswordField passwordFld;
    //tab objekti
    public TextField objectNameFld;
    public TextField objectLocationFld;
    public TextField objectRateFld;
    public Button deleteObjectBtn;
    public Button addObjectBtn;
    //tab discipline
    public TextField disciplineNameFld;
    public Button deleteDisciplineBtn;
    public Button addDisciplineBtn;

    AdminModel model;
    private FitpassDAO dao = FitpassDAO.getInstance();

    public AdminController(FitpassDAO dao) {
        this.dao = dao;
    }

    @FXML
    public void initialize() {
        nameFld.textProperty().addListener((obs, oldName, newName) -> {
            if (dao.getValidation().validateNameAndSurname(newName)) {
                nameFld.getStyleClass().removeAll("poljeNijeIspravno");
                nameFld.getStyleClass().add("poljeIspravno");
            } else {
                nameFld.getStyleClass().removeAll("poljeIspravno");
                nameFld.getStyleClass().add("poljeNijeIspravno");
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
            if (dao.getValidation().validatePassword(newPassword)) {
                passwordFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordFld.getStyleClass().add("poljeIspravno");
            } else {
                passwordFld.getStyleClass().removeAll("poljeIspravno");
                passwordFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        objectNameFld.textProperty().addListener((obs, oldObjectName, newObjectName) -> {
            if (dao.getValidation().validateNameOfObject(newObjectName)) {
                objectNameFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectNameFld.getStyleClass().add("poljeIspravno");
            } else {
                objectNameFld.getStyleClass().removeAll("poljeIspravno");
                objectNameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        objectLocationFld.textProperty().addListener((obs, oldObjectLocation, newObjectLocation) -> {
            if (dao.getValidation().validateLocation(newObjectLocation)) {
                objectLocationFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectLocationFld.getStyleClass().add("poljeIspravno");
            } else {
                objectLocationFld.getStyleClass().removeAll("poljeIspravno");
                objectLocationFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        objectRateFld.textProperty().addListener((obs, oldObjectRate, newObjectRate) -> {
            if (dao.getValidation().validateGrade(Integer.parseInt(newObjectRate))) {
                objectRateFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectRateFld.getStyleClass().add("poljeIspravno");
            } else {
                objectRateFld.getStyleClass().removeAll("poljeIspravno");
                objectRateFld.getStyleClass().add("poljeNijeIspravno");
            }
        });

        disciplineNameFld.textProperty().addListener((obs, oldDisciplineName, newDisciplineName) -> {
            if (dao.getValidation().validateDiscipline(newDisciplineName)) {
                disciplineNameFld.getStyleClass().removeAll("poljeNijeIspravno");
                disciplineNameFld.getStyleClass().add("poljeIspravno");
            } else {
                disciplineNameFld.getStyleClass().removeAll("poljeIspravno");
                disciplineNameFld.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    public void addUser(ActionEvent actionEvent) {
        //treba izbrisati objekat iz baze
    }

    public void editUser(ActionEvent actionEvent) {
        //treba izbrisati objekat iz baze
    }

    public void deleteUser(ActionEvent actionEvent) {
        //treba izbrisati objekat iz baze
    }

    public void deleteObject(ActionEvent actionEvent) {
        //treba izbrisati objekat iz baze
    }

    public void addObject(ActionEvent actionEvent) {
        //treba dodati objekat u bazu
    }

    public void editObject(ActionEvent actionEvent) {
        //treba izbrisati objekat iz baze
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
        FitpassDAO dao = FitpassDAO.getInstance();
        UserAccountController ctrl = new UserAccountController(dao);

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
