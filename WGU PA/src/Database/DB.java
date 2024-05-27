package Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for Database Connection.
 */
public class DB {

    /**
     * Database Protocol.
     */
    private static final String protocol = "jdbc";
    /**
     * Database Vendor Name.
     */
    private static final String vendorName = ":mysql:";
    /**
     * Database IP Address.
     */
    private static final String ipAddress = "//localhost:3306/";
    /**
     * Database Name.
     */
    private static final String dbName = "client_schedule";
    /**
     * Database combined URL and Server TImezone.
     */
    public static final String jdbcURL = protocol + vendorName + ipAddress + dbName + "?connectionTimeZone=Server";
    /**
     * Database Driver.
     */
    private static final String MYSQLJBCDriver = "com.mysql.jdbc.Driver";
    /**
     * Database Username.
     */
    private static final String username = "sqlUser";
    /**
     * Database Password.
     */
    private static final String password = "Passw0rd!";
    /**
     * Database Connection.
     */
    private static Connection conn;

    /**
     * Establishes connection with the database.
     */
    public static void startConnection() {
        try {
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);

            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Getter for conn.
     * @return conn.
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * Closes connection with database.
     */
    public static void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }

}