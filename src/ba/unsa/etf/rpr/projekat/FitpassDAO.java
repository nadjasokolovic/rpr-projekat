package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

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
    private PreparedStatement probniUpit;


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
}
