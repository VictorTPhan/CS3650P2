package entries;

import java.util.HashSet;

import database.Database;
import database.DatabaseEntry;

/**
 * A representation of a UserGroup within the Twitter database, that maintains a
 * group of users and subgroups.
 */
public class UserGroup extends DatabaseEntry {
    private HashSet<UID> members;
    private HashSet<UserGroup> subGroups;

    /**
     * Initializes all the fields of this UserGroup.
     * 
     * @param name The name of this Group as a String.
     */
    public UserGroup(String name) {
        this.name = name;
        this.uid = new UID(this);
        this.members = new HashSet<>();
        this.subGroups = new HashSet<>();
    }

    /**
     * Gets the group name of this user.
     * 
     * @return The name of this Group as a String.
     */
    public String getGroupName() {
        return name;
    }

    /**
     * Accesses a collection of groups that are under this UserGroup.
     * 
     * @return A Hashset of UserGroups that are subgroups of this group.
     */
    public HashSet<UserGroup> getSubGroups() {
        return subGroups;
    }

    /**
     * Accesses a collection of members that are under this UserGroup.
     * 
     * @return A Hashset of Users that are members of this group.
     */
    public HashSet<UID> getMembers() {
        return members;
    }

    /**
     * Adds a new User to this group's member list.
     * 
     * @param newMember The UserUID of the User that is being added.
     * @return True if the operation was successful.
     */
    public boolean addMember(UID newMember) {
        if (members.contains(newMember)) {
            return false;
        } else {
            members.add(newMember);
            Database.getInstance().newEntryCallback();
            return true;
        }
    }

    /**
     * Adds a new group to this UserGroup's list of subgroups.
     * 
     * @param subGroup The GroupUID of the UserGroup that is being added.
     * @return True if the operation was successful.
     */
    public boolean addSubGroup(UserGroup subGroup) {
        if (subGroups.contains(subGroup)) {
            return false;
        } else {
            subGroups.add(subGroup);
            Database.getInstance().newEntryCallback();
            return true;
        }
    }

    @Override
    public String toString() {
        return getGroupName();
    }
}
