package observer_pattern;

public interface Listener {
    public void update();

    public void listenTo(Subject s);

    public void stopListeningTo(Subject s);
}
