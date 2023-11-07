package project.users;

import java.util.*;

public class UID {
    private User associatedUser;
    private String UID;

    public UID(User associatedUser) {
        this.associatedUser = associatedUser;
        this.UID = UUID.randomUUID().toString();
        System.out.println(this.UID);
    }

    public User getUser() {
        return associatedUser;
    }

    public String getUID() {
        return UID;
    }
}
