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

class ViewNewComplaints extends AbstractTableModel {
    private List<Complaint> complaints;
    private String[] columnNames = {"ID", "Type", "Description", "Teacher Username", "Complaint Department", "State", "Solution"};

    public ViewNewComplaints(List<Complaint> complaints) {
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
                return complaint.gettype();
            case 2:
                return complaint.getdetails();
            case 3:
                return complaint.gettchUsername();
            case 4:
                return complaint.getdept();
            case 5:
                return complaint.getState().getClass().getSimpleName();
            case 6:
                return complaint.getSolution();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}

public class ViewNewComplaintsPageM {

    private static Complaint selectedComplaint;
    
    //public static void main(String[] args) {
      //  SwingUtilities.invokeLater(() -> createAndShowGUI());
    //}
    public ViewNewComplaintsPageM(String mN, String pa, List<Complaint> c)
    {
        createAndShowGUI(mN, pa, c);
    }

    private static void createAndShowGUI(String mN, String p, List<Complaint> c) {
        JFrame frame = new JFrame("Complaint Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom TableModel using the complaints list
        ViewNewComplaints tableModel = new ViewNewComplaints(c);

        // Create a JTable with the custom TableModel
        JTable table = new JTable(tableModel);

        // Create a JScrollPane to handle scrolling if the table is too large
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JButton to assign complaints
        JButton assignButton = new JButton("Assign Complaint");
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
              // Handle assigning complaints here
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    //String employeeName = JOptionPane.showInputDialog(frame, "Assign to employee:");
                    //if (employeeName != null) {
                        String[] mngrDetails = getDeetsBasedOnUName(mN, "Managers.txt");
                        DepartmentName dept = DepartmentName.valueOf(mngrDetails[3]);
                        Manager m = new Manager(mngrDetails[0], mngrDetails[1], mngrDetails[2], DepartmentMngmtMain.getDept(dept));
                        //m.assignComplaint(c.get(selectedRow), m);
                        selectedComplaint = c.get(selectedRow);
                        //c.get(selectedRow).setAssignedTo(employeeName);
                        List<Employee> allEmployees = DepartmentMngmtMain.getDept(dept).getEmployees();
                        System.out.println(selectedComplaint);
                        new DisplayDepartmentEmployees(mN, p, allEmployees, selectedComplaint);

                        // Notify the table model that data has changed

                        tableModel.fireTableDataChanged();
                    //}
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a complaint to assign.");
                }
            }
        }
        );

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                // Add code to navigate back to the previous page (e.g., Manager homepage or wherever needed)
                // For example, you might create a ManagerHomePage class and instantiate it here.
            }
        });

        
       // Add components to the JFrame
       frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
       JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
       buttonPanel.add(assignButton);
       buttonPanel.add(backButton);
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
