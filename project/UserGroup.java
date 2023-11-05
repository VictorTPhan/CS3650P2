package project;

import java.util.*;

import visitor_pattern.Entity;
import visitor_pattern.Visitor;

public class UserGroup implements Entity {
    private String name;
    private GroupUID groupUID;
    private HashSet<UID> members;
    private HashSet<UserGroup> subGroups;

    public UserGroup(String name) {
        this.name = name;
        this.groupUID = new GroupUID(this);
        this.members = new HashSet<>();
        this.subGroups = new HashSet<>();
    }

    public String getGroupName() {
        return name;
    }

    public GroupUID getUID() {
        return groupUID;
    }

    public HashSet<UserGroup> getSubGroups() {
        return subGroups;
    }

    public HashSet<UID> getMembers() {
        return members;
    }

    public boolean addMember(UID newMember) {
        if (members.contains(newMember)) {
            return false;
        } else {
            members.add(newMember);
            Database.getInstance().newEntryCallback();
            return true;
        }
    }

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
}
