import model.Expense;
import model.ExpenseManager;
import storage.JsonStorage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ExpenseManagerGUI extends JFrame {
    private final ExpenseManager manager = new ExpenseManager();
    private final JsonStorage storage = new JsonStorage();

    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Amount", "Category", "Date", "Comment"}, 0);
    private final JTable table = new JTable(tableModel);

    private final JTextField amountField = new JTextField();
    private final JComboBox<String> categoryBox = new JComboBox<>(new String[]{"FOOD", "TRANSPORT", "ENTERTAINMENT", "UTILITIES", "MEDICINE", "OTHER"});
    private final JTextField dateField = new JTextField(LocalDate.now().toString());
    private final JTextField commentField = new JTextField();

    public ExpenseManagerGUI() {
        setTitle("Expense Manager");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Expense"));

        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(new JLabel("Comment:"));
        inputPanel.add(new JLabel(""));

        inputPanel.add(amountField);
        inputPanel.add(categoryBox);
        inputPanel.add(dateField);
        inputPanel.add(commentField);

        JButton addButton = new JButton("Add");
        inputPanel.add(addButton);
        add(inputPanel, BorderLayout.NORTH);

        // Center panel
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        JButton deleteButton = new JButton("Delete Selected");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        bottomPanel.add(deleteButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action listeners
        addButton.addActionListener(e -> addExpense());
        deleteButton.addActionListener(e -> deleteSelected());
        saveButton.addActionListener(e -> saveToFile());
        loadButton.addActionListener(e -> loadFromFile());

        loadFromFile(); // Load on start
        setVisible(true);
    }

    private void addExpense() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String category = (String) categoryBox.getSelectedItem();
            LocalDate date = LocalDate.parse(dateField.getText());
            String comment = commentField.getText();

            Expense expense = new Expense(category, amount, date, comment);
            manager.addExpense(expense);
            tableModel.addRow(new Object[]{amount, category, date.toString(), comment});
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row != -1) {
            tableModel.removeRow(row);
            manager.setExpenses(getAllFromTable());
        }
    }

    private void saveToFile() {
        storage.saveExpenses(manager.getAllExpenses());
        JOptionPane.showMessageDialog(this, "Saved to expenses.json");
    }

    private void loadFromFile() {
        List<Expense> loaded = storage.loadExpenses();
        manager.setExpenses(loaded);
        tableModel.setRowCount(0);
        for (Expense e : loaded) {
            tableModel.addRow(new Object[]{e.getAmount(), e.getCategory(), e.getDate(), e.getComment()});
        }
    }

    private List<Expense> getAllFromTable() {
        manager.setExpenses(List.of()); // очистим
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            double amount = Double.parseDouble(tableModel.getValueAt(i, 0).toString());
            String category = tableModel.getValueAt(i, 1).toString();
            LocalDate date = LocalDate.parse(tableModel.getValueAt(i, 2).toString());
            String comment = tableModel.getValueAt(i, 3).toString();
            manager.addExpense(new Expense(category, amount, date, comment));
        }
        return manager.getAllExpenses();
    }

    private void clearFields() {
        amountField.setText("");
        commentField.setText("");
        dateField.setText(LocalDate.now().toString());
        categoryBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpenseManagerGUI::new);
    }
}
