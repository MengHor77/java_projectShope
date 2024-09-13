package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;
import java.util.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import static java.lang.System.exit;
import java.util.ArrayList;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class StockHome extends JPanel {
private JPanel dataPanel; 
    private File uploadedImageFile;
    private Connection cn;

public StockHome() {
    ConnectionDatabase();  // Connect to the database
    createTable();         // Create the table if it doesn't exist

    // Set layout to null to use setBounds
    setLayout(null);

    // Create search components
    JTextField searchByIdField = new JTextField(10);
    JTextField searchByNameField = new JTextField(15);
    JButton searchButton = new JButton("Search");
    JButton refresh = new JButton("refresh");
    // Position and size the search components
    searchByIdField.setBounds(120, 10, 100, 30);
    searchByNameField.setBounds(380, 10, 150, 30);
    searchButton.setBounds(550, 10, 100, 30); // Adjusted x-coordinate to avoid overlapping

    // Add search components to the panel
    add(new JLabel("Search by ID:")).setBounds(20, 10, 100, 30);
    add(searchByIdField);
    add(new JLabel("Search by Name:")).setBounds(250, 10, 120, 30);
    add(searchByNameField);
    add(searchButton);

    // Create and position the data panel
    dataPanel = new JPanel(new BorderLayout());
    dataPanel.setBounds(20, 100, 1460, 500); // Adjusted size to fit within the frame
    add(dataPanel);

    // Add listener to search button
  searchButton.addActionListener(e -> searchProduct(searchByIdField.getText(), searchByNameField.getText()));

    // Load initial data
    showData();

    // Add product button
    JButton addbtn = new JButton("Add Product");
    addbtn.setBounds(20, 60, 120, 30); // Adjusted y-coordinate to be below the data panel
    add(addbtn);
    
    addbtn.addActionListener(e -> frameInputData());
    refresh.setBounds(160, 60, 120, 30);
   refresh.addActionListener(e -> showData());
   add(refresh);
}

     // Method to establish a connection to the MySQL database
    private void ConnectionDatabase() {
        try {
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the database.");
        }
    }
    
    // Method to create a table in the database
    private void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS home_stock ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "pro_name VARCHAR(255), "
                + "pro_price DOUBLE NOT NULL, "
                + "pro_qty INT NOT NULL, "
                + "img VARCHAR(255))"; // Store image path as VARCHAR
        try (PreparedStatement pstmt = cn.prepareStatement(createTableSQL)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating table.");
        }
    }

    // Method to insert a product into the database
   private void insertTable(String pro_name, double pro_price, int pro_qty, File uploadedImageFile) {
    // Check if any required field is missing
    if (pro_name == null || pro_name.trim().isEmpty() ||
        uploadedImageFile == null || !uploadedImageFile.exists()) {
        JOptionPane.showMessageDialog(this, "Please provide all required fields including the image.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String insertSQL = "INSERT INTO home_stock (pro_name, pro_price, pro_qty, img) VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = cn.prepareStatement(insertSQL)) {
        pstmt.setString(1, pro_name);
        pstmt.setDouble(2, pro_price);
        pstmt.setInt(3, pro_qty);
        pstmt.setString(4, uploadedImageFile.getAbsolutePath()); // Store the file path

        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Product inserted successfully.");
        
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error inserting product into the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void frameInputData() {
        JFrame frameInputData = new JFrame("Product Input Form");
        frameInputData.setSize(550, 500);
        frameInputData.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameInputData.setLayout(null);

        // Labels
        JLabel jlinput_name = new JLabel("INPUT PRODUCT NAME");
        JLabel jlinput_price = new JLabel("INPUT PRODUCT PRICE");
        JLabel jlinput_qty = new JLabel("INPUT PRODUCT QTY");
        JButton submitButton = new JButton("Submit");

        // Text Fields
        JTextField jt_name = new JTextField(20);
        JTextField jt_price = new JTextField(20);
        JTextField jt_qty = new JTextField(20);

        // Add components to the frame
        jlinput_name.setBounds(50, 50, 150, 25);
        jt_name.setBounds(200, 50, 150, 25);

        jlinput_price.setBounds(50, 100, 150, 25);
        jt_price.setBounds(200, 100, 150, 25);

        jlinput_qty.setBounds(50, 150, 150, 25);
        jt_qty.setBounds(200, 150, 150, 25);
        submitButton.setBounds(210, 410, 150, 25);

        // Image label to display the dragged or uploaded image
        JLabel imageLabel = new JLabel("Drag and Drop Image Here", SwingConstants.CENTER);
        imageLabel.setBounds(200, 200, 300, 200);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add Drag and Drop functionality
        imageLabel.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (!droppedFiles.isEmpty()) {
                        File file = droppedFiles.get(0);
                        uploadedImageFile = file;
                        if (uploadedImageFile != null) {
                            ImageIcon icon = new ImageIcon(new ImageIcon(uploadedImageFile.getAbsolutePath()).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH));
                            imageLabel.setIcon(icon);
                            imageLabel.setText(null);
                        } else {
                            JOptionPane.showMessageDialog(frameInputData, "Please upload a valid image file", "Invalid Image", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frameInputData, "Error uploading image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Button to open file chooser for selecting an image
        JButton buttonUploadImage = new JButton("Choose Image");
        buttonUploadImage.setBounds(50, 200, 140, 25);
        buttonUploadImage.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select an image");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(frameInputData);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                uploadedImageFile = selectedFile;
                try {
                    if (uploadedImageFile != null) {
                        ImageIcon icon = new ImageIcon(new ImageIcon(uploadedImageFile.getAbsolutePath()).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH));
                        imageLabel.setIcon(icon);
                        imageLabel.setText(null);
                    } else {
                        JOptionPane.showMessageDialog(frameInputData, "Invalid image format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frameInputData, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adding components to the frame
        frameInputData.add(jlinput_name);
        frameInputData.add(jt_name);
        frameInputData.add(jlinput_price);
        frameInputData.add(jt_price);
        frameInputData.add(jlinput_qty);
        frameInputData.add(jt_qty);
        frameInputData.add(submitButton);
        frameInputData.add(imageLabel);
        frameInputData.add(buttonUploadImage);

        // Action listener for submit button
       // Action listener for submit button
submitButton.addActionListener(e -> {
    // Get values from text fields
    String pro_name = jt_name.getText().trim();
    String priceText = jt_price.getText().trim();
    String qtyText = jt_qty.getText().trim();
    
    // Check if any required field is empty or image is not selected
    if (pro_name.isEmpty() || priceText.isEmpty() || qtyText.isEmpty() || uploadedImageFile == null || !uploadedImageFile.exists()) {
        JOptionPane.showMessageDialog(this, "Please provide all required fields including the image.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Parse price and quantity
    double pro_price;
    int pro_qty;
    try {
        pro_price = Double.parseDouble(priceText);
        pro_qty = Integer.parseInt(qtyText);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Invalid price or quantity format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Insert into the database
    insertTable(pro_name, pro_price, pro_qty, uploadedImageFile);
   // JOptionPane.showMessageDialog(this, "Product inserted successfully.");

    // Refresh data and close the input frame
    showData();
    frameInputData.dispose();
});

         frameInputData.setVisible(true);
        frameInputData.setLocationRelativeTo(null);
    }

      // Method to show data in a JPanel
private void showData() {
        String query = "SELECT id, pro_name, pro_price, pro_qty, img FROM home_stock";
        try (PreparedStatement pstmt = cn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Create column names
            String[] columnNames = {"ID", "Product Name", "Price", "Quantity", "Image", "Action"};

            // Create table model and set data
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                final int id = rs.getInt("id");
                final String proName = rs.getString("pro_name");
                final double proPrice = rs.getDouble("pro_price");
                final int proQty = rs.getInt("pro_qty");
                final String imgPath = rs.getString("img");

                // Ensure imgPath is not null
                final String finalImgPath = (imgPath != null) ? imgPath : "";

                // Create a JPanel with "Edit" and "Delete" buttons for each row
              JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center-aligned
                JButton editButton = new JButton("Edit");
                JButton deleteButton = new JButton("Delete");

                // Add buttons to buttonPanel
                buttonPanel.add(editButton);
                buttonPanel.add(deleteButton);

                // Add ActionListener for editButton and deleteButton
                editButton.addActionListener(e -> openEditFrame(id, proName, proPrice, proQty, finalImgPath));
                deleteButton.addActionListener(e -> deleteProduct(id));

                // Add the row to the model
                model.addRow(new Object[]{id, proName, proPrice, proQty, finalImgPath, buttonPanel});
            }

            // Create the table and set the custom renderer for the image column
            JTable table = new JTable(model) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 5; // Only the "Action" column is editable
                }
            };

            table.setRowHeight(110); // Adjust row height as needed
            table.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());

            // Add a custom renderer and editor for the "Action" column to display buttons
            table.getColumn("Action").setCellRenderer(new ButtonRenderer());
            table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), table));

            // Set column widths
            table.getColumnModel().getColumn(0).setPreferredWidth(50); // ID column
            table.getColumnModel().getColumn(1).setPreferredWidth(150); // Product Name column
            table.getColumnModel().getColumn(2).setPreferredWidth(100); // Price column
            table.getColumnModel().getColumn(3).setPreferredWidth(100); // Quantity column
            table.getColumnModel().getColumn(4).setPreferredWidth(200); // Image column
            table.getColumnModel().getColumn(5).setPreferredWidth(200); // Action column

            // Prevent column reordering and resizing
            table.getTableHeader().setReorderingAllowed(false);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setResizable(false);
            }

            // Create the scroll pane
            JScrollPane scrollPane = new JScrollPane(table);

            // Clear the panel and add the scroll pane
            dataPanel.removeAll();
            dataPanel.setLayout(new BorderLayout());
            dataPanel.add(scrollPane, BorderLayout.CENTER);

            // Refresh the panel
            dataPanel.revalidate();
            dataPanel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from the database.");
        }
    }

   private void searchProduct(String id, String name) {
    // Construct the query based on provided parameters
    String query = "SELECT id, pro_name, pro_price, pro_qty, img FROM home_stock WHERE 1=1";
    if (!name.trim().isEmpty()) {
        query += " AND pro_name LIKE ?";
    }
    if (!id.trim().isEmpty()) {
        query += " AND id = ?";
    }

    try (PreparedStatement pstmt = cn.prepareStatement(query)) {
        int index = 1;
        if (!name.trim().isEmpty()) {
            pstmt.setString(index++, "%" + name + "%");
        }
        if (!id.trim().isEmpty()) {
            pstmt.setInt(index, Integer.parseInt(id));
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            // Create column names
            String[] columnNames = {"ID", "Product Name", "Price", "Quantity", "Image", "Action"};

            // Create table model and set data
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                final int idResult = rs.getInt("id");
                final String proName = rs.getString("pro_name");
                final double proPrice = rs.getDouble("pro_price");
                final int proQty = rs.getInt("pro_qty");
                final String imgPath = rs.getString("img");

                // Ensure imgPath is not null
                final String finalImgPath = (imgPath != null) ? imgPath : "";

                // Create a JPanel with "Edit" and "Delete" buttons for each row
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JButton editButton = new JButton("Edit");
                JButton deleteButton = new JButton("Delete");
                buttonPanel.add(editButton);
                buttonPanel.add(deleteButton);

                // Add ActionListener for editButton and deleteButton
                editButton.addActionListener(e -> openEditFrame(idResult, proName, proPrice, proQty, finalImgPath));
                deleteButton.addActionListener(e -> deleteProduct(idResult));

                // Add the row to the model
                model.addRow(new Object[]{idResult, proName, proPrice, proQty, finalImgPath, buttonPanel});
            }

            // Create the table and set the custom renderer for the image column
            JTable table = new JTable(model) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 5; // Only the "Action" column is editable
                }
            };

            table.setRowHeight(110); // Adjust row height as needed
            table.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());

            // Add a custom renderer and editor for the "Action" column to display buttons
            table.getColumn("Action").setCellRenderer(new ButtonRenderer());
            table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), table));

            // Set column widths
            table.getColumnModel().getColumn(0).setPreferredWidth(50); // ID column
            table.getColumnModel().getColumn(1).setPreferredWidth(150); // Product Name column
            table.getColumnModel().getColumn(2).setPreferredWidth(100); // Price column
            table.getColumnModel().getColumn(3).setPreferredWidth(100); // Quantity column
            table.getColumnModel().getColumn(4).setPreferredWidth(200); // Image column
            table.getColumnModel().getColumn(5).setPreferredWidth(200); // Action column

            // Prevent column reordering and resizing
            table.getTableHeader().setReorderingAllowed(false);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setResizable(false);
            }

            // Create the scroll pane
            JScrollPane scrollPane = new JScrollPane(table);

            // Clear the panel and add the scroll pane
            dataPanel.removeAll();
            dataPanel.setLayout(new BorderLayout());
            dataPanel.add(scrollPane, BorderLayout.CENTER);

            // Refresh the panel
            dataPanel.revalidate();
            dataPanel.repaint();

        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error searching for products.");
    }
}

