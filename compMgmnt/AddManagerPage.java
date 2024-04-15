
//import java.awt.*;
//import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import javax.swing.event.*;

public class AddManagerPage extends JPanel {
    private JComboBox dept; 
    JFrame frame;   
    private JTextField usernameField;
    private JTextField passField;
    private JTextField nameField;
    private JLabel uName;
    private JLabel passW;
    private JLabel managerName;
    private JLabel managerDept;
    private JToggleButton save;
    private JToggleButton back;
    


    public AddManagerPage(String n, String p) {
        createUI(n, p);
    }
    public void createUI(String n, String p){

        frame = new JFrame("Add Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] deptItems = {"IT", "Admin", "Accounts"};
        //construct components
        dept = new JComboBox (deptItems);
        usernameField = new JTextField (25);
        passField = new JTextField (15);
        nameField = new JTextField (30);
        uName = new JLabel ("Username:");
        passW = new JLabel ("Password:");
        managerName = new JLabel ("Name:");
        managerDept = new JLabel ("Department:");
        save = new JToggleButton ("Save", false);
        back = new JToggleButton ("Back", false);

        //adjust size and set layout
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLayout (null);


        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                    frame.dispose();
                    new AdministratorHomePage(n, p);
                   
            }
        });

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String deptName = (String) dept.getSelectedItem();
                Administrator a = new Administrator(n, p);
                boolean flag = a.addManager(usernameField.getText(), passField.getText(), nameField.getText(), deptName);
                if(flag == false)
                {
                    JOptionPane.showMessageDialog(frame, "This username already exists. Try something else. /There already exists a Manager for this department"); 
                }
                frame.dispose();
                new AdministratorHomePage(n, p);
            }
        });
        

        //add components
        add (dept);
        add (usernameField);
        add (passField);
        add (nameField);
        add (uName);
        add (passW);
        add (managerName);
        add (managerDept);
        add (save);
        add (back);


        //set component bounds (only needed by Absolute Positioning)
        dept.setBounds (280, 125, 155, 25);
        usernameField.setBounds (280, 175, 155, 25);
        passField.setBounds (280, 225, 155, 25);
        nameField.setBounds (280, 70, 155, 25);
        uName.setBounds (175, 175, 100, 25);
        passW.setBounds (175, 225, 100, 25);
        managerName.setBounds (175, 70, 100, 25);
        managerDept.setBounds (175, 125, 100, 25);
        save.setBounds (490, 285, 100, 25);
        back.setBounds (380, 285, 100, 25);
        // Add the panel to the frame's content pane
        frame.getContentPane().add(this);

        // Pack and make the frame visible
        frame.pack();
        frame.setVisible(true);
    }

}

