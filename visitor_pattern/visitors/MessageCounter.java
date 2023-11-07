package visitor_pattern.visitors;

import project.groups.UserGroup;
import project.users.User;
import visitor_pattern.Traverser;

/**
 * A variation of the Traverser that counts how many tweets are in the database.
 */
public class MessageCounter extends Traverser {

    private int total = 0;

    @Override
    public void onVisitedUser(User user) {
        // Count how many tweets this user has made.
        total += user.getTweets().size();
    }

    /**
     * Returns the amount of tweets counted during this Traverser's visits.
     * Will return 0 if it did not visit any groups.
     * 
     * @return The number of tweets counted during this Traverser's visits.
     */
    public int getTweetCount() {
        return total;
    }

    // Not used.
    @Override
    public void onVisitedUserGroup(UserGroup group) {
        throw new UnsupportedOperationException("Unimplemented method 'onVisitedUserGroup'");
    }
}
