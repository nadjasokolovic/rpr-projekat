package ba.unsa.etf.rpr.projekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        //postavljanje bosanskog kao defaultnog jezika
        Locale.setDefault(new Locale("bs", "BA"));

        FitpassDAO dao = FitpassDAO.getInstance();
        LoginController ctrl = new LoginController(dao);
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Fitpass Sarajevo");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
//    public static void main(String[] args) {
//        // write your code here
//        FitpassDAO dao = FitpassDAO.getInstance();
//        System.out.println(dao.osobeIzBaze());
//    }

}