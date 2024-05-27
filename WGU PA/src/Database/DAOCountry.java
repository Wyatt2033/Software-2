package Database;

import Model.ModelCountry;
import Model.ModelCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO Class for Countries.
 */
public class DAOCountry {
    /**
     * Selects country info from database.
     * @return country list.
     */

    public static ObservableList<ModelCountry> countries() {
        Connection conn;
        PreparedStatement ps;
        ObservableList<ModelCountry> countries = FXCollections.observableArrayList();

        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT Country_ID, Country FROM countries;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ModelCountry country = new ModelCountry(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"));
                countries.add(country);
            }
            return countries;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves country ID for country combo box, using country name.
     * @param country Country Name
     * @return country ID.
     */
    public static int CountryCombo(String country) {
        Connection conn;
        PreparedStatement ps;
        int countryID;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT Country_ID FROM countries WHERE Country = ? ");
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            rs.next();
            countryID = rs.getInt(1);
            return countryID;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }


}
