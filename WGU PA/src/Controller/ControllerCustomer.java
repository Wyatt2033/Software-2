package Controller;

import Database.DAOAppointments;
import Database.DAOCountry;
import Database.DAOCustomers;
import Database.DAODivision;
import Model.ModelCountry;
import Model.ModelCustomer;
import Model.ModelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


/**
 * Controller Class for Customer View.
 */
public class ControllerCustomer {
    /**
     * true whem customer is being modified.
     */
    public static boolean modifyClicked;
    /**
     * Customer name text.
     */
    public TextField custNameText;
    /**
     * Customer address text.
     */
    public TextField custAddressText;
    /**
     * Customer Postal Text.
     * */
    public TextField custPostalText;
    /**
     * Customer Division Combo Box.
     */
    public ComboBox<String> custDivisionCombo;
    /**
     * Customer Phone Text.
     */
    public TextField custPhoneText;
    /**
     * Customer Country Combo.
     */
    public ComboBox<String> custCountryCombo;
    /**
     * Customer ID Text.
     */
    public TextField custIDText;
    /**
     * Model Selected Customer.
     */
    private ModelCustomer selectedCustomer;

    /**
     * Checks if user is modifying an existing customer.
     * Sets combo boxes with their respective data.
     */
    public void initialize() {
        if (modifyClicked) {
            modifyCustomer();
        }
        setCountryCombo();
        setDivisionsCombo();

    }

    /**
     * Creates list of country info to populate combo box.
     */
    public void setCountryCombo(){
        ObservableList<String> countryComboList = FXCollections.observableArrayList();
        ObservableList<ModelCountry> countries = DAOCountry.countries();

        if(countries != null) {
            for(ModelCountry country : countries) {
                countryComboList.add(country.getCountryID() + " - " + country.getCountryName());
            }
        }
        custCountryCombo.setItems(countryComboList);
    }

    /**
     * Includes Lambda expression. This expression was necessary to properly display the divisions for the selected country.
     * Creates list of first division info to populate combo box.
     */
    public void setDivisionsCombo() {
        custCountryCombo.valueProperty().addListener(((observableValue, o, t1) -> {
                    if (observableValue == null) {
                        custDivisionCombo.getItems().clear();
                        custDivisionCombo.setDisable(true);
                    }
                    else {
                        ObservableList<String> divisionComboList = FXCollections.observableArrayList();
                        ObservableList<ModelDivision> divisions = DAODivision.divisions(Integer.parseInt(t1.replaceAll("[\\D]","")));
                        if (divisions != null) {
                            for(ModelDivision division : divisions) {
                                divisionComboList.add(division.getDivID() + " - " + division.getDiv());
                            }
                        }
                        custDivisionCombo.setItems(divisionComboList);
                        custDivisionCombo.setDisable(false);
                    }
                } )
        );

    }

/**
 * Gets current max customer ID and adds to it.
 * @return new customer ID.
 */
    public int setCustID () {
        int custID = DAOCustomers.getMaxCustID();
        return custID+1;
    }

    /**
     * Gets value of selected first divisoon combo box and parses it to select only the first division ID.
     * @return division ID of selected item.
     */
    public int divComboInt() {
        String divString = String.valueOf(custDivisionCombo.getValue());
        return (int) Integer.parseInt(divString.replaceAll("[\\D]",""));
    }
    /**
     * Confirms that all required text fields are filled.
     * @return boolean
     */
    public boolean checkFilledFields() {

        return !custNameText.getText().isEmpty() && !custPhoneText.getText().isEmpty() && !custAddressText.getText().isEmpty()
                && !custPostalText.getText().isEmpty() && !custDivisionCombo.getSelectionModel().isEmpty() && !custCountryCombo.getSelectionModel().isEmpty();

    }

    /**
     * When the save button is clicked, a series of checks is ran to make sure no conflicting customer information is inserted into the database.
     * @param actionEvent Button Click
     * @throws IOException IO Exception
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        if (!checkFilledFields()) {
            Alert Confirm = new Alert(Alert.AlertType.INFORMATION);
            Confirm.setHeaderText("Please confirm all customer fields are filled.");
            Optional<ButtonType> result = Confirm.showAndWait();
        }
        else if (modifyClicked) {
            DAOCustomers.modifyCustomer(
                    Integer.parseInt(custIDText.getText()),
                    custNameText.getText(),
                    custAddressText.getText(),
                    divComboInt(),
                    custPostalText.getText(),
                    custPhoneText.getText());
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Screen");
            stage.setScene(scene);
            stage.show();
            modifyClicked = false;

        }

        else {
            DAOCustomers.newCustomer(
             setCustID(),
             custNameText.getText(),
             custAddressText.getText(),
             divComboInt(),
             custPostalText.getText(),
             custPhoneText.getText());
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
     * Populated the customer fields with existing customer's data.
     */
    public void modifyCustomer() {

        selectedCustomer = ControllerMainScreen.getCustomerModify();
        custIDText.setText(String.valueOf(selectedCustomer.getCustID()));
        custNameText.setText(selectedCustomer.getCustName());
        custAddressText.setText(selectedCustomer.getCustAddress());
        setModifyDivisionsCombo();
        setModifyCountryCombo();
        custPostalText.setText(selectedCustomer.getCustPostal());
        custPhoneText.setText(selectedCustomer.getCustPhone());
    }

    /**
     * Sets the division combo box when modifying an existing customer.
     */
    private void setModifyDivisionsCombo() {
        String firstDiv = selectedCustomer.getCustFirstDiv();
        int divID = DAODivision.DivCombo(firstDiv);
        custDivisionCombo.setValue(divID + " - " + firstDiv);

    }

    /**
     * Sets the country combo box when modifying an existing customer.
     */
    private void setModifyCountryCombo() {
        String country = selectedCustomer.getCustCountry();
        int countryID = DAOCountry.CountryCombo(country);
        custCountryCombo.setValue(countryID + " - " + country);
            ObservableList<String> divisionComboList = FXCollections.observableArrayList();
            ObservableList<ModelDivision> divisions = DAODivision.divisions(countryID);
        if (divisions != null) {
            for(ModelDivision division : divisions) {
                divisionComboList.add(division.getDivID() + " - " + division.getDiv());
            }
        }
        custDivisionCombo.setItems(divisionComboList);
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
