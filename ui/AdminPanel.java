package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import database.Database;
import entries.UID;
import entries.User;
import entries.UserGroup;
import observer_pattern.Listener;
import observer_pattern.Subject;

/**
 * A Singleton that represents a UI window that adminstrators can use to manage
 * users.
 */
public class AdminPanel extends JFrame implements Listener {
    private JTextArea userNameTextArea;
    private JButton addUserButton;
    private JTextArea groupNameTextArea;
    private JButton addGroupButton;
    private JTextArea userViewUIDTextArea;
    private JButton openUserViewButton;
    private JButton showUserTotalButton;
    private JButton showGroupTotalButton;
    private JButton showMessagesTotalButton;
    private JButton showPositivePercentageButton;
    private JButton validateUIDButton;
    private JButton getLastUpdatedUserButton;
    private DefaultTreeModel databaseTreeModel;
    private DefaultMutableTreeNode rootTreeNode;
    private JTree databaseTree;

    private static AdminPanel instance;

    /**
     * Accesses the singleton instance of the admin panel. Lazy loads.
     * 
     * @return The singleton instance of the admin panel.
     */
    public static AdminPanel getInstance() {
        if (instance == null) {
            return new AdminPanel();
        } else {
            instance = new AdminPanel();
            return instance;
        }
    }

