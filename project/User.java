package project;

import java.util.*;

import project.GroupUID;
import project.ITweet;
import project.Tweet;
import project.UID;
import visitor_pattern.Entity;
import visitor_pattern.Visitor;

public class User implements Entity {
    private String username;
    private UID uid;
    private GroupUID joinedGroup;
    private List<UID> followers;
    private List<UID> followings;
    private List<ITweet> newsFeed;
    private List<ITweet> tweets;

    public User(String username) {
        this.username = username;
        this.uid = new UID(this);
        joinedGroup = null;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.tweets = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public GroupUID getJoinedGroup() {
        return joinedGroup;
    }

    public UID getUID() {
        return uid;
    }

    public List<UID> getFollowers() {
        return followers;
    }

    public List<UID> getFollowings() {
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
        return tweet;
    }

    public void followUser(UID other) {
        this.followings.add(other);
        other.getUser().followers.add(uid);
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
}