private void openEditFrame(int productId, String name, double price, int qty, String imgPath) {
    JFrame frameEditData = new JFrame("Edit Product");
    frameEditData.setSize(550, 500);
    frameEditData.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frameEditData.setLayout(null);

    // Labels
    JLabel jlinput_name = new JLabel("Edit Product Name");
    JLabel jlinput_price = new JLabel("Edit Product Price");
    JLabel jlinput_qty = new JLabel("Edit Product Quantity");
    JButton saveButton = new JButton("Save Changes");
    JButton changeImageButton = new JButton("Change Image");

    // Pre-fill text fields with existing values
    JTextField jt_name = new JTextField(name, 20);
    JTextField jt_price = new JTextField(String.valueOf(price), 20);
    JTextField jt_qty = new JTextField(String.valueOf(qty), 20);

    // Add components to the frame
    jlinput_name.setBounds(50, 50, 150, 25);
    jt_name.setBounds(200, 50, 150, 25);

    jlinput_price.setBounds(50, 100, 150, 25);
    jt_price.setBounds(200, 100, 150, 25);

    jlinput_qty.setBounds(50, 150, 150, 25);
    jt_qty.setBounds(200, 150, 150, 25);
    saveButton.setBounds(210, 410, 150, 25);
    changeImageButton.setBounds(50, 410, 150, 25);

    // Image label to display the current image
    JLabel imageLabel = new JLabel("Drag and Drop Image Here", SwingConstants.CENTER);
    imageLabel.setBounds(200, 200, 300, 200);
    imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // Load the existing image if the path is valid
    if (imgPath != null && !imgPath.isEmpty()) {
        ImageIcon icon = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH));
        imageLabel.setIcon(icon);
        imageLabel.setText(null);
    } else {
        imageLabel.setIcon(null);
        imageLabel.setText("No Image"); // Optional: indicate no image available
    }

    // Add Drag and Drop functionality or use the existing buttonUploadImage
    // (Same as you have in `frameInputData`)

    // Add components to the frame
    frameEditData.add(jlinput_name);
    frameEditData.add(jt_name);
    frameEditData.add(jlinput_price);
    frameEditData.add(jt_price);
    frameEditData.add(jlinput_qty);
    frameEditData.add(jt_qty);
    frameEditData.add(saveButton);
    frameEditData.add(imageLabel);
    frameEditData.add(changeImageButton);

    // Save button logic to update the database
    saveButton.addActionListener(e -> {
        String pro_name = jt_name.getText();
        double pro_price = Double.parseDouble(jt_price.getText());
        int pro_qty = Integer.parseInt(jt_qty.getText());

        // Call method to update the product
        updateProduct(productId, pro_name, pro_price, pro_qty, uploadedImageFile != null ? uploadedImageFile.getAbsolutePath() : imgPath);

        frameEditData.dispose(); // Dispose only after saving
    });

    // Change Image button logic to open file chooser
    changeImageButton.addActionListener(e -> {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(frameEditData);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon newImageIcon = new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath()).getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH));
            imageLabel.setIcon(newImageIcon);
            imageLabel.setText(null);
            uploadedImageFile = selectedFile; // Store the selected file for saving
        }
    });

    frameEditData.setVisible(true);
    frameEditData.setLocationRelativeTo(null);
}

