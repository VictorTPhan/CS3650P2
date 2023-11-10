package project.users;

import project.tweets.ITweet;
import project.tweets.Tweet;
import project.ui.UserView;
import visitor_pattern.Entity;
import visitor_pattern.Visitor;

import java.util.ArrayList;
import java.util.List;

import observer_pattern.Listener;
import observer_pattern.Subject;

/**
 * A representation of a User within the Twitter database, that can make their
 * own tweets and follow other users.
 */
public class User implements Entity, Subject, Listener {
    private String username;
    private UserUID uid;
    private List<Listener> followers;
    private List<UserUID> followings;
    private List<ITweet> tweets;
    private UserView associatedUserView;

    /**
     * Initializes all the fields of a User object.
     * 
     * @param username The username of this User as a String.
     */
    public User(String username) {
        this.username = username;
        this.uid = new UserUID(this);
        this.followings = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.followers = new ArrayList<>();
        associatedUserView = null;
    }

    /**
     * Gets the username of this User.
     * 
     * @return The username of this user, as a String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the UID of this User.
     * 
     * @return The UserUID of this user, as a String.
     */
    public UserUID getUID() {
        return uid;
    }

    /**
     * Gets the list of accounts this User is following.
     * 
     * @return The list of user this User is following, as an ArrayList of UserUIDs.
     */
    public List<UserUID> getFollowings() {
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
     * Creates a Tweet object and notifies all followers of a new Tweet.
     * 
     * @param content The textual portion of the Tweet itself.
     * @return The created Tweet.
     */
    public Tweet tweet(String content) {
        Tweet tweet = new Tweet(content, uid);
        tweets.add(tweet);
        notifyListeners();
        return tweet;
    }

    /**
     * Puts this User on the other User's follower's list so that this User may be
     * notified of any new tweets from the other User.
     * 
     * @param other The User to follow.
     */
    public void followUser(UserUID other) {
        this.followings.add(other);
        listenTo(other.getUser());
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
        return getUsername();
    }
}
