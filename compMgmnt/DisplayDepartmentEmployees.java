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

class ViewEmployees extends AbstractTableModel {
    public List<Employee> employees;
    private String[] columnNames = {"Employee Name"};
    
    public ViewEmployees(List<Employee> emp) {
        this.employees = emp;

    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return employee.getName();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}

public class DisplayDepartmentEmployees {

    private List<Employee> selectedEmployees;
    private List<Complaint> selectedComplaints;

    public DisplayDepartmentEmployees(String mN, String pa, List<Employee> employees, Complaint c) {
        this.selectedEmployees = employees;
        this.selectedComplaints = new ArrayList<>();
        selectedComplaints.add(c);
        createAndShowGUI(mN, pa);
    }


    private void createAndShowGUI(String mN, String p) {
        JFrame frame = new JFrame("Complaint Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] mngrDetails = getDeetsBasedOnUName(mN, "Managers.txt");
        DepartmentName dept = DepartmentName.valueOf(mngrDetails[3]);
        Manager m = new Manager(mngrDetails[0], mngrDetails[1], mngrDetails[2], DepartmentMngmtMain.getDept(dept));


        ViewEmployees tableModel = new ViewEmployees(selectedEmployees);
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);

        JButton assignButton = new JButton("Assign Complaint");
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle assigning complaints here
                selectedEmployees = getSelectedEmployees(table);

                // Assign the selected employees to the selected complaints using Manager's function
                System.out.println("I have reached Assigned complaintss");
                for (Complaint selectedComplaint : selectedComplaints) {
                    System.out.println(selectedComplaint);
                    m.assignComplaint(selectedComplaint, selectedEmployees);
                }
                JOptionPane.showMessageDialog(frame, "Complaint Assigned!");
                new ManagerHomePage(mN, p, selectedComplaints);
                // Notify the table model that data has changed
                tableModel.fireTableDataChanged();
            }

        });

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(assignButton, BorderLayout.SOUTH);

        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private List<Employee> getSelectedEmployees(JTable table) {
        List<Employee> selectedEmployees = new ArrayList<>();
        int[] selectedRows = table.getSelectedRows();

        for (int selectedRow : selectedRows) {
            selectedEmployees.add(((ViewEmployees) table.getModel()).employees.get(selectedRow));
        }

        return selectedEmployees;
    }

    public String[] getDeetsBasedOnUName(String targetName, String FileName) {
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
        } catch (IOException ex) {
            ex.printStackTrace();
            return new String[0]; // Handle exception by returning an empty array
        }
    }
}