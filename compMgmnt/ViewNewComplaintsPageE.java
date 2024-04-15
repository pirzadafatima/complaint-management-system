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

public class ViewNewComplaintsPageE {

    private static Complaint selectedComplaint;
    
    //public static void main(String[] args) {
      //  SwingUtilities.invokeLater(() -> createAndShowGUI());
    //}
    public ViewNewComplaintsPageE(String mN, String pa, List<Complaint> c)
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
        JButton solutionButton = new JButton("Provide Solution");
        solutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
{                // Handle assigning complaints here
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String solution = JOptionPane.showInputDialog(frame, "Enter the solution:");
                    if (solution != null) {
                        String[] empDetails = getDeetsBasedOnUName(mN,"Employees.txt");  //if we send filename as Parameter then the same function can be used instead of making 3 separate functions
                            DepartmentName dept = DepartmentName.valueOf(empDetails[3]);
                            Employee emp = new Employee(empDetails[0], empDetails[1], empDetails[2], DepartmentMngmtMain.getDept(dept));
                            boolean yes=  emp.setSolution(solution, c.get(selectedRow));
                            if(yes)
                            {
                                JOptionPane.showMessageDialog(frame, "The solution has been recorded.");
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(frame, "The solution to this complaint has already been provided by another employee.");
                            }
                        /*String[] mngrDetails = getDeetsBasedOnUName(mN, "Managers.txt");
                        DepartmentName dept = DepartmentName.valueOf(mngrDetails[3]);
                        Manager m = new Manager(mngrDetails[0], mngrDetails[1], mngrDetails[2], DepartmentMngmtMain.getDept(dept));
                        //m.assignComplaint(c.get(selectedRow), m);
                        selectedComplaint = c.get(selectedRow);
                        
                        List<Employee> allEmployees = DepartmentMngmtMain.getDept(dept).getEmployees();
                        System.out.println(selectedComplaint);
                        new DisplayDepartmentEmployees(mN, p, allEmployees, selectedComplaint);
*/
                        // Notify the table model that data has changed

                        tableModel.fireTableDataChanged();
                    //}
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a complaint to provide solution for.");
                }
                }
            }
        }
        });

        // Create a JButton for the back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                // Add code to navigate back to the previous page (e.g., Employee homepage or wherever needed)
            }
        });

        // Create a JPanel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(solutionButton);
        buttonPanel.add(backButton); // Add the backButton to the buttonPanel

// Add components to the JFrame
frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH); // Add buttonPanel to the content pane


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
