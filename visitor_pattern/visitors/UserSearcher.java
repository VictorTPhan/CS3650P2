package visitor_pattern.visitors;

import project.groups.UserGroup;
import project.users.User;
import visitor_pattern.Traverser;

/**
 * A variation of the Traverser that searches for a User in the database given a
 * UID string.
 */
public class UserSearcher extends Traverser {

    private String target;
    private User searchResult = null;

    /**
     * Initializes this Traverser with the target UID.
     * 
     * @param target a String representation of the UID in question.
     */
    public UserSearcher(String target) {
        this.target = target;
    }

    @Override
    public void onVisitedUser(User user) {
        // Determine if the user's UID matches our target.
        if (user.getUID().getUID().equals(this.target)) {
            searchResult = user;
        }
    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {

    }

    /**
     * Returns the user if any matched the UID provided on this Traverser's
     * instantiation.
     * Will return null if no user was found with the target UID, or if this
     * Traverser has not visited any groups.
     * 
     * @return A User object whose UID matches the given UID String, or null.
     */
    public User getSearchResult() {
        return searchResult;
    }
}
