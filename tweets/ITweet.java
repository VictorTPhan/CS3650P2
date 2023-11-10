package tweets;

import entries.User;

/**
 * An interface representing a Tweet in the Twitter database.
 */
public interface ITweet {
    /**
     * Accesses the content of the tweet.
     * 
     * @return A String representation of the tweet.
     */
    public String getTweet();

    /**
     * Accesses the User who poste the tweet.
     * 
     * @return The User who posted the tweet.
     */
    public User getPoster();
}
