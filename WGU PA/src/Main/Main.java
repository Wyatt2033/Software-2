package Main;

import Database.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Main Class, Loads login view.
 */
public class Main extends Application {
    @Override

    /**
     * Starts application off at login screen.
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Initializes and closes connection with database.
     * @param args args
     */
    public static void main(String[] args){
        DB.startConnection();
        launch(args);
        DB.closeConnection();


    }


}