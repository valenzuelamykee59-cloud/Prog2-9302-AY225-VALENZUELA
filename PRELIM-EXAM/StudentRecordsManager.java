// Programmer Identifier: (Mykee Valenzuela L.) (25-1837-237)


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class StudentRecordsManager extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtID, txtFirstName, txtLastName, txtSearch;
    private JButton btnAdd, btnDelete, btnSearch, btnClearSearch;
    
    public StudentRecordsManager() {
        // Set window title with your identifier
        setTitle("Student Records Manager - (Mykee Valenzuela L.) (25-1837-237)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        
        // Initialize components
        initializeComponents();
        
        // Load data from CSV
        loadDataFromCSV();
        
        setVisible(true);
    }
    
    private void initializeComponents() {
        // Create table model with column names
        String[] columnNames = {"StudentID", "First Name", "Last Name", "LAB WORK 1", 
                                "LAB WORK 2", "LAB WORK 3", "PRELIM EXAM", "ATTENDANCE GRADE"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Create search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search by Student ID"));
        
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search");
        btnClearSearch = new JButton("Clear Search");
        
        searchPanel.add(new JLabel("Student ID:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnClearSearch);
        
        // Add search action listeners
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchByStudentID();
            }
        });
        
        btnClearSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearSearch();
            }
        });
        
        // Add Enter key support for search
        txtSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchByStudentID();
            }
        });
        
        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Record"));
        
        // Create text fields
        txtID = new JTextField(15);
        txtFirstName = new JTextField(15);
        txtLastName = new JTextField(15);
        
        // Create labels and add to panel
        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(txtID);
        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(txtFirstName);
        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(txtLastName);
        
        // Create buttons with better styling
        btnAdd = new JButton("➕ Add Record");
        btnAdd.setBackground(new Color(40, 167, 69));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 12));
        
        btnDelete = new JButton("🗑️ Delete Selected");
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 12));
        
        inputPanel.add(btnAdd);
        inputPanel.add(btnDelete);
        
        // Add action listeners
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });
        
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });
        
        // Layout
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }
    
    private void loadDataFromCSV() {
        // Try multiple possible locations for the CSV file
        String[] possiblePaths = {
            "MOCK_DATA.csv",                          // Same directory as .class file
            "./MOCK_DATA.csv",                        // Current directory
            "../MOCK_DATA.csv",                       // Parent directory
            System.getProperty("user.dir") + "/MOCK_DATA.csv"  // Absolute path to current directory
        };
        
        String csvFile = null;
        File file = null;
        
        // Find the CSV file
        for (String path : possiblePaths) {
            file = new File(path);
            if (file.exists()) {
                csvFile = path;
                break;
            }
        }
        
        if (csvFile == null) {
            String currentDir = System.getProperty("user.dir");
            JOptionPane.showMessageDialog(this,
                "Error: MOCK_DATA.csv file not found!\n\n" +
                "Current working directory: " + currentDir + "\n\n" +
                "Please place MOCK_DATA.csv in one of these locations:\n" +
                "1. Same folder as StudentRecordsManager.class\n" +
                "2. Current directory: " + currentDir,
                "File Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String line;
        String csvSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header line
            br.readLine();
            
            // Read each line and add to table
            while ((line = br.readLine()) != null) {
                // Split the line by comma
                String[] data = line.split(csvSplitBy);
                
                // Add row to table model
                tableModel.addRow(data);
            }
            
            JOptionPane.showMessageDialog(this, 
                "Data loaded successfully! Total records: " + tableModel.getRowCount(),
                "Success", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                "Error: MOCK_DATA.csv file not found!\nPlease ensure the file is in the same directory as the program.",
                "File Not Found", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error reading the CSV file: " + e.getMessage(),
                "I/O Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Unexpected error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void addRecord() {
        String id = txtID.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        
        // Validate input
        if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields (ID, First Name, Last Name)",
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Add new row with empty grades (can be modified to accept grade inputs)
        Object[] newRow = {id, firstName, lastName, "0", "0", "0", "0", "0"};
        tableModel.addRow(newRow);
        
        // Clear text fields
        txtID.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        
        JOptionPane.showMessageDialog(this,
            "Record added successfully!",
            "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a row to delete",
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this record?",
            "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this,
                "Record deleted successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void searchByStudentID() {
        String searchID = txtSearch.getText().trim();
        
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a Student ID to search",
                "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Clear current table
        tableModel.setRowCount(0);
        
        // Find the CSV file
        String[] possiblePaths = {
            "MOCK_DATA.csv",
            "./MOCK_DATA.csv",
            "../MOCK_DATA.csv",
            System.getProperty("user.dir") + "/MOCK_DATA.csv"
        };
        
        String csvFile = null;
        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                csvFile = path;
                break;
            }
        }
        
        if (csvFile == null) {
            JOptionPane.showMessageDialog(this,
                "Error: MOCK_DATA.csv file not found!",
                "File Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String line;
        String csvSplitBy = ",";
        int foundCount = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header line
            br.readLine();
            
            // Read each line and check if Student ID matches
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                
                // Check if Student ID contains the search term
                if (data[0].contains(searchID)) {
                    tableModel.addRow(data);
                    foundCount++;
                }
            }
            
            if (foundCount == 0) {
                JOptionPane.showMessageDialog(this,
                    "No records found with Student ID containing: " + searchID,
                    "No Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Found " + foundCount + " record(s) matching: " + searchID,
                    "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
                
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error searching records: " + e.getMessage(),
                "Search Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void clearSearch() {
        txtSearch.setText("");
        tableModel.setRowCount(0);
        loadDataFromCSV();
    }
    
    public static void main(String[] args) {
        // Use Swing's event dispatch thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StudentRecordsManager();
            }
        });
    }
}
