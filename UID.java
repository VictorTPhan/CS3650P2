import java.util.*;

public class UID {
    private User associatedUser;
    private String UID;

    public UID(User associatedUser) {
        this.associatedUser = associatedUser;
        this.UID = UUID.randomUUID().toString();
    }

    public User getUser() {
        return associatedUser;
    }

    public String getUID() {
        return UID;
    }
}