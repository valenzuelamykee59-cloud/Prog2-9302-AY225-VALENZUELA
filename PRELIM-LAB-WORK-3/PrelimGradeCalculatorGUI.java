import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrelimGradeCalculatorGUI extends JFrame {
    
    private JTextField studentNameField;
    private JTextField lab1Field, lab2Field, lab3Field;
    private JTextField classStandingField;
    private JTextField prelimExamField;
    private JTextArea resultArea;
    
    public PrelimGradeCalculatorGUI() {
        setTitle("Prelim Grade Calculator");
        setSize(650, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Title
        JLabel titleLabel = new JLabel("PRELIM GRADE CALCULATOR");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Input section
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Button section
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Result section
        JLabel resultLabel = new JLabel("Results:");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBackground(Color.WHITE);
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(resultLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(scrollPane);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        
        int row = 0;
        
        // Student Name
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        studentNameField = new JTextField(25);
        panel.add(studentNameField, gbc);
        
        row++;
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        
        // Lab Work grades section
        row++;
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel labLabel = new JLabel("Lab Work Grades:");
        labLabel.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(labLabel, gbc);
        
        row++;
        addInputField(panel, "Lab Work 1:", lab1Field = new JTextField(15), row++);
        addInputField(panel, "Lab Work 2:", lab2Field = new JTextField(15), row++);
        addInputField(panel, "Lab Work 3:", lab3Field = new JTextField(15), row++);
        
        // Spacing
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        row++;
        
        // Class Standing
        addInputField(panel, "Class Standing:", classStandingField = new JTextField(15), row++);
        
        // Prelim Exam
        addInputField(panel, "Prelim Exam:", prelimExamField = new JTextField(15), row++);
        
        return panel;
    }
    
    private void addInputField(JPanel panel, String labelText, JTextField field, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.gridy = row;
        
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(new Color(245, 245, 245));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton calculateButton = new JButton("Calculate Grade");
        calculateButton.setPreferredSize(new Dimension(150, 40));
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setBackground(new Color(52, 152, 219));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        calculateButton.setBorderPainted(false);
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        calculateButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                calculateButton.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(MouseEvent e) {
                calculateButton.setBackground(new Color(52, 152, 219));
            }
        });
        
        calculateButton.addActionListener(e -> calculateGrade());
        
        JButton clearButton = new JButton("Clear All");
        clearButton.setPreferredSize(new Dimension(150, 40));
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(149, 165, 166));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorderPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        clearButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                clearButton.setBackground(new Color(127, 140, 141));
            }
            public void mouseExited(MouseEvent e) {
                clearButton.setBackground(new Color(149, 165, 166));
            }
        });
        
        clearButton.addActionListener(e -> clearFields());
        
        panel.add(calculateButton);
        panel.add(clearButton);
        
        return panel;
    }
    
    private void calculateGrade() {
        try {
            // Get input values
            String studentName = studentNameField.getText().trim();
            if (studentName.isEmpty()) {
                showError("Please enter student name.");
                return;
            }
            
            double lab1 = parseDouble(lab1Field.getText(), "Lab Work 1");
            double lab2 = parseDouble(lab2Field.getText(), "Lab Work 2");
            double lab3 = parseDouble(lab3Field.getText(), "Lab Work 3");
            double classStanding = parseDouble(classStandingField.getText(), "Class Standing");
            double prelimExam = parseDouble(prelimExamField.getText(), "Prelim Exam");
            
            // Validate grades are within reasonable range (0-100)
            if (!isValidGrade(lab1) || !isValidGrade(lab2) || !isValidGrade(lab3) || 
                !isValidGrade(classStanding) || !isValidGrade(prelimExam)) {
                showError("All grades must be between 0 and 100.");
                return;
            }
            
            // Calculate Lab Work Average
            double labWorkAverage = (lab1 + lab2 + lab3) / 3;
            
            // Calculate Prelim Grade
            double prelimGrade = (0.60 * labWorkAverage) + (0.10 * classStanding) + (0.30 * prelimExam);
            
            // Determine Letter Grade and Remarks
            String letterGrade;
            String remarks;
            
            if (prelimGrade >= 97.5) {
                letterGrade = "1.0";
                remarks = "Excellent";
            } else if (prelimGrade >= 94.5) {
                letterGrade = "1.25";
                remarks = "Excellent";
            } else if (prelimGrade >= 91.5) {
                letterGrade = "1.5";
                remarks = "Very Good";
            } else if (prelimGrade >= 88.5) {
                letterGrade = "1.75";
                remarks = "Very Good";
            } else if (prelimGrade >= 85.5) {
                letterGrade = "2.0";
                remarks = "Good";
            } else if (prelimGrade >= 82.5) {
                letterGrade = "2.25";
                remarks = "Good";
            } else if (prelimGrade >= 79.5) {
                letterGrade = "2.5";
                remarks = "Satisfactory";
            } else if (prelimGrade >= 76.5) {
                letterGrade = "2.75";
                remarks = "Satisfactory";
            } else if (prelimGrade >= 74.5) {
                letterGrade = "3.0";
                remarks = "Passed";
            } else if (prelimGrade >= 70.0) {
                letterGrade = "4.0";
                remarks = "Conditional";
            } else {
                letterGrade = "5.0";
                remarks = "Failed";
            }
            
            // Display Results
            StringBuilder result = new StringBuilder();
            result.append("========================================\n");
            result.append("       PRELIM GRADE REPORT\n");
            result.append("========================================\n");
            result.append("Student Name: ").append(studentName).append("\n");
            result.append("----------------------------------------\n");
            result.append(String.format("Lab Work 1:        %.2f\n", lab1));
            result.append(String.format("Lab Work 2:        %.2f\n", lab2));
            result.append(String.format("Lab Work 3:        %.2f\n", lab3));
            result.append(String.format("Lab Work Average:  %.2f\n", labWorkAverage));
            result.append("----------------------------------------\n");
            result.append(String.format("Class Standing:    %.2f\n", classStanding));
            result.append(String.format("Prelim Exam:       %.2f\n", prelimExam));
            result.append("----------------------------------------\n");
            result.append(String.format("PRELIM GRADE:      %.2f\n", prelimGrade));
            result.append("Letter Grade:      ").append(letterGrade).append("\n");
            result.append("Remarks:           ").append(remarks).append("\n");
            result.append("========================================\n");
            
            resultArea.setText(result.toString());
            
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values for all grade fields.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }
    
    private double parseDouble(String text, String fieldName) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a value for " + fieldName);
        }
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for " + fieldName);
        }
    }
    
    private boolean isValidGrade(double grade) {
        return grade >= 0 && grade <= 100;
    }
    
    private void clearFields() {
        studentNameField.setText("");
        lab1Field.setText("");
        lab2Field.setText("");
        lab3Field.setText("");
        classStandingField.setText("");
        prelimExamField.setText("");
        resultArea.setText("");
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void main(String[] args) {
        // Use system look and feel for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and show the GUI
        SwingUtilities.invokeLater(() -> {
            PrelimGradeCalculatorGUI gui = new PrelimGradeCalculatorGUI();
            gui.setVisible(true);
        });
    }
}