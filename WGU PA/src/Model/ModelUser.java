package Model;

/**
 * Model Class for Users.
 */
public class ModelUser {
    /**
     * Model User Username.
     */
    private String username;
    /**
     * Model User User ID.
     */
    private int userID;

    /**
     * Constructor for users.
     * @param userID User ID
     * @param username Username
     */
    public ModelUser(int userID, String username) {
        this.username = username;
        this.userID = userID;
    }

    /**
     * Getter for username.
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for User ID.
     * @return User ID
     */
    public int getUserID() {
        return userID;
    }

}

