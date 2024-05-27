package Model;

/**
 * Model Class for Contacts.
 */
public class ModelContact {
    private int contactID;
    private String contactName;

    /**
     * Constructor for contacts
     * @param contactID Contact ID
     * @param contactName Contact Name
     */
    public ModelContact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * Getter for contact ID.
     * @return contact ID
     */
    public int getContactID() { return contactID;}

    /**
     * Getter for contact name.
     * @return contact name.
     */
    public String getContactName() { return contactName;}
}
