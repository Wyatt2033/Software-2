package Database;

import Model.ModelCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Selects all existing customers to populate the table view.
 */
public class DAOCustomers {

    /**
     * Model Customer Observable List.
     * @return Customers.
     */
    public static ObservableList<ModelCustomer> customers() {
        Connection conn;
        PreparedStatement ps;
        ObservableList<ModelCustomer> customers = FXCollections.observableArrayList();

        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT c.Customer_ID, c.Customer_Name, c.Address, d.Division, e.Country, c.Postal_Code, c.Phone  " +
                    "FROM customers AS c INNER JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID INNER JOIN countries as e ON d.Country_ID = e.Country_ID;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ModelCustomer customer = new ModelCustomer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Division"),
                        rs.getString("Country"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"));
                        customers.add(customer);

            }
            return customers;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Insterts new customer into database.
     * @param ID Customer ID
     * @param Name Customer Name
     * @param Address Customer Address
     * @param FirstDiv Customer First Div
     * @param Postal Customer Postal
     * @param Phone Customer Phone
     */
    public static void newCustomer(int ID, String Name, String Address, int FirstDiv, String Postal, String Phone) {
        Connection conn;
        PreparedStatement ps;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("INSERT INTO customers(Customer_ID, Customer_Name, Address, Division_ID, Postal_Code, Phone) VALUES (?, ?, ?, ?, ? ,?)");
            ps.setInt(1,ID);
            ps.setString(2,Name);
            ps.setString(3,Address);
            ps.setInt(4,FirstDiv);
            ps.setString(5,Postal);
            ps.setString(6,Phone);
            ps.execute();


        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    /**
     *  Updates an existing customer's information.
     * @param custID customer ID
     * @param Name customer Name
     * @param Address customer Address
     * @param FirstDiv customer First Division
     * @param Postal customer Postal
     * @param Phone customer Phone Number
     */
    public static void modifyCustomer(int custID, String Name, String Address, int FirstDiv, String Postal, String Phone) {
        Connection conn;
        PreparedStatement ps;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Division_ID = ?, Postal_Code = ?, Phone = ? WHERE Customer_ID = ?");
            ps.setString(1,Name);
            ps.setString(2,Address);
            ps.setInt(3, FirstDiv);
            ps.setString(4, Postal);
            ps.setString(5,Phone);
            ps.setInt(6, custID);
            ps.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Retrieves a list of all customers for the new appointment customer combo field.
     * @return list of customers.
     */
    public static ObservableList<ModelCustomer> customerList() {
        Connection conn;
        PreparedStatement ps;
        ObservableList<ModelCustomer> customers = FXCollections.observableArrayList();
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT Customer_ID, Customer_Name FROM customers;");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                ModelCustomer Customer = new ModelCustomer(
                        result.getInt("Customer_ID"),
                        result.getString("Customer_Name"));
                        customers.add(Customer);
            }
            return customers;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
    /**
     * Retrieves the customer name using the customer's ID number.
     * @param custID Customer ID
     * @return Customer's name based on ID number.
     */
    public static String customerCombo(int custID) {
        Connection conn;
        PreparedStatement ps;
        String customerName;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT customer_Name FROM customers WHERE Customer_ID = ? ");
            ps.setInt(1, custID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            customerName = rs.getString(1);
            return customerName;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Selects the max customer ID, so new customer ID's can be assigned.
     * @return current max customer ID.
     */
    public static int getMaxCustID() {
        Connection Conn;
        PreparedStatement ps;
        int custID;
        try {
            Conn = DB.getConnection();
            ps=Conn.prepareStatement("SELECT MAX(Customer_ID) FROM customers");
            ResultSet rs = ps.executeQuery();
            rs.next();
            custID = rs.getInt(1);
            return custID;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * Selects all appointments held by a specific customer.
     * @param custID customer ID
     * @return list of appointments associated with customer.
     */
    public static boolean AssociatedAppCust (int custID) {
        Connection Conn;
        PreparedStatement ps;
        try {
            Conn = DB.getConnection();
            ps = Conn.prepareStatement("SELECT * FROM Appointments WHERE Customer_ID = ?");
            ps.setInt(1,custID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            else {
                return false;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Deleted all appointments associated with a specific customer.
     * @param custID customer ID
     */
    public static void DeleteAssociatedApps(int custID) {
        Connection Conn;
        PreparedStatement ps;
        try {
            Conn = DB.getConnection();
            ps = Conn.prepareStatement("DELETE FROM Appointments WHERE Customer_ID = ?");
            ps.setInt(1,custID);
            ps.execute();
            System.out.println("Associated Appointments Delete Successful");


        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }


    /**
     * Deletes a customer based on matching customer ID.
     * @param custID customer ID
     */
    public static void DeleteCustomer(int custID) {
        Connection Conn;
        PreparedStatement ps;
        try {
            Conn = DB.getConnection();
            ps = Conn.prepareStatement("DELETE FROM Customers WHERE Customer_ID = ?");
            ps.setInt(1,custID);
            ps.execute();
            System.out.println("Delete Successful");



        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }



}



