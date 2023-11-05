package visitor_pattern;

import project.UID;
import project.User;
import project.UserGroup;

public abstract class Traverser implements Visitor {

    public abstract void onVisitedUser(User user);

    public abstract void onVisitedUserGroup(UserGroup group);

    @Override
    public void visit(User user) {
        onVisitedUser(user);
    }

    @Override
    public void visit(UserGroup userGroup) {
        onVisitedUserGroup(userGroup);
        for (UID member : userGroup.getMembers()) {
            member.getUser().accept(this);
        }
        for (UserGroup subgroup : userGroup.getSubGroups()) {
            subgroup.accept(this);
        }
    }
}
