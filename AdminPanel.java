import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.xml.crypto.Data;

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

        JButton openUserViewButton = new JButton("Open User View");
        openUserViewButton.setBounds(620, 200, 150, 75);
        add(openUserViewButton);

        JTextArea userViewUIDTextArea = new JTextArea(5, 5);
        userViewUIDTextArea.setBounds(450, 200, 150, 75);
        add(userViewUIDTextArea);

        JButton showUserTotalButton = new JButton("Show User Total");
        showUserTotalButton.setBounds(450, 290, 150, 75);
        add(showUserTotalButton);

        JButton showGroupTotalButton = new JButton("Show Group Total");
        showGroupTotalButton.setBounds(620, 290, 150, 75);
        add(showGroupTotalButton);

        JButton showMessagesTotalButton = new JButton("Show Messages Total");
        showMessagesTotalButton.setBounds(450, 380, 150, 75);
        add(showMessagesTotalButton);

        JButton showPositivePercentageButton = new JButton("Show Positive Percentage");
        showPositivePercentageButton.setBounds(620, 380, 150, 75);
        add(showPositivePercentageButton);

        setSize(800, 600);
        setLayout(null);
        setVisible(true);
    }
}
