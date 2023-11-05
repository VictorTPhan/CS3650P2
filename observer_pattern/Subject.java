package observer_pattern;

public interface Subject {
    public void register(Listener l);

    public void deregister(Listener l);

    public void notifyListeners();
}
