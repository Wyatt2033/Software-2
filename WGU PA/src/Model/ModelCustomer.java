package Model;

/**
 * Creates model for customers.
 */
public class ModelCustomer {
    /**
     * Model Customer ID.
     */
    private int custID;
    /**
     * Model Customer Name.
     */
    private String custName;
    /**
     * Model Customer Address.
     */
    private String custAddress;
    /**
     * Model Customer First Division.
     */
    private String custFirstDiv;
    /**
     * Model Customer Country.
     */
    private String custCountry;
    /**
     * Model Customer Postal.
     */
    private String custPostal;
    /**
     * Model CCustomer Phone Number.
     */
    private String custPhone;

    /** Constructor for customer.
     *
     * @param custID Customer ID
     * @param custName Customer Name
     * @param custAddress Customer Address
     * @param custFirstDiv Customer First Division
     * @param custCountry Customer Country
     * @param custPostal Customer Postal
     * @param custPhone Customer Phone Number
     */
    public ModelCustomer(int custID, String custName, String custAddress, String custFirstDiv, String custCountry, String custPostal, String custPhone) {

        this.custID = custID;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custFirstDiv = custFirstDiv;
        this.custCountry = custCountry;
        this.custPostal = custPostal;
        this.custPhone = custPhone;
    }

    /** Creates customer model for display with combo boxes.
     *
     * @param custID Customer ID
     * @param custName Customer Name
     */
    public ModelCustomer(int custID, String custName) {
        this.custID = custID;
        this.custName = custName;
    }

    /**
     * Getter for customer ID.
     * @return Customer ID
     */
    public int getCustID() {
        return custID;
    }

    /**
     * Getter for customer name.
     * @return Customer Name
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @return Customer Address
     */
    public String getCustAddress() {
        return custAddress;
    }

    /**
     * Getter for first division.
     * @return Customer First Division
     */
    public String getCustFirstDiv() {
        return custFirstDiv;
    }

    /**
     * Getter for coutnry.
     * @return Customer Country
     */
    public String getCustCountry() {
        return custCountry;
    }

    /**
     * Getter for postal code.
     * @return Customer Postal Code
     */
    public String getCustPostal() {
        return custPostal;
    }

    /**
     * Getter for phone number.
     * @return Customer Phone Number
     */
    public String getCustPhone() {
        return custPhone;
    }
}
