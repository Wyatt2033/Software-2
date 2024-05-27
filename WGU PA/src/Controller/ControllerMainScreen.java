package Controller;

import Database.DAOAppointments;
import Database.DAOCustomers;
import Model.ModelAppointments;
import Model.ModelCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller Class for Main Screen.
 */
public class ControllerMainScreen implements Initializable {
    /**
     *  Application ID Table Column.
     */
    public TableColumn<Object, Object> appID;
    /**
     * Application Description Table Column.
     */
    public TableColumn<Object, Object> appDesc;
    /**
     * Application Location. Table Column.
     */
    public TableColumn<Object, Object> appLoc;
    /**
     * Application Contact Table Column.
     */
    public TableColumn<Object, Object> appContact;
    /**
     * Application Type Table Column.
     */
    public TableColumn<Object, Object> appType;
    /**
     * Application Start Date/Time Table Column.
     */
    public TableColumn<Object, Object> appStart;
    /**
     * Application End Date/Time Table Column.
     */
    public TableColumn<Object, Object> appEnd;
    /**
     * Application Customer ID Table Column.
     */
    public TableColumn<Object, Object> appCustID;
    /**
     * Application User ID Table Column.
     */
    public TableColumn<Object, Object> userID;
    /**
     * Customer ID Table Column.
     */
    public TableColumn<Object, Object> custID;
    /**
     * Customer Name Table Column.
     */
    public TableColumn<Object, Object> custName;
    /**
     * Customer Address Table Column.
     */
    public TableColumn<Object, Object> custAddress;
    /**
     * Customer First Division Table Column.
     */
    public TableColumn<Object, Object> custFirstDiv;
    /**
     * Customer Postal Table Column.
     */
    public TableColumn<Object, Object> custPostal;
    /**
     * Customer Phone Table Column.
     */
    public TableColumn<Object, Object> custPhone;
    /**
     * Appointment Title Table Column.
     */
    public TableColumn<Object, Object> appTitle;
    /**
     * Appointment Tableview Tab.
     */
    public Tab appTab;
    /**
     * Appointment Model TableView.
     */
    public TableView<Model.ModelAppointments> appTable;
    /**
     * Customer Country Table Column.
     */
    public TableColumn<Object, Object> custCountry;
    /**
     * Customer Model Tableview.
     */
    public TableView<Model.ModelCustomer> custTable;
    /**
     * Model Appointment Appointment being Modified,
     */
    public static ModelAppointments appointmentModify;
    /**
     * Model Customer Customer being Modified.
     */
    public static ModelCustomer customerModify;
    private Object Frame;
    /**
     * Model Appointment Appointment Upcoming Alert.
     */
    private ModelAppointments alertAppointment;


    /**
     * Gets the appointment being modified.
     * @return appointment to modify.
     */
    public static ModelAppointments getAppointmentModify() { return appointmentModify;}

    /**
     * Gets the customer being modified.
     * @return customer to modify.
     */
    public static ModelCustomer getCustomerModify() { return customerModify;}

    /**
     * Provided functionality to the all appointments radio button.
     * @param actionEvent Button Click
     */
    public void onRadAll(ActionEvent actionEvent) {
        appTable.setItems(DAOAppointments.appointments());
    }

    /**
     * Provided functionality to the filter by month radio button.
     * @param actionEvent Button Click
     */
    public void onRadMonth(ActionEvent actionEvent) {
    appTable.setItems(DAOAppointments.monthView());
    }

    /**
     * Provides functionality to the filter by week radio button.
     * @param actionEvent Button Click
     */
    public void onRadWeek(ActionEvent actionEvent) {
        appTable.setItems(DAOAppointments.weekView());
    }

