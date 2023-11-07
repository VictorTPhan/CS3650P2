package project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import database.Database;
import observer_pattern.Listener;
import observer_pattern.Subject;
import project.groups.UserGroup;
import project.users.UID;
import project.users.User;

public class AdminPanel extends JFrame implements Listener {
    private static AdminPanel instance;
    DefaultTreeModel treeModel;
    DefaultMutableTreeNode root;
    JTree tree;

    public static AdminPanel getInstance() {
        if (instance == null) {
            return new AdminPanel();
        } else {
            instance = new AdminPanel();
            return instance;
        }
    }

    private AdminPanel() {
        listenTo(Database.getInstance());

        JTextArea userUIDTextArea = new JTextArea(5, 5);
        userUIDTextArea.setBounds(450, 20, 150, 75);
        add(userUIDTextArea);

        JButton addUserButton = new JButton("Add User");
        addUserButton.setBounds(620, 20, 150, 75);
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    Object userObject = selectedNode.getUserObject();
                    if (userObject instanceof User) {
                        JOptionPane.showMessageDialog(null, "You cannot add a user inside another user.");
                    } else if (userObject instanceof UserGroup) {
                        ((UserGroup) userObject).addMember(new User(userUIDTextArea.getText()).getUID());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a node to add a child node.");
                }
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
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    Object userObject = selectedNode.getUserObject();
                    if (userObject instanceof User) {
                        JOptionPane.showMessageDialog(null, "You cannot add a group inside another user.");
                    } else if (userObject instanceof UserGroup) {
                        ((UserGroup) userObject).addSubGroup(new UserGroup(groupUIDTextArea.getText()).getUID());
                    } else {
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a node to add a child node.");
                }
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

        // Create the JFrame and JTree
        root = new DefaultMutableTreeNode(Database.getInstance().getRoot());
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);

        // Add a TreeSelectionListener to the JTree
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    Object userObject = selectedNode.getUserObject();
                    if (userObject instanceof User) {
                        userViewUIDTextArea.setText(((User) userObject).getUID().getUID());
                    }
                }
            }
        });

        JScrollPane pane = new JScrollPane(tree);
        pane.setBounds(0, 0, 400, 400);

        // Add the tree and panel to the JFrame
        add(pane);

        setSize(800, 600);
        setLayout(null);
        setVisible(true);
    }

    private void addRootGroup(UserGroup rootGroup) {
        for (UID member : rootGroup.getMembers()) {
            root.add(new DefaultMutableTreeNode(member.getUser()));
        }
        for (UserGroup subGroup : rootGroup.getSubGroups()) {
            addGroupToTreeNode(root, subGroup);
        }
    }

    private void addGroupToTreeNode(DefaultMutableTreeNode current, UserGroup group) {
        DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
        for (UID member : group.getMembers()) {
            groupNode.add(new DefaultMutableTreeNode(member.getUser()));
        }
        for (UserGroup subGroup : group.getSubGroups()) {
            addGroupToTreeNode(groupNode, subGroup);
        }
        current.add(groupNode);
    }

    @Override
    public void update() {
        System.out.println("Refreshing Tree");

        // Clear the existing nodes
        root.removeAllChildren();
        treeModel.reload();

        addRootGroup(Database.getInstance().getRoot());

        // Reload the tree model to reflect the changes
        treeModel.reload();
    }

    @Override
    public void listenTo(Subject s) {
        s.register(this);
    }

    @Override
    public void stopListeningTo(Subject s) {
        s.deregister(this);
    }

}
