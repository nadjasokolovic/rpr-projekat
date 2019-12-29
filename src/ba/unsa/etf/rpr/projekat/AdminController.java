package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    AdminModel model;

    public AdminController(AdminModel model) {
        this.model = model;
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
