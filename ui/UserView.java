package ui;

import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import database.Database;
import entries.UID;
import entries.User;
import tweets.ITweet;

public class UserView extends JFrame {
    private User user;

    private JLabel userNameLabel;
    private JLabel creationTimeLabel;
    private JLabel lastUpdateTimeLabel;
    private JTextArea userUIDTextArea;
    private JButton followUserButton;
    private JTextArea tweetMessageTextArea;
    private JButton postTweetButton;
    private JLabel followingListLabel;
    private JList followingList;
    private JLabel newsFeedListLabel;
    private JList newsFeed;

    public UserView(UID uid) {
        this.user = (User) uid.getDatabaseEntry();

        // Lets the associated user object link itself to this frame so that it may
        // notify the screen to rerender on data changes.
        user.linkToNewView(this);

        // Username Label
        userNameLabel = new JLabel(user.getName());
        userNameLabel.setBounds(0, 0, 300, 30);
        add(userNameLabel);

        // Creation Time Label
        creationTimeLabel = new JLabel("Created " + user.getCreationTime());
        creationTimeLabel.setBounds(0, 10, 300, 30);
        add(creationTimeLabel);

        // Update Time Label
        lastUpdateTimeLabel = new JLabel("Last Updated " + user.getLastUpdateTime());
        lastUpdateTimeLabel.setBounds(0, 20, 300, 30);
        add(lastUpdateTimeLabel);

        // User UID Text Area
        userUIDTextArea = new JTextArea(5, 5);
        userUIDTextArea.setBounds(0, 50, 150, 75);
        add(userUIDTextArea);

        // Follow User Button
        followUserButton = new JButton("Follow User");
        followUserButton.setBounds(180, 50, 150, 75);
        followUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFollowUserButtonClicked();
            }
        });
        add(followUserButton);

        // Following List Label
        followingListLabel = new JLabel("Following List");
        followingListLabel.setBounds(0, 120, 300, 30);
        add(followingListLabel);

        // Following List View
        followingList = new JList(getFollowing());
        followingList.setBounds(0, 150, 300, 100);
        add(followingList);

        // Tweet Text Area
        tweetMessageTextArea = new JTextArea(5, 5);
        tweetMessageTextArea.setBounds(0, 260, 150, 75);
        add(tweetMessageTextArea);

        // Post Tweet Button
        postTweetButton = new JButton("Post Tweet");
        postTweetButton.setBounds(180, 260, 150, 75);
        postTweetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onPostTweetButtonClicked();
            }
        });
        add(postTweetButton);

        // News Feed List Label
        newsFeedListLabel = new JLabel("News Feed");
        newsFeedListLabel.setBounds(0, 340, 300, 30);
        add(newsFeedListLabel);

        // News Feed List View
        newsFeed = new JList(getNewsFeed());
        newsFeed.setBounds(0, 370, 300, 100);
        add(newsFeed);

        setSize(500, 500);
        setLayout(null);
        setVisible(true);
    }

    /**
     * A method that is invoked whenever the "Post Tweet" button is clicked.
     */
    protected void onPostTweetButtonClicked() {
        user.tweet(tweetMessageTextArea.getText());
    }

    /**
     * A method that is invoked whenever the "Follow User" button is clicked.
     */
    protected void onFollowUserButtonClicked() {
        // Make sure that this user is valid first.
        UID otherUID = Database.getInstance().validateUID(userUIDTextArea.getText());

        // If possible, follow that user and refresh this frame's view.
        if (otherUID != null) {
            user.followUser(otherUID);
            refreshFollowingListView();
            refreshNewsFeed();
        } else {
            JOptionPane.showMessageDialog(null, "This is not a valid user ID.");
        }
    }

    /**
     * Creates a list of users (String only) that this user is following.
     * 
     * @return An ArrayList of Users as Strings (particularly usernames).
     */
    private String[] getFollowing() {
        List<String> output = new ArrayList<>();
        for (User following : user.getFollowings()) {
            output.add(following.getName());
        }
        return output.toArray(new String[0]);
    }

    /**
     * Visually rerenders the list view that displays this user's following list.
     */
    private void refreshFollowingListView() {
        DefaultListModel<String> newListModel = new DefaultListModel<>();

        // Repopulate following feed with new data
        String[] followings = getFollowing();
        for (String s : followings) {
            newListModel.addElement(s);
        }

        followingList.setModel(newListModel);
    }

    /**
     * Creates a list of tweets (String only) that were made by accounts that this
     * user is following.
     * 
     * @return An ArrayList of Tweets as Strings.
     */
    private String[] getNewsFeed() {
        List<String> output = new ArrayList<>();
        for (User following : user.getFollowings()) {
            for (ITweet tweet : following.getTweets()) {
                output.add(tweet.getTweet());
            }
        }
        return output.toArray(new String[0]);
    }

    /**
     * Refreshes the news feed with new tweets from this account's followed list.
     */
    public void refreshNewsFeed() {
        DefaultListModel<String> updatedNewsFeed = new DefaultListModel<>();

        // Repopulate news feed with new data
        String[] newTweets = getNewsFeed();
        for (String s : newTweets) {
            updatedNewsFeed.addElement(s);
        }

        newsFeed.setModel(updatedNewsFeed);

        // Update last update time label
        lastUpdateTimeLabel.setText("Last Updated " + user.getLastUpdateTime());
    }
}
