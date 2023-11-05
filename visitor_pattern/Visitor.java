package visitor_pattern;

import project.User;
import project.UserGroup;

public interface Visitor {
    void visit(User user);

    void visit(UserGroup userGroup);
}
