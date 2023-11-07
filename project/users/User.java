package project.users;

import java.util.*;

import project.groups.GroupUID;
import project.tweets.ITweet;
import project.tweets.Tweet;
import project.ui.UserView;
import visitor_pattern.Entity;
import visitor_pattern.Visitor;
import observer_pattern.Listener;
import observer_pattern.Subject;

public class User implements Entity, Subject, Listener {
    private String username;
    private UserUID uid;
    private GroupUID joinedGroup;
    private List<UserUID> followers;
    private List<Listener> followListeners;
    private List<UserUID> followings;
    private List<ITweet> newsFeed;
    private List<ITweet> tweets;
    private UserView associatedUserView = null;

    public User(String username) {
        this.username = username;
        this.uid = new UserUID(this);
        joinedGroup = null;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.tweets = new ArrayList<>();
        this.followListeners = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public GroupUID getJoinedGroup() {
        return joinedGroup;
    }

    public UserUID getUID() {
        return uid;
    }

    public List<UserUID> getFollowers() {
        return followers;
    }

    public List<UserUID> getFollowings() {
        return followings;
    }

    public List<ITweet> getNewsFeed() {
        return newsFeed;
    }

    public List<ITweet> getTweets() {
        return tweets;
    }

    public Tweet tweet(String content) {
        Tweet tweet = new Tweet(content, uid);
        tweets.add(tweet);
        notifyListeners();
        return tweet;
    }

    public void followUser(UserUID other) {
        this.followings.add(other);
        other.getUser().followers.add(uid);
        listenTo(other.getUser());
        System.out.println("Followed user");
    }

    public void joinGroup(GroupUID group) {
        if (group.getGroup().addMember(uid)) {
            joinedGroup = group;
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void linkToNewView(UserView newUserView) {
        if (newUserView != null) {
            this.associatedUserView = newUserView;
            System.out.println("Connected to user view");
        }
    }

    private void notifyUserView() {
        if (associatedUserView != null) {
            System.out.println("Notified User View");
            associatedUserView.refresh();
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
        followListeners.add(l);
    }

    @Override
    public void deregister(Listener l) {
        followListeners.remove(l);
    }

    @Override
    public void notifyListeners() {
        for (Listener l : followListeners) {
            l.update();
        }
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
