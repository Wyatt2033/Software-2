package Database;

import Model.ModelAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * DAO Class for Appointments.
 */
public class DAOAppointments {

    /**
     * Selects all existing appointments to populate the table view.
     * @return appointments list.
     */
    public static ObservableList<ModelAppointments> appointments() {
        Connection conn;
        PreparedStatement ps;
        ObservableList<ModelAppointments> appointments = FXCollections.observableArrayList();

        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, " +
                    "a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, b.Contact_Name, b.Contact_ID FROM appointments AS a," +
                    " contacts AS b WHERE a.Contact_ID = b.Contact_ID");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                ModelAppointments AppointmentsQuery = new ModelAppointments(
                        result.getInt("Appointment_ID"),
                        result.getString("Title"),
                        result.getString("Description"),
                        result.getString("Location"),
                        result.getString("Contact_Name"),
                        result.getString("Type"),
                        result.getTimestamp("Start").toLocalDateTime(),
                        result.getTimestamp("End").toLocalDateTime(),
                        result.getInt("Customer_ID"),
                        result.getInt("User_ID"),
                        result.getInt("Contact_ID"));

                        System.out.println(result.getTimestamp("End"));
                appointments.add(AppointmentsQuery);
            }
            return appointments;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Selects appointments that fit the monthView filter.
     * @return month view appointments.
     */
    public static ObservableList<ModelAppointments> monthView() {
        Connection conn;
        PreparedStatement ps;
        ObservableList<ModelAppointments> appointments = FXCollections.observableArrayList();

        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, " +
                    "a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, b.Contact_Name, b.Contact_ID FROM appointments AS a," +
                    " contacts AS b WHERE a.Contact_ID = b.Contact_ID AND month(Start)=month(now());");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                ModelAppointments AppointmentsQuery = new ModelAppointments(
                        result.getInt("Appointment_ID"),
                        result.getString("Title"),
                        result.getString("Description"),
                        result.getString("Location"),
                        result.getString("Contact_Name"),
                        result.getString("Type"),
                        result.getTimestamp("Start").toLocalDateTime(),
                        result.getTimestamp("End").toLocalDateTime(),
                        result.getInt("Customer_ID"),
                        result.getInt("User_ID"),
                        result.getInt("Contact_ID"));
                appointments.add(AppointmentsQuery);
            }
            return appointments;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves appointments that fit the weekView filter.
     * @return weekView appointments
     */
    public static ObservableList<ModelAppointments> weekView() {
        Connection conn;
        PreparedStatement ps;
        ObservableList<ModelAppointments> appointments = FXCollections.observableArrayList();

        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, " +
                    "a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, b.Contact_Name, b.Contact_ID FROM appointments AS a," +
                    " contacts AS b WHERE a.Contact_ID = b.Contact_ID AND week(Start)=week(now());");
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                ModelAppointments AppointmentsQuery = new ModelAppointments(
                        result.getInt("Appointment_ID"),
                        result.getString("Title"),
                        result.getString("Description"),
                        result.getString("Location"),
                        result.getString("Contact_Name"),
                        result.getString("Type"),
                        result.getTimestamp("Start").toLocalDateTime(),
                        result.getTimestamp("End").toLocalDateTime(),
                        result.getInt("Customer_ID"),
                        result.getInt("User_ID"),
                        result.getInt("Contact_ID"));
                appointments.add(AppointmentsQuery);
            }
            return appointments;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an existing appointment's information.
     * @param appID Appointment ID
     * @param title Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param contact Appointment Contact
     * @param type Appointment Type
     * @param start Appointment Start
     * @param end Appointment End
     * @param customer_id Appointment Customer ID
     * @param user_id Appointment User ID
     */
    public static void modifyAppointment(int appID, String title, String description, String location, int contact, String type, LocalDateTime start, LocalDateTime end, int customer_id, int user_id)  {
        Connection conn;
        PreparedStatement ps;
        try{ conn = DB.getConnection();
            ps = conn.prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ? WHERE Appointment_ID = ?;");
            ps.setString(1,title);
            ps.setString(2,description);
            ps.setString(3, location);
            ps.setInt(4, contact);
            ps.setString(5, type);
            ps.setTimestamp(6, Timestamp.valueOf(start));
            ps.setTimestamp(7, Timestamp.valueOf(end));
            ps.setInt(8, customer_id);
            ps.setInt(9, user_id);
            ps.setInt(10,appID);
            ps.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    /**
     * Adds functionality to insert new appointment data into the database.
     * @param ID Appointment ID
     * @param title Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param contact Appointment Contact
     * @param type Appointment Type
     * @param start Appointment Start Date
     * @param end Appointment End Date
     * @param customer_id Appointment Customer ID
     * @param user_id Appointment User ID
     */
    public static void newAppointment(int ID, String title, String description, String location, int contact, String type, LocalDateTime start, LocalDateTime end, int customer_id, int user_id) {
        Connection conn;
        PreparedStatement ps;
        try {    conn = DB.getConnection();
            ps = conn.prepareStatement("INSERT INTO appointments(Appointment_ID, Title, Description, Location, Contact_ID, Type, Start, End, Customer_ID, User_ID) VALUES(?,?,?,?,?,?,?,?,?,?);");
            ps.setInt(1,ID);
            ps.setString(2,title);
            ps.setString(3,description);
            ps.setString(4, location);
            ps.setInt(5, contact);
            ps.setString(6, type);
            ps.setTimestamp(7, Timestamp.valueOf(start));
            ps.setTimestamp(8, Timestamp.valueOf(end));
            ps.setInt(9, customer_id);
            ps.setInt(10, user_id);
            ps.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();}
;
    }

    /**
     * Finds the current highest appointment ID value.
     * @return max appointment ID value.
     */
        public static int getMaxAppID() {
        Connection Conn;
        PreparedStatement ps;
        int appID;
        try {
            Conn = DB.getConnection();
            ps=Conn.prepareStatement("SELECT MAX(Appointment_ID) FROM Appointments");
            ResultSet rs = ps.executeQuery();
            rs.next();
            appID = rs.getInt(1);
            return appID;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
            return 0;
        }

    /**
     * Finds applies the times of a new appointment to the existing appointments. If there is a conflicting appointment, it will return true.
     * @param custID Appointment Customer ID
     * @param start Appointment Start Date
     * @param end Appointment End Date
     * @return true/false value of appointment conflict.
     */
        public static boolean appointmentConflict(int custID, LocalDateTime start, LocalDateTime end) {
        Connection Conn;
        PreparedStatement ps;
            ZonedDateTime zonedStartDateTime =  start.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
            start = zonedStartDateTime.toLocalDateTime();
            ZonedDateTime zonedEndDateTime = end.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
            end = zonedEndDateTime.toLocalDateTime();
        try {
            Conn = DB.getConnection();
            ps=Conn.prepareStatement("SELECT * FROM appointments WHERE Customer_ID = ? AND ((? BETWEEN start AND end) OR (? BETWEEN start and end) OR (? < start AND ? > end))");
            ps.setInt(1, custID);
            ps.setObject(2, start);
            ps.setObject(3, end);
            ps.setObject(4, start);
            ps.setObject(5, end);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
        }


    /**
     * Finds applies the times of an existing modified appointment to the other existing appointments. If there is a conflicting appointment, it will return true.
     * @param custID Appointment Customer ID
     * @param start Appointment Start Date
     * @param end Appointment End Date
     * @param appID Appointment iD
     * @return true/false value of appointment conflict.
     */
    public static boolean modAppointmentConflict(int custID, LocalDateTime start, LocalDateTime end, int appID) {
        Connection Conn;
        PreparedStatement ps;
         ZonedDateTime zonedStartDateTime =  start.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
         start = zonedStartDateTime.toLocalDateTime();
         ZonedDateTime zonedEndDateTime = end.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
         end = zonedEndDateTime.toLocalDateTime();

        try {
            Conn = DB.getConnection();
            ps=Conn.prepareStatement("SELECT * FROM appointments WHERE Customer_ID = ? AND ((? BETWEEN start AND end) OR (? BETWEEN start and end) OR (? < start AND ? > end)) AND Appointment_ID != ?");
            ps.setInt(1, custID);
            ps.setObject(2, start);
            ps.setObject(3, end);
            ps.setObject(4, start);
            ps.setObject(5, end);
            ps.setInt(6,appID);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();


            return rs.next();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes an appointment based on matching appointment ID.
     * @param appID Appointment ID
     */
        public static void DeleteAppointment (int appID) {
        Connection Conn;
        PreparedStatement ps;
        try {
            Conn = DB.getConnection();
            ps = Conn.prepareStatement("DELETE FROM Appointments WHERE Appointment_ID = ?");
            ps.setInt(1,appID);
            ps.execute();
            System.out.println("Delete Successful");


        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        }

    /**
     * Model Appointments Alert Observable List.
     * @return Upcoming appointments.
     */
    public static ObservableList<ModelAppointments> AppointmentAlert() {
            Connection conn;
            PreparedStatement ps;
            ObservableList<ModelAppointments> appointments = FXCollections.observableArrayList();
            try {
                conn = DB.getConnection();
                ps = conn.prepareStatement("SELECT Appointment_ID, Start FROM appointments" +
                        " WHERE Start <= date_add(now(), interval 15 minute) AND Start >= now();");
                ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        ModelAppointments newAppointments = new ModelAppointments(
                                rs.getInt("Appointment_ID"),
                                rs.getTimestamp("Start").toLocalDateTime());
                        appointments.add(newAppointments);
                    }

                return appointments;





            } catch (SQLException exception) {
                exception.printStackTrace();
            }


    return null;
}

}


