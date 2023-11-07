package project.users;

import java.util.UUID;

public class UserUID {
    private User associatedUser;
    private String UID;

    public UserUID(User associatedUser) {
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
