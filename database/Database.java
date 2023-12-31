package database;

import observer_pattern.Listener;
import observer_pattern.Subject;
import visitor_pattern.visitors.GroupCounter;
import visitor_pattern.visitors.LastUpdatedTimestampFinder;
import visitor_pattern.visitors.MessageCounter;
import visitor_pattern.visitors.PositiveTweetCounter;
import visitor_pattern.visitors.UIDValidator;
import visitor_pattern.visitors.UserCounter;
import visitor_pattern.visitors.UserSearcher;

import java.util.*;

import entries.UID;
import entries.UserGroup;

/**
 * A Singleton that maintains a way to access the root group of the Twitter
 * database.
 */
public class Database implements Subject {
    private UserGroup root;
    private static Database instance;
    private List<Listener> listeners;

    /**
     * Fetches the Singleton instance of this Database.
     * 
     * @return A reference to a Database Singleton.
     */
    public static Database getInstance() {
        // Lazy loading.
        if (instance == null) {
            instance = new Database();
            return instance;
        }
        return instance;
    }

    private Database() {
        listeners = new ArrayList<>();

        // Create a group named "Root".
        root = new UserGroup("Root");
    }

    /**
     * Accesses the root group of the database, for which all users and groups are
     * categorized under.
     * 
     * @return The root group of the database.
     */
    public UserGroup getRoot() {
        return root;
    }

    @Override
    public void register(Listener l) {
        // Adds this listener only if it is not in our listeners list.
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

    /**
     * A function that should be invoked within a Database instance whenever a
     * modification is made to the UserGroup tree, either by a new subgroup or a new
     * user.
     */
    public void newEntryCallback() {
        notifyListeners();
    }

    /**
     * Searches the database starting at the root node for a user with the given UID
     * String.
     * 
     * @param UIDString A String representation of the UID of the target user.
     * @return A UID object of the user if they were found. Otherwise, returns null.
     */
    public UID validateUID(String UIDString) {
        UserSearcher userSearcher = new UserSearcher(UIDString);
        root.accept(userSearcher);
        System.out.println("Result: " + userSearcher.getSearchResult());
        if (userSearcher.getSearchResult() != null) {
            return userSearcher.getSearchResult().getUID();
        } else {
            return null;
        }
    }

    /**
     * Counts how many users exist within the database, starting at the root node.
     * 
     * @return The number of users within the database.
     */
    public int getTotalUsers() {
        UserCounter userCounter = new UserCounter();
        root.accept(userCounter);
        return userCounter.getUserCount();
    }

    /**
     * Counts how many groups exist within the database, starting at the root node.
     * 
     * @return The number of groups within the database.
     */
    public int getTotalGroups() {
        GroupCounter groupCounter = new GroupCounter();
        root.accept(groupCounter);
        return groupCounter.getGroupCount();
    }

    /**
     * Counts how many tweets exist within the database across all users, starting
     * at the root node.
     * 
     * @return The number of tweets in the database.
     */
    public int getTotalTweets() {
        MessageCounter messageCounter = new MessageCounter();
        root.accept(messageCounter);
        return messageCounter.getTweetCount();
    }

    /**
     * Counts how many positive tweets exist within the database across all users,
     * starting at the root node.
     * 
     * @return The number of positive tweets in the database.
     */
    public int getTotalPositiveTweets() {
        PositiveTweetCounter positiveTweetCounter = new PositiveTweetCounter();
        root.accept(positiveTweetCounter);
        return positiveTweetCounter.getTotalPositiveTweets();
    }

    /**
     * Does a check to see if all UIDs in the system are valid.
     * 
     * @return True if all UIDs are unique and have no spaces.
     */
    public boolean doUIDValidityCheck() {
        UIDValidator uidValidator = new UIDValidator();
        root.accept(uidValidator);
        return uidValidator.getResult();
    }

    /**
     * Determines the User with the most recently updated lastUpdateTime field in
     * the system.
     * 
     * @return the UID of the resulting User.
     */
    public UID getMostRecentlyUpdatedUser() {
        LastUpdatedTimestampFinder lastUpdatedTimestampFinder = new LastUpdatedTimestampFinder();
        root.accept(lastUpdatedTimestampFinder);
        return lastUpdatedTimestampFinder.getResult();
    }
}
