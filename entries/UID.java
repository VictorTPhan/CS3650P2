package entries;

import java.util.UUID;

import database.DatabaseEntry;

/**
 * A class that identifies an DatabaseEntry.
 */
public class UID {
    private DatabaseEntry associatedEntry;
    private String UID;

    /**
     * Associates a newly instantiated UID with a particular DatabaseEntry object
     * and generates a UID for that user.
     * 
     * @param associatedUser The User to associate this UID with.
     */
    public UID(DatabaseEntry associatedEntry) {
        this.associatedEntry = associatedEntry;
        this.UID = UUID.randomUUID().toString();
        System.out.println(this.UID);
    }

    /**
     * Accesses the user associated with this UID.
     * 
     * @return The User associated with this UID.
     */
    public DatabaseEntry getDatabaseEntry() {
        return associatedEntry;
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
