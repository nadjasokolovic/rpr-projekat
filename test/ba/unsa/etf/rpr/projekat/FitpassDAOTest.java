package ba.unsa.etf.rpr.projekat;

import ba.unsa.etf.rpr.projekat.FitpassDAO;
import ba.unsa.etf.rpr.projekat.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class FitpassDAOTest {

    @Test
    void numberOfUsersTest() {
        //Testira ukupan broj korisnika u bazi
        FitpassDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        FitpassDAO dao = FitpassDAO.getInstance();

        ArrayList<User> users = dao.getAllUsers();
        assertEquals(5, users.stream().count());
    }

    @Test
    void addUserTest() {
        //Testira da li je dodavanje korisnika uspjesno
        FitpassDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        FitpassDAO dao = FitpassDAO.getInstance();
        int numberBefore = dao.getAllUsers().size(); //treba biti 5

        dao.addUser(new User(1, "Neko", "Neko", "test", "test"));
        int numberAfter = dao.getAllUsers().size();

        assertAll ( "Adding user",
                () -> assertEquals(5, numberBefore),
                () -> assertEquals(6, numberAfter)
        );
    }

    @Test
    void editUserTest() {
        //Testira da li se podaci o korisniku ispravno izmjenjuju
        FitpassDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        FitpassDAO dao = FitpassDAO.getInstance();
        User u = new User(2, "Omar", "Sokolović", "osokolovic", "test");
        dao.editUser(u);
        //pronaci cemo sve korisnike
        ArrayList<User> users = dao.getAllUsers();
        //pronaci cu korisnika kojeg sam izmijenila
        ArrayList<User> tmp = users.stream().filter(user -> user.getUsername().equals("osokolovic")).collect(Collectors.toCollection(ArrayList::new));
        assertAll ( "Editing user",
                () -> assertEquals(5, users.size()),
                () -> assertEquals(1, tmp.size()),
                () -> assertEquals("test", tmp.get(0).getPassword())
        );
    }


}
