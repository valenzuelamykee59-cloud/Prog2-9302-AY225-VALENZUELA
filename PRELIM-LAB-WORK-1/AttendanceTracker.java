import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * AttendanceTracker - A Java Swing application for tracking student attendance
 * This program creates a GUI window with labeled fields for attendance information
 * and automatically captures system date/time and generates e-signatures.
 * 
 * @author [Your Name]
 * @version 1.0
 * @date January 2026
 */
public class AttendanceTracker extends JFrame {

    // Declare text fields for user input and display
    private JTextField nameField;
    private JTextField courseField;
    private JTextField timeInField;
    private JTextField signatureField;
    
    // DateTimeFormatter for proper formatting of date and time
    private DateTimeFormatter formatter;

    /**
     * Constructor - Initializes the GUI window and all components
     * Sets up the layout, labels, text fields, and buttons
     */
    public AttendanceTracker() {
        // Set window title
        setTitle("Attendance Tracker");
        
        // Set window size (width=400, height=300)
        setSize(400, 300);
        
        // Close program when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Center window on screen
        setLocationRelativeTo(null);
        
        // Initialize date/time formatter for readable output
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Create main panel with GridLayout (5 rows, 2 columns, spacing)
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create labels for each field
        JLabel nameLabel = new JLabel("Attendance Name:");
        JLabel courseLabel = new JLabel("Course / Year:");
        JLabel timeLabel = new JLabel("Time In:");
        JLabel signatureLabel = new JLabel("E-Signature:");

        // Initialize text fields
        nameField = new JTextField();
        courseField = new JTextField();
        
        // Get current system date and time, format it properly
        LocalDateTime currentTime = LocalDateTime.now();
        timeInField = new JTextField(currentTime.format(formatter));
        
        // Generate unique e-signature using UUID
        signatureField = new JTextField(UUID.randomUUID().toString());

        // Make system-generated fields non-editable
        timeInField.setEditable(false);
        signatureField.setEditable(false);
        
        // Set background color for non-editable fields to indicate they're automatic
        timeInField.setBackground(new Color(240, 240, 240));
        signatureField.setBackground(new Color(240, 240, 240));

        // Create Save button with action listener
        JButton saveButton = new JButton("Save Attendance");
        saveButton.addActionListener(e -> saveAttendance());
        
        // Add all components to panel in order
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(courseLabel);
        panel.add(courseField);
        panel.add(timeLabel);
        panel.add(timeInField);
        panel.add(signatureLabel);
        panel.add(signatureField);

        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);
        
        // Create button panel at bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Saves attendance data to a text file
     * Writes all field values to attendance.txt in append mode
     * Shows success or error message to user
     */
    private void saveAttendance() {
        // Validate that required fields are filled
        if (nameField.getText().trim().isEmpty() || courseField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all required fields.", 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Try to write to file, handle any IO exceptions
        try (FileWriter writer = new FileWriter("attendance.txt", true)) {
            // Write formatted attendance record
            writer.write("=================================\n");
            writer.write("ATTENDANCE RECORD\n");
            writer.write("=================================\n");
            writer.write("Name: " + nameField.getText() + "\n");
            writer.write("Course/Year: " + courseField.getText() + "\n");
            writer.write("Time In: " + timeInField.getText() + "\n");
            writer.write("E-Signature: " + signatureField.getText() + "\n");
            writer.write("=================================\n\n");

            // Show success message
            JOptionPane.showMessageDialog(this, 
                "Attendance saved successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
                
            // Clear input fields for next entry
            nameField.setText("");
            courseField.setText("");
            
            // Update time and signature for next entry
            LocalDateTime currentTime = LocalDateTime.now();
            timeInField.setText(currentTime.format(formatter));
            signatureField.setText(UUID.randomUUID().toString());
            
        } catch (IOException ex) {
            // Show error message if file write fails
            JOptionPane.showMessageDialog(this, 
                "Error saving attendance: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method - Entry point of the program
     * Creates and displays the AttendanceTracker window
     */
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater for thread safety
        SwingUtilities.invokeLater(() -> {
            AttendanceTracker tracker = new AttendanceTracker();
            tracker.setVisible(true);
        });
    }
}
