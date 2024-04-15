import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

class ViewComplaints extends AbstractTableModel {
    private List<Complaint> complaints;
    private String[] columnNames = {"ID", "Status"};

    public ViewComplaints(List<Complaint> complaints) {
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
        Complaint complaint = complaints.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return complaint.getID();
            case 1:
                return complaint.getState();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}

public class ITDepartmentSummary {
    public ITDepartmentSummary(String n, String p, List<Complaint> c)
    {
        createAndShowGUI(n, p, c);
    }

    private static void createAndShowGUI(String n, String p, List<Complaint> c) {
        JFrame frame = new JFrame("Complaint Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom TableModel using the complaints list
        ViewComplaints tableModel = new ViewComplaints(c);
        System.out.println(c);
        // Create a JTable with the custom TableModel
        JTable table = new JTable(tableModel);

        // Create a JScrollPane to handle scrolling if the table is too large
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JButton for the back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
               // new DeptWiseComplaints(n, p, c, null, null);
                // Add code to navigate back to the previous page (e.g., Manager homepage or wherever needed)
            }
        });

        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the selected complaint
                    Complaint selectedComplaint = c.get(selectedRow);
                    int cid1 = selectedComplaint.getID();
                    String[] directorDetails = getDeetsBasedOnUName(n, "Director.txt");
                    Director d = new Director(directorDetails[0], directorDetails[1], directorDetails[2]);

                    String[] dates = d.getNewAssignedDates(cid1);
                    Complaint c2 = ComplaintMgmnt.loadComplaintFromSystem(selectedComplaint.getID());
                    DepartmentName dept = DepartmentName.valueOf(c2.getdept());
                    // Display details in a pop-up
                    StringBuilder detailsMessage = new StringBuilder();
            detailsMessage.append("Description\nType: ").append(c2.gettype()).append("\nStatus: ")
                .append(c2.getState().getClass().getSimpleName()).append("\n")
                .append("Teacher: ").append(Administrator.findTeacherByUsername(c2.gettchUsername()).getname()).append("\n")
                .append("Manager: ").append(DepartmentMngmtMain.getDept(dept).getManagerofDept().getName()).append("\n")
                .append("Complaint Date: ").append(dates[0]).append("\n")
                .append("Assigned Date: ").append(dates[1]).append("\n");

            if (dates[1] != null) {
                List<String> assignedEmployees = d.getAssignedEmployees(cid1);

                if (!assignedEmployees.isEmpty()) {
                    detailsMessage.append("Assigned Employees:\n");
                    for (String employeeUsername : assignedEmployees) {
                        Employee employee = Administrator.findEmployeeByUsername(employeeUsername);
                        detailsMessage.append(employee.getName()).append("\n");
                    }
                } else {
                    detailsMessage.append("No employees assigned for this complaint.\n");
                }
            }
                    JOptionPane.showMessageDialog(frame, detailsMessage, "Complaint Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a complaint to view details for.");
                }
            }
        });
        // Create a JPanel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        //buttonPanel.add(assignButton);
        buttonPanel.add(backButton);
        buttonPanel.add(viewDetailsButton);

        // Add components to the JFrame
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        // Set frame properties
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    
       
    
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
