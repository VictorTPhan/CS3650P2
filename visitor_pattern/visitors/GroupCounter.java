package visitor_pattern.visitors;

import project.groups.UserGroup;
import project.users.User;
import visitor_pattern.Traverser;

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
