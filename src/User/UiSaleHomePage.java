package User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.SwingUtilities;
import jv.Product;

public class UiSaleHomePage extends JPanel {

    private JFrame cartFrame;
    private JPanel main;
    private Connection cn;
    private List<Product> cartItems;

    public UiSaleHomePage() {
        createTable();
        cartItems = new ArrayList<>();

        main = new JPanel();
        main.setPreferredSize(new Dimension(1500, 1800));
        main.setLayout(new GridLayout(0, 4, 10, 10));
        add(main);

        List<Product> products = fetchProducts();
        displayProducts(products);
    }

    private void mouseCursorPointer(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private void addToCart(Product newItem) {
        for (Product item : cartItems) {
            if (item.getId() == newItem.getId()) {
                item.setQty(item.getQty() + 1);
                showCart();
                return;
            }
        }

        newItem.setQty(1);
        cartItems.add(newItem);

        if (cartFrame != null) {
            cartFrame.dispose();
        }
        showCart();
    }

    private void showCart() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cart is empty.");
            if (cartFrame != null) {
                cartFrame.dispose();
            }
            return;
        }

        if (cartFrame != null) {
            cartFrame.dispose();
        }

        cartFrame = new JFrame("Cart");
        cartFrame.setSize(new Dimension(600, 400));
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new GridLayout(cartItems.size() + 2, 1, 10, 10));

        double totalPayment = 0.00;

        for (Product item : cartItems) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(null);
            itemPanel.setPreferredSize(new Dimension(550, 120));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            ImageIcon resizedIcon = new ImageIcon(resizeImage(item.getImage().getImage(), 80, 80));
            JLabel imageLabel = new JLabel(resizedIcon);
            imageLabel.setBounds(10, 10, 80, 80);
            itemPanel.add(imageLabel);

            JLabel idLabel = new JLabel("ID: " + item.getId());
            idLabel.setBounds(100, 10, 150, 20);
            itemPanel.add(idLabel);

            JLabel nameLabel = new JLabel("Name: " + item.getName());
            nameLabel.setBounds(100, 30, 200, 20);
            itemPanel.add(nameLabel);

            JLabel priceLabel = new JLabel("Price: $" + item.getPrice());
            priceLabel.setBounds(100, 50, 200, 20);
            itemPanel.add(priceLabel);

            SpinnerModel model = new SpinnerNumberModel(item.getQty(), 1, 1000, 1);
            JSpinner qtySpinner = new JSpinner(model);
            qtySpinner.setBounds(100, 80, 50, 20);
            itemPanel.add(qtySpinner);

            JButton updateButton = new JButton("Update");
            mouseCursorPointer(updateButton);
            updateButton.setBackground(Color.cyan);
            updateButton.setBounds(160, 80, 80, 20);
            updateButton.addActionListener(e -> {
                int newQty = (int) qtySpinner.getValue();
                item.setQty(newQty);
                showCart();
            });
            itemPanel.add(updateButton);

            JButton removeButton = new JButton("X");
            mouseCursorPointer(removeButton);
            removeButton.setBackground(Color.cyan);
            removeButton.setBounds(250, 80, 50, 20);
            removeButton.addActionListener(e -> {
                cartItems.remove(item);
                showCart();
            });
            itemPanel.add(removeButton);

            cartPanel.add(itemPanel);

            totalPayment += item.getQty() * item.getPrice();
        }

        JLabel totalLabel = new JLabel("Total Payment: $" + String.format("%.2f", totalPayment));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 1, 10, 10));

        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(null);
        totalPanel.setPreferredSize(new Dimension(550, 80));
        totalLabel.setBounds(0, 0, 550, 40);
        totalPanel.add(totalLabel);

        JButton clickToPayButton = new JButton("Click to Pay");
        mouseCursorPointer(clickToPayButton);
        clickToPayButton.setBackground(Color.cyan);
        clickToPayButton.setPreferredSize(new Dimension(100, 30));
        clickToPayButton.setBounds(10, 50, 150, 30);
        clickToPayButton.addActionListener(e -> {
            showListProductReciept();
            cartFrame.dispose();
        });
        totalPanel.add(clickToPayButton);

        cartPanel.add(totalPanel);

        JScrollPane scrollPane = new JScrollPane(cartPanel);
        cartFrame.add(scrollPane);
        cartFrame.setVisible(true);
    }

    private void showQRCode() {
        String imagePath = "src/Images/QR_code/QR_code.png";
        ImageIcon qrCodeImage = new ImageIcon(imagePath);

        JFrame qrFrame = new JFrame("Scan QR Code to Pay");
        qrFrame.setSize(400, 450);
        qrFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        qrFrame.setLayout(new BorderLayout());

        Image image = qrCodeImage.getImage();
        Image scaledImage = image.getScaledInstance(qrFrame.getWidth(), qrFrame.getHeight() - 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        double totalPayment = 0.0;
        for (Product item : cartItems) {
            totalPayment += item.getQty() * item.getPrice();
        }

        JLabel totalLabel = new JLabel("Total Payment: $" + String.format("%.2f", totalPayment), SwingConstants.CENTER);
        JLabel imageLabel = new JLabel(scaledIcon, SwingConstants.CENTER);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBackground(Color.cyan);
        mouseCursorPointer(confirmButton);

        confirmButton.addActionListener(e -> {
            qrFrame.dispose();
            JOptionPane.showMessageDialog(null, "Payment Successful! Quantity updated.");
            cartItems.clear();
        });

        qrFrame.add(totalLabel, BorderLayout.NORTH);
        qrFrame.add(imageLabel, BorderLayout.CENTER);
        qrFrame.add(confirmButton, BorderLayout.SOUTH);

        qrFrame.setVisible(true);
    }

    private void showListProductReciept() {
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Cart is empty.");
            return;
        }

        JFrame receiptFrame = new JFrame("Receipt");
        receiptFrame.setSize(new Dimension(500, 400));
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new GridLayout(cartItems.size() + 4, 1, 10, 10));

        double totalPayment = 0.00;

        JPanel headerPanel = new JPanel();
        JLabel title_Label = new JLabel("Product Details");
        title_Label.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(title_Label);
        receiptPanel.add(headerPanel);

        for (Product item : cartItems) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(null);
            itemPanel.setPreferredSize(new Dimension(450, 100));
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            JLabel idLabel = new JLabel("ID: " + item.getId());
            idLabel.setBounds(10, 10, 150, 20);
            itemPanel.add(idLabel);

            JLabel nameLabel = new JLabel("Name: " + item.getName());
            nameLabel.setBounds(10, 30, 200, 20);
            itemPanel.add(nameLabel);

            JLabel priceLabel = new JLabel("Price: $" + item.getPrice());
            priceLabel.setBounds(10, 50, 200, 20);
            itemPanel.add(priceLabel);

            JLabel qtyLabel = new JLabel("Qty: " + item.getQty());
            qtyLabel.setBounds(250, 30, 100, 20);
            itemPanel.add(qtyLabel);

            receiptPanel.add(itemPanel);

            totalPayment += item.getQty() * item.getPrice();
        }

        JLabel totalLabel = new JLabel("Total Payment: $" + String.format("%.2f", totalPayment), SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        receiptPanel.add(totalLabel);

        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel paymentOptionsPanel = new JPanel();
        paymentOptionsPanel.setLayout(new GridLayout(1, 2));
        JRadioButton cashRadioButton = new JRadioButton("Cash");
        JRadioButton qrRadioButton = new JRadioButton("QR Code");
        buttonGroup.add(cashRadioButton);
        buttonGroup.add(qrRadioButton);
        paymentOptionsPanel.add(cashRadioButton);
        paymentOptionsPanel.add(qrRadioButton);

        JButton confirmButton = new JButton("Confirm Payment");
        confirmButton.setBackground(Color.cyan);
        mouseCursorPointer(confirmButton);

      confirmButton.addActionListener(e -> {
    if (cashRadioButton.isSelected() || qrRadioButton.isSelected()) {
        // Process each item in the cart
        for (Product item : cartItems) {
            double total = item.getQty() * item.getPrice();
            insertProductSale(item.getName(), item.getPrice(), item.getQty(), total);
        }

        if (cashRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Cash payment successful!");
        } else {
            showQRCode();
            JOptionPane.showMessageDialog(null, "QR Code payment successful!");
        }
        
        updateStockQuantities();
        cartItems.clear();
        receiptFrame.dispose();
    } else {
        JOptionPane.showMessageDialog(null, "Please select a payment method.");
    }
});

        receiptPanel.add(paymentOptionsPanel);
        receiptPanel.add(confirmButton);

        JScrollPane scrollPane = new JScrollPane(receiptPanel);
        receiptFrame.add(scrollPane);
        receiptFrame.setVisible(true);
    }
    
    private void insertProductSale(String productName, double productPrice, int productQuantity, double total) {
    int userId = SessionManager.getCurrentUserId(); // Retrieve the current user's ID

    String insertSQL = "INSERT INTO product_sale (user_id, pro_name, pro_price, pro_quantity, total) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement ps = cn.prepareStatement(insertSQL)) {
        ps.setInt(1, userId);
        ps.setString(2, productName);
        ps.setDouble(3, productPrice);
        ps.setInt(4, productQuantity);
        ps.setDouble(5, total);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Product sale recorded successfully.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error inserting product sale: " + e.getMessage());
    }
}

    // Method to update stock quantities

    private void updateStockQuantities() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            if (cn == null || cn.isClosed()) {
                conDatabase(); // Ensure the connection is established
            }

            // Assuming cartItems is a List of Product objects, each with an id and quantity
            for (Product product : cartItems) {
                String updateSQL = "UPDATE home_stock SET pro_qty = pro_qty - ? WHERE pro_name = ?";
                pstmt = cn.prepareStatement(updateSQL);
                pstmt.setInt(1, product.getQty()); // Quantity to deduct
                pstmt.setString(2, product.getName()); // Product name
                pstmt.executeUpdate();
            }

            // Notify the user of successful stock update
            //JOptionPane.showMessageDialog(null, "Stock quantities updated successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error updating stock quantities: " + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close(); // Close the PreparedStatement to release resources
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error closing PreparedStatement: " + ex.getMessage());
                }
            }
        }
    }

    private List<Product> fetchProducts() {
        List<Product> products = new ArrayList<>();

        String dbURL = "jdbc:mysql://localhost:3306/stock";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            String query = "SELECT id, pro_name, pro_price, img FROM home_stock";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("pro_name");
                double price = resultSet.getDouble("pro_price");
                String imagePath = resultSet.getString("img"); // The image path stored as VARCHAR

                ImageIcon productImage = null;

                // Check if the image path is valid and load the image
                if (imagePath != null && !imagePath.isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        productImage = new ImageIcon(imageFile.getAbsolutePath());
                    } else {
                        System.out.println("Image not found at: " + imagePath);
                        productImage = new ImageIcon(); // Optionally, set a placeholder image
                    }
                } else {
                    System.out.println("No image path provided for product: " + name);
                    productImage = new ImageIcon(); // Optionally, set a placeholder image
                }

                // Create a new Product object
                Product product = new Product(id, name, price, productImage);
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return products;
    }

    private void displayProducts(List<Product> products) {
        main.removeAll();

        for (Product product : products) {
            JPanel productPanel = new JPanel();
            productPanel.setPreferredSize(new Dimension(350, 300));
            productPanel.setLayout(null);
            productPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            ImageIcon resizedIcon = new ImageIcon(resizeImage(product.getImage().getImage(), 200, 200));
            JLabel imageLabel = new JLabel(resizedIcon);
            imageLabel.setBounds(50, 10, 210, 200);
            productPanel.add(imageLabel);

            JLabel idLabel = new JLabel("ID: " + product.getId()); // Add ID label
            idLabel.setBounds(10, 220, 300, 20);
            productPanel.add(idLabel);

            JLabel nameLabel = new JLabel("Name: " + product.getName());
            nameLabel.setBounds(10, 240, 300, 20);
            productPanel.add(nameLabel);

            JLabel priceLabel = new JLabel("Price: $" + product.getPrice());
            priceLabel.setBounds(10, 260, 300, 20);
            productPanel.add(priceLabel);

            JButton addButton = new JButton("Add to Cart");
            mouseCursorPointer(addButton);
            addButton.setBackground(Color.cyan);
            addButton.setBounds(100, 290, 120, 20);
            addButton.addActionListener(e -> {
                addToCart(product);
            });
            productPanel.add(addButton);

            main.add(productPanel);
        }

        revalidate();
        repaint();
    }

    private Image resizeImage(Image originalImage, int width, int height) {
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void conDatabase() {
        try {
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stock", "root", "");
           // JOptionPane.showMessageDialog(null, "connect succes to database !");
        } catch (SQLException e) {
            e.getMessage();

        }
    }

    private void createTable() {
        String table = "CREATE TABLE IF NOT EXISTS product_sale ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, " // Primary key for the product_sale table
                + "user_id INT, " // Foreign key reference to user table
                + "pro_name VARCHAR(255), "
                + "pro_price DOUBLE, "
                + "pro_quantity INT, "
                + "total INT, "
                + "date_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (user_id) REFERENCES user(id))"; // Foreign key constraint

        Statement sm = null;
        try {
            if (cn == null || cn.isClosed()) {
                conDatabase(); // Ensure the connection is established
            }
            sm = cn.createStatement();
            sm.executeUpdate(table);
           // JOptionPane.showMessageDialog(null, "Table product_sale created successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating table: " + e.getMessage());
        } finally {
            if (sm != null) {
                try {
                    sm.close(); // Close the statement to release resources
                } catch (SQLException e) {
                    // Handle potential errors
                    JOptionPane.showMessageDialog(null, "Error closing statement: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        // Create and display UiSaleHomePage frame using SwingUtilities
        SwingUtilities.invokeLater(() -> {
            // Create the JFrame for UiSaleHomePage
            JFrame frame = new JFrame("Sale Home Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1500, 700);

            // Create the UiSaleHomePage component
            UiSaleHomePage uiSaleHomePage = new UiSaleHomePage();

            // Add the UiSaleHomePage component to a JScrollPane for scrolling
            JScrollPane scrollPane = new JScrollPane(uiSaleHomePage);

            // Add the scroll pane to the frame
            frame.add(scrollPane);

            // Make the frame visible
            frame.setVisible(true);
        });
    }

}
