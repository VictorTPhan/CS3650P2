package entries;

import tweets.ITweet;
import tweets.Tweet;
import ui.UserView;
import visitor_pattern.Visitor;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseEntry;
import observer_pattern.Listener;
import observer_pattern.Subject;

/**
 * A representation of a User within the Twitter database, that can make their
 * own tweets and follow other users.
 */
public class User extends DatabaseEntry implements Subject, Listener {
    private List<Listener> followers;
    private List<User> followings;
    private List<ITweet> tweets;
    private UserView associatedUserView;
    private long creationTime;
    private long lastUpdateTime;

    /**
     * Initializes all the fields of a User object.
     * 
     * @param username The username of this User as a String.
     */
    public User(String username) {
        this.name = username;
        this.uid = new UID(this);
        this.followings = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.creationTime = System.currentTimeMillis();
        this.lastUpdateTime = creationTime;
        associatedUserView = null;
    }

    /**
     * Gets the list of accounts this User is following.
     * 
     * @return The list of user this User is following, as an ArrayList of UserUIDs.
     */
    public List<User> getFollowings() {
        return followings;
    }

    /**
     * Gets the list of tweets this user has made.
     * 
     * @return The list of tweets this user has made, as an ArrayList of ITweets.
     */
    public List<ITweet> getTweets() {
        return tweets;
    }

    /**
     * Gets the time this user was made.
     * 
     * @return the time in milliseconds that this user was made.
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * Gets the timestamp of when the user last made a post or when their news feed
     * was updated.
     * 
     * @return the timestamp of the last user made a post or when their newsfeed was
     *         updated.
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * Resets the lastUpdateTime timestamp field of this User to the current time in
     * milliseconds.
     */
    private void updateLastUpdateTimestamp() {
        lastUpdateTime = System.currentTimeMillis();
    }

    /**
     * Creates a Tweet object and notifies all followers of a new Tweet.
     * 
     * @param content The textual portion of the Tweet itself.
     * @return The created Tweet.
     */
    public Tweet tweet(String content) {
        Tweet tweet = new Tweet(content, uid);
        tweets.add(tweet);
        updateLastUpdateTimestamp();
        notifyUserView();
        notifyListeners();
        return tweet;
    }

    /**
     * Puts this User on the other User's follower's list so that this User may be
     * notified of any new tweets from the other User.
     * 
     * @param other The User to follow.
     */
    public void followUser(UID other) {
        DatabaseEntry entry = other.getDatabaseEntry();
        if (entry instanceof User) {
            this.followings.add((User) entry);
            listenTo((User) entry);
        }
        updateLastUpdateTimestamp();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * Links this User object to a JSwing Frame so that the frame can be refreshed
     * whenever this User is notified of any new data to display.
     * 
     * @param newUserView The UserView object to associate with this User.
     */
    public void linkToNewView(UserView newUserView) {
        if (newUserView != null) {
            this.associatedUserView = newUserView;
        }
    }

    /**
     * A function that tells the UserView to refresh its content.
     */
    private void notifyUserView() {
        if (associatedUserView != null) {
            associatedUserView.refreshNewsFeed();
        }
    }

    @Override
    public void update() {
        updateLastUpdateTimestamp();
        notifyUserView();
    }

    @Override
    public void listenTo(Subject s) {
        s.register(this);
    }

    @Override
    public void stopListeningTo(Subject s) {
        s.deregister(this);
    }

    @Override
    public void register(Listener l) {
        followers.add(l);
    }

    @Override
    public void deregister(Listener l) {
        followers.remove(l);
    }

    @Override
    public void notifyListeners() {
        for (Listener l : followers) {
            l.update();
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
