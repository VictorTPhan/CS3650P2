package project.ui;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

import project.tweets.ITweet;
import project.users.UID;
import singletons.Database;

public class UserView extends JFrame {
    private UID uid;
    private JList followingList;
    private JList newsFeed;

    public UserView(UID uid) {
        this.uid = uid;
        this.uid.getUser().linkToNewView(this);

        JLabel userNameLabel = new JLabel(uid.getUser().getUsername());
        userNameLabel.setBounds(0, 0, 300, 30);
        add(userNameLabel);

        JTextArea userUIDTextArea = new JTextArea(5, 5);
        userUIDTextArea.setBounds(0, 30, 150, 75);
        add(userUIDTextArea);

        JButton followUserButton = new JButton("Follow User");
        followUserButton.setBounds(180, 30, 150, 75);
        followUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UID otherUID = Database.getInstance().validateUID(userUIDTextArea.getText());
                if (otherUID != null) {
                    uid.getUser().followUser(otherUID);

                    DefaultListModel<String> newListModel = new DefaultListModel<>();
                    String[] followings = getFollowing();
                    for (String s : followings) {
                        newListModel.addElement(s);
                    }
                    followingList.setModel(newListModel);
                }
            }
        });
        add(followUserButton);

        followingList = new JList(getFollowing());
        followingList.setBounds(0, 120, 300, 100);
        add(followingList);

        JTextArea tweetMessageTextArea = new JTextArea(5, 5);
        tweetMessageTextArea.setBounds(0, 240, 150, 75);
        add(tweetMessageTextArea);

        JButton postTweetButton = new JButton("Post Tweet");
        postTweetButton.setBounds(180, 240, 150, 75);
        postTweetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uid.getUser().tweet(tweetMessageTextArea.getText());
            }
        });
        add(postTweetButton);

        newsFeed = new JList(getNewsFeed());
        newsFeed.setBounds(0, 340, 300, 100);
        add(newsFeed);

        setSize(500, 500);
        setLayout(null);
        setVisible(true);
    }

    private String[] getFollowing() {
        List<String> output = new ArrayList<>();
        for (UID following : uid.getUser().getFollowings()) {
            output.add(following.getUser().getUsername());
        }
        return output.toArray(new String[0]);
    }

    private String[] getNewsFeed() {
        List<String> output = new ArrayList<>();
        for (UID following : uid.getUser().getFollowings()) {
            for (ITweet tweet : following.getUser().getTweets()) {
                output.add(tweet.getTweet());
            }
        }
        return output.toArray(new String[0]);
    }

    void onUserFollowingTweetedSomething(UID userFollowing) {

    }

    void onTweetedSomething() {

    }

    public void refresh() {
        DefaultListModel<String> newFeed = new DefaultListModel<>();
        String[] followings = getNewsFeed();
        for (String s : followings) {
            newFeed.addElement(s);
        }
        newsFeed.setModel(newFeed);
    }
}
