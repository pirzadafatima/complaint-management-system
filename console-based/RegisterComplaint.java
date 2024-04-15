import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RegisterComplaint {
    private JFrame frame;
    private JTextField deptName;
    private JTextField complaint;
    private JTextField complaintType;
    private JButton complainButton;

    public RegisterComplaint(String n, String p) {
        createUI(n, p);
        frame.setVisible(true);
    }

    private void createUI(String n, String p) {
        frame = new JFrame("Complaint Registration");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(new JLabel("Please enter Department Name: "), gbc);

        gbc.gridy++;
        deptName = new JTextField(25);
        deptName.setPreferredSize(new Dimension(200, deptName.getPreferredSize().height));
        centerPanel.add(deptName, gbc);

        gbc.gridy++;
        centerPanel.add(new JLabel("Complaint Type:"), gbc);

        gbc.gridy++;
        complaintType = new JTextField(25);
        complaintType.setPreferredSize(new Dimension(200, complaintType.getPreferredSize().height));
        centerPanel.add(complaintType, gbc);

        gbc.gridy++;
        centerPanel.add(new JLabel("Complaint:"), gbc);

        gbc.gridy++;
        complaint = new JTextField(25);
        complaint.setPreferredSize(new Dimension(200, complaint.getPreferredSize().height));
        centerPanel.add(complaint, gbc);

        gbc.gridy++;
        complainButton = new JButton("Register Complaint");
        complainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Your code here
                // Teacher teacher = new Teacher(n, p, 1, "Teacher Name"); // Adjust parameters accordingly
                // teacher.registerComplaint(complaintType.getText(), complaint.getText(), n, deptName.getText());
                //new Complaint(complaintType.getText(), complaint.getText(), n, deptName.getText());
                
                //GET teacher object here and then call fileComplaint using that 
                JOptionPane.showMessageDialog(null, "Complaint registered successfully");
                frame.dispose();
                new TeacherHomePage(n, p);
            }
        });
        centerPanel.add(complainButton, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterComplaint("Name", "Position"));
    }
}
