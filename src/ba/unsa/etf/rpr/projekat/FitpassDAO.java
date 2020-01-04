package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    private Connection conn;

    private FitpassDAO() {
        try {
            //pokusaj konekcije
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
            //ovdje u posebnom try bloku treba probati izvrsiti neki upit
            //ako taj upit ne uspije u catch bloku kreiramo bazu i napunimo je defaultnim podacima
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
