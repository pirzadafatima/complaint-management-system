import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;


public class EmployeeHomePage {
    private JFrame frame;

    private JButton viewNewComplaintButton;
    private JButton logoutButton;

    public EmployeeHomePage(String n, String p, List<Complaint> cL) {
        createUI(n, p, cL);
        frame.setVisible(true);
    }

    private void createUI(String n, String p,List<Complaint> cL) {
        frame = new JFrame("Employee");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        viewNewComplaintButton = new JButton("View New Complaints");
        logoutButton = new JButton("Logout");

        

        viewNewComplaintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //frame.dispose();
                new ViewNewComplaintsPageE(n, p, cL);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    frame.dispose();
                new LoginPage(); // Assuming LoginPage is the class for the login page
            }
        }
        });

        // Add buttons to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(viewNewComplaintButton);
        buttonPanel.add(logoutButton);
        

        // Add the panel with the buttons to the center panel
        centerPanel.add(buttonPanel);

        frame.add(centerPanel, BorderLayout.CENTER);
    }
}