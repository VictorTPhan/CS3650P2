package project.ui;

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
import project.tweets.ITweet;
import project.users.UserUID;

public class UserView extends JFrame {
    private UserUID uid;

    private JLabel userNameLabel;
    private JTextArea userUIDTextArea;
    private JButton followUserButton;
    private JTextArea tweetMessageTextArea;
    private JButton postTweetButton;
    private JLabel followingListLabel;
    private JList followingList;
    private JLabel newsFeedListLabel;
    private JList newsFeed;

    public UserView(UserUID uid) {
        this.uid = uid;

        // Lets the associated user object link itself to this frame so that it may
        // notify the screen to rerender on data changes.
        this.uid.getUser().linkToNewView(this);

        // Username Label
        userNameLabel = new JLabel(uid.getUser().getUsername());
        userNameLabel.setBounds(0, 0, 300, 30);
        add(userNameLabel);

        // User UID Text Area
        userUIDTextArea = new JTextArea(5, 5);
        userUIDTextArea.setBounds(0, 30, 150, 75);
        add(userUIDTextArea);

        // Follow User Button
        followUserButton = new JButton("Follow User");
        followUserButton.setBounds(180, 30, 150, 75);
        followUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onFollowUserButtonClicked();
            }
        });
        add(followUserButton);

        // Following List Label
        followingListLabel = new JLabel("Following List");
        followingListLabel.setBounds(0, 100, 300, 30);
        add(followingListLabel);

        // Following List View
        followingList = new JList(getFollowing());
        followingList.setBounds(0, 130, 300, 100);
        add(followingList);

        // Tweet Text Area
        tweetMessageTextArea = new JTextArea(5, 5);
        tweetMessageTextArea.setBounds(0, 240, 150, 75);
        add(tweetMessageTextArea);

        // Post Tweet Button
        postTweetButton = new JButton("Post Tweet");
        postTweetButton.setBounds(180, 240, 150, 75);
        postTweetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onPostTweetButtonClicked();
            }
        });
        add(postTweetButton);

        // News Feed List Label
        newsFeedListLabel = new JLabel("News Feed");
        newsFeedListLabel.setBounds(0, 320, 300, 30);
        add(newsFeedListLabel);

        // News Feed List View
        newsFeed = new JList(getNewsFeed());
        newsFeed.setBounds(0, 350, 300, 100);
        add(newsFeed);

        setSize(500, 500);
        setLayout(null);
        setVisible(true);
    }

    /**
     * A method that is invoked whenever the "Post Tweet" button is clicked.
     */
    protected void onPostTweetButtonClicked() {
        uid.getUser().tweet(tweetMessageTextArea.getText());
    }

    /**
     * A method that is invoked whenever the "Follow User" button is clicked.
     */
    protected void onFollowUserButtonClicked() {
        // Make sure that this user is valid first.
        UserUID otherUID = Database.getInstance().validateUID(userUIDTextArea.getText());

        // If possible, follow that user and refresh this frame's view.
        if (otherUID != null) {
            uid.getUser().followUser(otherUID);
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
        for (UserUID following : uid.getUser().getFollowings()) {
            output.add(following.getUser().getUsername());
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
        for (UserUID following : uid.getUser().getFollowings()) {
            for (ITweet tweet : following.getUser().getTweets()) {
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
    }
}
