package visitor_pattern;

/**
 * An interface for any object that needs to accept a Visitor.
 */
public interface Entity {
    /**
     * Allows a Visitor to perform an operation on this object.
     * 
     * @param visitor The Visitor object to accept.
     */
    void accept(Visitor visitor);
}