    /**
     * Generates the Admin Panel JFrame with all of the inner widgets using
     * composition.
     */
    private AdminPanel() {
        // So that the Database pay notify this view of new changes
        listenTo(Database.getInstance());

        // User Name Text Area
        userNameTextArea = new JTextArea(5, 5);
        userNameTextArea.setBounds(450, 20, 150, 75);
        add(userNameTextArea);

        // Add User Button
        addUserButton = new JButton("Add User");
        addUserButton.setBounds(620, 20, 150, 75);
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddUserButtonClicked();
            }
        });
        add(addUserButton);

        // Group Name Text Area
        groupNameTextArea = new JTextArea(5, 5);
        groupNameTextArea.setBounds(450, 110, 150, 75);
        add(groupNameTextArea);

        // Add Group Button
        addGroupButton = new JButton("Add Group");
        addGroupButton.setBounds(620, 110, 150, 75);
        addGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAddGroupButtonClicked();
            }
        });
        add(addGroupButton);

        // User View UID Text Area
        userViewUIDTextArea = new JTextArea(5, 5);
        userViewUIDTextArea.setBounds(450, 200, 150, 75);
        add(userViewUIDTextArea);

        // Open User View Text Area
        openUserViewButton = new JButton("Open User View");
        openUserViewButton.setBounds(620, 200, 150, 75);
        openUserViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOpenUserViewButtonClicked();
            }
        });
        add(openUserViewButton);

        // Show User Total Button
        showUserTotalButton = new JButton("Show User Total");
        showUserTotalButton.setBounds(450, 290, 150, 75);
        showUserTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onShowUserTotalButtonClicked();
            }
        });
        add(showUserTotalButton);

        // Show Group Total Button
        showGroupTotalButton = new JButton("Show Group Total");
        showGroupTotalButton.setBounds(620, 290, 150, 75);
        showGroupTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onShowGroupTotalButtonClicked();
            }
        });
        add(showGroupTotalButton);

        // Show Messages Total Button
        showMessagesTotalButton = new JButton("Show Messages Total");
        showMessagesTotalButton.setBounds(450, 380, 150, 75);
        showMessagesTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onShowMessagesTotalButtonClicked();
            }
        });
        add(showMessagesTotalButton);

        // Show Positive Percentage Button
        showPositivePercentageButton = new JButton("Show Positive Percentage");
        showPositivePercentageButton.setBounds(620, 380, 150, 75);
        showPositivePercentageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onShowPositivePercentageButtonClicked();
            }
        });
        add(showPositivePercentageButton);

        // Show Validate UID Button
        validateUIDButton = new JButton("Validate UIDs");
        validateUIDButton.setBounds(450, 470, 150, 75);
        validateUIDButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onValidateUIDButtonClicked();
            }
        });
        add(validateUIDButton);

        // Show Get Most Recently Updated User Button
        getLastUpdatedUserButton = new JButton("Get Most Recently Updated User");
        getLastUpdatedUserButton.setBounds(620, 470, 150, 75);
        getLastUpdatedUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onGetLastUpdatedUserButtonClicked();
            }
        });
        add(getLastUpdatedUserButton);

        // Widgets Releated to the Database Tree
        rootTreeNode = new DefaultMutableTreeNode(Database.getInstance().getRoot());
        databaseTreeModel = new DefaultTreeModel(rootTreeNode);
        databaseTree = new JTree(databaseTreeModel);
        databaseTree.setCellRenderer(new CustomTreeCellRenderer());

        // Database Tree
        databaseTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                onItemInTreeSelected();
            }
        });

        JScrollPane pane = new JScrollPane(databaseTree);
        pane.setBounds(0, 0, 400, 400);
        add(pane);

        setSize(800, 600);
        setLayout(null);
        setVisible(true);
    }

    /**
     * A method that is invoked whenever the "Add Group" button is clicked.
     * Generates a new UserGroup in the database provided that the currently
     * selected node in the tree is another UserGroup.
     */
    protected void onAddGroupButtonClicked() {
        // Get the currently selected node in the tree.
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) databaseTree.getLastSelectedPathComponent();

        if (selectedNode != null) {
            Object userObject = selectedNode.getUserObject();
            if (userObject instanceof User) {
                JOptionPane.showMessageDialog(null, "You cannot add a group inside another user.");
            } else if (userObject instanceof UserGroup) {
                ((UserGroup) userObject).addSubGroup(new UserGroup(groupNameTextArea.getText()));
            } else {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a node to add a child node.");
        }
    }

    /**
     * A method that is invoked whenever a node on the database tree is selected.
     */
    protected void onItemInTreeSelected() {
        // Get the selected node in the tree
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) databaseTree.getLastSelectedPathComponent();

        // If the node was a user, then fill in the User View UID text area with that
        // user's UID
        if (selectedNode != null) {
            Object userObject = selectedNode.getUserObject();
            if (userObject instanceof User) {
                userViewUIDTextArea.setText(((User) userObject).getUID().getUID());
            }
        }
    }

    /**
     * A method that is invoked whenever the "Add User" button is clicked on.
     * Generates a new user in the database provided that we are currently selecting
     * a UserGroup within the database tree.
     */
    protected void onAddUserButtonClicked() {
        // Get the selected node in the tree
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) databaseTree.getLastSelectedPathComponent();

        if (selectedNode != null) {
            Object userObject = selectedNode.getUserObject();
            if (userObject instanceof User) {
                JOptionPane.showMessageDialog(null, "You cannot add a user inside another user.");
            } else if (userObject instanceof UserGroup) {
                ((UserGroup) userObject).addMember(new User(userNameTextArea.getText()).getUID());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a node to add a child node.");
        }
    }

    /**
     * A method that is invoked whenever the "Open User View" button is clicked on.
     * Generates a new JFrame if we are currently selecting a valid user or if a
     * valid UID was supplied to the "User View UID" text area.
     */
    protected void onOpenUserViewButtonClicked() {
        UID uid = Database.getInstance().validateUID(userViewUIDTextArea.getText());
        if (uid != null) {
            new UserView(uid);
        } else {
            JOptionPane.showMessageDialog(null, "This is not a valid user ID.");
        }
    }

    /**
     * A method that is invoked whenever the "Show User Total" button is clicked on.
     */
    protected void onShowUserTotalButtonClicked() {
        int totalUsers = Database.getInstance().getTotalUsers();
        JOptionPane.showMessageDialog(null, "There are " + totalUsers + " users.");
    }

    /**
     * A method that is invoked whenever the "Show Group Total" button is clicked
     * on.
     */
    protected void onShowGroupTotalButtonClicked() {
        int totalGroups = Database.getInstance().getTotalGroups();
        JOptionPane.showMessageDialog(null, "There are " + totalGroups + " groups.");
    }

    /**
     * A method that is invoked whenever the "Show Messages Total" button is clicked
     * on.
     */
    protected void onShowMessagesTotalButtonClicked() {
        int totalMessages = Database.getInstance().getTotalTweets();
        JOptionPane.showMessageDialog(null, "There are " + totalMessages + " tweets.");
    }

    /**
     * A method that is invokved whenever the "Show Positive Percentage" button is
     * clicked on.
     */
    protected void onShowPositivePercentageButtonClicked() {
        // Calculate the percentage
        int totalMessages = Database.getInstance().getTotalTweets();
        int totalPositiveTweets = Database.getInstance().getTotalPositiveTweets();
        int percentage = (int) ((((double) totalPositiveTweets) / totalMessages) * 100);

        JOptionPane.showMessageDialog(null, percentage + "% of tweets are positive.");
    }

    /**
     * A method that is invoked whenever the "Validate UID" button is clicked on.
     */
    protected void onValidateUIDButtonClicked() {
        boolean result = Database.getInstance().doUIDValidityCheck();
        String resultText = result ? "All UIDs are valid." : "Invalid UIDs!";

        JOptionPane.showMessageDialog(null, resultText);
    }

    /**
     * A method that is invoked whenever the "Get Most Recently Updated User" button
     * is clicked on.
     */
    protected void onGetLastUpdatedUserButtonClicked() {
        UID result = Database.getInstance().getMostRecentlyUpdatedUser();

        JOptionPane.showMessageDialog(null, "The most recently updated user has UID " + result.getUID());
    }

    /**
     * Builds the root node of the tree, and recursively builds the rest based on
     * the root's subgroups.
     * 
     * @param rootGroup The root UserGroup as supplied by the Database Singleton.
     */
    private void addRootGroup(UserGroup rootGroup) {
        // Add all members
        for (UID member : rootGroup.getMembers()) {
            rootTreeNode.add(new DefaultMutableTreeNode(member.getDatabaseEntry()));
        }

        // Recursively add all subgroups
        for (UserGroup subGroup : rootGroup.getSubGroups()) {
            addGroupToTreeNode(rootTreeNode, subGroup);
        }
    }

    /**
     * Builds a group within the tree under a specified node and recursively builds
     * the rest based on the group's subgroups.
     * 
     * @param current The parent of this group.
     * @param group   The UserGroup we want to display.
     */
    private void addGroupToTreeNode(DefaultMutableTreeNode current, UserGroup group) {
        // Create a node for this group.
        DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);

        // Add all members
        for (UID member : group.getMembers()) {
            groupNode.add(new DefaultMutableTreeNode(member.getDatabaseEntry()));
        }

        // Recursively add all sub groups
        for (UserGroup subGroup : group.getSubGroups()) {
            addGroupToTreeNode(groupNode, subGroup);
        }

        // Add this group to the parent
        current.add(groupNode);
    }

    // Invoked by the Database
    @Override
    public void update() {
        rootTreeNode.removeAllChildren();
        databaseTreeModel.reload();

        addRootGroup(Database.getInstance().getRoot());
        databaseTreeModel.reload();
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
