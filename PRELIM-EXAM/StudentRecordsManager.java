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
        setTitle("Student Records Manager - (Mykee Valenzuela L.) (25-1837-237)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        initializeComponents();
        loadDataFromCSV();

        setVisible(true);
    }

    private void initializeComponents() {

        String[] columnNames = {
            "StudentID", "First Name", "Last Name",
            "LAB WORK 1", "LAB WORK 2", "LAB WORK 3",
            "PRELIM EXAM", "ATTENDANCE GRADE"
        };

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== SEARCH PANEL =====
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search by Student ID"));

        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search");
        btnClearSearch = new JButton("Clear Search");

        searchPanel.add(new JLabel("Student ID:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnClearSearch);

        btnSearch.addActionListener(e -> searchByStudentID());
        btnClearSearch.addActionListener(e -> clearSearch());
        txtSearch.addActionListener(e -> searchByStudentID());

        // ===== INPUT PANEL =====
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Record"));

        txtID = new JTextField(15);
        txtFirstName = new JTextField(15);
        txtLastName = new JTextField(15);

        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(txtID);
        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(txtFirstName);
        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(txtLastName);

        btnAdd = new JButton("Add Record");
        btnDelete = new JButton("Delete Selected");

        inputPanel.add(btnAdd);
        inputPanel.add(btnDelete);

        btnAdd.addActionListener(e -> addRecord());
        btnDelete.addActionListener(e -> deleteRecord());

        // ===== LAYOUT =====
        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void loadDataFromCSV() {
        String[] paths = {
            "MOCK_DATA.csv",
            "./MOCK_DATA.csv",
            "../MOCK_DATA.csv",
            System.getProperty("user.dir") + "/MOCK_DATA.csv"
        };

        File csvFile = null;
        for (String path : paths) {
            File f = new File(path);
            if (f.exists()) {
                csvFile = f;
                break;
            }
        }

        if (csvFile == null) {
            JOptionPane.showMessageDialog(
                this,
                "MOCK_DATA.csv not found.\nCurrent directory:\n" + System.getProperty("user.dir"),
                "File Not Found",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            tableModel.setRowCount(0);
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                tableModel.addRow(line.split(","));
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error reading CSV file:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void addRecord() {
        String id = txtID.getText().trim();
        String first = txtFirstName.getText().trim();
        String last = txtLastName.getText().trim();

        if (id.isEmpty() || first.isEmpty() || last.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please fill in all fields.",
                "Input Error",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        tableModel.addRow(new Object[]{id, first, last, "0", "0", "0", "0", "0"});

        txtID.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");

        JOptionPane.showMessageDialog(
            this,
            "Record added successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void deleteRecord() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a record to delete.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this record?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
            JOptionPane.showMessageDialog(
                this,
                "Record deleted successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void searchByStudentID() {
        String searchID = txtSearch.getText().trim();

        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Enter a Student ID to search.",
                "Input Required",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        tableModel.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("MOCK_DATA.csv"))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].contains(searchID)) {
                    tableModel.addRow(data);
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Search error:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearSearch() {
        txtSearch.setText("");
        loadDataFromCSV();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentRecordsManager::new);
    }
}