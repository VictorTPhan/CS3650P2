package observer_pattern;

/**
 * An interface for objects that need to be notified of certain events by
 * objects that use the Subject interface.
 */
public interface Listener {
    /**
     * A function that is only supposed to be invoked by a Subject that this
     * Listener is listening to.
     */
    public void update();

    /**
     * Requests that the Subject in question add this Listener to its list of
     * listeners.
     * 
     * @param subject The Subject to listen to.
     */
    public void listenTo(Subject subject);

    /**
     * Requests that the Subject in question remove this Listener from its list of
     * listeners.
     * 
     * @param subject The Subject to listen to.
     */
    public void stopListeningTo(Subject subject);
}
