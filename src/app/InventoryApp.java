package app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InventoryApp extends JFrame {
    private ArrayList<ProductModel> products;
    private ProductTableModel tableModel;
    private JTable table;

    public InventoryApp() {
        products = new ArrayList<>();

        tableModel = new ProductTableModel(products);
        table = new JTable(tableModel);

        setTitle("Inventory App");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel tombol
        JPanel panel = new JPanel();
        JButton addBtn = new JButton("Add Product");
        JButton delBtn = new JButton("Delete Product");

        //Add product
        addBtn.addActionListener(e -> {
            JTextField codeField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField qtyField = new JTextField();
            JTextField priceField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            inputPanel.add(new JLabel("Code:"));
            inputPanel.add(codeField);
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Qty:"));
            inputPanel.add(qtyField);
            inputPanel.add(new JLabel("Price:"));
            inputPanel.add(priceField);

            int result = JOptionPane.showConfirmDialog(
                    this, inputPanel, "Add Product",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String code = codeField.getText().trim();
                    String name = nameField.getText().trim();
                    int qty = Integer.parseInt(qtyField.getText().trim());
                    double price = Double.parseDouble(priceField.getText().trim());

                    tableModel.addProduct(new ProductModel(code, name, qty, price));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Qty and Price must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //Delete 
        delBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.removeProduct(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Select a product to delete!");
            }
        });

        panel.add(addBtn);
        panel.add(delBtn);

        add(panel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryApp().setVisible(true));
    }
}
