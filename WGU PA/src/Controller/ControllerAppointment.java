package Controller;

import Database.DAOAppointments;
import Database.DAOContact;
import Database.DAOCustomers;
import Database.DAOUsers;
import Model.ModelAppointments;
import Model.ModelContact;
import Model.ModelCustomer;
import Model.ModelUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Controller Class for Appointment View.
 */
public class ControllerAppointment {

    /**
     * Appointment Title Text.
     */
    public TextField titleText;
    /**
     * Appointment Description Text.
     */
    public TextField descText;
    /**
     * Appointment Location Text.
     */
    public TextField locText;
    /**
     * Appointment Type Text.
     */
    public TextField typeText;
    /**
     * Appointment Contact Combo.
     */
    public ComboBox<String> contactCombo;
    /**
     * Appointment User Combo.
     */
    public ComboBox<String> userCombo;
    /**
     * Appointment Customer Combo.
     */
    public ComboBox<String> customerCombo;
    /**
     * Appointment Date Start Date Picker.
     */
    public DatePicker dateStartDate;
    /**
     * Appointment Date End Date Picker.
     */
    public DatePicker dateEndDate;
    /**
     * Appointment Start Date TIme Combo.
     */
    public ComboBox<LocalTime> dateStartTime;
    /**
     * Appointment End Date Time Combo.
     */
    public ComboBox<LocalTime> dateEndTime;
    /**
     * Appointment ID Text.
     */
    public TextField AppIDText;
    /**
     * Modified Clicked Boolean.
     */
    public static boolean modifyClicked;
    /**
     * Model Selected Appointment.
     */
    private ModelAppointments selectedAppointment;

    /**
     * Sets the end and start time combo boxes to uneditable until a date is selected from the date picker boxes.
     * Sets combo fields with their respective data.
     * Also checks if user is modifying anm existing appointment.
     */
    public void initialize() {
        dateStartDate.setEditable(false);
        dateEndDate.setEditable(false);
        setComboFields();
        if (modifyClicked) {
            modifyAppointment();
        }

    }

    /**
     * Creates lists of customers, users, and contacts to populate combo boxes.
     */
    public void setComboFields() {
        ObservableList<String> customerComboList = FXCollections.observableArrayList();
        ObservableList<String> userComboList = FXCollections.observableArrayList();
        ObservableList<String> contactComboList = FXCollections.observableArrayList();

        ObservableList<ModelCustomer> customers = DAOCustomers.customerList();
        ObservableList<ModelUser> users = DAOUsers.usersList();
        ObservableList<ModelContact> contacts = DAOContact.contactList();

        if (customers != null) {
            for (ModelCustomer customer : customers) {
                customerComboList.add(customer.getCustID() + " - " + customer.getCustName());
            }
        }

        if (users != null) {
            for (ModelUser user : users) {
                userComboList.add(user.getUserID() + " - " + user.getUsername());
            }
        }

        if (contacts != null) {
            for (ModelContact contact : contacts) {
                contactComboList.add(contact.getContactID() + " - " + contact.getContactName());
            }
        }
        else { System.out.println("Error"); }

        LocalTime officeHours = LocalTime.of(8,0);
        LocalTime officeCloseHours = LocalTime.of(22,0);
        LocalDate officeDate = LocalDate.now();
        ZoneId officeZone = ZoneId.of("America/New_York");
        ZonedDateTime officeTime = ZonedDateTime.of(officeDate,officeHours, officeZone);
        System.out.println( officeTime);


        LocalTime openHours = officeTime.withZoneSameInstant(ZoneId.of(TimeZone.getDefault().getID())).toLocalTime();


        while (officeHours.isBefore(officeCloseHours)) {

            dateStartTime.getItems().add(openHours);
            dateEndTime.getItems().add(openHours);
            openHours = openHours.plusMinutes(15);
            officeCloseHours = officeCloseHours.minusMinutes(15);


        }
        customerCombo.setItems(customerComboList);
        userCombo.setItems(userComboList);
        contactCombo.setItems(contactComboList);

    }

    /**
     * Gets current max appointment ID adds to it.
     * @return new appointment ID.
     */
    public int setAppID () {
        int appID = DAOAppointments.getMaxAppID();
         return appID+1;
    }

    /**
     * Gets value of selected contact combo box item and parses it to select only the contact ID.
     * @return contact ID of selected item.
     */
    public int contactComboInt() {
        String contactString = String.valueOf(contactCombo.getValue());
        return  Integer.parseInt(contactString.replaceAll("[\\D]",""));
    }

