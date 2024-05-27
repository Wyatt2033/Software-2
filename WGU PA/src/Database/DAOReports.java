package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO Class for Reports
 */
public class DAOReports {
    /**
     * Selects information to populate the Type and Month Report tableview.
     * @return appointments by month and type.
     */
    public static ResultSet TypeMonth() {
        Connection conn;
        PreparedStatement ps;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT Type, monthname(Start) AS Start, count(*) AS Appointments FROM appointments group by Type, monthname(Start) ORDER BY Type;");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Selects the contact appointment information. It takes the tiemzone as input to properly display the time.
     * @param timeZone the timezone for the start and end time to be converted to.
     * @return Schedule for contacts.
     */
    public static ResultSet Contact(String timeZone) {
        Connection conn;
        PreparedStatement ps;

        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT c.Contact_Name, a.Appointment_ID, a.Title, a.Description, CONVERT_TZ(a.Start, '+00:00', ? ) AS Start, CONVERT_TZ(a.End, '+00:00', ? ) AS end" +
                    ", a.Customer_ID FROM appointments AS a, contacts AS c WHERE a.Contact_ID = c.Contact_ID order by Contact_Name;");
            ps.setString(1, timeZone);
            ps.setString(2, timeZone);

            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Selects total customer count per country.
     * @return Customer count per country.
     */
    public static ResultSet Country () {
        Connection conn;
        PreparedStatement ps;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT count(customer_ID) AS Customers, c.Country, c.Country_ID FROM customers AS a INNER JOIN first_level_divisions AS b INNER JOIN countries AS C WHERE a.Division_ID = b.Division_ID AND b.Country_ID = c.Country_ID GROUP BY Country");
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}


