package Database;


import Model.ModelUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DAOUsers {


    /**
     * Selects all user information.
     * @return user list.
     */
    public static ObservableList<ModelUser> usersList() {
        Connection conn;
        PreparedStatement ps;
        ObservableList<ModelUser> users = FXCollections.observableArrayList();
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT User_ID, User_Name FROM users;");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                ModelUser User = new ModelUser(
                        result.getInt("User_ID"),
                        result.getString("User_Name"));
                users.add(User);
            }

            return users;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Takes the login info from user login page and verifies it with the SQL user info.
     * @param Username Username
     * @param Password Password
     * @return login true/false.
     */
    public static Boolean login(String Username, String Password) {

        Connection conn;
        PreparedStatement ps;
        String username;
        String password;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT * FROM users WHERE BINARY User_Name = ? AND  BINARY Password = ? ;");
            ps.setString(1, Username);
            ps.setString(2, Password);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;




        }

    }

    /**
     * Selects all user information to populate the user combo box.
     * @param userID User ID
     * @return user info.
     */
    public static String userCombo(int userID) {
        Connection conn;
        PreparedStatement ps;
        String userName;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT User_Name FROM users WHERE User_ID = ? ");
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            userName = rs.getString(1);
            return userName;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}