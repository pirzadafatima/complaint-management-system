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
    private String[] columnNames = {"ID", "Description", "State"};

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

    public Object getValueAt(int rowIndex, int columnIndex) {
    String[] complaint = complaints.get(rowIndex);
    if (complaint != null && columnIndex < complaint.length) {
        switch (columnIndex) {
            case 0: return complaint[0]; // ID
            case 1: return complaint[1]; // Description
            case 2: return complaint[2]; // state
            default: return null;
        }
    }
    return null;
    }




    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // Helper method to parse complaints and convert them to a List of arrays
}


public class ViewAllDeptComplaintsPage {

    private static Complaint selectedComplaint;
    
    //public static void main(String[] args) {
      //  SwingUtilities.invokeLater(() -> createAndShowGUI());
    //}
    public ViewAllDeptComplaintsPage(String mN, String pa, List<String> c)
    {
        //System.out.println(c);
        createAndShowGUI(mN, pa, c);
    }

    private static void createAndShowGUI(String mN, String p, List<String> c) {
        JFrame frame = new JFrame("Complaint Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //System.out.println(c);

        // Create a custom TableModel using the complaints list
        ViewComplaint tableModel = new ViewComplaint(parseComplaints(c));
        //System.out.println(c);

        // Create a JTable with the custom TableModel
        JTable table = new JTable(tableModel);
                System.out.println(c);

        // Create a JScrollPane to handle scrolling if the table is too large
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JButton to assign complaints
        //JButton assignButton = new JButton("Assign Complaint");
       /*  assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
              // Handle assigning complaints here
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    //String employeeName = JOptionPane.showInputDialog(frame, "Assign to employee:");
                    //if (employeeName != null) {
                        
                        // Notify the table model that data has changed

                        tableModel.fireTableDataChanged();
                    //}
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a complaint to assign.");
                }
            }
        }
        );
        */


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

    private static List<String[]> parseComplaints(List<String> complaints) {
    List<String[]> parsedComplaints = new ArrayList<>();
    for (String complaint : complaints) {
        parsedComplaints.add(complaint.split(":"));
    }
    return parsedComplaints;
}
}