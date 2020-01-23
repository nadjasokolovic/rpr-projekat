package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public class FitpassDAO {
    private Validation validation = new Validation();

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
    }

    private static FitpassDAO instance = null;

    //ovdje cemo pisati sve PreparedStatement-e
    private PreparedStatement probniUpit, provjeraKorisnikaUpit, dodajOsobuUpit, dodajKorisnikaUpit, maxOsobaIDUpit, maxKorisnikIDUpit, provjraUsernameUpit;
    private PreparedStatement azurirajPasswordUpit, dajKorisnikaUpit, korisniciUpit, izmijeniKorisnika, objektiUpit, idKorisnikaUpit, idAktivnostUpit;
    private PreparedStatement izbirsiKorisnikAktivnostUpit, izbrisiAktivnostUpit, izbrisiKorisnikaUpit, izbrisiOsobuUpit;
    private PreparedStatement dodajObjekatUpit, maxObjekatIDUpit, izmijeniObjekatUpit, idObjektaUpit, idTreningUpit, izbrisiTreningZaObjekatUpit;
    private PreparedStatement izbrisiObjekatDisciplinaUpit, izbrisiObjekatUpit;

    private Connection conn;

    private FitpassDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            probniUpit = conn.prepareStatement("SELECT * FROM osoba");
        }catch (SQLException e) {
            e.printStackTrace();
            //ako je ovaj upit bacio izuzetak potrebno je bazu napuniti defaultnim podacima

            System.out.println("Pozivam regenerisi");
            regenerisiBazu();
        }

        try {
            provjeraKorisnikaUpit = conn.prepareStatement("SELECT o.osoba_id FROM osoba o WHERE o.username=? AND o.password=?");
            dodajOsobuUpit = conn.prepareStatement("INSERT INTO osoba VALUES(?,?,?,?,?,?)");
            maxOsobaIDUpit = conn.prepareStatement("SELECT MAX(osoba_id)+1 FROM osoba");
            dodajKorisnikaUpit = conn.prepareStatement("INSERT INTO korisnik VALUES(?,?,?,?,?,?,?)");
            maxKorisnikIDUpit = conn.prepareStatement("SELECT MAX(korisnik_id)+1 FROM korisnik");
            provjraUsernameUpit = conn.prepareStatement("SELECT o.osoba_id FROM osoba o WHERE o.username=?");
            azurirajPasswordUpit = conn.prepareStatement("UPDATE osoba SET password=? WHERE osoba_id=?");
            dajKorisnikaUpit = conn.prepareStatement("SELECT o.password FROM osoba o WHERE o.username=?");
            korisniciUpit = conn.prepareStatement("SELECT * FROM osoba WHERE tip_osobe=?");
            izmijeniKorisnika = conn.prepareStatement("UPDATE osoba SET ime=?, prezime=?, username=?, password=? WHERE osoba_id=?");
            objektiUpit = conn.prepareStatement("SELECT * FROM objekat");
            idKorisnikaUpit = conn.prepareStatement("SELECT k.korisnik_id FROM korisnik k WHERE k.osoba_id=?");
            idAktivnostUpit = conn.prepareStatement("SELECT ka.aktivnost_id FROM korisnik_aktivnost ka WHERE ka.korisnik_id=?");
            izbirsiKorisnikAktivnostUpit = conn.prepareStatement("DELETE FROM korisnik_aktivnost WHERE korisnik_id=?");
            izbrisiAktivnostUpit = conn.prepareStatement("DELETE FROM aktivnost WHERE aktivnost_id=?");
            izbrisiKorisnikaUpit = conn.prepareStatement("DELETE FROM korisnik WHERE korisnik_id=?");
            izbrisiOsobuUpit = conn.prepareStatement("DELETE FROM osoba WHERE osoba_id=?");
            dodajObjekatUpit = conn.prepareStatement("INSERT INTO objekat VALUES(?,?,?,?,?)");
            maxObjekatIDUpit = conn.prepareStatement("SELECT MAX(objekat_id)+1 FROM objekat");
            izmijeniObjekatUpit = conn.prepareStatement("UPDATE objekat SET naziv=?, prosjecna_ocjena=?, opcina=?, adresa=? WHERE objekat_id=?");
            idObjektaUpit = conn.prepareStatement("SELECT o.objekat_id FROM objekat o WHERE o.naziv=? AND o.adresa=?");
            idTreningUpit = conn.prepareStatement("SELECT t.trening_id FROM trening t WHERE t.objekat_id=?");
            izbrisiTreningZaObjekatUpit = conn.prepareStatement("DELETE FROM trening WHERE trening_id=?");
            izbrisiObjekatDisciplinaUpit = conn.prepareStatement("DELETE FROM objekat_disciplina WHERE objekat_id=?");
            izbrisiObjekatUpit = conn.prepareStatement("DELETE FROM objekat WHERE objekat_id=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
            System.out.println("Baza kreirana");
        } catch (FileNotFoundException e) {
            System.out.println("Ne postoji SQL datoteka… nastavljam sa praznom bazom");
        }
    }

    public String osobeIzBaze() {
        String s = "";
        try {
            probniUpit = conn.prepareStatement("SELECT ime FROM osoba");
            ResultSet res = probniUpit.executeQuery();
            while (res.next()) {
                s += res.getString(1) + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }

    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableSet<Object> objects = FXCollections.observableSet();
    private ObservableMap<String, HashSet<Object>> disciplines = FXCollections.observableHashMap(); //kljuc: naziv discipline, vrijednost:skup objekata u kojima postoje treninzi te discipline
    private ObservableList<Training> trainings = FXCollections.observableArrayList();

    public static FitpassDAO getInstance() {
        if(instance == null)
            instance = new FitpassDAO();

        return instance;
    }

    public static void removeInstance() {
        if (instance != null) {
            try {
                instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }

    private int getPersonId() {
        try {
            ResultSet result = maxOsobaIDUpit.executeQuery();
            if(result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; //nikada nece vratiti
    }

    private int getObjectId() {
        try {
            ResultSet result = maxObjekatIDUpit.executeQuery();
            if(result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; //nikada nece vratiti
    }

    private int getUserId() {
        try {
            ResultSet result = maxKorisnikIDUpit.executeQuery();
            if(result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; //nikada nece vratiti
    }

    public int getIdForUsername(String username) {
        try {
            provjraUsernameUpit.setString(1, username);
            ResultSet rs = provjraUsernameUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  -1; //ako ne postoji taj username
    }

    public String getPasswordForUsername(String username) {
        try {
            dajKorisnikaUpit.setString(1, username);
            ResultSet rs = dajKorisnikaUpit.executeQuery();
            if(rs.next())
                return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean checkUser(String username, String password) {
        //potrebno je da u bazi pronadje korisnika i ako nisu njegovi podaci da mu onemoguci prijavu na aplikaciju
        try {
            provjeraKorisnikaUpit.setString(1, username);
            provjeraKorisnikaUpit.setString(2, password);

            ResultSet result = provjeraKorisnikaUpit.executeQuery();
            if(result.next())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(String name, String surname, String username, String password) {
        //prvo upisuje u tabelu osoba
        try {
            int personId = getPersonId();

            dodajOsobuUpit.setInt(1, personId);
            dodajOsobuUpit.setString(2, name);
            dodajOsobuUpit.setString(3, surname);
            dodajOsobuUpit.setString(4, username);
            dodajOsobuUpit.setString(5, password);
            dodajOsobuUpit.setString(6, "korisnik");

            dodajOsobuUpit.executeUpdate();

            //sada je potrebno dodati i u tabelu korisnik
            dodajKorisnikaUpit.setInt(1, getUserId());
            dodajKorisnikaUpit.setInt(2, personId);
            //ostale parametre cu postaviti na null jer prilikom kreiranja racuna ne treba imati postavljenu clanarinu ili rezervisane termine
            //ti podaci ce se naknadno azurirati
            dodajKorisnikaUpit.setString(3, null);
            dodajKorisnikaUpit.setString(4, null);
            dodajKorisnikaUpit.setInt(5, 0);
            dodajKorisnikaUpit.setInt(6, 0);
            dodajKorisnikaUpit.setInt(7, 0);

            dodajKorisnikaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkUsername(String username) {
        try {
            provjraUsernameUpit.setString(1, username);
            ResultSet result = provjraUsernameUpit.executeQuery();
            if(result.next())
                return true; //znaci da je taj username vec zauzet

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updatePassword(String username, String newPassword) {
        try {
            int id = getIdForUsername(username);
            if(id == -1) {
                //ako nema tog korisnika u bazi
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Greška!");
                alert.setHeaderText("Neispravno koričko ime!");
                alert.setContentText("Ponovite Vaš unos.");
                alert.showAndWait();
            }
            else {
                azurirajPasswordUpit.setString(1, newPassword);
                azurirajPasswordUpit.setInt(2, id);
                azurirajPasswordUpit.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            korisniciUpit.setString(1, "korisnik");
            ResultSet result = korisniciUpit.executeQuery();
            while (result.next()) {
                User tmp = new User();
                tmp.setName(result.getString(2));
                tmp.setSurname(result.getString(3));
                tmp.setUsername(result.getString(4));
                tmp.setPassword(result.getString(5));

                users.add(tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void editUser(User user) {
        try {
            izmijeniKorisnika.setString(1, user.getName());
            izmijeniKorisnika.setString(2, user.getSurname());
            izmijeniKorisnika.setString(3, user.getUsername());
            izmijeniKorisnika.setString(4, user.getPassword());
            int id = getIdForUsername(user.getUsername());
            if(id != -1)
                izmijeniKorisnika.setInt(5, id);
            else
                return;

            izmijeniKorisnika.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Object> getAllObjects() {
        ArrayList<Object> objects = new ArrayList<>();
        try {
            ResultSet result = objektiUpit.executeQuery();
            while (result.next()){
                Object tmp = new Object(result.getString(2), result.getString(4), result.getString(5), result.getDouble(3));
                objects.add(tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objects;
    }

    public int getUserIdForPersonId(int id) {
        try {
            idKorisnikaUpit.setInt(1, id);

            ResultSet rs = idKorisnikaUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; //nece nikad vratiti ovo
    }

    public int getActivityIdForUserId(int id) {
        try {
            idAktivnostUpit.setInt(1, id);

            ResultSet rs = idAktivnostUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; //nece nikad vratiti ovo
    }


    public void deleteActivityUser(int userId) {
        try {
            izbirsiKorisnikAktivnostUpit.setInt(1, userId);

            izbirsiKorisnikAktivnostUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteActivity(int activityId) {
        try {
            izbrisiAktivnostUpit.setInt(1, activityId);

            izbrisiAktivnostUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try {
            izbrisiKorisnikaUpit.setInt(1, userId);

            izbrisiKorisnikaUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(int personId) {
        try {
            izbrisiOsobuUpit.setInt(1, personId);

            izbrisiOsobuUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addObject(Object object) {
        try {
            int id = getObjectId();

            dodajObjekatUpit.setInt(1, id);
            dodajObjekatUpit.setString(2, object.getName());
            dodajObjekatUpit.setDouble(3, object.getAverageRate());
            dodajObjekatUpit.setString(4, object.getMunicipality());
            dodajObjekatUpit.setString(5, object.getAdress());

            dodajObjekatUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void editObject(Object object){
        try {
            izmijeniObjekatUpit.setString(1, object.getName());
            izmijeniObjekatUpit.setDouble(2, object.getAverageRate());
            izmijeniObjekatUpit.setString(3, object.getMunicipality());
            izmijeniObjekatUpit.setString(4, object.getAdress());
            int id = getObjectIdForNameAndAddress(object.getName(), object.getAdress());
            izmijeniObjekatUpit.setInt(5, id);

            izmijeniObjekatUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getObjectIdForNameAndAddress(String name, String adress) {
        //objekat cu pretrazivati po nazivu i adresi
        //nema sanse da na istoj adresi postoje 2 objekta sa istim nazivom, sto ce nam obezbijediti jedinstven id
        try {
            idObjektaUpit.setString(1, name);
            idObjektaUpit.setString(2, adress);

            ResultSet result = idObjektaUpit.executeQuery();
            System.out.println(result.next());
            if(result.next()) {
                return result.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getTrainingIdForObject(int idObjekta) {
        try {
            idTreningUpit.setInt(1, idObjekta);
            ResultSet rs = idTreningUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void deleteTraining(int id) {
        try {
            izbrisiTreningZaObjekatUpit.setInt(1, id);
            izbrisiTreningZaObjekatUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteObjectDiscipline(int id) {
        try {
            izbrisiObjekatDisciplinaUpit.setInt(1, id);
            izbrisiTreningZaObjekatUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteObject(int id) {
        try {
            izbrisiObjekatUpit.setInt(1, id);
            izbrisiObjekatUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
