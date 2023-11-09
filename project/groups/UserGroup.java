package project.groups;

import java.util.HashSet;

import database.Database;
import project.users.UserUID;
import visitor_pattern.Entity;
import visitor_pattern.Visitor;

/**
 * A representation of a UserGroup within the Twitter database, that maintains a
 * group of users and subgroups.
 */
public class UserGroup implements Entity {
    private String name;
    private GroupUID groupUID;
    private HashSet<UserUID> members;
    private HashSet<UserGroup> subGroups;

    /**
     * Initializes all the fields of this UserGroup.
     * 
     * @param name The name of this Group as a String.
     */
    public UserGroup(String name) {
        this.name = name;
        this.groupUID = new GroupUID(this);
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
     * Gets the UID of this Group.
     * 
     * @return The GroupUID of this UserGroup, as a String.
     */
    public GroupUID getUID() {
        return groupUID;
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
    public HashSet<UserUID> getMembers() {
        return members;
    }

    /**
     * Adds a new User to this group's member list.
     * 
     * @param newMember The UserUID of the User that is being added.
     * @return True if the operation was successful.
     */
    public boolean addMember(UserUID newMember) {
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
    public boolean addSubGroup(GroupUID subGroup) {
        if (subGroups.contains(subGroup.getGroup())) {
            return false;
        } else {
            subGroups.add(subGroup.getGroup());
            Database.getInstance().newEntryCallback();
            return true;
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return getGroupName();
    }
}
