package visitor_pattern.visitors;

import project.groups.UserGroup;
import project.users.User;
import visitor_pattern.Traverser;

/**
 * A variation of the Traverser that counts how many groups exist given an
 * initial group.
 */
public class GroupCounter extends Traverser {

    private int total = 0;

    // Not used.
    @Override
    public void onVisitedUser(User user) {

    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {
        total++;
    }

    /**
     * Returns the amount of groups counted during this Traverser's visits.
     * Will return 0 if it did not visit any groups.
     * 
     * @return The number of groups counted during this Traverser's visits.
     */
    public int getGroupCount() {
        return total;
    }
}
