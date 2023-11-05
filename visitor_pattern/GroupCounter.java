package visitor_pattern;

import project.User;
import project.UserGroup;

public class GroupCounter extends Traverser {

    private int total = 0;

    @Override
    public void onVisitedUser(User user) {
        // Nothing.
    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {
        total++;
    }

    public int getGroupCount() {
        return total;
    }
}
