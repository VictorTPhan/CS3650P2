package project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import database.Database;
import project.groups.UserGroup;
import project.users.UID;
import project.users.User;

public class AdminPanel extends JFrame {
    private static AdminPanel instance;

    public static AdminPanel getInstance() {
        if (instance == null) {
            return new AdminPanel();
        } else {
            instance = new AdminPanel();
            return instance;
        }
    }

    private AdminPanel() {
        JTextArea userUIDTextArea = new JTextArea(5, 5);
        userUIDTextArea.setBounds(450, 20, 150, 75);
        add(userUIDTextArea);

        JButton addUserButton = new JButton("Add User");
        addUserButton.setBounds(620, 20, 150, 75);
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Database.getInstance().getRoot().addMember(new User(userUIDTextArea.getText()).getUID());
            }
        });
        add(addUserButton);

        JTextArea groupUIDTextArea = new JTextArea(5, 5);
        groupUIDTextArea.setBounds(450, 110, 150, 75);
        add(groupUIDTextArea);

        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.setBounds(620, 110, 150, 75);
        addGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Database.getInstance().getRoot().addSubGroup(new UserGroup(groupUIDTextArea.getText()).getUID());
            }
        });
        add(addGroupButton);

        JTextArea userViewUIDTextArea = new JTextArea(5, 5);
        userViewUIDTextArea.setBounds(450, 200, 150, 75);
        add(userViewUIDTextArea);

        JButton openUserViewButton = new JButton("Open User View");
        openUserViewButton.setBounds(620, 200, 150, 75);
        openUserViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UID uid = Database.getInstance().validateUID(userViewUIDTextArea.getText());
                if (uid != null) {
                    System.out.println("Making new window to view user");
                    new UserView(uid);
                }
            }
        });
        add(openUserViewButton);

        JButton showUserTotalButton = new JButton("Show User Total");
        showUserTotalButton.setBounds(450, 290, 150, 75);
        showUserTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int totalUsers = Database.getInstance().getTotalUsers();
                System.out.println(totalUsers);
            }
        });
        add(showUserTotalButton);

        JButton showGroupTotalButton = new JButton("Show Group Total");
        showGroupTotalButton.setBounds(620, 290, 150, 75);
        showGroupTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int totalGroups = Database.getInstance().getTotalGroups();
                System.out.println(totalGroups);
            }
        });
        add(showGroupTotalButton);

        JButton showMessagesTotalButton = new JButton("Show Messages Total");
        showMessagesTotalButton.setBounds(450, 380, 150, 75);
        showMessagesTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int totalMessages = Database.getInstance().getTotalTweets();
                System.out.println(totalMessages);
            }
        });
        add(showMessagesTotalButton);

        JButton showPositivePercentageButton = new JButton("Show Positive Percentage");
        showPositivePercentageButton.setBounds(620, 380, 150, 75);
        showPositivePercentageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int totalPositiveTweets = Database.getInstance().getTotalPositiveTweets();
                System.out.println(totalPositiveTweets);
            }
        });
        add(showPositivePercentageButton);

        setSize(800, 600);
        setLayout(null);
        setVisible(true);
    }
}
