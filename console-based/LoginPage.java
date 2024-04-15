import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

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
            System.out.println("Login successful. Redirecting to user interface...");
            System.out.println("Designation: " + s);
            return userDeets;
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return userDeets;
        }
    }
}

