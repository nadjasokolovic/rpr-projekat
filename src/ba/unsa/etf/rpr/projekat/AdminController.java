package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
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
        Parent root = null;
        Stage myStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/userAccount.fxml"));
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
