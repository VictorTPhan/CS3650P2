package database;

import visitor_pattern.Entity;
import visitor_pattern.Visitor;
import entries.UID;
import entries.User;
import entries.UserGroup;

public abstract class DatabaseEntry implements Entity {
    protected String name;
    protected UID uid;

    /**
     * Gets the username of this entry.
     * 
     * @return The username of this entry, as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the UID of this entry.
     * 
     * @return The UserUID of this entry, as a String.
     */
    public UID getUID() {
        return uid;
    }

    @Override
    public void accept(Visitor visitor) {
        if (this instanceof User) {
            visitor.visit((User) this);
        } else if (this instanceof UserGroup) {
            visitor.visit((UserGroup) this);
        }
    }
}
