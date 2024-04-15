import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTeacherPage extends JPanel {
    private JTextField deptField;
    private JTextField cNameField;
    private JTextField usernameField;
    private JTextField passField;
    private JTextField nameField;
    private JLabel uName;
    private JLabel passW;
    private JLabel TeachName;
    private JLabel TeachDept;
    private JLabel cName;
    private JToggleButton save;
    private JToggleButton back;
    JFrame frame;

    public AddTeacherPage(String n, String p) {
        teachercreateUI(n, p);
    }

    public void teachercreateUI(String n, String p) {
        frame = new JFrame("Add Teacher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Construct components
        deptField = new JTextField(5);
        cNameField = new JTextField(15);
        usernameField = new JTextField(25);
        passField = new JTextField(15);
        nameField = new JTextField(30);
        uName = new JLabel("Username:");
        passW = new JLabel("Password:");
        TeachName = new JLabel("Name:");
        TeachDept = new JLabel("Department:");
        cName = new JLabel("Course Name:");
        save = new JToggleButton("Save", false);
        back = new JToggleButton ("Back", false);

        // Adjust size and set layout
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLayout(null);

        // Add components
        add(deptField);
        add(cNameField);
        add(usernameField);
        add(passField);
        add(nameField);
        add(uName);
        add(passW);
        add(TeachName);
        add(TeachDept);
        add(cName);
        add(save);
        add(back);

        // Set component bounds (only needed by Absolute Positioning)
        deptField.setBounds(280, 110, 155, 25);
        cNameField.setBounds(280, 150, 155, 25);
        usernameField.setBounds(280, 190, 155, 25);
        passField.setBounds(280, 230, 155, 25);
        nameField.setBounds(280, 70, 155, 25);
        uName.setBounds(175, 190, 100, 25);
        passW.setBounds(175, 230, 100, 25);
        TeachName.setBounds(175, 70, 100, 25);
        TeachDept.setBounds(175, 110, 100, 25);
        cName.setBounds(175, 150, 100, 25);
        save.setBounds(490, 285, 100, 25);
        back.setBounds(490, 285, 100, 25);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //try {
                    frame.dispose();
                    new AdministratorHomePage(n, p);
                    
                //} catch (IOException ex) {
                 // ex.printStackTrace();
                //}
            }
        });

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Administrator a = new Administrator(n, p);
                boolean t = a.AddTeacher(usernameField.getText(), passField.getText(), nameField.getText(), deptField.getText(), cNameField.getText());
                if(t == false)
                {
                    JOptionPane.showMessageDialog(frame, "This username already exists. Try something else."); 
                }
                frame.dispose();
                new AdministratorHomePage(n, p);
            }
        });

        // Add the panel to the frame's content pane
        frame.getContentPane().add(this);

        // Pack and make the frame visible
        frame.pack();
        frame.setVisible(true);
    }

    
}
