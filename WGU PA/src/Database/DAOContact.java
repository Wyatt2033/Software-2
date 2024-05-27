package Database;

import Model.ModelContact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO Class for Contacts,
 */
public class DAOContact {
    /**
     * Selects contacts info from database.
     * @return contact list.
     */
    public static ObservableList<ModelContact> contactList() {
        Connection conn;
        PreparedStatement ps;
        ObservableList<ModelContact> contacts = FXCollections.observableArrayList();
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT Contact_ID, Contact_Name FROM contacts;");
            ResultSet result = ps.executeQuery();
            while(result.next()) {
                ModelContact Contact = new ModelContact(
                        result.getInt("Contact_ID"),
                        result.getString("Contact_Name"));
                        contacts.add(Contact);
            }
            return contacts;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Selects corresponding contact name using contact ID.
     * @param contactID Contact ID
     * @return contact name.
     */
    public static String contactCombo(int contactID) {
        Connection conn;
        PreparedStatement ps;
        String contactName;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT Contact_name FROM contacts WHERE Contact_ID = ? ");
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            contactName = rs.getString(1);
            return contactName;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
