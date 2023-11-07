package project.tweets;

import project.users.UserUID;
import project.users.User;

public class Tweet implements ITweet {
    private String content;
    private UserUID poster;

    public Tweet(String content, UserUID poster) {
        this.content = content;
        this.poster = poster;
    }

    @Override
    public String getTweet() {
        return content;
    }

    @Override
    public User getPoster() {
        return poster.getUser();
    }
}
