package visitor_pattern;

import project.User;
import project.UserGroup;

public class MessageCounter extends Traverser {

    private int total = 0;

    @Override
    public void onVisitedUser(User user) {
        total += user.getTweets().size();
    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {
        // Nothing.
    }

    public int getTweetCount() {
        return total;
    }
}
