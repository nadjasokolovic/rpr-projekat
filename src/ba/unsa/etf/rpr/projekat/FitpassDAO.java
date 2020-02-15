package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.scene.control.Alert;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FitpassDAO {
    //konstanta koja oznacava broj termina koje dobijaju aktivni korisnici
    public final int numberOfFreeTrainings = 5;

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
    private PreparedStatement dodajObjekatUpit, maxObjekatIDUpit, izmijeniObjekatUpit, idObjektaUpit, idTreningUpit, izbrisiTreningZaObjekatUpit, izbrisiObjekatOcjenaUpit;
    private PreparedStatement izbrisiObjekatDisciplinaUpit, izbrisiObjekatUpit, dodajOcjenuZaObjekatUpit, objektiZaDisciplinuUpit;
    private PreparedStatement disciplineZaObjekatUpit, izbrisiDisciplinuZaObjekat, dodajDisciplinuUpit, maxDisciplinaIDUpit, dodajDisciplinuZaObjekatUpit, postojiDisciplinaUpit, idDisciplineUpit;
    private PreparedStatement iskoristenoTerminaUpit, ukupnoTerminaUpit, obavijestiUpit, korisnikUpit, ocjeneZaObjekatUpit, disciplineUpit;
    private PreparedStatement idObjektaZaNazivUpit, treninziZaObjekatUpit, azurirajTreningKorisnikaUpit, evidentirajClanarinu, maxObavijestIDUpit, dodajObavijestUpit;
    private PreparedStatement krajClanarineUpit, korisniciTreninziUpit, tipOsobeUpit, aktivnostKorisnikaUpit, dodajTermineUpit;

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
            maxObavijestIDUpit = conn.prepareStatement("SELECT MAX(obavijest_id)+1 FROM obavijest");
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
            dodajObjekatUpit = conn.prepareStatement("INSERT INTO objekat VALUES(?,?,?,?)");
            maxObjekatIDUpit = conn.prepareStatement("SELECT MAX(objekat_id)+1 FROM objekat");
            izmijeniObjekatUpit = conn.prepareStatement("UPDATE objekat SET naziv=?, opcina=?, adresa=? WHERE objekat_id=?");
            idObjektaUpit = conn.prepareStatement("SELECT o.objekat_id FROM objekat o WHERE o.naziv=? AND o.adresa=?");
            idTreningUpit = conn.prepareStatement("SELECT t.trening_id FROM trening t WHERE t.objekat_id=?");
            izbrisiTreningZaObjekatUpit = conn.prepareStatement("DELETE FROM trening WHERE objekat_id=?");
            izbrisiObjekatDisciplinaUpit = conn.prepareStatement("DELETE FROM objekat_disciplina WHERE objekat_id=?");
            izbrisiObjekatUpit = conn.prepareStatement("DELETE FROM objekat WHERE objekat_id=?");
            disciplineZaObjekatUpit = conn.prepareStatement("SELECT d.disciplina_id, d.naziv FROM disciplina d, objekat_disciplina od, objekat o WHERE o.objekat_id=od.objekat_id AND od.disciplina_id=d.disciplina_id AND o.objekat_id=?");
            izbrisiDisciplinuZaObjekat = conn.prepareStatement("DELETE FROM objekat_disciplina WHERE objekat_id=? AND disciplina_id=?");
            dodajDisciplinuUpit = conn.prepareStatement("INSERT INTO disciplina VALUES(?,?)");
            maxDisciplinaIDUpit = conn.prepareStatement("SELECT MAX(disciplina_id)+1 FROM disciplina");
            dodajDisciplinuZaObjekatUpit = conn.prepareStatement("INSERT INTO objekat_disciplina VALUES(?,?)");
            postojiDisciplinaUpit = conn.prepareStatement("SELECT * FROM disciplina WHERE naziv=?");
            idDisciplineUpit = conn.prepareStatement("SELECT disciplina_id FROM disciplina WHERE naziv=?");
            iskoristenoTerminaUpit = conn.prepareStatement("SELECT k.iskoristeno_termina FROM korisnik k WHERE k.korisnik_id=?");
            ukupnoTerminaUpit = conn.prepareStatement("SELECT k.ukupno_termina FROM korisnik k WHERE k.korisnik_id=?");
            obavijestiUpit = conn.prepareStatement("SELECT o.tekst FROM obavijest o WHERE o.korisnik_id=?");
            korisnikUpit = conn.prepareStatement("SELECT o.osoba_id, o.ime, o.prezime, o.username, o.password FROM osoba o WHERE o.osoba_id=?");
            izbrisiObjekatOcjenaUpit = conn.prepareStatement("DELETE FROM objekat_ocjena WHERE objekat_id=?");
            dodajOcjenuZaObjekatUpit = conn.prepareStatement("INSERT INTO objekat_ocjena VALUES(?,?)");
            objektiZaDisciplinuUpit = conn.prepareStatement("SELECT o.objekat_id, o.naziv, o.opcina, o.adresa FROM objekat o, objekat_disciplina od, disciplina d WHERE o.objekat_id=od.objekat_id AND od.disciplina_id=d.disciplina_id AND d.disciplina_id=?");
            ocjeneZaObjekatUpit = conn.prepareStatement("SELECT c.iznos FROM objekat o, objekat_ocjena oc, ocjena c WHERE o.objekat_id=? AND o.objekat_id=oc.objekat_id AND oc.ocjena_id=c.ocjena_id");
            disciplineUpit = conn.prepareStatement("SELECT * FROM disciplina");
            idObjektaZaNazivUpit = conn.prepareStatement("SELECT o.objekat_id FROM objekat o WHERE o.naziv=?");
            treninziZaObjekatUpit = conn.prepareStatement("SELECT t.trening_id, t.pocetak, t.kraj, t.dan FROM trening t WHERE t.objekat_id=? AND t.dan=?");
            azurirajTreningKorisnikaUpit = conn.prepareStatement("UPDATE korisnik SET trening_id=?, iskoristeno_termina=? WHERE korisnik_id=?");
            evidentirajClanarinu = conn.prepareStatement("UPDATE korisnik SET pocetak_clanarine=?, kraj_clanarine=?, ukupno_termina=?, iskoristeno_termina=? WHERE osoba_id=?");
            dodajObavijestUpit = conn.prepareStatement("INSERT INTO obavijest VALUES(?,?,?)");
            krajClanarineUpit = conn.prepareStatement("SELECT kraj_clanarine FROM korisnik WHERE korisnik_id=?");
            korisniciTreninziUpit = conn.prepareStatement("SELECT k.korisnik_id, o.ime, o.prezime, o.username, o.password, t.trening_id, t.pocetak, t.kraj, t.dan FROM osoba o, korisnik k, trening t WHERE o.osoba_id=k.osoba_id AND k.trening_id=t.trening_id AND t.dan=?");
            tipOsobeUpit = conn.prepareStatement("SELECT tip_osobe FROM osoba WHERE username=?");
            aktivnostKorisnikaUpit = conn.prepareStatement("SELECT a.aktivnost_id, a.mjesec, a.godina FROM aktivnost a, korisnik_aktivnost ka, korisnik k WHERE k.korisnik_id=ka.korisnik_id AND ka.aktivnost_id=a.aktivnost_id AND k.korisnik_id=?");
            dodajTermineUpit = conn.prepareStatement("UPDATE korisnik SET ukupno_termina=? WHERE korisnik_id=?");

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

    //metode koje daju maksimalan id iz tabele, kako bi omogucile ispravno dodavanje korisnika i objekata
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

    private int getDisciplineId() {
        try {
            ResultSet result = maxDisciplinaIDUpit.executeQuery();
            if(result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; //nikada nece vratiti
    }

    private int getNotificationId() {
        try {
            ResultSet result = maxObavijestIDUpit.executeQuery();
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

    public void addUser(User user) {
        //prvo upisuje u tabelu osoba
        try {
            int personId = getPersonId();

            dodajOsobuUpit.setInt(1, personId);
            dodajOsobuUpit.setString(2, user.getName());
            dodajOsobuUpit.setString(3, user.getSurname());
            dodajOsobuUpit.setString(4, user.getUsername());
            dodajOsobuUpit.setString(5, user.getPassword());
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

            //Ovaj korisnik ce se upisati i u datoteku persons.txt
            saveInFile(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void saveInFile(User user) {
        File file = new File("persons.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write("\n" + user.getName() + " " + user.getSurname());

            br.close();
            fr.close();
        } catch (IOException e) {
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
                tmp.setId(result.getInt(1));
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
            izmijeniKorisnika.setInt(5, user.getId());

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
                Object tmp = new Object(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
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
            //dodajObjekatUpit.setDouble(3, object.getAverageRate());
            dodajObjekatUpit.setString(3, object.getMunicipality());
            dodajObjekatUpit.setString(4, object.getAdress());

            dodajObjekatUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void editObject(Object object){
        try {
            izmijeniObjekatUpit.setString(1, object.getName());
            //izmijeniObjekatUpit.setDouble(2, object.getAverageRate());
            izmijeniObjekatUpit.setString(2, object.getMunicipality());
            izmijeniObjekatUpit.setString(3, object.getAdress());
            izmijeniObjekatUpit.setInt(4, object.getId());

            izmijeniObjekatUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            izbrisiObjekatDisciplinaUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteObjectRate(int idObjekta) {
        try {
            izbrisiObjekatOcjenaUpit.setInt(1,idObjekta);
            izbrisiObjekatOcjenaUpit.executeUpdate();
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

    public ArrayList<Discipline> getDisciplinesForObject(int objectId){
        ArrayList<Discipline> tmp = new ArrayList<>();
        try {
            disciplineZaObjekatUpit.setInt(1, objectId);
            ResultSet result = disciplineZaObjekatUpit.executeQuery();
            while (result.next()) {
                Discipline d = new Discipline(result.getInt(1), result.getString(2));
                tmp.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    public void deleteDisciplineForObject(int objectId, int disciplineId){
        try {
            izbrisiDisciplinuZaObjekat.setInt(1, objectId);
            izbrisiDisciplinuZaObjekat.setInt(2, disciplineId);
            izbrisiDisciplinuZaObjekat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int addDiscipline(String name) {
        //dodaje disciplinu u bazu i odmah vraca id te dodane discipline
        int id = -1;
        try {
            ResultSet rs = maxDisciplinaIDUpit.executeQuery();
            id = rs.getInt(1);
            dodajDisciplinuUpit.setInt(1, id);
            dodajDisciplinuUpit.setString(2, name);

            dodajDisciplinuUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private boolean postojiDisciplina(String naziv) {
        try {
            postojiDisciplinaUpit.setString(1, naziv);
            ResultSet rs = postojiDisciplinaUpit.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int idDiscipline(String naziv) {
        try {
            idDisciplineUpit.setString(1, naziv);
            ResultSet rs = idDisciplineUpit.executeQuery();
            if(rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void addDisciplineForObject(int id, String name) {
        //prvo provjerimo postoji li ta disciplina u bazi, ako ne postoji dodamo je
        //ako ta disciplina u tabeli disciplina vec postoji ne trebamo je dodavati, nego samo dodamo u tabelu objekat_disciplina
        if(!postojiDisciplina(name)) {
            int idDiscipline = addDiscipline(name);
            if(idDiscipline == -1)
                return; //znaci nije proslo dodavanje

            //potrebno je dodati u tabelu objekat_disciplina
            try {
                dodajDisciplinuZaObjekatUpit.setInt(1, id);
                dodajDisciplinuZaObjekatUpit.setInt(2, idDiscipline);

                dodajDisciplinuZaObjekatUpit.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            //ako postoji potreban nam je samo id te discipline da bismo mogli dodati u tabelu objekat_disciplina
            int disciplineId = idDiscipline(name);
            try {
                dodajDisciplinuZaObjekatUpit.setInt(1, id);
                dodajDisciplinuZaObjekatUpit.setInt(2, disciplineId);

                dodajDisciplinuZaObjekatUpit.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getNumberTerminsUsed(int userId) {
        try {
            iskoristenoTerminaUpit.setInt(1, userId);

            return iskoristenoTerminaUpit.executeQuery().getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getNumberOfTermins(int userId) {
        try {
            ukupnoTerminaUpit.setInt(1, userId);

            return ukupnoTerminaUpit.executeQuery().getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<String> getNotifications(int userId) {
        ArrayList<String> notifications = new ArrayList<>();
        try {
            obavijestiUpit.setInt(1, userId);
            ResultSet rs = obavijestiUpit.executeQuery();
            while (rs.next())
                notifications.add(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public void editProfil(String user, String newName, String newSurname, String newUsername, String newPassword) {
        //potrebno je pronaci osobu na osnovu username
        int personId = getIdForUsername(user);
        try {
            izmijeniKorisnika.setString(1, newName);
            izmijeniKorisnika.setString(2, newSurname);
            izmijeniKorisnika.setString(3, newUsername);
            izmijeniKorisnika.setString(4, newPassword);
            izmijeniKorisnika.setInt(5, personId);

            izmijeniKorisnika.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(int personId) {
        try {
            korisnikUpit.setInt(1, personId);
            ResultSet result = korisnikUpit.executeQuery();
            return new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addObjectRate(int objectId, int rate) {
        try {
            dodajOcjenuZaObjekatUpit.setInt(1, objectId);
            //mogu ovako samo ocjenu, jer u bazi svaka ocjena se poklapa sa njenim id-om, npr ocjena 3 ima id 3
            dodajOcjenuZaObjekatUpit.setInt(2, rate);

            dodajOcjenuZaObjekatUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Object> getObjectsForDiscipline(int disciplineId) {
        //ova metoda ce na osnovu id discipline vracati listu svih objekata u kojim je ta disciplina moguca
        ArrayList<Object> objects = new ArrayList<>();
        try {
            objektiZaDisciplinuUpit.setInt(1, disciplineId);
            ResultSet result = objektiZaDisciplinuUpit.executeQuery();
            while (result.next())
                objects.add(new Object(result.getInt(1), result.getString(2), result.getString(3), result.getString(4)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objects;
    }

    public Double averageRate(int objectId) {
        //ova metoda ce ucitati sve ocjene za neki objekat i izracunati njihov prosjek
        double averageRate = 0, average = 0;
        int vel = 0;
        try {
            ocjeneZaObjekatUpit.setInt(1, objectId);
            ResultSet result = ocjeneZaObjekatUpit.executeQuery();
            while (result.next()) {
                averageRate += result.getDouble(1);
                vel++;
            }
            average = averageRate / vel;
            //Zaokruzivanje na dvije decimale
            average = Math.round(average * 100);
            average /= 100;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return average;
    }

    public ArrayList<Discipline> getAllDisciplines(){
        ArrayList<Discipline> tmp = new ArrayList<>();
        try {
            ResultSet result = disciplineUpit.executeQuery();
            while (result.next())
                tmp.add(new Discipline(result.getInt(1), result.getString(2)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    public ArrayList<Training> getTrainingsOnDay(String objectName, String dayOfWeek) {
        ArrayList<Training> trainings = new ArrayList<>();
        //prvo je potrebno pronaci id objekta na osnovu naziva
        int objectId = getObjectIdForName(objectName);

        //zatim pronalazimo sve treninge u tabeli Trening koji su vezani za taj objekat
        try {
            treninziZaObjekatUpit.setInt(1, objectId);
            treninziZaObjekatUpit.setString(2, dayOfWeek);
            ResultSet result = treninziZaObjekatUpit.executeQuery();
            while (result.next()){
                int id = result.getInt(1);
                //u bazi su pocetak i kraj dati u formatu hh:mm(npr. 10:00)
                // dodat cu jos :00 za sekunde i onda cu takvo vrijeme moci slati u konstruktor LocalTime
                DateTimeFormatter parser = DateTimeFormatter.ofPattern("H:mm");

                String pocetak = result.getString(2);
                LocalTime start = LocalTime.parse(pocetak, parser);
                String kraj = result.getString(3);
                LocalTime end = LocalTime.parse(kraj, parser);

                String day = result.getString(4);

                //poslat cu null kao parametar za user-a jer za ovaj dio to nije bitno
                trainings.add(new Training(id, start, end, null, day));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainings;
    }

    private int getObjectIdForName(String objectName) {
        try {
            idObjektaZaNazivUpit.setString(1, objectName);
            return idObjektaZaNazivUpit.executeQuery().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int reserveTraining(int userId, int trainingId) {
        int successful = 1; //1 - moguca rezervacija jer ima dovoljno neiskoristenih termina
        int ukupno = getNumberOfTermins(userId);
        int iskoristeno = getNumberTerminsUsed(userId);
        //Provjera da li ima dovoljno neiskoristenih termina
        if(iskoristeno >= ukupno)
            successful = 2; //2 - rezervacija nije moguca jer nema dovoljno neiskoristenih termina

        //Provjera da li je clanarina jos uvijek aktivna
        LocalDate now = LocalDate.now();
        String endMembershipFee = getEndOfMembershipFee(userId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");
        LocalDate endLocalDate = LocalDate.parse(endMembershipFee, formatter);
        if(endLocalDate.isBefore(now))
            successful = 3;

        if(successful == 1){
            try {
                azurirajTreningKorisnikaUpit.setInt(1, trainingId);
                azurirajTreningKorisnikaUpit.setInt(2, iskoristeno + 1);
                azurirajTreningKorisnikaUpit.setInt(3, userId);

                azurirajTreningKorisnikaUpit.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                successful = 4; //3 - greska pri radu sa bazom
            }
        }
        return successful;
    }

    public void extendMembershipFee(int personId, String start, String end, int number) {
        try {
            int id = getUserIdForPersonId(personId);
            evidentirajClanarinu.setString(1, start);
            evidentirajClanarinu.setString(2, end);
            evidentirajClanarinu.setInt(3, number);
            evidentirajClanarinu.setInt(4, 0);
            evidentirajClanarinu.setInt(5, id);

            evidentirajClanarinu.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNotification(int personId, String notification) {
        try {
            int id = getUserIdForPersonId(personId);
            dodajObavijestUpit.setInt(1, getNotificationId());
            dodajObavijestUpit.setString(2, notification);
            dodajObavijestUpit.setInt(3, id);

            dodajObavijestUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getEndOfMembershipFee(int userId) {
        try {
            krajClanarineUpit.setInt(1, userId);
            return krajClanarineUpit.executeQuery().getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ""; //nece nikad vratiti ovo
    }

    public Map<User, Training> getTrainingsForAllUsers(String day) {
        //Kljuc mape je korisnik koji je rezervisao trening, a vrijednost je rezervisani trening
        Map<User, Training> tmp = new HashMap<>();

        try {
            korisniciTreninziUpit.setString(1, day);
            ResultSet result = korisniciTreninziUpit.executeQuery();
            while (result.next()) {
                User u = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));

                DateTimeFormatter parser = DateTimeFormatter.ofPattern("H:mm");
                String pocetak = result.getString(7);
                LocalTime start = LocalTime.parse(pocetak, parser);
                String kraj = result.getString(8);
                LocalTime end = LocalTime.parse(kraj, parser);

                Training t = new Training(result.getInt(6), start, end, u, day);

                tmp.put(u, t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    public String getTypeOfPerson(String username) {
        try {
            tipOsobeUpit.setString(1, username);
            return tipOsobeUpit.executeQuery().getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<Activity> getActivityForUser(String username) {
        Activity[] userActivities = new Activity[10000];
        int personId = getIdForUsername(username);
        int userId = getUserIdForPersonId(personId);
        try {
            aktivnostKorisnikaUpit.setInt(1, userId);
            ResultSet result = aktivnostKorisnikaUpit.executeQuery();
            int i = 0;
            while (result.next()) {
                userActivities[i] = new Activity(result.getInt(1), result.getString(2), result.getInt(3));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Activity>(Arrays.asList(userActivities));
    }

    public void addTrainings(String username) {
        int personId = getIdForUsername(username);
        int userId = getUserIdForPersonId(personId);
        int number = getNumberOfTermins(userId);
        try {
            dodajTermineUpit.setInt(1, number + this.numberOfFreeTrainings);
            dodajTermineUpit.setInt(2, userId);

            dodajTermineUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
