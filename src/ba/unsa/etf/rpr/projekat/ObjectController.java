package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ObjectController {
    public TableView trainingTableView;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public Button reserveBtn;
    public Label rateLabel;
    public Label objectNameLabel;

    FitpassDAO dao = FitpassDAO.getInstance();

    SimpleObjectProperty<Training> selectedTraining = new SimpleObjectProperty<>();

    //atributi koji ce sluziti za postavljanje podataka trenutnog objekta
    private String objectName;
    private String objectRate;

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setObjectRate(String objectRate) {
        this.objectRate = objectRate;
    }

    public ObjectController(FitpassDAO dao) {
        this.dao = dao;
    }

    //atributi koji ce sluziti za pristup trenutno prijavljenom korisniku
    private String username, password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @FXML
    public void initialize() {
        objectNameLabel.setText(objectName);
        rateLabel.setText(objectRate);

        //Inicijalizacija tabele
        //iz baze je potrebno ucitati sve treninge za trenutni objekat i danasnji dan

        LocalDate currentDate = LocalDate.now();
        DayOfWeek day = currentDate.getDayOfWeek(); //vracat ce naziv dana, sve velikim slovima (npr. FRIDAY), a tako sam i u bazi dane pisala


        trainingTableView.setItems(FXCollections.observableArrayList(dao.getTrainingsOnDay(objectNameLabel.getText(), day.toString())));

        //Placeholder ukoliko nema elemenata u tabeli
        trainingTableView.setPlaceholder(new Label("Žao nam je, u ovom objektu danas nema treninga za Vašu disciplinu."));

        //postavljanje cellValueFactorija
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startOfTraining"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endOfTraining"));

        //Postavljanje trenutno izabranog treninga
        trainingTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldObject, newObject) -> {
            selectedTraining.set((Training) trainingTableView.getSelectionModel().getSelectedItem());
        });

    }

    public void makeReservation(ActionEvent actionEvent) {
        //Posto korisnik moze rezervisati samo jedan trening za jedan dan, dovoljno je samo azurirati kolonu trening_id u tabeli Korisnik
        //tacnije postaviti na id novoizabranog treninga
        //I potrebno je povecati broj iskoristenih termina za 1

        //prvo je potrebno da pronadjem korisnika
        int personId = dao.getIdForUsername(this.username);
        int userId = dao.getUserIdForPersonId(personId);

        if(selectedTraining != null) {
            int successful = dao.reserveTraining(userId, selectedTraining.getValue().getId());

            if(successful == 1) {
                //Obavijestiti korisnika o uspjesnoj rezervaciji
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rezervacija treninga");
                alert.setHeaderText("Uspješno ste rezervisali trening.");
                alert.setContentText("Želimo Vam ugodan trening!");
                //Zatvaranje stranice za rezervaciju, kada se zatvori alert
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Node n = (Node) actionEvent.getSource();
                    Stage stage = (Stage) n.getScene().getWindow();
                    stage.close();
                }
            }
            else if(successful == 2){
                //Obavijestiti korisnika o tome da je iskoristio sve termine
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rezervacija treninga");
                alert.setHeaderText("Rezervacija nije moguća!");
                alert.setContentText("Iskoristili ste dozvoljeni broj termina.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Node n = (Node) actionEvent.getSource();
                    Stage stage = (Stage) n.getScene().getWindow();
                    stage.close();
                }
            }
            else if(successful == 3) {
                //Obavijestiti korisnika o tome da mu je clanarina istekla
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rezervacija treninga");
                alert.setHeaderText("Rezervacija nije moguća!");
                alert.setContentText("Vaša članarina više nije aktivna.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Node n = (Node) actionEvent.getSource();
                    Stage stage = (Stage) n.getScene().getWindow();
                    stage.close();
                }
            }
            else if(successful == 4){
                //Obavijestiti korisnika o tome da se desila greska u bazi
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Rezervacija treninga");
                alert.setHeaderText("Rezervacija nije moguća zbog greške pri radu sa bazom podataka.");
                alert.setContentText("Molimo Vas pokušajte kasnije.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Node n = (Node) actionEvent.getSource();
                    Stage stage = (Stage) n.getScene().getWindow();
                    stage.close();
                }
            }
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void goToProfil(ActionEvent actionEvent) {
        FitpassDAO dao = FitpassDAO.getInstance();
        UserAccountController ctrl = new UserAccountController(dao);

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userAccount.fxml"), bundle);
        loader.setController(ctrl);
        sendData(ctrl);
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

    private void sendData(UserAccountController ctrl) {
        //prvo na osnovu username koji je unique iz baze ucitavati id osobe
        int personId = dao.getIdForUsername(this.username);
        //potrebno je pronaci idKorisnika na osnovu id osobe
        int userId = dao.getUserIdForPersonId(personId);

        //iz tabele korisnik dobijamo podatke o broju iskoristenih termina i broju preostalih
        int brojIskoristenihTermina = dao.getNumberTerminsUsed(userId);
        //iz baze ucitavamo ukupan broj termina da bismo dobili broj preostalih
        int ukupnoTermina = dao.getNumberOfTermins(userId);
        //iz baze ucitavamo sve obavijesti za korisnika
        ArrayList<String> obavijesti = dao.getNotifications(userId);

        //slanje ovih podataka u userAccountController
        ctrl.setUsername(this.username);
        ctrl.setNumberOfTrainings(ukupnoTermina - brojIskoristenihTermina);
        ctrl.setNumberOfTrainingsUsed(brojIskoristenihTermina);
        ctrl.setNotifications(obavijesti);
    }
}
