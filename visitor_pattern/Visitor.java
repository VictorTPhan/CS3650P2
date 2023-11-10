package visitor_pattern;

import entries.User;
import entries.UserGroup;

/**
 * An interface for any object with an encoded operation that it must perform on
 * objects that implement the Entity interface.
 */
public interface Visitor {
    /**
     * Performs an operation on a User object.
     * 
     * @param user The User object to visit.
     */
    void visit(User user);

    /**
     * Performs an operation on a UserGroup object.
     * 
     * @param userGroup The UserGroup object to visit.
     */
    void visit(UserGroup userGroup);
}
