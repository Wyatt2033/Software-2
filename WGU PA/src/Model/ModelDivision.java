package Model;

/**
 * Created model for divisions.
 */
public class ModelDivision {
    /**
     * Model Division ID.
     */
    private int divID;
    /**
     * Model Division
     */
    private String div;

    /**
     * Constructor for divisions.
     * @param divID Division ID
     * @param div Division Name
     */
    public ModelDivision(int divID, String div) {
        this.divID = divID;
        this.div = div;
    }

    /**
     * Getter for division ID.
     * @return division ID.
     */
    public int getDivID() {
        return divID;
    }

    /**
     * Getter for division name.
     * @return division name
     */
    public String getDiv() {
        return div;
    }
}
