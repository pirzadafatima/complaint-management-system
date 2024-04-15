import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AdministratorHomePage extends JPanel {
    private JButton addTeacherButton;
    private JButton removeTeacherButton;
    private JButton removeManagerButton;
    private JButton addManagerButton;
    private JButton addEmployeeButton;
    private JButton removeEmployeeButton;
    private JButton addDirectorButton;
    private JButton removeDirectorButton;
    private JFrame frame;

    public AdministratorHomePage(String n, String p) {
        createUI(n, p);
        frame.setVisible(true);
    }

    public void createUI(String n, String p) {
        frame = new JFrame("Administrator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // Set a specific number of rows (4 in this case)
        
        // Construct menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");
        JMenuItem logOut = new JMenuItem("Log Out");
        fileMenu.add(logOut);
        
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        // Construct components
        addTeacherButton = new JButton("Add Teacher");
        removeTeacherButton = new JButton("Remove Teacher");
        addManagerButton = new JButton("Add Manager");
        removeManagerButton = new JButton("Remove Manager");
        addEmployeeButton = new JButton("Add Employee");
        removeEmployeeButton = new JButton("Remove Employee");
        addDirectorButton = new JButton("Add Director");
        removeDirectorButton = new JButton("Remove Director");
        // Add action listeners
        addTeacherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AddTeacherPage(n, p);
            }
        });

        addManagerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AddManagerPage(n, p);
            }
        });

        addEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AddEmployeePage(n, p);
            }
        });

        removeTeacherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RemoveTeacherPage(n, p);
            }
        });

        removeManagerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RemoveManagerPage(n, p);
            }
        });

        removeEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RemoveEmployeePage(n, p);
            }
        });

        addDirectorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.out.println("Reached Director");
                new AddDirectorPage(n, p);
            }
        });

        removeDirectorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new RemoveDirectorPage(n, p);
            }
        });

        logOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginPage(); // Assuming LoginPage is the class for the login page
            }
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        // Add components to the panel
        panel.add(addTeacherButton);
        panel.add(removeTeacherButton);
        panel.add(addManagerButton);
        panel.add(removeManagerButton);
        panel.add(addEmployeeButton);
        panel.add(removeEmployeeButton);
        panel.add(addDirectorButton);
        panel.add(removeDirectorButton);

        // Add panel to the content pane
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // Pack and set frame visibility
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}