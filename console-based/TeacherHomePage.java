import java.awt.*;
import java.awt.event.*;
//import java.io.IOException;
import javax.swing.*;

public class TeacherHomePage {
    private JFrame frame;
    //private JTextField usernameField;
    //private JPasswordField passwordField;
    private JButton complainButton;


    public TeacherHomePage(String n, String p) {
        createUI(n, p);
        frame.setVisible(true);
    }

    private void createUI(String n, String p) {
        frame = new JFrame("Teacher");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //JLabel usernameLabel = new JLabel("Username:");
        //usernameField = new JTextField(15);

        //JLabel passwordLabel = new JLabel("Password:");
        //passwordField = new JPasswordField(15);

        complainButton = new JButton("Register Complaint");
        complainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //try {
                    frame.dispose();
                   new RegisterComplaint(n, p);
               //} catch (IOException ex) {
                 //ex.printStackTrace();
            // }
            }
        });

         // Add button to a panel
         JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
         buttonPanel.add(complainButton);
 
         // Add the panel with the button to the center panel
         centerPanel.add(buttonPanel);
       // centerPanel.add(usernameLabel);
        //centerPanel.add(usernameField);
        //centerPanel.add(passwordLabel);
        //centerPanel.add(passwordField);
        //centerPanel.add(complainButton);

        frame.add(centerPanel, BorderLayout.CENTER);
    }

}


