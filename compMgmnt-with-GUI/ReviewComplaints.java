import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ViewComplaint extends AbstractTableModel {
    private List<String[]> complaints;
    private String[] columnNames = {"ID", "Description", "Solution"};

    public ViewComplaint(List<String[]> complaints) {
        this.complaints = complaints;
    }

    @Override
    public int getRowCount() {
        return complaints.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
public Object getValueAt(int rowIndex, int columnIndex) {
    String[] complaint = complaints.get(rowIndex);
    if (complaint != null && columnIndex < complaint.length) {
        switch (columnIndex) {
            case 0: return complaint[0]; // ID
            case 1: return complaint[1]; // Type
            case 2: return complaint[2]; // Description // Solution
            default: return null;
        }
    }
    return null;
}

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}

public class ReviewComplaints {

    //private static Complaint selectedComplaint;
    
    //public static void main(String[] args) {
      //  SwingUtilities.invokeLater(() -> createAndShowGUI());
    //}
    public ReviewComplaints(String mN, String pa, List<String> c)
    {
        createAndShowGUI(mN, pa, c);
    }

    private static void createAndShowGUI(String mN, String p, List<String> c) {
        JFrame frame = new JFrame("Complaint Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// Debug print statements
//System.out.println("Complaints List: " + c);


// Create a custom TableModel using the complaints list
ViewComplaint tableModel = new ViewComplaint(parseComplaints(c));


        // Create a custom TableModel using the complaints list
        //ViewComplaints tableModel = new ViewComplaints(c);

        // Create a JTable with the custom TableModel
        JTable table = new JTable(tableModel);

        // Create a JScrollPane to handle scrolling if the table is too large
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JButton to review complaints
        JButton reviewButton = new JButton("Give Feedback");
        reviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int choice = showFeedbackDialog(frame);
                    if (choice == JOptionPane.YES_OPTION) {
                        // User is satisfied with the solution
                        // Handle feedback logic here
                        String[] mngrDetails = getDeetsBasedOnUName(mN, "Managers.txt");
                        DepartmentName dept = DepartmentName.valueOf(mngrDetails[3]);
                        Manager m = new Manager(mngrDetails[0], mngrDetails[1], mngrDetails[2], DepartmentMngmtMain.getDept(dept));
                        int complaintID = Integer.parseInt(c.get(selectedRow).split(":")[0]);
                        boolean satisfied = Boolean.parseBoolean("true");

                        m.reviewComplaintSolution(complaintID, satisfied);
                        System.out.println("Satisfied with the solution");
                    } else if (choice == JOptionPane.NO_OPTION) {
                        String[] mngrDetails = getDeetsBasedOnUName(mN, "Managers.txt");
                        DepartmentName dept = DepartmentName.valueOf(mngrDetails[3]);
                        Manager m = new Manager(mngrDetails[0], mngrDetails[1], mngrDetails[2], DepartmentMngmtMain.getDept(dept));
                        int complaintID = Integer.parseInt(c.get(selectedRow).split(":")[0]);
                        boolean satisfied = Boolean.parseBoolean("false");

                        m.reviewComplaintSolution(complaintID, satisfied);
                        System.out.println("Not satisfied with the solution");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a complaint to review.");
                }
            }
        });
        


        // Create a JButton for the back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                // Add code to navigate back to the previous page (e.g., Manager homepage or wherever needed)
            }
        });

        // Create a JPanel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //buttonPanel.add(assignButton);
        buttonPanel.add(backButton);
        buttonPanel.add(reviewButton);
        // Add components to the JFrame
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        // Set frame properties
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }

    private static int showFeedbackDialog(JFrame parentFrame) {
        Object[] options = {"Yes", "No"};
        return JOptionPane.showOptionDialog(parentFrame,
                "Are you satisfied with the solution?",
                "Feedback",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
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
    private static List<String[]> parseComplaints(List<String> complaints) {
        List<String[]> parsedComplaints = new ArrayList<>();
        for (String complaint : complaints) {
            parsedComplaints.add(complaint.split(":"));
        }
        return parsedComplaints;
    }

}
