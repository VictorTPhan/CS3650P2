package project;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import observer_pattern.Listener;
import observer_pattern.Subject;

public class AdminObjectTree extends JPanel implements Listener {
    private Subject databaseRefSubject;
    DefaultMutableTreeNode root;
    private JTree tree;

    public AdminObjectTree() {
        listenTo(Database.getInstance());

        root = new DefaultMutableTreeNode("Root");
        tree = new JTree(root);
        add(tree);
    }

    public void regenerateTree() {
        DefaultMutableTreeNode child = new DefaultMutableTreeNode("Lol");
        root.insert(child, root.getChildCount());
        tree.scrollPathToVisible(new TreePath(child.getPath()));
    }

    @Override
    public void update() {
        regenerateTree();
    }

    @Override
    public void listenTo(Subject s) {
        databaseRefSubject = s;
        s.register(this);
    }

    @Override
    public void stopListeningTo(Subject s) {
        s.deregister(this);
        databaseRefSubject = null;
    }
}
