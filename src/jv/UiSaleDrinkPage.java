//package jv;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Cursor;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.GridLayout;
//import java.awt.Image;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import javax.imageio.ImageIO;
//import javax.swing.BorderFactory;
//import javax.swing.ButtonGroup;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JOptionPane;
//import javax.swing.JRadioButton;
//import javax.swing.JSpinner;
//import javax.swing.SpinnerModel;
//import javax.swing.SpinnerNumberModel;
//import javax.swing.SwingConstants;
//
//public class UiSaleDrinkPage extends JPanel {
//
//    private JFrame cartFrame;
//    private JPanel main;
//    private List<Product> cartItems; // List to store cart items
//
//    public UiSaleDrinkPage() {
//        
//          cartItems = new ArrayList<>(); 
//        main = new JPanel();
//        main.setPreferredSize(new Dimension(1500, 1800));
//        //main.setBackground(Color.blue);
//        main.setLayout(new GridLayout(0, 4, 10, 10)); 
//        add(main);
//        List<Product> products = fetchProducts();
//        displayProducts(products); 
//  
//    }
//      private void mouseCursorPointer(JButton button) {
//    button.addMouseListener(new MouseAdapter() {
//        @Override
//        public void mouseEntered(MouseEvent e) {
//            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//        }
//    });
//}
//
//
//    private void addToCart(Product newItem) {
//        for (Product item : cartItems) {
//            if (item.getId() == newItem.getId()) {
//                item.setQty(item.getQty() + 1); 
//                //JOptionPane.showMessageDialog(null, "Product quantity updated.");
//                showCart();
//                return; 
//            }
//        }
//
//        // If the item is not in the cart, add it with an initial quantity of 1
//        newItem.setQty(1);
//        cartItems.add(newItem);
//       // JOptionPane.showMessageDialog(null, "Product has been added to the cart");
//
//        if (cartFrame != null) {
//            cartFrame.dispose();
//        }
//        showCart();
//    }
//
//
//    private void showCart() {
//        if (cartItems.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Cart is empty.");
//               cartFrame.dispose();
//            return;
//        }
//
//        if (cartFrame != null) {
//            cartFrame.dispose();
//        }
//
//        cartFrame = new JFrame("Cart");
//        cartFrame.setSize(new Dimension(600, 400));
//        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        JPanel cartPanel = new JPanel();
//        cartPanel.setLayout(new GridLayout(cartItems.size() + 2, 1, 10, 10)); 
//
//        double totalPayment = 0.00;
//
//        for (Product item : cartItems) {
//            JPanel itemPanel = new JPanel();
//            itemPanel.setLayout(null);
//            itemPanel.setPreferredSize(new Dimension(550, 120)); 
//            itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//
//            // Resize the image to fit within the desired bounds
//            ImageIcon resizedIcon = new ImageIcon(resizeImage(item.getImage().getImage(), 80, 80));
//            JLabel imageLabel = new JLabel(resizedIcon);
//            imageLabel.setBounds(10, 10, 80, 80); 
//            itemPanel.add(imageLabel);
//
//            JLabel idLabel = new JLabel("ID: " + item.getId());
//            idLabel.setBounds(100, 10, 150, 20); 
//            itemPanel.add(idLabel);
//
//            JLabel nameLabel = new JLabel("Name: " + item.getName());
//            nameLabel.setBounds(100, 30, 200, 20); 
//            itemPanel.add(nameLabel);
//
//            JLabel priceLabel = new JLabel("Price: $" + item.getPrice());
//            priceLabel.setBounds(100, 50, 200, 20); 
//            itemPanel.add(priceLabel);
//
//            // Quantity Spinner
//            SpinnerModel model = new SpinnerNumberModel(item.getQty(), 1, 1000, 1); 
//            JSpinner qtySpinner = new JSpinner(model);
//            qtySpinner.setBounds(100, 80, 50, 20); 
//            itemPanel.add(qtySpinner);
//
//            // Update button for quantity changes
//            JButton updateButton = new JButton("Update");
//            mouseCursorPointer(updateButton);
//            updateButton.setBackground(Color.cyan);
//            updateButton.setBounds(160, 80, 80, 20); 
//            updateButton.addActionListener(e -> {
//                int newQty = (int) qtySpinner.getValue();
//                item.setQty(newQty);
//                showCart(); 
//            });
//            itemPanel.add(updateButton);
//
//            // Remove button to remove the item from the cart
//            JButton removeButton = new JButton("X"); 
//             mouseCursorPointer(removeButton);
//            removeButton.setBackground(Color.cyan);
//            removeButton.setBounds(250, 80, 50, 20); 
//            removeButton.addActionListener(e -> {
//                cartItems.remove(item);
//                showCart(); 
//            });
//            itemPanel.add(removeButton);
//
//            cartPanel.add(itemPanel);
//
//            totalPayment += item.getQty() * item.getPrice();
//        }
//
//        JLabel totalLabel = new JLabel("Total Payment: $" + String.format("%.2f", totalPayment));
//        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 1, 10, 10));
//
//        JPanel totalPanel = new JPanel();
//        totalPanel.setLayout(null); 
//        totalPanel.setPreferredSize(new Dimension(550, 80)); 
//
//        totalLabel.setBounds(0, 0, 550, 40); 
//        totalPanel.add(totalLabel);
//
//        // Add Show Receipt/Pay button
//        JButton clickToPayButton = new JButton("Click to Pay");
//        mouseCursorPointer(clickToPayButton);
//
//        clickToPayButton.setBackground(Color.cyan);
//        clickToPayButton.setPreferredSize(new Dimension(100, 30)); 
//        clickToPayButton.setBounds(10, 50, 150, 30); 
//        clickToPayButton.addActionListener(e -> {
//            showListProductReciept(); 
//            cartFrame.dispose(); 
//        });
//        totalPanel.add(clickToPayButton);
//
//        cartPanel.add(totalPanel);
//
//        JScrollPane scrollPane = new JScrollPane(cartPanel);
//        cartFrame.add(scrollPane);
//        cartFrame.setVisible(true);
//    }
//   private void showQRCode() {
//    // Path to the QR code image
//    String imagePath = "src/Images/QR_code/QR_code.png";
//
//    // Load the image
//    ImageIcon qrCodeImage = new ImageIcon(imagePath);
//
//    // Create a new frame to display the QR code
//    JFrame qrFrame = new JFrame("Scan QR Code to Pay");
//    qrFrame.setSize(400, 450);
//    qrFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    qrFrame.setLayout(new BorderLayout()); // Set layout to BorderLayout
//
//    // Get the image from the ImageIcon and scale it to fit the frame
//    Image image = qrCodeImage.getImage(); // Get the original image
//    Image scaledImage = image.getScaledInstance(qrFrame.getWidth(), qrFrame.getHeight() - 100, Image.SCALE_SMOOTH);
//    ImageIcon scaledIcon = new ImageIcon(scaledImage);
//
//    // Calculate total payment
//    double totalPayment = 0.0;
//    for (Product item : cartItems) {
//        totalPayment += item.getQty() * item.getPrice();
//    }
//
//    // Create a label to show the total payment
//    JLabel totalLabel = new JLabel("Total Payment: $" + String.format("%.2f", totalPayment), SwingConstants.CENTER);
//
//    // Create a label and set the scaled image as its icon
//    JLabel imageLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
//
//    // Create a "Confirm Payment" button
//    JButton confirmButton = new JButton("Confirm");
//    confirmButton.setBackground(Color.cyan);
//    mouseCursorPointer(confirmButton);
//
//    confirmButton.addActionListener(e -> {
//        // Close the QR code frame
//        qrFrame.dispose();
//        JOptionPane.showMessageDialog(null, "Payment Successful! Quantity updated.");
//        cartItems.clear();
//    });
//
//    // Add the totalLabel to the top (NORTH)
//    qrFrame.add(totalLabel, BorderLayout.NORTH);
//
//    // Add the imageLabel (QR code image) to the center
//    qrFrame.add(imageLabel, BorderLayout.CENTER);
//
//    // Add the confirmButton to the bottom (SOUTH)
//    qrFrame.add(confirmButton, BorderLayout.SOUTH);
//
//    // Show the frame
//    qrFrame.setVisible(true);
//}
//
//  private void showListProductReciept() {
//    if (cartItems.isEmpty()) {
//        JOptionPane.showMessageDialog(null, "Cart is empty.");
//        return;
//    }
//
//    JFrame receiptFrame = new JFrame("Receipt");
//    receiptFrame.setSize(new Dimension(500, 400));
//    receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//    JPanel receiptPanel = new JPanel();
//    receiptPanel.setLayout(new GridLayout(cartItems.size() + 4, 1, 10, 10));
//
//    double totalPayment = 0.00;
//
//    // Header
//    JPanel headerPanel = new JPanel();
//    headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//    JLabel title_Label = new JLabel("Product Details");
//    title_Label.setFont(new Font("Arial", Font.BOLD, 16));
//    headerPanel.add(title_Label);
//    receiptPanel.add(headerPanel);
//
//    // Product details
//    for (Product item : cartItems) {
//        JPanel itemPanel = new JPanel();
//        itemPanel.setLayout(null);
//        itemPanel.setPreferredSize(new Dimension(450, 100));
//        itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//
//        // Manually position labels with setBounds
//        JLabel idLabel = new JLabel("ID: " + item.getId());
//        idLabel.setBounds(10, 10, 150, 20);
//        itemPanel.add(idLabel);
//
//        JLabel nameLabel = new JLabel("Name: " + item.getName());
//        nameLabel.setBounds(10, 30, 200, 20);
//        itemPanel.add(nameLabel);
//
//        JLabel priceLabel = new JLabel("Price: $" + item.getPrice());
//        priceLabel.setBounds(10, 50, 200, 20);
//        itemPanel.add(priceLabel);
//
//        JLabel qtyLabel = new JLabel("Qty: " + item.getQty());
//        qtyLabel.setBounds(10, 70, 200, 20);
//        itemPanel.add(qtyLabel);
//
//        receiptPanel.add(itemPanel);
//
//        totalPayment += item.getQty() * item.getPrice();
//    }
//
//    // Display total payment for the entire cart
//    JLabel totalPaymentLabel = new JLabel("Total Payment: $" + String.format("%.2f", totalPayment));
//    totalPaymentLabel.setHorizontalAlignment(SwingConstants.CENTER);
//    totalPaymentLabel.setFont(new Font("Arial", Font.BOLD, 16));
//    totalPaymentLabel.setBorder(BorderFactory.createEmptyBorder(10, 1, 10, 10));
//
//    JPanel totalPaymentPanel = new JPanel();
//    totalPaymentPanel.setLayout(new BorderLayout());
//    totalPaymentPanel.add(totalPaymentLabel, BorderLayout.CENTER);
//
//    receiptPanel.add(totalPaymentPanel);
//
//    // Payment options
//    JPanel paymentOptionsPanel = new JPanel();
//    paymentOptionsPanel.setLayout(new FlowLayout());
//
//    JLabel paymentLabel = new JLabel("Choose Payment Method: ");
//    paymentOptionsPanel.add(paymentLabel);
//
//    JRadioButton cashRadioButton = new JRadioButton("Cash");
//    JRadioButton qrCodeRadioButton = new JRadioButton("Scan QR Code");
//
//    ButtonGroup paymentGroup = new ButtonGroup();
//    paymentGroup.add(cashRadioButton);
//    paymentGroup.add(qrCodeRadioButton);
//
//    paymentOptionsPanel.add(cashRadioButton);
//    paymentOptionsPanel.add(qrCodeRadioButton);
//
//    JButton payButton = new JButton("Pay");
//    mouseCursorPointer(payButton);
//
//    payButton.setBackground(Color.cyan);
//    payButton.setPreferredSize(new Dimension(100, 30));
//
//    
// payButton.addActionListener(e -> {
//    Connection connection = connectToDatabase();
//    if (connection != null) {
//        try {
//            // Ensure sales table exists
//            String createSalesTableQuery = "CREATE TABLE IF NOT EXISTS data_sale_drink_page ("
//                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
//                    + "product_id INT NOT NULL, "
//                    + "product_name VARCHAR(255) NOT NULL, "
//                    + "product_price DOUBLE NOT NULL, "
//                    + "quantity INT NOT NULL, "
//                    + "total_amount DOUBLE NOT NULL, "
//                    + "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
//            try (PreparedStatement createTableStmt = connection.prepareStatement(createSalesTableQuery)) {
//                createTableStmt.executeUpdate();
//            }
//
//            if (cashRadioButton.isSelected()) {
//                // Update stock and insert sales records
//                String fetchQuery = "SELECT pro_qty FROM drinkstock WHERE pro_id = ?";
//                String updateQuery = "UPDATE drinkstock SET pro_qty = ? WHERE pro_id = ?";
//                String insertSalesQuery = "INSERT INTO data_sale_drink_page (product_id, product_name, product_price, quantity, total_amount) VALUES (?, ?, ?, ?, ?)";
//
//                try (PreparedStatement fetchStmt = connection.prepareStatement(fetchQuery);
//                     PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
//                     PreparedStatement insertStmt = connection.prepareStatement(insertSalesQuery)) {
//
//                    for (Product item : cartItems) {
//                        int purchasedQty = item.getQty();
//                        
//                        // Fetch the current quantity from the database
//                        fetchStmt.setInt(1, item.getId());
//                        ResultSet rs = fetchStmt.executeQuery();
//                        if (rs.next()) {
//                            int currentQty = rs.getInt("pro_qty");
//                            int newQty = currentQty - purchasedQty;
//
//                            // Update the database with the new quantity
//                            updateStmt.setInt(1, newQty);
//                            updateStmt.setInt(2, item.getId());
//                            updateStmt.executeUpdate();
//                        }
//
//                        // Insert into sales table
//                        insertStmt.setInt(1, item.getId());
//                        insertStmt.setString(2, item.getName());
//                        insertStmt.setDouble(3, item.getPrice());
//                        insertStmt.setInt(4, item.getQty());
//                        insertStmt.setDouble(5, item.getQty() * item.getPrice());
//
//                        insertStmt.addBatch(); // Add to batch
//                    }
//
//                    // Execute batch insert
//                    insertStmt.executeBatch();
//                }
//
//                JOptionPane.showMessageDialog(null, "Payment Successful! Quantity updated.");
//                cartItems.clear(); // Clear the cart
//                receiptFrame.dispose(); // Close the receipt window
//
//            } else if (qrCodeRadioButton.isSelected()) {
//                showQRCode();
//
//                // Update stock and insert sales records
//                String fetchQuery = "SELECT pro_qty FROM drinkstock WHERE pro_id = ?";
//                String updateQuery = "UPDATE drinkstock SET pro_qty = ? WHERE pro_id = ?";
//                String insertSalesQuery = "INSERT INTO data_sale_drink_page (product_id, product_name, product_price, quantity, total_amount) VALUES (?, ?, ?, ?, ?)";
//
//                try (PreparedStatement fetchStmt = connection.prepareStatement(fetchQuery);
//                     PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
//                     PreparedStatement insertStmt = connection.prepareStatement(insertSalesQuery)) {
//
//                    for (Product item : cartItems) {
//                        int purchasedQty = item.getQty();
//
//                        // Fetch the current quantity from the database
//                        fetchStmt.setInt(1, item.getId());
//                        ResultSet rs = fetchStmt.executeQuery();
//                        if (rs.next()) {
//                            int currentQty = rs.getInt("pro_qty");
//                            int newQty = currentQty - purchasedQty;
//
//                            // Update the database with the new quantity
//                            updateStmt.setInt(1, newQty);
//                            updateStmt.setInt(2, item.getId());
//                            updateStmt.executeUpdate();
//                        }
//
//                        // Insert into sales table
//                        insertStmt.setInt(1, item.getId());
//                        insertStmt.setString(2, item.getName());
//                        insertStmt.setDouble(3, item.getPrice());
//                        insertStmt.setInt(4, item.getQty());
//                        insertStmt.setDouble(5, item.getQty() * item.getPrice());
//
//                        insertStmt.addBatch(); // Add to batch
//                    }
//
//                    // Execute batch insert
//                    insertStmt.executeBatch();
//                }
//
//                cartItems.clear(); // Clear the cart
//                receiptFrame.dispose(); // Close the receipt window
//            } else {
//                JOptionPane.showMessageDialog(null, "Please choose a payment method.");
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "An error occurred while updating the database.");
//        }
//    } else {
//        JOptionPane.showMessageDialog(null, "Database connection failed.");
//    }
//});
//
//    paymentOptionsPanel.add(payButton);
//    receiptPanel.add(paymentOptionsPanel);
//
//    JScrollPane scrollPane = new JScrollPane(receiptPanel);
//    receiptFrame.add(scrollPane);
//    receiptFrame.setVisible(true);
//}
//
//// Method to resize the image
//    private Image resizeImage(Image originalImage, int width, int height) {
//        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//    }
//
//    private Connection connectToDatabase() {
//        try {
//            String url = "jdbc:mysql://localhost:3306/stock";
//            String user = "root";
//            String password = "";
//            return DriverManager.getConnection(url, user, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<Product> fetchProducts() {
//        List<Product> productList = new ArrayList<>();
//        Connection connection = connectToDatabase();
//        if (connection == null) {
//            System.out.println("Database connection failed.");
//            return productList;
//        }
//
//        String query = "SELECT * FROM drinkstock";
//        try (PreparedStatement pstmt = connection.prepareStatement(query); ResultSet resultSet = pstmt.executeQuery()) {
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("pro_id");
//                String name = resultSet.getString("pro_name");
//                double price = resultSet.getDouble("pro_price");
//                int quantity = resultSet.getInt("pro_qty");
//                String category = resultSet.getString("pro_category");
//                String imagePath = resultSet.getString("img");
//
//                ImageIcon imageIcon = loadImage(imagePath, 280, 300);
//                Product product = new Product(id, name, price, quantity, category, imageIcon);
//                productList.add(product);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return productList;
//    }
//
//    private ImageIcon loadImage(String imagePath, int width, int height) {
//        String[] parts = imagePath.split("#");
//        String actualPath = parts.length > 1 ? parts[1] : parts[0];
//
//        File imageFile = new File(actualPath);
//        if (imageFile.exists()) {
//          //  System.out.println("Loading image from: " + actualPath);
//            try {
//                BufferedImage img = ImageIO.read(imageFile);
//                Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//                return new ImageIcon(resizedImg);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return new ImageIcon();
//            }
//        } else {
//            System.out.println("File not found: " + actualPath);
//            return new ImageIcon();
//        }
//    }
//
//    public void displayProducts(List<Product> products) {
//        for (Product product : products) {
//            JPanel productPanel = new JPanel();
//            productPanel.setBackground(Color.LIGHT_GRAY);
//            productPanel.setLayout(null);
//            productPanel.setPreferredSize(new Dimension(350, 400));
//
//            JLabel imageLabel = new JLabel(product.getImage());
//            imageLabel.setBounds(50, 10, 280, 300);
//            JLabel id_Label = new JLabel("ID: " + product.getId());
//            id_Label.setBounds(50, 320, 100, 20);
//
//            JLabel name_Label = new JLabel("Name: " + product.getName());
//            name_Label.setBounds(50, 350, 200, 20);
//
//            JLabel price_Label = new JLabel("Price: $" + product.getPrice());
//            price_Label.setBounds(50, 380, 200, 20);
//
//            JButton addButton = new JButton("Add to Cart");
//            mouseCursorPointer(addButton);
//
//            addButton.setBackground(Color.cyan);
//            addButton.setBounds(50, 410, 120, 25);
//            addButton.addActionListener(e -> addToCart(product)); 
//
//            productPanel.add(imageLabel);
//            productPanel.add(id_Label);
//            productPanel.add(name_Label);
//            productPanel.add(price_Label);
//            productPanel.add(addButton);
//            main.add(productPanel);
//        }
//      // main.revalidate();
//    }
//
//    public static void main(String[] args) {
//       new UiSaleDrinkPage();
//       
//    }
//}
