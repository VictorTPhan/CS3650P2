package visitor_pattern;

import entries.UID;
import entries.User;
import entries.UserGroup;

/**
 * A more concrete implementation of the Visitor interface that is designed to
 * perform operations on a hierarchy of UserGroups and Users.
 */
public abstract class Traverser implements Visitor {

    /**
     * A callback method invoked whenever a Traverser visits a User.
     * 
     * @param user The User to visit.
     */
    public abstract void onVisitedUser(User user);

    /**
     * A callback method invokved whenever a Traverser visits a Group.
     * 
     * @param group The group to visit.
     */
    public abstract void onVisitedUserGroup(UserGroup group);

    @Override
    public void visit(User user) {
        onVisitedUser(user);
    }

    @Override
    public void visit(UserGroup userGroup) {
        // Perform an operation on this group object.
        onVisitedUserGroup(userGroup);

        // Perform an operation on all members of this group.
        for (UID member : userGroup.getMembers()) {
            member.getDatabaseEntry().accept(this);
        }

        // Recursively perform this operation on all subgroups.
        for (UserGroup subgroup : userGroup.getSubGroups()) {
            subgroup.accept(this);
        }
    }
}
