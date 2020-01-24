package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

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
    public TextField objectRateFld;
    public Button deleteObjectBtn;
    public Button addObjectBtn;

    public ListView objectsList;
    //tab discipline
    public TextField disciplineNameFld;
    public Button deleteDisciplineBtn;
    public Button addDisciplineBtn;
    public ChoiceBox objectChoice;

    public ListView disciplineList;

    //tab termini
    public ListView trainingsList;


    private FitpassDAO dao = FitpassDAO.getInstance();

    //skup u koji ce se ucitavati svi korisnici iz baze i prikazivati u listView-u u tabu korisnici
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Object> objects = FXCollections.observableArrayList();

    //trenutno izabrane vrijednost u listView-u
    private SimpleObjectProperty<User> user = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Object> object = new SimpleObjectProperty<>();

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

        //postavljanje trenutno odabrane vrijednosti u listView-u
        usersList.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
            this.setUser((User)newUser);

            usersList.refresh();
        });

        objectsList.getSelectionModel().selectedItemProperty().addListener((obs, oldObject, newObject) -> {
            this.setObject((Object)newObject);

            objectsList.refresh();
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
                objectRateFld.textProperty().unbindBidirectional(oldObject.averageRateProperty());
            }
            if (newObject == null) {
                objectNameFld.setText("");
                objectMunicipalityFld.setText("");
                objectAdressFld.setText("");
                objectRateFld.setText("");
            }
            else {
                objectNameFld.textProperty().bindBidirectional(newObject.nameProperty());
                objectMunicipalityFld.textProperty().bindBidirectional(newObject.municipalityProperty());
                objectAdressFld.textProperty().bindBidirectional(newObject.adressProperty());
                StringConverter<? extends Number> converter = new DoubleStringConverter();
                objectRateFld.textProperty().bindBidirectional(newObject.averageRateProperty(), (StringConverter<Number>) converter);
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

        objectRateFld.textProperty().addListener((obs, oldObjectRate, newObjectRate) -> {
            if (dao.getValidation().validateGrade(newObjectRate)) {
                objectRateFld.getStyleClass().removeAll("poljeNijeIspravno");
                objectRateFld.getStyleClass().add("poljeIspravno");
                okObject = true;
            } else {
                objectRateFld.getStyleClass().removeAll("poljeIspravno");
                objectRateFld.getStyleClass().add("poljeNijeIspravno");
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
            //kada se tek doda neki novi objekat prosjecna ocjena mu je nula, i ona ce se tek kasnije racunati pomocu recenzije korisnika
            tmp.setAverageRate(0);
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
            tmp.setAverageRate(Double.parseDouble(objectRateFld.getText()));
            tmp.setMunicipality(objectMunicipalityFld.getText());
            tmp.setAdress(objectAdressFld.getText());

            dao.editObject(tmp);
            this.setObject(tmp);
            //ovo promijeni u bazi ok
        }
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

    public void deleteDiscipline(ActionEvent actionEvent) {
        //brisat ce se ta disciplina iz baze
    }

    public void addDiscipline(ActionEvent actionEvent) {
        //dodavat ce disciplinu u bazu
    }
}
