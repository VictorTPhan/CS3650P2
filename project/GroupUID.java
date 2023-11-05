package project;

import java.util.*;

public class GroupUID {
    private UserGroup associatedGroup;
    private String UID;

    public GroupUID(UserGroup associatedGroup) {
        this.associatedGroup = associatedGroup;
        this.UID = UUID.randomUUID().toString();
    }

    public UserGroup getGroup() {
        return associatedGroup;
    }

    public String getUID() {
        return UID;
    }
}
