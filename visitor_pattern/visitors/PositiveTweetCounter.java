package visitor_pattern.visitors;

import project.groups.UserGroup;
import project.tweets.ITweet;
import project.users.User;
import visitor_pattern.Traverser;

/**
 * A variation of the Traverser that counts how many tweets are in the database
 * have positive words.
 */
public class PositiveTweetCounter extends Traverser {

    private int total = 0;

    // A constant list of positive words.
    private final String[] positiveWords = {
            "good",
            "great",
            "happy",
            "positive",
            "amazing"
    };

    /**
     * Determines if a given tweet contains positive words.
     * 
     * @param tweet An ITWeet interface containing the tweet itself, which is a
     *              String.
     * @return true if the ITweet interface contains any positive words.
     */
    private boolean isTweetPositive(ITweet tweet) {
        boolean isPositive = false;

        // Loop through all positive words to see if it exists in the tweet.
        for (String positiveWord : positiveWords) {
            if (tweet.getTweet().toLowerCase().contains(positiveWord)) {
                isPositive = true;
                break;
            }
        }
        return isPositive;
    }

    @Override
    public void onVisitedUser(User user) {
        // Loop through all this user's tweets and count how many were positive.
        for (ITweet tweet : user.getTweets()) {
            if (isTweetPositive(tweet)) {
                total++;
            }
        }
    }

    // Not used.
    @Override
    public void onVisitedUserGroup(UserGroup group) {

    }

    /**
     * Returns the amount of positive tweets counted during this Traverser's visits.
     * Will return 0 if it did not visit any groups.
     * 
     * @return The number of positive tweets counted during this Traverser's visits.
     */
    public int getTotalPositiveTweets() {
        return total;
    }
}