    /**
     * Populates the appointment and customer table views.

     * @param url Url
     * @param resourceBundle Resource Bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



            appTable.setItems(DAOAppointments.appointments());
            appID.setCellValueFactory(new PropertyValueFactory<>("appID"));
            appTitle.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
            appDesc.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
            appLoc.setCellValueFactory(new PropertyValueFactory<>("appLoc"));
            appContact.setCellValueFactory(new PropertyValueFactory<>("appContact"));
            appType.setCellValueFactory(new PropertyValueFactory<>("appType"));
            appStart.setCellValueFactory(new PropertyValueFactory<>("appStart"));
            appEnd.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
            appCustID.setCellValueFactory(new PropertyValueFactory<>("appCustID"));
            userID.setCellValueFactory(new PropertyValueFactory<>("userID"));

            custTable.setItems(DAOCustomers.customers());
            custID.setCellValueFactory(new PropertyValueFactory<>("custID"));
            custName.setCellValueFactory(new PropertyValueFactory<>("custName"));
            custAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
            custFirstDiv.setCellValueFactory(new PropertyValueFactory<>("custFirstDiv"));
            custCountry.setCellValueFactory(new PropertyValueFactory<>("custCountry"));
            custPostal.setCellValueFactory(new PropertyValueFactory<>("custPostal"));
            custPhone.setCellValueFactory(new PropertyValueFactory<>("custPhone"));


    }


    /**
     * When clicked, brings user to the add appointment screen.
     * @param actionEvent Button Click
     * @throws IOException IO Exception
     */
    @FXML
    private void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Appointment.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Deletes selected appointment.
     * @param actionEvent Button Click
     * @throws IOException IO Exception
     */
    public void onDeleteApp(ActionEvent actionEvent) throws IOException {
        int appID = appTable.getSelectionModel().getSelectedItem().getAppID();
        String appType = appTable.getSelectionModel().getSelectedItem().getAppType();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Appointment: " + appType + " - " + appID);
        alert.setContentText("Are you sure you want to delete this appointment?");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            DAOAppointments.DeleteAppointment(appID);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * When the modify appointment button is clicked, changes modify clicked value to true and gets selected item. If no appointment is selected, a pop is shown.
     * @param actionEvent Button Click
     * @throws IOException IOException
     */
    public void onModifyAppointment(ActionEvent actionEvent) throws IOException {
        ControllerAppointment.modifyClicked = true;
        appointmentModify = appTable.getSelectionModel().getSelectedItem();
        if (appointmentModify != null) {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Appointment.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Appointments");
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please select an appointment to modify.");
            Optional<ButtonType> confirmation = alert.showAndWait();
        }


    }


    /**
     * When clicked, the user is brought to the reports screen.
     * @param actionEvent Button Click
     * @throws IOException IO Exception
     */
    public void onReports(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Reports.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * When clicked, the user is brought to the add customer screen.
     * @param actionEvent Button Click
     * @throws IOException IO Exception
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Customer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Deleted the selected customer and all associated appointments. Pop ups are used to inform user that all associated appointments will also be deleted.
     * @param actionEvent Button Click
     * @throws IOException IO Exception
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws IOException {
        int custID = custTable.getSelectionModel().getSelectedItem().getCustID();
        String custName = DAOCustomers.customerCombo(custID);
        boolean  custAppointments = DAOCustomers.AssociatedAppCust(custID);

        if (custAppointments) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Customer: " + custName + " - " + custID );
                alert.setContentText("This customer has existing appointments, all associated appointments " +
                        "must also be deleted. Do you wish to proceed with deletion?");
                Optional<ButtonType> confirmation2 = alert.showAndWait();
                    if (confirmation2.isPresent() && confirmation2.get() == ButtonType.OK) {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setHeaderText("Customer: " + custName + " - " + custID );
                        alert1.setContentText("This customer and all associated appointments were deleted.");
                        Optional<ButtonType> info = alert1.showAndWait();
                        DAOCustomers.DeleteAssociatedApps(custID);
                        DAOCustomers.DeleteCustomer(custID);
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setTitle("Main Screen");
                        stage.setScene(scene);
                        stage.show();
                    }
            }

        if (!custAppointments) {
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                 alert.setHeaderText("Customer: " + custName + " - " + custID );
                 alert.setContentText("Are you sure you want to delete this customer?");
                 Optional<ButtonType> confirmation = alert.showAndWait();
                    if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                        DAOCustomers.DeleteCustomer(custID);
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setTitle("Main Screen");
                        stage.setScene(scene);
                        stage.show();
                    }
             }
    }


    /**
     * When the modify customer button is clicked, changes modify clicked value to true and gets selected item. If no customer is selected, a pop is shown.
     * @param actionEvent Button Click
     * @throws IOException IO Exception
     */
    public void onModifyCustomer(ActionEvent actionEvent) throws IOException {
        ControllerCustomer.modifyClicked = true;
        customerModify = custTable.getSelectionModel().getSelectedItem();
        if (customerModify != null) {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Customer.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Customer");
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer to modify.");
            Optional<ButtonType> confirmation = alert.showAndWait();
        }


    }

    /**
     * Exists application completely, confirms with an alert pop up.
     * @param actionEvent Button Click
     */
    public void onExitButton(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Exit");
        confirm.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }


}
