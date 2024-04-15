//import java.awt.*;
//import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveManagerPage extends JPanel {
    private JTextField nameField;
    private JLabel employeeName;
    private JToggleButton delete;
    private JToggleButton back;
    JFrame frame;
    public RemoveManagerPage(String n, String p) {
        //construct components
        frame = new JFrame("Remove Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nameField = new JTextField (30);
        employeeName = new JLabel ("Enter Employee Username:");
        delete = new JToggleButton ("Delete", false);
        back = new JToggleButton ("Back", false);

        //adjust size and set layout
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLayout (null);
        frame.getContentPane().add(this);


        //add components
        add (nameField);
        add (employeeName);
        add (delete);
        add (back);

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

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Administrator a = new Administrator(n, p);
                boolean tt = a.RemoveManagerfromAllfiles(nameField.getText());
                if(tt == false)
                {
                    JOptionPane.showMessageDialog(frame, "This username does not exist. Enter username again.");
                }
                frame.dispose();
                new AdministratorHomePage(n, p);
            }
        });


        //set component bounds (only needed by Absolute Positioning)
        nameField.setBounds (305, 70, 155, 25);
        employeeName.setBounds (130, 70, 175, 25);
        delete.setBounds (490, 285, 100, 25);
        back.setBounds (380, 285, 100, 25);
        frame.pack();
        frame.setVisible (true);
    }


}
