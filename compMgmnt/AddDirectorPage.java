
//import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import javax.swing.event.*;

public class AddDirectorPage extends JPanel {
   // private JComboBox dept; 
    JFrame frame;   
    private JTextField usernameField;
    private JTextField passField;
    private JTextField nameField;
    private JLabel uName;
    private JLabel passW;
    private JLabel directorName;
    private JToggleButton save;
    private JToggleButton back;

    public AddDirectorPage(String n, String p) {
        createUI(n, p);
    }
    public void createUI(String n, String p){

        frame = new JFrame("Add Employee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //String[] deptItems = {"IT", "Admin", "Accounts"};
        //construct components
        //dept = new JComboBox (deptItems);
        usernameField = new JTextField (25);
        passField = new JTextField (15);
        nameField = new JTextField (30);
        uName = new JLabel ("Username:");
        passW = new JLabel ("Password:");
        directorName = new JLabel ("Name:");
        save = new JToggleButton ("Save", false);
        back = new JToggleButton ("Back", false);

        //adjust size and set layout
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);         
        setLayout (null);

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
                //String deptName = (String) dept.getSelectedItem();
                Administrator a = new Administrator(n, p);
                boolean t = a.AddDirector(usernameField.getText(), passField.getText(), nameField.getText());
                if(t == false)
                {
                    JOptionPane.showMessageDialog(frame, "This username already exists/There is already a Director in the system. Try something else."); 
                }
                frame.dispose();
                new AdministratorHomePage(n, p);
            }
        });

        //add components
        //add (dept);
        add (usernameField);
        add (passField);
        add (nameField);
        add (uName);
        add (passW);
        add (directorName);
        //add (employeeDept);
        add (save);
        add (back);

        //set component bounds (only needed by Absolute Positioning)
        //dept.setBounds (280, 125, 155, 25);
        usernameField.setBounds (280, 175, 155, 25);
        passField.setBounds (280, 225, 155, 25);
        nameField.setBounds (280, 70, 155, 25);
        uName.setBounds (175, 175, 100, 25);
        passW.setBounds (175, 225, 100, 25);
        directorName.setBounds (175, 70, 100, 25);
        //employeeDept.setBounds (175, 125, 100, 25);
        save.setBounds (490, 285, 100, 25);
        back.setBounds (380, 285, 100, 25);
        // Add the panel to the frame's content pane
        frame.getContentPane().add(this);

        // Pack and make the frame visible
        frame.pack();
        frame.setVisible(true);
    }

}

