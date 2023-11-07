package project.users;

import java.util.UUID;

/**
 * A class that identifies a User object.
 */
public class UserUID {
    private User associatedUser;
    private String UID;

    /**
     * Associates a newly instantiated UID with a particular User object and
     * generates a UID for that user.
     * 
     * @param associatedUser The User to associate this UID with.
     */
    public UserUID(User associatedUser) {
        this.associatedUser = associatedUser;
        this.UID = UUID.randomUUID().toString();
        System.out.println(this.UID);
    }

    /**
     * Accesses the user associated with this UID.
     * 
     * @return The User associated with this UID.
     */
    public User getUser() {
        return associatedUser;
    }

    /**
     * Accesses the UID String associated with this UID.
     * 
     * @return The UID String associated with this UID.
     */
    public String getUID() {
        return UID;
    }
}
