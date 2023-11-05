package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class UserView extends JFrame {
    public UserView(UID uid) {
        JTextArea userUIDTextArea = new JTextArea(5, 5);
        userUIDTextArea.setBounds(0, 20, 150, 75);
        add(userUIDTextArea);

        JButton followUserButton = new JButton("Follow User");
        followUserButton.setBounds(180, 20, 150, 75);
        followUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UID otherUID = Database.getInstance().validateUID(userUIDTextArea.getText());
                if (otherUID != null) {
                    uid.getUser().followUser(otherUID);
                }
            }
        });
        add(followUserButton);

        JTextArea tweetMessageTextArea = new JTextArea(5, 5);
        tweetMessageTextArea.setBounds(0, 110, 150, 75);
        add(tweetMessageTextArea);

        JButton postTweetButton = new JButton("Post Tweet");
        postTweetButton.setBounds(180, 110, 150, 75);
        postTweetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uid.getUser().tweet(tweetMessageTextArea.getText());
            }
        });
        add(postTweetButton);

        setSize(500, 500);
        setLayout(null);
        setVisible(true);
    }
}
