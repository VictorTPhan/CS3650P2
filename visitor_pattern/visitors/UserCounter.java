package visitor_pattern.visitors;

import entries.User;
import entries.UserGroup;
import visitor_pattern.Traverser;

/**
 * A variation of the Traverser that counts how many users are in the database.
 */
public class UserCounter extends Traverser {

    private int total = 0;

    @Override
    public void onVisitedUser(User user) {
        total++;
    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {

    }

    /**
     * Returns the amount of users counted during this Traverser's visits.
     * Will return 0 if it did not visit any groups.
     * 
     * @return The number of users counted during this Traverser's visits.
     */
    public int getUserCount() {
        return total;
    }
}
