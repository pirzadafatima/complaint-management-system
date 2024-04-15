import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateInputExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Date Input Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout());

        // Create a formatted text field for date input
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##/##/####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JFormattedTextField formattedTextField = new JFormattedTextField(maskFormatter);

        // Add formatted text field to the panel
        panel.add(new JLabel("Enter Date (MM/DD/YYYY):"));
        panel.add(formattedTextField);

        JButton submitButton = new JButton("Submit");
        panel.add(submitButton);

        submitButton.addActionListener(e -> {
            // Get the text from the formatted text field
            String dateString = formattedTextField.getText();

            // Parse the date string to a Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date selectedDate = dateFormat.parse(dateString);

                // Now you can use the selectedDate to perform other actions
                System.out.println("Selected Date: " + selectedDate);
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(frame, "Invalid date format. Please enter the date in MM/DD/YYYY format.");
            }
        });

        frame.getContentPane().add(panel);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
