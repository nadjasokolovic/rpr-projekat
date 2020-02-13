package ba.unsa.etf.rpr.projekat;

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
import java.util.ResourceBundle;

public class ObjectController {
    public TableView trainingTableView;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public Button reserveBtn;
    public Label rateLabel;
    public Label objectNameLabel;

    FitpassDAO dao = FitpassDAO.getInstance();

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

    }

    public void makeReservation(ActionEvent actionEvent) {
        //treba upisati rezervaciju u bazu i otvorit alert na kojem pise uspjesno ste rezervisali
        //korisnik je odustao od uredjivanja profila i nema potrebe za upisivanjem u bazu
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
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
}
