package Database;

import Model.ModelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO Class for Divisions.
 */
public class DAODivision {

    /**
     * Selects division info from database.
     * @param countryID Country ID
     * @return division list.
     */
    public static ObservableList<ModelDivision> divisions(int countryID) {
        Connection conn;
        PreparedStatement ps;

        ObservableList<ModelDivision> divisions = FXCollections.observableArrayList();

        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT Division_ID, Division FROM first_level_divisions WHERE Country_ID = ?;");
            ps.setInt(1, countryID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                ModelDivision division = new ModelDivision(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"));
                divisions.add(division);
            }
            return divisions;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Selects the division ID based on input of division name.
     * @param firstDiv First Division
     * @return division ID result set.
     */
    public static int DivCombo(String firstDiv) {
        Connection conn;
        PreparedStatement ps;
        int divID;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT Division_ID FROM first_level_divisions WHERE Division = ? ");
            ps.setString(1, firstDiv);
            ResultSet rs = ps.executeQuery();
            rs.next();
            divID = rs.getInt(1);
            return divID;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}

