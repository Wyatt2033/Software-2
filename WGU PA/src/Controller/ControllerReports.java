package Controller;

import Database.DAOReports;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Controller Class for Reports.
 */
public class ControllerReports {
    /**
     * Toggle Group Report Radio Buttons.
     */
    public ToggleGroup RGroup;
    /**
     * Report Tableview.
     */
    public TableView reportTable;

    private Object Frame;

    /**
     * Sets the default tableview to "onTypeMonth".
     * @throws SQLException SQL Exception
     */
    public void initialize() throws SQLException {
        DAOReports.TypeMonth();
        buildData(DAOReports.TypeMonth());





    }


    /**
     * Includes Lambda expression. This expression was necessary to dynamically create the tableview in the report screens.
     * Builds the TableView using SQL query results.
     * @param rs Result Set
     * @throws SQLException SQL Exception
     */
    private void buildData(ResultSet rs) throws SQLException {
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            final int x = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                if (param.getValue().get(x) != null) {
                    return new SimpleStringProperty(param.getValue().get(x).toString());
                } else {
                    return null;
                }
            });
            reportTable.getColumns().addAll(col);
        }
        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i));
            }
            data.add(row);
        }
        reportTable.setItems(data);
    }


    /**
     * Clears the report table and inserts DAOReports.TypeMonth query results into buildData.
     * @param actionEvent Button Click
     * @throws SQLException SQL Exception
     */
    public void onTypeMonth(ActionEvent actionEvent) throws SQLException {
        reportTable.getColumns().clear();
        buildData(DAOReports.TypeMonth());

    }

    /**
     * Clears the report table and inserts DAO.Reports.Contact query results into buildData.
     * @param actionEvent Button Click
     * @throws SQLException SQL Exception
     */
    public void onContact(ActionEvent actionEvent) throws SQLException {
        reportTable.getColumns().clear();
        buildData(DAOReports.Contact(TimeZone()));
    }

    /**
     * Clears the report table and inerts DAO.Reports.Country query results into build data.
     * @param actionEvent Button Click
     * @throws SQLException SQL Exception
     */
    public void onCountry(ActionEvent actionEvent) throws SQLException {
        reportTable.getColumns().clear();
        buildData(DAOReports.Country());
    }
    /**
     * provides the timezone offset and returns it in a format that can be used with the SQL CONVERT_TZ.
     * @return SQL ready string.
     */
    public String TimeZone() {

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);
        ZoneOffset offset = zdt.getOffset();
        System.out.println(offset);
        return offset.toString();

   }

    /**
     * Provides functionality to the back button.
     * @param actionEvent Button Click
     * @throws IOException IOException
     */
    public void onBackButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();

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