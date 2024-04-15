import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class RemoveEmployeePage extends JPanel {
    private JButton back;
    private JButton delete;
    private JTextField empName;
    private JLabel eUname;

    public RemoveEmployeePage(String n, String p) {

        JFrame frame = new JFrame ("Remove Employee");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().add (new RemoveEmployeePage(n, p));
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible (true);

        //construct components
        back = new JButton ("Back");
        delete = new JButton ("Delete");
        empName = new JTextField (20);
        eUname = new JLabel ("Enter Employee Username:");

        //adjust size and set layout
        setPreferredSize (new Dimension (624, 329));
        setLayout (null);

        //add components
        add (back);
        add (delete);
        add (empName);
        add (eUname);

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
                boolean tt = a.RemoveEmployeefromAllfiles(empName.getText());
                if(tt == false)
                {
                    JOptionPane.showMessageDialog(frame, "This username does not exist. Enter username again.");
                }
                JOptionPane.showMessageDialog(frame, "Employee " + empName.getText() + "has been deleted");

                frame.dispose();
                new AdministratorHomePage(n, p);
            }
        });
        //set component bounds (only needed by Absolute Positioning)
        back.setBounds (390, 295, 100, 20);
        delete.setBounds (500, 295, 100, 20);
        empName.setBounds (320, 85, 140, 25);
        eUname.setBounds (145, 85, 165, 25);
    }


}
