package visitor_pattern.visitors;

import entries.UID;
import entries.User;
import entries.UserGroup;
import visitor_pattern.Traverser;

/**
 * A variation of the Traverser that determines the User in the database with
 * the most recently updated lastUpdateTime field.
 */
public class LastUpdatedTimestampFinder extends Traverser {

    private long mostRecentTimestamp = -1;
    private UID result;

    @Override
    public void onVisitedUser(User user) {
        if (user.getLastUpdateTime() > mostRecentTimestamp) {
            mostRecentTimestamp = user.getLastUpdateTime();
            result = user.getUID();
        }
    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {
        // Do nothing
    }

    /**
     * Gets the result of a search for the user with the most recently updated
     * timestamp.
     * 
     * @return The UID of the user with the largest lastUpdateTime field.
     */
    public UID getResult() {
        return result;
    }
}
