package project.tweets;

import project.users.UserUID;
import project.users.User;

/**
 * A data representation of a Tweet in the Twitter database.
 */
public class Tweet implements ITweet {
    private String content;
    private UserUID poster;

    /**
     * Creates a Tweet object.
     * 
     * @param content The actual content of the tweet, as a String.
     * @param poster  The UserUID of the tweet author.
     */
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
