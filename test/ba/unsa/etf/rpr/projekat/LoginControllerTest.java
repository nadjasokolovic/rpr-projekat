package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {
    Stage theStage;
    LoginController ctrl;

    @Start
    public void start (Stage stage) throws Exception {
        // Brisemo bazu za slucaj da su prethodni testovi kreirali/brisali drzave
        FitpassDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        FitpassDAO dao = FitpassDAO.getInstance();

        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        ctrl = new LoginController(dao);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void validationUsernameTest(FxRobot robot) {
        //Polje username
        // Na pocetku polje ima neku svijetlo narandzastu boju
        TextField username = robot.lookup("#usernameFld").queryAs(TextField.class);
        Background bg = username.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffe6b3"))
                colorFound = true;
        assertTrue(colorFound);

        // Upisujemo username
        robot.clickOn("#usernameFld");
        robot.write("n");
        //ovo nije ispravno
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        // Sada je username validan
        robot.write("sokolovic");
        username = robot.lookup("#usernameFld").queryAs(TextField.class);
        bg = username.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("adff2f"))
                colorFound = true;
        assertTrue(colorFound);

    }

}