//package jv;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JLabel;
//import javax.swing.ImageIcon;
//import javax.swing.BoxLayout;
//import javax.swing.border.EmptyBorder;
//import javax.swing.border.LineBorder;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class DataSaleHealthAndBeautyPage extends JPanel {
//    ArrayList<Product> Data_sale = new ArrayList<>();
//    private static final String URL = "jdbc:mysql://localhost:3306/stock";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";
//    private static Connection cn;
//
//    public DataSaleHealthAndBeautyPage() {
//        setLayout(new BorderLayout());
//        setBackground(Color.cyan);
//        setPreferredSize(new Dimension(1400, 800));
//
//        connectToDatabase(); // Ensure the connection is established
//        if (cn != null) {
//            getData();
//        } else {
//            JOptionPane.showMessageDialog(this, "Database connection failed.");
//        }
//
//        // Create a panel to center the title in the NORTH region
//        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        titlePanel.setBackground(Color.LIGHT_GRAY); // Match the background color
//
//        JLabel title = new JLabel(" Total Sale of Health And Beauty Page ");
//        title.setOpaque(true); 
//        title.setBackground(Color.LIGHT_GRAY);
//        title.setForeground(Color.black);
//        Font boldFont = new Font("Arial", Font.BOLD, 18);
//        title.setFont(boldFont);
//        titlePanel.add(title); // Add title to the title panel
//        add(titlePanel, BorderLayout.NORTH); // Add the title panel to the NORTH region
//
//        // Panel for product details
//        JPanel productPanel = new JPanel();
//        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
//        productPanel.setBackground(Color.white);
//
//        // Add product details as labels to the panel with borders
//        for (Product product : Data_sale) {
//            JPanel productContainer = new JPanel();
//            productContainer.setPreferredSize(new Dimension(1500, 50)); 
//            productContainer.setMaximumSize(new Dimension(1500, 50)); 
//            productContainer.setMinimumSize(new Dimension(1500, 50)); 
//            productContainer.setLayout(new BoxLayout(productContainer, BoxLayout.Y_AXIS));
//            productContainer.setBorder(new LineBorder(Color.BLACK, 1)); 
//            productContainer.setBackground(Color.white); 
//
//            JLabel productLabel = new JLabel(
//                "Product ID: " + product.getId() +
//                "       ,Name: " + product.getName() +
//                "       ,Price: $" + product.getPrice() +
//                "       ,Quantity: " + product.getQty() +
//                "       ,Total Amount: $" + (product.getPrice() * product.getQty())
//            );
//            productLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); 
//            productLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT); 
//
//            // Optionally, display the image if available
//            if (product.getImage() != null) {
//                JLabel imageLabel = new JLabel(product.getImage());
//                imageLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
//                productContainer.add(imageLabel);
//            }
//
//            productContainer.add(productLabel);
//            productPanel.add(productContainer);
//        }
//
//        JScrollPane js_jp1 = new JScrollPane(productPanel);
//        js_jp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_jp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//
//        add(js_jp1, BorderLayout.CENTER);
//    }
//
//    private void connectToDatabase() {
//        try {
//            cn = DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Failed to connect to the database.");
//        }
//    }
//
//    private void getData() {
//        String query = "SELECT "
//                + "product_id AS id, "
//                + "product_name AS name, "
//                + "product_price AS price, "
//                + "SUM(quantity) AS qty, "
//                + "SUM(total_amount) AS total_amount "
//                + "FROM data_sale_health_and_beauty_page "
//                + "GROUP BY product_id, product_name, product_price "
//                + "HAVING SUM(total_amount) > 0";
//
//        try (PreparedStatement stmt = cn.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                double price = rs.getDouble("price");
//                int qty = rs.getInt("qty");
//                String category = ""; // Placeholder since category isn't in the query
//                ImageIcon image = null; // Placeholder since image isn't in the query
//
//                Product product = new Product(id, name, price, qty, category, image);
//                Data_sale.add(product);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(this, "not data of total sale in Health And Beauty page !");
//        }
//    }
//}
