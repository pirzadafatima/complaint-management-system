import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;

public class Main extends JPanel {
    private JToggleButton login;
    private JMenuBar jcomp2;
    private JLabel welcome;

    public Main() {
        //construct preComponents
        JMenu fileMenu = new JMenu ("File");
        JMenuItem printItem = new JMenuItem ("Print");
        fileMenu.add (printItem);
        JMenuItem exitItem = new JMenuItem ("Exit");
        fileMenu.add (exitItem);
        JMenu helpMenu = new JMenu ("Help");
        JMenuItem contentsItem = new JMenuItem ("Contents");
        helpMenu.add (contentsItem);
        JMenuItem aboutItem = new JMenuItem ("About");
        helpMenu.add (aboutItem);

        //construct components
        login = new JToggleButton ("Login", false);
        jcomp2 = new JMenuBar();
        jcomp2.add (fileMenu);
        jcomp2.add (helpMenu);
        welcome = new JLabel ("Welcome to FAST-NU Complaint Management System.");

        //adjust size and set layout
        setPreferredSize (new Dimension (624, 329));
        setLayout (null);

        //add components
        add (login);
        add (jcomp2);
        add (welcome);
        DepartmentMngmtMain.getDept(DepartmentName.IT).setEmployees();
        DepartmentMngmtMain.getDept(DepartmentName.IT).sManager();
        DepartmentMngmtMain.getDept(DepartmentName.IT).setComplaints();

        DepartmentMngmtMain.getDept(DepartmentName.Admin).setEmployees();
        DepartmentMngmtMain.getDept(DepartmentName.Admin).sManager();
        DepartmentMngmtMain.getDept(DepartmentName.Admin).setComplaints();

        DepartmentMngmtMain.getDept(DepartmentName.Accounts).setEmployees();
        DepartmentMngmtMain.getDept(DepartmentName.Accounts).sManager();
        DepartmentMngmtMain.getDept(DepartmentName.Accounts).setComplaints();
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            new LoginPage();
            }});

        //set component bounds (only needed by Absolute Positioning)
        login.setBounds (240, 125, 150, 40);
        jcomp2.setBounds (0, 0, 625, 25);
        welcome.setBounds (150, 45, 325, 60);
    }


    public static void main (String[] args) {
        JFrame frame = new JFrame ("Complaint Management System");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new Main());
        frame.pack();
        frame.setVisible (true);
    }
}

            
                   
                    