    /**
     * Gets value of selected user combo box item and parses it to select only the user ID.
     * @return user ID of selected item.
     */
    public int userComboInt() {
        String userString = String.valueOf(userCombo.getValue());
        return  Integer.parseInt(userString.replaceAll("[\\D]",""));
    }

    /**
     * Gets value of selected customer combo box item and parses it to select only customer ID.
     * @return customer ID of selected item.
     */
    public int customerComboInt() {
         String custString = String.valueOf(customerCombo.getValue());
        return  Integer.parseInt(custString.replaceAll("[\\D]",""));
    }

    /**
     * Confirms that all required text fields are filled.
     * @return boolean
     */
    public boolean checkFilledFields() {

        return !titleText.getText().isEmpty() && !descText.getText().isEmpty() && !locText.getText().isEmpty() && !contactCombo.getSelectionModel().isEmpty()
                && !typeText.getText().isEmpty() && !(dateStartDate.getValue() == null) && !(dateStartTime.getValue() == null)
                && !(dateEndDate.getValue() == null) && !(dateEndTime.getValue() == null) && !customerCombo.getSelectionModel().isEmpty()
                && !userCombo.getSelectionModel().isEmpty();

    }

    /**
     * Confirms the appointment start date is during business hours.
     * @return boolean.
     */
    public boolean validStartDate() {
        LocalDateTime validDate = LocalDateTime.of(dateStartDate.getValue(),
                LocalTime.parse(dateStartTime.getSelectionModel().getSelectedItem().toString()));
        return validDate.getDayOfWeek() != DayOfWeek.SATURDAY && validDate.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    /**
     * Confirms the appointment end date is during business hours.
     * @return boolean.
     */
    public boolean validEndDate() {
        LocalDateTime validDate = LocalDateTime.of(dateEndDate.getValue(),
                LocalTime.parse(dateEndTime.getSelectionModel().getSelectedItem().toString()));
        return validDate.getDayOfWeek() != DayOfWeek.SATURDAY && validDate.getDayOfWeek() != DayOfWeek.SUNDAY;
    }

    /**
     * Confirms the appointment end date is after the start date.
     * @return boolean.
     */
    public boolean endAfterStart() {
        LocalDateTime startDateTime = LocalDateTime.of(dateStartDate.getValue(),
                LocalTime.parse(dateStartTime.getSelectionModel().getSelectedItem().toString()));
        LocalDateTime endDateTime = LocalDateTime.of(dateEndDate.getValue(),
                LocalTime.parse(dateEndTime.getSelectionModel().getSelectedItem().toString()));
        return startDateTime.isBefore(endDateTime);
    }

    /**
     * Confirms the selected new appointment start and end time isn't conflicting with any existing customer appointments.
     * @return boolean.
     */
    public boolean checkAppConflict(){
    int custID =  customerComboInt();
    LocalDateTime start = LocalDateTime.of(dateStartDate.getValue(),
            LocalTime.parse(dateStartTime.getSelectionModel().getSelectedItem().toString()));
    LocalDateTime end = LocalDateTime.of(dateEndDate.getValue(),
                LocalTime.parse(dateEndTime.getSelectionModel().getSelectedItem().toString()));

   return DAOAppointments.appointmentConflict(custID, start, end);

    }

    /**
     *  Confirms the selected modified appointment start and end time isn't conflicting with any existing customer appointments.
     * @return boolean
     */
    public boolean checkModAppConflict(){
        int custID =  customerComboInt();
        LocalDateTime start = LocalDateTime.of(dateStartDate.getValue(),
                LocalTime.parse(dateStartTime.getSelectionModel().getSelectedItem().toString()));
        LocalDateTime end = LocalDateTime.of(dateEndDate.getValue(),
                LocalTime.parse(dateEndTime.getSelectionModel().getSelectedItem().toString()));
        int appID = Integer.parseInt(AppIDText.getText());

        return DAOAppointments.modAppointmentConflict(custID, start, end, appID);

    }


    /**
     * When the save button is clicked, a series of checks is ran to make sure no conflicting appointment information is inserted into the database.
     * @param actionEvent Button Click
     * @throws IOException IO Exception
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        if (!checkFilledFields()) {
            Alert Confirm = new Alert(Alert.AlertType.INFORMATION);
            Confirm.setHeaderText("Please confirm all appointment fields are filled.");
            Optional<ButtonType> result = Confirm.showAndWait();
        }
        else if (!validStartDate()) {
            Alert Confirm = new Alert(Alert.AlertType.INFORMATION);
            Confirm.setHeaderText("Please confirm appointment start date is during business hours.");
            Optional<ButtonType> result = Confirm.showAndWait();
        }
        else if (!validEndDate()) {
            Alert Confirm = new Alert(Alert.AlertType.INFORMATION);
            Confirm.setHeaderText("Please confirm appointment end date is during business hours.");
            Optional<ButtonType> result = Confirm.showAndWait();
        }

        else if (!endAfterStart()) {
            Alert Confirm = new Alert(Alert.AlertType.INFORMATION);
            Confirm.setHeaderText("Please confirm appointment end time is after start time.");
            Optional<ButtonType> result = Confirm.showAndWait();
        }

        else if (modifyClicked) {
            if (checkModAppConflict()) {
                Alert Confirm = new Alert(Alert.AlertType.INFORMATION);
                Confirm.setHeaderText("Please confirm new appointment doesn't have a time conflict with existing appointments.");
                Optional<ButtonType> result = Confirm.showAndWait();
            } else {
                DAOAppointments.modifyAppointment(
                        Integer.parseInt(AppIDText.getText()),
                        titleText.getText(),
                        descText.getText(),
                        locText.getText(),
                        contactComboInt(),
                        typeText.getText(),
                        LocalDateTime.of(dateStartDate.getValue(),
                                LocalTime.parse(dateStartTime.getSelectionModel().getSelectedItem().toString())),
                        LocalDateTime.of(dateEndDate.getValue(),
                                LocalTime.parse(dateEndTime.getSelectionModel().getSelectedItem().toString())),
                        customerComboInt(),
                        userComboInt());
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
                modifyClicked = false;

            }
        }

        else {
            if (checkAppConflict()) {
                Alert Confirm = new Alert(Alert.AlertType.INFORMATION);
                Confirm.setHeaderText("Please confirm new appointment doesn't have a time conflict with existing appointments.");
                Optional<ButtonType> result = Confirm.showAndWait();}

            else {
                DAOAppointments.newAppointment(
                        setAppID(),
                        titleText.getText(),
                        descText.getText(),
                        locText.getText(),
                        contactComboInt(),
                        typeText.getText(),
                        LocalDateTime.of(dateStartDate.getValue(),
                                LocalTime.parse(dateStartTime.getSelectionModel().getSelectedItem().toString())),
                        LocalDateTime.of(dateEndDate.getValue(),
                                LocalTime.parse(dateEndTime.getSelectionModel().getSelectedItem().toString())),
                        customerComboInt(),
                        userComboInt());
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
                modifyClicked = false;
            }
        }
    }

    /**
     * Sets the contact combo boxes when modifying an existing appointment.
     */
    public void setContactCombo() {
        int contID = selectedAppointment.getContactID();
        String contactName = DAOContact.contactCombo(contID);
        contactCombo.setValue(contID + " - " + contactName);
    }

    /**
     * Sets the customer combo box when modifying an existing appointment.
     */
    public void setCustomerCombo() {
       int custID = selectedAppointment.getAppCustID();
       String customerName = DAOCustomers.customerCombo(custID);
        customerCombo.setValue(custID + " - " + customerName);
    }

    /**
     * Sets the user combo box when modifying an exiting appointment.
     */
    public void setUserCombo() {
        int userID = selectedAppointment.getUserID();
        String userName = DAOUsers.userCombo(userID);

        userCombo.setValue(userID + " - " + userName);
    }

    /**
     * Populates the appointment fields with existing appointment's data.
     */
    public void modifyAppointment() {

            selectedAppointment = ControllerMainScreen.getAppointmentModify();
            AppIDText.setText(String.valueOf(selectedAppointment.getAppID()));
            titleText.setText(selectedAppointment.getAppTitle());
            descText.setText(selectedAppointment.getAppDesc());
            locText.setText(selectedAppointment.getAppLoc());
            setContactCombo();
            typeText.setText(selectedAppointment.getAppType());
            dateStartDate.setValue(selectedAppointment.getAppStart().toLocalDate());
            dateStartTime.setValue(selectedAppointment.getAppStart().toLocalTime());
            dateEndDate.setValue(selectedAppointment.getAppEnd().toLocalDate());
            dateEndTime.setValue(selectedAppointment.getAppEnd().toLocalTime());
            setCustomerCombo();
            setUserCombo();




    }

    /**
     * Cancels the current screen, confirms with an alert pop up.
     * @param actionEvent Button Click
     * @throws IOException IO Exception
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Alert Confirm = new Alert(Alert.AlertType.CONFIRMATION);
        Confirm.setTitle("Confirm Cancel");
        Confirm.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = Confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();
            modifyClicked = false;

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
