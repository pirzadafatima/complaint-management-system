import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherHomePage {
    private JFrame frame;
    private JButton complainButton;
    private JButton reviewComplaintsButton;
    private JButton viewComplaintsButton;
    private JButton logoutButton;

    public TeacherHomePage(String n, String p) {
        createUI(n, p);
        frame.setVisible(true);
    }

    private void createUI(String n, String p) {
        frame = new JFrame("Teacher");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        String[] teachDetails = getDeetsBasedOnUName(n, "Teachers.txt");
        Teacher t = new Teacher(teachDetails[0],teachDetails[1], teachDetails[2], teachDetails[3], teachDetails[4]);
        t.LoadFiledComplaints();  //as teacher has logged in, load their filed complaints in system

        List<String> notifications = t.viewNotifications(n);
        int numOfNotif = notifications.size();
        String notify = "You have "+ numOfNotif + " notifications.";
        JOptionPane.showMessageDialog(frame, notify, "New Notifications", JOptionPane.INFORMATION_MESSAGE);
                

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        complainButton = new JButton("Register Complaint");
        complainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RegisterComplaint(n, p);
            }
        });

        viewComplaintsButton = new JButton("View Complaints");
        viewComplaintsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement logic to view complaints
                String[] teachDetails = getDeetsBasedOnUName(n, "Teachers.txt");
                Teacher t = new Teacher(teachDetails[0], teachDetails[1], teachDetails[2], teachDetails[3], teachDetails[4]);
                System.out.println(teachDetails[0]);
                System.out.println(teachDetails[1]);
                System.out.println(teachDetails[2]);
                System.out.println(teachDetails[3]);
                System.out.println(teachDetails[4]);
                t.LoadFiledComplaints();
                List<Complaint> c = t.getFiledComplaints(); 
                System.out.println(c);
                new ViewNewComplaintsPageT(n, p, c);
                //JOptionPane.showMessageDialog(frame, "Viewing complaints feature coming soon!");
            }
        });


        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    frame.dispose();
                new LoginPage(); // Assuming LoginPage is the class for the login page
            }
    }});
        
        reviewComplaintsButton = new JButton("View Notifications");
        reviewComplaintsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] teachDetails = getDeetsBasedOnUName(n, "Teachers.txt");
                Teacher t = new Teacher(teachDetails[0],teachDetails[1], teachDetails[2], teachDetails[3], teachDetails[4]);
                t.LoadFiledComplaints();
                new ReviewNewComplaintsPageT(n,p,notifications);
        }});
        // Add buttons to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(complainButton);
        buttonPanel.add(viewComplaintsButton);
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