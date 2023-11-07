package visitor_pattern.visitors;

import project.groups.UserGroup;
import project.users.UID;
import project.users.User;
import visitor_pattern.Traverser;

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
