import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
//import java.text.ParseException;
import javax.swing.*;

public class DirectorHomePage {
    private JFrame frame;
    private JButton viewSelectedComplaints;
    private JButton logoutButton;
    //private JButton viewComplaintSummary;


    public DirectorHomePage(String n, String p) {
        createUI(n, p);
        frame.setVisible(true);
    }

    private void createUI(String n, String p) {
        frame = new JFrame("Director");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        viewSelectedComplaints = new JButton("View Selected Complaints");
        String[] teachDetails = getDeetsBasedOnUName(n,"Director.txt");
        Director d = new Director(teachDetails[0], teachDetails[1], teachDetails[2]);



        viewSelectedComplaints.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String startDateStr = JOptionPane.showInputDialog(frame, "Enter the start Date (YYYY-MM-DD):");
                String endDateStr = JOptionPane.showInputDialog(frame, "Enter the end Date (YYYY-MM-DD):");
        
                //try {
                    Date sD = d.parseDate(startDateStr);
                    Date eD = d.parseDate(endDateStr);
        
                    List<Complaint> filteredComplaints = d.viewComplaintSummary(sD, eD);
                    List<Complaint> itComplaints = new ArrayList<>();
                    List<Complaint> adminComplaints = new ArrayList<>();
                    List<Complaint> accountsComplaints = new ArrayList<>();
                    for (Complaint complaint : filteredComplaints) 
                    {
                        switch (complaint.getdept()) 
                        {
                            case "IT":
                                itComplaints.add(complaint);
                                break;
                            case "Admin":
                                adminComplaints.add(complaint);
                                break;
                            case "Accounts":
                                accountsComplaints.add(complaint);
                                break;
                // Add more cases for other departments if needed
                        }
                        String detailsMessage = "There are no complaint in between the entered dates.";
                        if (itComplaints.size() == 0 && adminComplaints.size() == 0 && accountsComplaints.size() == 0) {
                            detailsMessage = "There are no complaints between the entered dates.";
                        } else {
                            detailsMessage = String.format("IT Department Complaints: %d\nAdmin Department Complaints: %d\n" +
                                    "Accounts Department Complaints: %d", itComplaints.size(), adminComplaints.size(), accountsComplaints.size());
            
                            frame.dispose();
                            new DeptWiseComplaints(n, p, itComplaints, adminComplaints, accountsComplaints);
                        }
                        JOptionPane.showMessageDialog(frame, detailsMessage, "Complaint Details", JOptionPane.INFORMATION_MESSAGE);
                        
                    }
                    // Further processing or displaying of complaints can be done here
                //} catch (ParseException ex) {
                 //   JOptionPane.showMessageDialog(frame, "Invalid date format. Please enter dates in YYYY-MM-DD format.");
               // }
            }
        });

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    // Perform logout actions here, e.g., show login screen or exit the application
                    // ...
                }
            }
        });
        // Add buttons to a panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(viewSelectedComplaints);
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
