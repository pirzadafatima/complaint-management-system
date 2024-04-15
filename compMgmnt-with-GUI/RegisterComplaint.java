import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class RegisterComplaint {
    private JFrame frame;
    private JComboBox<String> deptName;
    private JTextField complaint;
    private JComboBox<String> complaintType;
    private JButton complainButton;
    private JButton backButton;

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

        String[] deptItems = {"IT", "Admin", "Accounts"};
        deptName = new JComboBox<>(deptItems);

        String[] complaintTypes = {"Problem", "Service/Equipment"};

        deptName.setPreferredSize(new Dimension(200, deptName.getPreferredSize().height));
        centerPanel.add(deptName, gbc);

        gbc.gridy++;
        centerPanel.add(new JLabel("Complaint Type:"), gbc);

        gbc.gridy++;
        complaintType = new JComboBox<>(complaintTypes);
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
                String[] teachDetails = getTeachDeetsBasedOnUName(n);
                String dept = (String) deptName.getSelectedItem();
                String type = (String) complaintType.getSelectedItem();
                System.out.println(teachDetails[0]);
                System.out.println(teachDetails[1]);
                System.out.println(teachDetails[2]);
                System.out.println(teachDetails[3]);
                System.out.println(teachDetails[4]);
                Teacher t = new Teacher(teachDetails[0], teachDetails[1], teachDetails[2], teachDetails[3], teachDetails[4]);
                System.out.println("I have not filed Complaint");
                t.fileComplaint(type, complaint.getText(), n, dept) ;
                System.out.println("I have filed Complaint");
                    JOptionPane.showMessageDialog(null, "Complaint registered successfully");
                    frame.dispose();
                    new TeacherHomePage(n, p);
                
            }
        });
        centerPanel.add(complainButton, gbc);

        gbc.gridy++;
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new TeacherHomePage(n, p);
            }
        });
        centerPanel.add(backButton, gbc);

        frame.add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterComplaint("Name", "Position"));
    }

    public String[] getTeachDeetsBasedOnUName(String targetName) {
        try (BufferedReader bufreader = new BufferedReader(new FileReader("Teachers.txt"))) {
            String line = bufreader.readLine();
            String[] chunk = null;

            while (line != null) {
                chunk = line.split(":");
                if (chunk[0].equals(targetName)) {
                    break;
                }

                line = bufreader.readLine();
                chunk = null;
            }

            return chunk != null ? chunk : new String[0];
        } catch (IOException ex) {
            ex.printStackTrace();
            return new String[0];
        }
    }
}
