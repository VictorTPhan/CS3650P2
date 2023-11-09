package project.groups;

import java.util.UUID;

/**
 * A class that identifies a UserGroup.
 */
public class GroupUID {
    private UserGroup associatedGroup;
    private String UID;

    /**
     * Associates a newly instantiated GroupUID with a particular UserGroup object
     * and generates a UID for that UserGroup.
     * 
     * @param associatedGroup The UserGroup to associate this GroupUID with.
     */
    public GroupUID(UserGroup associatedGroup) {
        this.associatedGroup = associatedGroup;
        this.UID = UUID.randomUUID().toString();
    }

    /**
     * Accesses the group associated with this UID.
     * 
     * @return The UserGroup associated with this UID.
     */
    public UserGroup getGroup() {
        return associatedGroup;
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
