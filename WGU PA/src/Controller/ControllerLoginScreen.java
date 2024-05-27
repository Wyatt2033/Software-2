package Controller;

import Database.DAOAppointments;
import Database.DAOUsers;
import Main.Main;
import Model.ModelAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controller Class for Login Screen View.
 */
public class ControllerLoginScreen {
    /**
     * Sign In Button.
     */
    public Button signInButton;
    /**
     * Log in Password Text.
     */
    public TextField passwordBox;
    /**
     * Log in Username Text.
     */
    public TextField usernameBox;
    /**
     * Log In Welcome Label.
     */
    public Label welcomeLabel;
    /**
     * Log In Username Label.
     */
    public Label usernameLabel;
    /**
     * Log in Password Label.
     */
    public Label passwordLabel;
    /**
     * Log In Zone Label.
     */
    public Label zoneLabel;
    /**
     * Log In User Zone ID label.
     */
    public Label identifiedZoneLabel;
    /**
     * Log In Username/Password Error Label.
     */
    public Label errorLabel;
    /**
     * Language Resource Bundle based on default user language.
     */
    private final ResourceBundle setLang = ResourceBundle.getBundle("Resource/Lang", Locale.getDefault());
    /**
     * Log In Exit Button.
     */
    public Button exitButton;
    private Object Frame;

    /**
     * Adds text to all text fields in the login screen, text will be english or french based on user's computer's default language.
     * Sets and displays current user's timezone.
     */
    public void initialize() {
        welcomeLabel.setText(setLang.getString("application"));
        usernameLabel.setText(setLang.getString("username"));
        passwordLabel.setText(setLang.getString("password"));
        zoneLabel.setText(setLang.getString("timeZone"));
        identifiedZoneLabel.setText(String.valueOf(TimeZone.getDefault().getID()));
        signInButton.setText(setLang.getString("signIn"));
        exitButton.setText(setLang.getString("exit"));
        UIManager.put("OptionPane.noButtonText", setLang.getString("n"));
        UIManager.put("OptionPane.yesButtonText", setLang.getString("y"));




    }

    /**
     * Runs through a check to confirm the text fields are filled before validating login info with the database.
     *  * Also checks for upcoming appointments and displays an alter pop up for each upcoming one.
     * @param event Button Click
     * @throws IOException IO Exception
     */
    @FXML
    private void signInButtonAction(ActionEvent event) throws IOException {

        if (usernameBox.getText().isEmpty() || passwordBox.getText().isEmpty()) {
            errorLabel.setText(setLang.getString("Error"));
            System.out.println("No Text");
            String userLogin = "Failed Login - textfield empty.";
            userActivityTracker(userLogin);
        }
        else {


           boolean successfulLogin = DAOUsers.login(usernameBox.getText(), passwordBox.getText());

            if (successfulLogin) {

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();

                ((Node) (event.getSource())).getScene().getWindow().hide();
                String activity = "Login Successful - user:" + usernameBox.getText() + " logged in.";
                userActivityTracker(activity);

                ObservableList<ModelAppointments> appointmentAlerts = DAOAppointments.AppointmentAlert();
                if (appointmentAlerts != null) {

                    int size = appointmentAlerts.size();
                    System.out.println(size);
                    int n = 0;
                    if (n < size) {


                        while (n < size) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("The following appointment is scheduled to start within the next 15 minutes:");
                            alert.setContentText("Appointment ID: " + appointmentAlerts.get(n).getAppID() + "\n" +
                                    "Appointment Start: " + appointmentAlerts.get(n).getAppStart());
                            alert.showAndWait();
                            n++;
                        }

                    }
                    else   {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("There are no upcoming appointments in the next 15 minutes.");
                        alert.showAndWait();
                    }
                }


            } else {
                errorLabel.setText(setLang.getString("Error2"));
                System.out.println("Login Failed");
                String activity = "Login failed - incorrect username/password.";
                userActivityTracker(activity);
            }
        }
    }


    /**
     * Writes user log in attempts to a text file located in the programs root folder.
     * @param activity User Activity
     * @throws IOException IO Exception
     */
    public static void userActivityTracker(String activity) throws IOException {

        try(FileWriter fw = new FileWriter("login_activity.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)){
            out.println(activity);
            out.println(new Timestamp(System.currentTimeMillis()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Exists application completely, confirms with an alert pop up.
     * @param actionEvent Button Click
     */
    public void onExitButton(ActionEvent actionEvent) {
        if (JOptionPane.showConfirmDialog((Component) Frame,setLang.getString("exit?"),setLang.getString("application2"),
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            System.exit(0);
    }
}
