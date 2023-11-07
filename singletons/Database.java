package singletons;

import observer_pattern.Listener;
import observer_pattern.Subject;
import project.groups.UserGroup;
import project.users.UID;
import visitor_pattern.visitors.GroupCounter;
import visitor_pattern.visitors.MessageCounter;
import visitor_pattern.visitors.PositiveTweetCounter;
import visitor_pattern.visitors.UserCounter;
import visitor_pattern.visitors.UserSearcher;

import java.util.*;

public class Database implements Subject {
    private UserGroup root;
    private static Database instance;
    private List<Listener> listeners;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
            return instance;
        }
        return instance;
    }

    private Database() {
        listeners = new ArrayList<>();
        root = new UserGroup("Root");
    }

    public UserGroup getRoot() {
        return root;
    }

    @Override
    public void register(Listener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void deregister(Listener l) {
        listeners.remove(l);
    }

    @Override
    public void notifyListeners() {
        for (Listener l : listeners) {
            l.update();
        }
    }

    public void newEntryCallback() {
        notifyListeners();
    }

    public UID validateUID(String text) {
        UserSearcher userSearcher = new UserSearcher(text);
        root.accept(userSearcher);
        System.out.println("Result: " + userSearcher.getSearchResult());
        return userSearcher.getSearchResult().getUID();
    }

    public int getTotalUsers() {
        UserCounter userCounter = new UserCounter();
        root.accept(userCounter);
        return userCounter.getUserCount();
    }

    public int getTotalGroups() {
        GroupCounter groupCounter = new GroupCounter();
        root.accept(groupCounter);
        return groupCounter.getGroupCount();
    }

    public int getTotalTweets() {
        MessageCounter messageCounter = new MessageCounter();
        root.accept(messageCounter);
        return messageCounter.getTweetCount();
    }

    public int getTotalPositiveTweets() {
        PositiveTweetCounter positiveTweetCounter = new PositiveTweetCounter();
        root.accept(positiveTweetCounter);
        return positiveTweetCounter.getTotalPositiveTweets();
    }
}
