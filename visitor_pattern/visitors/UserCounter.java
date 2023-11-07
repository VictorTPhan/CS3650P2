package visitor_pattern.visitors;

import project.groups.UserGroup;
import project.users.User;
import visitor_pattern.Traverser;

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
