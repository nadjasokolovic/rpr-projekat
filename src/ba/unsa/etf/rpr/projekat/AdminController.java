package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import javax.jws.soap.SOAPBinding;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

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

    public ListView usersList;
    //tab objekti
    public TextField objectNameFld;
    public TextField objectMunicipalityFld;
    public TextField objectAdressFld;
    public Button deleteObjectBtn;
    public Button addObjectBtn;

    public ListView objectsList;
    //tab discipline
    public TextField disciplineNameFld;
    public Button deleteDisciplineBtn;
    public Button addDisciplineBtn;
    public ChoiceBox<Object> objectChoice;
    public ListView disciplinesList;

    //tab termini
    public Label dayLabel;
    public ListView trainingsList;



    //za prosljedjivanje podataka
    private String username, password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private FitpassDAO dao = FitpassDAO.getInstance();

    //skup u koji ce se ucitavati svi korisnici iz baze i prikazivati u listView-u u tabu korisnici
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Object> objects = FXCollections.observableArrayList();

    //trenutno izabrane vrijednost u listView-u
    private SimpleObjectProperty<User> user = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Object> object = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Discipline> discipline = new SimpleObjectProperty<>();

    public User getUser() {
        return user.get();
    }

    public SimpleObjectProperty<User> userProperty() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    public Object getObject() {
        return object.get();
    }

    public SimpleObjectProperty<Object> objectProperty() {
        return object;
    }

    public void setObject(Object object) {
        this.object.set(object);
    }

    public Discipline getDiscipline() {
        return discipline.get();
    }

    public SimpleObjectProperty<Discipline> disciplineProperty() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline.set(discipline);
    }

    //sluze za provjeru da li je cijela forma validna
    private boolean okUser = true;
    private boolean okObject = true;

    public AdminController(FitpassDAO dao) {
        this.dao = dao;
    }

    @FXML
    public void initialize() {
        //popunjavamo podacima sve kolekcije koje sluze za prikaz podataka
        usersList.setItems(FXCollections.observableArrayList(dao.getAllUsers()));
        objectsList.setItems(FXCollections.observableArrayList(dao.getAllObjects()));
        objectChoice.setItems(FXCollections.observableArrayList(dao.getAllObjects()));

        //postavljanje trenutno odabrane vrijednosti u listView-u
        usersList.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
            this.setUser((User)newUser);

            usersList.refresh();
        });

        objectsList.getSelectionModel().selectedItemProperty().addListener((obs, oldObject, newObject) -> {
            this.setObject((Object)newObject);

            objectsList.refresh();
        });

        disciplinesList.getSelectionModel().selectedItemProperty().addListener((obs, oldDiscipline, newDiscipline) -> {
            this.setDiscipline((Discipline) newDiscipline);

            disciplinesList.refresh();
        });

        //bidirectional binding za sva polja
        userProperty().addListener((obs, oldUser, newUser) -> {
            if (oldUser != null) {
                nameFld.textProperty().unbindBidirectional(oldUser.nameProperty());
                surnameFld.textProperty().unbindBidirectional(oldUser.surnameProperty());
                usernameFld.textProperty().unbindBidirectional(oldUser.usernameProperty());
                passwordFld.textProperty().unbindBidirectional(oldUser.passwordProperty());
            }
            if (newUser == null) {
                nameFld.setText("");
                surnameFld.setText("");
                usernameFld.setText("");
                passwordFld.setText("");
            }
            else {
                nameFld.textProperty().bindBidirectional(newUser.nameProperty());
                surnameFld.textProperty().bindBidirectional(newUser.surnameProperty());
                usernameFld.textProperty().bindBidirectional(newUser.usernameProperty());
                passwordFld.textProperty().bindBidirectional(newUser.passwordProperty());
            }
        });

        objectProperty().addListener((obs, oldObject, newObject) -> {
            if (oldObject != null) {
                objectNameFld.textProperty().unbindBidirectional(oldObject.nameProperty());
                objectMunicipalityFld.textProperty().unbindBidirectional(oldObject.municipalityProperty());
                objectAdressFld.textProperty().unbindBidirectional(oldObject.adressProperty());
            }
            if (newObject == null) {
                objectNameFld.setText("");
                objectMunicipalityFld.setText("");
                objectAdressFld.setText("");
            }
            else {
                objectNameFld.textProperty().bindBidirectional(newObject.nameProperty());
                objectMunicipalityFld.textProperty().bindBidirectional(newObject.municipalityProperty());
                objectAdressFld.textProperty().bindBidirectional(newObject.adressProperty());
                StringConverter<? extends Number> converter = new DoubleStringConverter();
            }
        });

        disciplineProperty().addListener((obs, oldDiscipline, newDiscipline) -> {
            if (oldDiscipline != null) {
                disciplineNameFld.textProperty().unbindBidirectional(oldDiscipline.nameProperty());
            }
            if (newDiscipline == null) {
                disciplineNameFld.setText("");
            }
            else {
                disciplineNameFld.textProperty().bindBidirectional(newDiscipline.nameProperty());
            }
        });

        nameFld.textProperty().addListener((obs, oldName, newName) -> {
            if (dao.getValidation().validateNameAndSurname(newName)) {
                nameFld.getStyleClass().removeAll("poljeNijeIspravno");
                nameFld.getStyleClass().add("poljeIspravno");
                this.okUser = true;
            } else {
                nameFld.getStyleClass().removeAll("poljeIspravno");
                nameFld.getStyleClass().add("poljeNijeIspravno");
                this.okUser = false;
            }
        });

        surnameFld.textProperty().addListener((obs, oldSurname, newSurname) -> {
            if (dao.getValidation().validateNameAndSurname(newSurname)) {
                surnameFld.getStyleClass().removeAll("poljeNijeIspravno");
                surnameFld.getStyleClass().add("poljeIspravno");
                this.okUser = true;
            } else {
                surnameFld.getStyleClass().removeAll("poljeIspravno");
                surnameFld.getStyleClass().add("poljeNijeIspravno");
                this.okUser = false;
            }
        });

        usernameFld.textProperty().addListener((obs, oldUsername, newUsername) -> {
            if (dao.getValidation().validateUsername(newUsername)) {
                usernameFld.getStyleClass().removeAll("poljeNijeIspravno");
                usernameFld.getStyleClass().add("poljeIspravno");
                this.okUser = true;
            } else {
                usernameFld.getStyleClass().removeAll("poljeIspravno");
                usernameFld.getStyleClass().add("poljeNijeIspravno");
                this.okUser = false;
            }
        });

        passwordFld.textProperty().addListener((obs, oldPassword, newPassword) -> {
            if (dao.getValidation().validatePassword(newPassword)) {
                passwordFld.getStyleClass().removeAll("poljeNijeIspravno");
                passwordFld.getStyleClass().add("poljeIspravno");
                this.okUser = true;
            } else {
                passwordFld.getStyleClass().removeAll("poljeIspravno");
                passwordFld.getStyleClass().add("poljeNijeIspravno");
                this.okUser = false;
            }
        });

        objectNameFld.textProperty().addListener((obs, oldObjectName, newObjectName) -> {
            if (dao.getValidation().validateNameOfObject(newObjectName)) {
                objectNameFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectNameFld.getStyleClass().add("poljeIspravno");
                okObject = true;
            } else {
                objectNameFld.getStyleClass().removeAll("poljeIspravno");
                objectNameFld.getStyleClass().add("poljeNijeIspravno");
                okObject = false;
            }
        });

        objectMunicipalityFld.textProperty().addListener((obs, oldObjectLocation, newObjectLocation) -> {
            if (dao.getValidation().validateLocation(newObjectLocation)) {
                objectMunicipalityFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectMunicipalityFld.getStyleClass().add("poljeIspravno");
                okObject = true;
            } else {
                objectMunicipalityFld.getStyleClass().removeAll("poljeIspravno");
                objectMunicipalityFld.getStyleClass().add("poljeNijeIspravno");
                okObject = false;
            }
        });

        objectAdressFld.textProperty().addListener((obs, oldObjectLocation, newObjectLocation) -> {
            if (dao.getValidation().validateLocation(newObjectLocation)) {
                objectAdressFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectAdressFld.getStyleClass().add("poljeIspravno");
                okObject = true;
            } else {
                objectAdressFld.getStyleClass().removeAll("poljeIspravno");
                objectAdressFld.getStyleClass().add("poljeNijeIspravno");
                okObject = false;
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

        //TAB discipline: Postavljanje svih disciplina u listView za objekat koji je odabran u choiceBox-u
        objectChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                ArrayList<Discipline> tmp = dao.getDisciplinesForObject(objectChoice.getItems().get((Integer) number2).getId());
                disciplinesList.setItems(FXCollections.observableArrayList(tmp));
            }
        });
        
        //TAB treninzi
        //postavljanje danasnjega dana na labelu
        LocalDate now = LocalDate.now();
        switch (now.getDayOfWeek()) {
            case MONDAY:
                dayLabel.setText("Ponedjeljak");
                break;
            case TUESDAY:
                dayLabel.setText("Utorak");
                break;
            case WEDNESDAY:
                dayLabel.setText("Srijeda");
                break;
            case THURSDAY:
                dayLabel.setText("Cetvrtak");
                break;
            case FRIDAY:
                dayLabel.setText("Petak");
                break;
            case SATURDAY:
                dayLabel.setText("Subota");
                break;
            case SUNDAY:
                dayLabel.setText("Nedjelja");
                break;
        }

        //Inicijalizacija tabele
        //iz baze je potrebno ucitati sve treninge za trenutni objekat i danasnji dan
        Map<User, Training> usersTrainingsMap = dao.getTrainingsForAllUsers(now.getDayOfWeek().toString());
        //Potrebno je formirati listu stringova koja sadrzi ime i prezime korisnika i rezervisani termin)
        ArrayList<String> tmp = new ArrayList<>();
        for (Map.Entry<User, Training> entry : usersTrainingsMap.entrySet()) {
            //System.out.println(entry.getKey() + " = " + entry.getValue());
            String s = entry.getKey().getName() + " " + entry.getKey().getSurname() + " (" + entry.getValue().getStartOfTraining() + "-" + entry.getValue().getEndOfTraining() + ")";
            tmp.add(s);
        }
        trainingsList.setItems(FXCollections.observableArrayList(tmp));
    }

    public void addUser(ActionEvent actionEvent) {
        //treba dodati korisnika u bazu ako je unesen validan korisnik
        if(okUser) {
            //treba provjeriti da li username postoji vec u bazi
            if(!dao.checkUsername(usernameFld.getText())){
                //ako ne postoji moze se dodati
                User tmp = new User();
                tmp.setId(0); //u metodi klase DAO se postavlja ovaj id na ispravnu vrijednost
                tmp.setName(nameFld.getText());
                tmp.setSurname(surnameFld.getText());
                tmp.setUsername(usernameFld.getText());
                tmp.setPassword(passwordFld.getText());

                dao.addUser(tmp);

                usersList.getItems().add(tmp);
            }
            else {
                //ako postoji potrebno je obavijestiti admina o neispravnim podacima
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška!");
                alert.setHeaderText("Izabrani username se već koristi!");
                alert.setContentText("Ponovite Vaš unos.");
                alert.showAndWait();

                usernameFld.setText("");
            }
        }

    }

    public void editUser(ActionEvent actionEvent) {
        //treba izmijeniti korisnika u bazi
        if(this.user != null){
            //ako postoji trenutno izabrani korisnik
            User tmp = new User();
            tmp.setId(this.getUser().getId()); //postavlja id na id trenutnog korisnika
            tmp.setName(nameFld.getText());
            tmp.setSurname(surnameFld.getText());
            tmp.setUsername(usernameFld.getText());
            tmp.setPassword(passwordFld.getText());

            dao.editUser(tmp);
            this.setUser(tmp);
            //ovo promijeni u bazi ok
        }
    }

    public void deleteUser(ActionEvent actionEvent) {
        //treba izbrisati trenutnog korisnika iz baze

        if(usersList.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Brisanje korisnika");
            String s = "Da li ste sigurni da želite obrisati korisnika: " + this.getUser().getName() + " " + this.getUser().getSurname() + "?";
            alert.setContentText(s);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                //prvo mi trebaju svi id iz tabela iz kojih cu brisati podatke
                int idOsobe = dao.getIdForUsername(this.getUser().getUsername());
                int idKorisnika = dao.getUserIdForPersonId(idOsobe);
                int idAktivnosti = dao.getActivityIdForUserId(idKorisnika);

                //brisemo prvo iz tabele korisnik_aktivnost sve aktivnosti koje su vezane za trenutnog korisnika
                dao.deleteActivityUser(idKorisnika);
                //brisemo u tabeli aktivnost
                dao.deleteActivity(idAktivnosti);
                //brisemo iz tabele korisnik
                dao.deleteUser(idKorisnika);
                //brisemo iz tabele osoba
                dao.deletePerson(idOsobe);
            }
            //potrebno je odmah korisnika izbaciti iz liste za prikaz
            usersList.getItems().remove(this.getUser());
            //da obrisemo selektovano u tableView, tj. da ne ostane selektovan grad iznad onog koji brisemo
            usersList.getSelectionModel().clearSelection();
        }
    }

    public void deleteObject(ActionEvent actionEvent) {
        //treba izbrisati objekat iz baze
        if(objectsList.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Brisanje objekta");
            String s = "Da li ste sigurni da želite obrisati objekat: " + this.getObject().getName() + "?";
            alert.setContentText(s);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                int idObjekta = this.getObject().getId();

                dao.deleteTraining(idObjekta);
                dao.deleteObjectDiscipline(idObjekta);
                dao.deleteObjectRate(idObjekta);
                dao.deleteObject(idObjekta);

            }
            objectsList.getItems().remove(this.getObject());
            objectsList.getSelectionModel().clearSelection();
        }

    }

    public void addObject(ActionEvent actionEvent) {
        //treba dodati objekat u bazu
        if(okObject) {
            Object tmp = new Object();
            tmp.setId(0); //opet ce se u metodi dao klase postaviti ispravan id
            tmp.setName(objectNameFld.getText());
            tmp.setMunicipality(objectMunicipalityFld.getText());
            tmp.setAdress(objectAdressFld.getText());

            dao.addObject(tmp);
            objectsList.getItems().add(tmp);
        }
    }

    public void editObject(ActionEvent actionEvent) {
        //treba izbrisati objekat iz baze
        //treba izmijeniti korisnika u bazi
        if(this.object != null){
            //ako postoji trenutno izabrani objekat
            Object tmp = new Object();
            tmp.setId(this.getObject().getId());
            tmp.setName(objectNameFld.getText());
            tmp.setMunicipality(objectMunicipalityFld.getText());
            tmp.setAdress(objectAdressFld.getText());

            dao.editObject(tmp);
            this.setObject(tmp);
            //ovo promijeni u bazi ok
        }
    }

    public void checkActivity(ActionEvent actionEvent) {
        //treba ucitati podatke i provjeriti je li korisnik uplacivao clanarinu u proteklih 3 mjeseca
        //otvoriti confirm prozor gdje ce se pitati admina da li zeli dodijeliti dodatne termine

        //ako admin dodijeli treba upisati u listu obavijesti korisnika da je dobio nove termine
    }

    public void extendMembershipFee(ActionEvent actionEvent) {
        //Trenutno izabranom korisniku
        //1. postaviti datum pocetka clanarine na danasnji datum
        //2. postaviti datum isteka na danasnji datum + 30
        //3. postaviti broj ukupnih termina na onaj broj koji admin unese sa tastature
        //4. postaviti broj iskoristenih termina na 0
        //5. postaviti u korisniku da je platio clanarinu za trenutni mjesec

        //Danasnji datum
        LocalDate currentDate = LocalDate.now();
        //Formiranje stringa koji ce se upisivati u bazu
        String start = Integer.toString(currentDate.getDayOfMonth()) + "." + Integer.toString(currentDate.getMonth().getValue()) + "." +
                       Integer.toString(currentDate.getYear()) + ".";

        //Registracija clanarine za trenutni mjesec
        user.getValue().registerMembershipFee(currentDate.getMonth().getValue());

        //Uvecavamo datum za 30 dana
        currentDate.plusDays(30);
        String end = Integer.toString(currentDate.getDayOfMonth()) + "." + Integer.toString(currentDate.getMonth().getValue()) + "." +
                Integer.toString(currentDate.getYear()) + ".";

        //Radi se unos sa tastature jer admin zna koji je tip clanarine uplacen
        //To se sve vrsi izvan ove aplikacije
        System.out.println("Unesite broj termina za korisnika: ");
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();
        if(number != 12 && number != 16 && number != 20)
            throw new IllegalArgumentException("Uneseni broj termina je neispravan.");

        dao.extendMembershipFee(user.getValue().getId(), start, end, number);

        //POSLATI OBAVJESTENJE ZA KORISNIKA
        dao.addNotification(user.getValue().getId(), "Vaša članarina je evidentirana " + start);

        //Obavijestiti admina o uspjesnoj evidenciji clanarine
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Evidencija članarine");
        alert.setHeaderText("");
        alert.setContentText("Uspješno ste evidentirali članarinu za korisnika.");
        alert.showAndWait();
    }

    public void deleteDiscipline(ActionEvent actionEvent) {
        if(disciplinesList.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Brisanje discipline za objekat");
            String s = "Da li ste sigurni da želite obrisati disciplinu: " + this.getDiscipline().getName() + "?";
            alert.setContentText(s);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                //brise disciplinu za izabrani objekat
                Object o = objectChoice.getSelectionModel().getSelectedItem();
                dao.deleteDisciplineForObject(o.getId(), this.getDiscipline().getId());

            }
            disciplinesList.getItems().remove(this.getDiscipline());
            disciplinesList.getSelectionModel().clearSelection();
        }
    }

    public void addDiscipline(ActionEvent actionEvent) {
        //dodavat ce disciplinu u bazu za izabrani objekat
        Object o = objectChoice.getSelectionModel().getSelectedItem();
        dao.addDisciplineForObject(o.getId(), disciplineNameFld.getText());

        disciplinesList.getItems().add(new Discipline(0, disciplineNameFld.getText()));
    }

    private void sendData(UserAccountController ctrl) {
        //prvo na osnovu username koji je unique iz baze ucitavati id osobe
        int personId = dao.getIdForUsername(this.getUsername());
        //potrebno je pronaci idKorisnika na osnovu id osobe
        int userId = dao.getUserIdForPersonId(personId);

        //iz tabele korisnik dobijamo podatke o broju iskoristenih termina i broju preostalih
        int brojIskoristenihTermina = dao.getNumberTerminsUsed(userId);
        //iz baze ucitavamo ukupan broj termina da bismo dobili broj preostalih
        int ukupnoTermina = dao.getNumberOfTermins(userId);
        //iz baze ucitavamo sve obavijesti za korisnika
        ArrayList<String> obavijesti = dao.getNotifications(userId);

        //slanje ovih podataka u userAccountController
        ctrl.setUsername(this.getUsername());
        ctrl.setNumberOfTrainings(ukupnoTermina - brojIskoristenihTermina);
        ctrl.setNumberOfTrainingsUsed(brojIskoristenihTermina);
        ctrl.setNotifications(obavijesti);
    }
}
