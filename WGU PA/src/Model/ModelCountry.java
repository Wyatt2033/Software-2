package Model;

/**
 * Creates model for countries.
 */
public class ModelCountry {
    /**
     * Country ID.
     */
    private int countryID;
    /**
     * Country Name.
     */
    private String countryName;

    /**
     * Constructor for countries.
     * @param countryID Country ID
     * @param countryName Country Name
     */
    public ModelCountry(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;

    }

    /**
     * Getter for country ID.
     * @return country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * Getter for country name.
     * @return country name
     */
    public String getCountryName() {
        return countryName;
    }
}
