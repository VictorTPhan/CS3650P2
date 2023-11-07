package observer_pattern;

/**
 * An interface used for objects that can have other objects that implement the
 * Listener interface to be notified of certain events.
 */
public interface Subject {
    /**
     * Adds a Listener to this Subject's list of listeners.
     * 
     * @param listener The Listener to add.
     */
    public void register(Listener listener);

    /**
     * Removes a Listener to this Subject's list of listeners.
     * 
     * @param listener The Listener to remove.
     */
    public void deregister(Listener listener);

    /**
     * Notifies all objects that are listening to this Subject.
     */
    public void notifyListeners();
}