private void updateProduct(int productId, String name, double price, int qty, String imgPath) {
    String updateSQL = "UPDATE home_stock SET pro_name = ?, pro_price = ?, pro_qty = ?, img = ? WHERE id = ?";

    try (PreparedStatement pstmt = cn.prepareStatement(updateSQL)) {
        pstmt.setString(1, name);
        pstmt.setDouble(2, price);
        pstmt.setInt(3, qty);
        pstmt.setString(4, imgPath != null ? imgPath : ""); // Ensure no null value
        pstmt.setInt(5, productId);
        pstmt.executeUpdate();

        JOptionPane.showMessageDialog(this, "Product updated successfully.");
        // Optionally refresh the data in the panel
         showData();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error updating product.");
    }
}

private void deleteProduct(int productId) {
    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        String deleteSQL = "DELETE FROM home_stock WHERE id = ?";
        try (PreparedStatement pstmt = cn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Product deleted successfully.");

            // Refresh the data in the panel
            showData(); 
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting product from the database.");
        }
    }
}

    // Custom cell renderer for images
   class ImageRenderer extends JLabel implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof String) {
            String imgPath = (String) value;

            if (imgPath != null && !imgPath.isEmpty()) {
                ImageIcon icon = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)); // Adjust size as needed
                setIcon(icon);
            } else {
                setIcon(null); // Handle the case where there is no image
                setText("No Image"); // Optional: display text if no image is available
            }
        } else {
            setIcon(null); // Handle unexpected value
            setText("No Image"); // Optional
        }
        return this;
    }
}

class ButtonRenderer extends JPanel implements TableCellRenderer {

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER)); // Align buttons in the center
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JPanel) {
            return (JPanel) value;
        }
        return this;
    }
}
class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton editButton;
    private JButton deleteButton;

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Align buttons in the center

        // Create "Edit" button
        editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            // Retrieve product details and open the edit form
            int productId = (int) table.getValueAt(row, 0); // Assuming ID is the first column
            String proName = (String) table.getValueAt(row, 1);
            double proPrice = (double) table.getValueAt(row, 2);
            int proQty = (int) table.getValueAt(row, 3);
            String imgPath = (String) table.getValueAt(row, 4);
            openEditFrame(productId, proName, proPrice, proQty, imgPath);
        });

        // Create "Delete" button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            int productId = (int) table.getValueAt(row, 0); // Assuming ID is the first column
            deleteProduct(productId);
        });

        // Add buttons to the panel
        panel.add(editButton);
        panel.add(deleteButton);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return panel;
    }
}


    public static void main(String[] args) {
        JFrame frame = new JFrame("Stock Home");
        StockHome stockHome = new StockHome();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1550, 900);
        frame.add(stockHome);
        frame.setVisible(true);

       
    }
}
