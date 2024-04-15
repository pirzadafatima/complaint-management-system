import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class ManagerHomePage {
    private JFrame frame;
    private JButton complainButton;
    //private JButton assignComplaintButton;
    private JButton viewNewComplaintButton;
    private JButton logoutButton;
    private JButton reviewComplaintsButton;

    public ManagerHomePage(String n, String p, List<Complaint> cL) {
        createUI(n, p, cL);
        frame.setVisible(true);
    }

    private void createUI(String n, String p,List<Complaint> cL) {
        frame = new JFrame("Manager");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        complainButton = new JButton("View Status of All Complaints");
        //assignComplaintButton = new JButton("Assign Complaints");
        viewNewComplaintButton = new JButton("View New Complaints");
        reviewComplaintsButton = new JButton("View Notifications");
        logoutButton = new JButton("Logout");



        String[] mngrDetails = getDeetsBasedOnUName(n, "Managers.txt");
                DepartmentName dept = DepartmentName.valueOf(mngrDetails[3]);
                Manager m = new Manager(mngrDetails[0], mngrDetails[1], mngrDetails[2], DepartmentMngmtMain.getDept(dept));
                //System.out.println(mngrDetails[0]);
                //System.out.println(mngrDetails[1]);
                //System.out.println(mngrDetails[2]);

                List<String> notifications = m.viewNotifications(n);
                int numOfNotif = notifications.size();
                System.out.println(notifications);
                String notify = "You have "+ numOfNotif + " notifications.";
                JOptionPane.showMessageDialog(frame, notify, "New Notifications", JOptionPane.INFORMATION_MESSAGE);

        complainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] mngrDetails = getDeetsBasedOnUName(n, "Managers.txt");
                DepartmentName dept = DepartmentName.valueOf(mngrDetails[3]);
                Manager m = new Manager(mngrDetails[0], mngrDetails[1], mngrDetails[2], DepartmentMngmtMain.getDept(dept));
                List<String> deptComplaints = m.viewAllFiledComplaints();
              //  System.out.println(deptComplaints);
                //frame.dispose();
                new ViewAllDeptComplaintsPage(n, p, deptComplaints);
            }
        });

        viewNewComplaintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //frame.dispose();
                new ViewNewComplaintsPageM(n, p, cL);
            }
        });

        
        reviewComplaintsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ReviewComplaints(n,p,notifications);
        }});

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
        buttonPanel.add(complainButton);
       // buttonPanel.add(assignComplaintButton);
        buttonPanel.add(viewNewComplaintButton);
        buttonPanel.add(reviewComplaintsButton);
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
