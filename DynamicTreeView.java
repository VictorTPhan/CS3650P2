import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DynamicTreeView {
    public static void main(String[] args) {
        // Create the JFrame and JTree
        JFrame frame = new JFrame("Dynamic TreeView Example");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root Node");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        JTree tree = new JTree(treeModel);

        // Create a button to add nodes dynamically
        JButton addButton = new JButton("Add Node");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New Node");
                    treeModel.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a node to add a child node.");
                }
            }
        });

        // Create a JPanel to hold the button
        JPanel panel = new JPanel();
        panel.add(addButton);

        // Add the tree and panel to the JFrame
        frame.add(new JScrollPane(tree));
        frame.add(panel, BorderLayout.SOUTH);

        // Set up the JFrame
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
