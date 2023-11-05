package visitor_pattern;

import java.util.*;

import project.ITweet;
import project.User;
import project.UserGroup;

public class PositiveTweetCounter extends Traverser {

    private int total = 0;
    private final String[] positiveWords = {
            "good",
            "great",
            "happy",
            "positive",
            "amazing"
    };

    public boolean isTweetPositive(ITweet tweet) {
        boolean isPositive = false;
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
        for (ITweet tweet : user.getTweets()) {
            if (isTweetPositive(tweet)) {
                total++;
            }
        }
    }

    @Override
    public void onVisitedUserGroup(UserGroup group) {
        // Nothing.
    }

    public int getTotalPositiveTweets() {
        return total;
    }
}
