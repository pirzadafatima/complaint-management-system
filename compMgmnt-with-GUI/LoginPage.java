import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.util.List;


public class LoginPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private String[] s;

    public LoginPage() {
        createUI();
        frame.setVisible(true);
    }

    private void createUI() {
        frame = new JFrame("Login Page");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    s = checkCredentials();

                    if(s[2].contentEquals("Teacher"))
                    {
                        frame.dispose();
                        new TeacherHomePage(s[0], s[1]);

                    }
                    else if(s[2].contentEquals("Administrator"))
                    {
                        frame.dispose();
                        Administrator A = new Administrator(s[0], s[1]);
                        new AdministratorHomePage(s[0], s[1]);
                    }
                    else if(s[2].contentEquals("Manager"))
                    {
                        
                        String[] mngrDetails = getDeetsBasedOnUName(s[0], "Managers.txt");
                        DepartmentName dept = DepartmentName.valueOf(mngrDetails[3]);
                        Manager m = new Manager(mngrDetails[0], mngrDetails[1], mngrDetails[2], DepartmentMngmtMain.getDept(dept));
                        //System.out.println(mngrDetails[0]);
                        //System.out.println(mngrDetails[1]);
                        //System.out.println(mngrDetails[2]);
                        //System.out.println(mngrDetails[3]);
                        List<Complaint> c = m.getNewComplaintNotification();
                       // List<String> notifications = m.viewNotifications(l_Username);
                         //   int numOfNotif = notifications.size();
                        frame.dispose();
                        System.out.println("Reached Manager");

                        new ManagerHomePage(s[0], s[1], c);
                        System.out.println("Reached After Manager");
                    }
                    else if(s[2].contentEquals("Director"))
                    {

                        frame.dispose();
                        //Administrator A = new Administrator(s[0], s[1]);
                        System.out.println(s[0]);
                        System.out.println(s[1]);
                        new DirectorHomePage(s[0], s[1]);
                    }
                    else if(s[2].contentEquals("Employee"))
                    {
                        frame.dispose();
                        String[] empDetails = getDeetsBasedOnUName(s[0],"Employees.txt");  //if we send filename as Parameter then the same function can be used instead of making 3 separate functions
                        DepartmentName dept = DepartmentName.valueOf(empDetails[3]);
                        Employee emp = new Employee(empDetails[0], empDetails[1], empDetails[2], DepartmentMngmtMain.getDept(dept));
                            System.out.println("I have reached here");

                            List<Complaint> c = emp.loadAssignedComplaints();
                            System.out.println(c);
                            frame.dispose();
                            System.out.println("Reached Employee");
                            new EmployeeHomePage(s[0], s[1], c);
                            System.out.println("I have reached not here");

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
        centerPanel.add(loginButton);

        frame.add(centerPanel, BorderLayout.CENTER);
    }

    private String[] checkCredentials() throws IOException {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());        
        userAuthentication userAuthentication = new userAuthentication();

        String s = userAuthentication.logIn(username, password);
        String[] userDeets = new String[3];
        userDeets[0] = username; //name
        userDeets[1] = password; //username
        userDeets[2] = s;  //dept
        if (s != null)
        {
            JOptionPane.showMessageDialog(frame, "Login successful!");
            //System.out.println("Login successful. Redirecting to user interface...");
            //System.out.println("Designation: " + s);
            return userDeets;
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return userDeets;
        }
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
            } 
            catch (IOException ex) {
                ex.printStackTrace();
                return new String[0]; // Handle exception by returning an empty array
            }
    }


    
}

