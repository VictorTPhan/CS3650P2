package visitor_pattern;

import project.User;
import project.UserGroup;

public class UserCounter extends Traverser {

    private int total = 0;

    @Override
    public void onVisitedUser(User user) {
        total++;
    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {
        // Nothing.
    }

    public int getUserCount() {
        return total;
    }
}
