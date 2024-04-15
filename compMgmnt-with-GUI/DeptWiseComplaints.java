import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DeptWiseComplaints {
    private JFrame frame;
    private JButton ITButton;
    private JButton adminButton;
    private JButton accountsButton;
    private JButton logoutButton;

    public DeptWiseComplaints(String n, String p, List<Complaint> it, List<Complaint> admin, List<Complaint> accounts) {
        createUI(n, p, it, admin, accounts);
        frame.setVisible(true);
    }

    private void createUI(String n, String p, List<Complaint> it, List<Complaint> admin, List<Complaint> accounts) {
        frame = new JFrame("Departments");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ITButton = new JButton("IT Department");
        ITButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // frame.dispose();
//System.out.println(it);
                new ITDepartmentSummary(n, p, it);
            }
        });

        adminButton = new JButton("Admin Department");
        adminButton .addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement logic to view complaints
                frame.dispose();
                new AdminDepartmentSummary(n, p, admin);
                //JOptionPane.showMessageDialog(frame, "Viewing complaints feature coming soon!");
            }
        });

        accountsButton = new JButton("Accounts Department");
        accountsButton .addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement logic to view complaints
                frame.dispose();
                new AccountsDepartmentSummary(n, p, accounts);
                //JOptionPane.showMessageDialog(frame, "Viewing complaints feature coming soon!");
            }
        });
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginPage(); // Assuming LoginPage is the class for the login page
            }
        });
        
        
        // Add buttons to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(adminButton);
        buttonPanel.add(ITButton);
        buttonPanel.add(accountsButton);
        buttonPanel.add(logoutButton);
        

        // Add the panel with the buttons to the center panel
        centerPanel.add(buttonPanel);

        frame.add(centerPanel, BorderLayout.CENTER);
    }

    public static String[] getDeetsBasedOnUName(String targetName, String FileName) {
        try (BufferedReader bufreader = new BufferedReader(new FileReader(FileName))) {
            String line = bufreader.readLine();
            String[] chunk = null; // Declare chunk outside the loop

            while (line != null) {
                chunk = line.split(":");
                if (chunk[0].equals(targetName)) {
                    break;
                }
                
                line = bufreader.readLine(); // Move to the next line
                chunk = null; // Reset chunk if the name doesn't match
            }
    
            return chunk != null ? chunk : new String[0]; // Return empty array if no match is found
            } 
            catch (IOException ex) {
                ex.printStackTrace();
                return new String[0]; // Handle exception by returning an empty array
            }
    }

}