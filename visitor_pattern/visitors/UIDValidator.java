package visitor_pattern.visitors;

import java.util.HashSet;
import java.util.Set;

import entries.UID;
import entries.User;
import entries.UserGroup;
import visitor_pattern.Traverser;

/**
 * A variation of the Traverser that scans the entire database and determines if
 * all of the UIDs in the database are unique and follow proper UID conventions.
 */
public class UIDValidator extends Traverser {

    Set<UID> memory;
    boolean result = true;

    public UIDValidator() {
        memory = new HashSet<UID>();
    }

    @Override
    public void onVisitedUser(User user) {
        checkUID(user.getUID());
    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {
        checkUID(group.getUID());
    }

    void checkUID(UID uid) {
        // 1. All UIDs must be unique
        if (memory.contains(uid)) {
            result = false;
        } else {
            memory.add(uid);
        }

        // 2. All UIDs cannot contain spaces
        if (uid.getUID().contains(" ")) {
            result = false;
        }
    }

    /**
     * Gets the result of the UID validation check.
     * 
     * @return true if all UIDs are unique and contain no spaces.
     */
    public boolean getResult() {
        return result;
    }
}
