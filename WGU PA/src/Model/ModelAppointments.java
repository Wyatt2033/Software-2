package Model;

import java.time.LocalDateTime;

/**
 * Model Class for Appointments.
 */
public class ModelAppointments {
    /**
     * Model Appointment ID.
     */
    private int appID;
    /**
     * Model Appointment Title.
     */
    private String appTitle;
    /**
     * Model Appointment Description.
     */
    private String appDesc;
    /**
     * Model Appointment Location.
     */
    private String appLoc;
    /**
     * Model Appointment Contact.
     */
    private String appContact;
    /**
     * Model Appointment Type.
     */
    private String appType;
    /**
     * Model Appointment Start Date Picker.
     */
    private LocalDateTime appStart;
    /**
     * Model Appointment ENd Date Picker.
     */
    private LocalDateTime appEnd;
    /**
     * Model Appointment Customer ID.
     */
    private int appCustID;
    /**
     * Model Appointment User ID.
     */
    private int userID;
    /**
     * Model Appointment Contact ID.
     */
    private int contactID;


    /**
     * Constructor for appointments.
     * @param appID appointment ID
     * @param appTitle appointment Title
     * @param appDesc appointment Description
     * @param appLoc appointment Location
     * @param appContact appointment Contact
     * @param appType appointment Type
     * @param appStart appointment Start
     * @param appEnd appointment End
     * @param appCustID appointment Customer ID
     * @param userID appointment User ID
     * @param contactID appointment Contact ID
     */
    public ModelAppointments(int appID, String appTitle, String appDesc, String appLoc, String appContact, String appType, LocalDateTime appStart,
                             LocalDateTime appEnd, int appCustID, int userID, int contactID) {
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDesc = appDesc;
        this.appLoc = appLoc;
        this.appContact = appContact;
        this.appType = appType;
        this.appStart = appStart;
        this.appEnd = appEnd;
        this.appCustID = appCustID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Constructor for Appointments.
     * @param appID Application ID.
     * @param appStart Application Start Date/Time.
     */
    public ModelAppointments (int appID, LocalDateTime appStart) {
        this.appID = appID;
        this.appStart = appStart;
    }

    /**
     * Getter for appointment ID.
     * @return Appointment ID
     */
    public int getAppID() {
        return appID;
    }

    /**
     * Getter for appointment title.
     * @return Appointment Title
     */
    public String getAppTitle() {
        return appTitle;
    }

    /**
     * Getter for appointment description.
     * @return Appointment Description
     */
    public String getAppDesc() {
        return appDesc;
    }

    /**
     * Getter for appointment location.
     * @return Appointment Location
     */
    public String getAppLoc() {
        return appLoc;
    }

    /**
     * Getter for appointment contact.
     * @return Appointment Contact
     */
    public String getAppContact() {
        return appContact;
    }

    /**
     * Getter for appointment type.
     * @return Appointment Type
     */
    public String getAppType() {
        return appType;
    }

    /**
     * Getter for appointment start time.
     * @return Appointment Start Time
     */
    public LocalDateTime getAppStart() {
        return appStart;
    }

    /**
     * Getter for appointment end time.
     * @return Appointment End Time
     */
    public LocalDateTime getAppEnd() {
        return appEnd;
    }

    /**
     * Getter for customer ID.
     * @return Appointment Customer ID
     */
    public int getAppCustID() {
        return appCustID;
    }

    /**
     * Getter for user ID.
     * @return Appointment User ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Getter for appointment ID.
     * @return Appointment Contact ID
     */
    public int getContactID() {return contactID;}

}
