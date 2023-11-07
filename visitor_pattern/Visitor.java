package visitor_pattern;

import project.groups.UserGroup;
import project.users.User;

public interface Visitor {
    void visit(User user);

    void visit(UserGroup userGroup);
}
