package visitor_pattern;

import project.UID;
import project.User;
import project.UserGroup;

public class UserSearcher extends Traverser {

    private String target;
    private User searchResult = null;

    public UserSearcher(String target) {
        this.target = target;
    }

    @Override
    public void onVisitedUser(User user) {
        if (user.getUID().getUID().equals(this.target)) {
            searchResult = user;
        }
    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {
        // Nothing
    }

    public User getSearchResult() {
        return searchResult;
    }
}
