package ba.unsa.etf.rpr.projekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FitpassDAO dao = FitpassDAO.getInstance();
        LoginController ctrl = new LoginController(dao);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Fitpass Sarajevo");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

    /*GeografijaDAO dao = GeografijaDAO.getInstance();
    DrzavaController ctrl = new DrzavaController(dao);
    Stage myStage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
        loader.setController(ctrl);
                Parent root = null;
                try {
                root = loader.load();
                } catch (IOException e) {
                e.printStackTrace();
                }
                myStage.setTitle("Država");
                myStage.setScene(new Scene(root, 400, 200));
                myStage.show();
*/