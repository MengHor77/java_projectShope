package Admin;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import javax.swing.JOptionPane;
import jv.HomePage;
import Admin.LogInAdmin;
import User.GetUserName;
import User.SessionManager;
import User.SignUpUser;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Mart extends JFrame {

    private static final String URL = "jdbc:mysql://localhost:3306/stock";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel fileAndTotalPanel;
private JTable table;
    public Mart() {
        setTitle("Mart Application");
        setPreferredSize(new Dimension(1500, 800));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(Color.gray);

        Font boldFont = new Font("Arial", Font.BOLD, 18);

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(1500, 120));
        menuPanel.setBackground(Color.gray);

        // Add logo and spacing
        RoundImagePanel logoPanel = new RoundImagePanel("src/Images/logo/logo.jpg", 100, 100, 0, 0);
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Create a sub-panel for the menu items to be centered
        JPanel menuItemsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        menuItemsPanel.setOpaque(false);

        // Add menu labels
        JLabel homeLabel = createMenuLabel("Home", boldFont);
        JLabel dailySaleLabel = createMenuLabel("Daily Sale", boldFont);
        mouseCursorPointerJabel(dailySaleLabel);

        JLabel adminLabel = createMenuLabel("Admin", boldFont);

        menuItemsPanel.add(homeLabel);
        menuItemsPanel.add(dailySaleLabel);
        menuItemsPanel.add(adminLabel);

        menuPanel.add(logoPanel, BorderLayout.WEST);
        menuPanel.add(menuItemsPanel, BorderLayout.CENTER);

        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS)); // Use BoxLayout to stack components vertically
        profilePanel.setOpaque(false);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 30)); // 50px from the top, 30px from the right

// Create profile image
        RoundImagePanel profileImagePanel = new RoundImagePanel("src/Images/profile/profile.png", 50, 30, 0, 0);
        profilePanel.add(profileImagePanel);
        mouseCursorPointer(profileImagePanel);

// Add the username below the profile image
        JLabel usernameLabel = new JLabel(getUsernameForCurrentUser());
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        profilePanel.add(usernameLabel); // Add the username label to the profile panel

        menuPanel.add(profilePanel, BorderLayout.EAST);

        add(menuPanel, BorderLayout.NORTH);

        // Card layout to switch between different panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.pink);
        cardPanel.setPreferredSize(new Dimension(1400, 2600));
        add(cardPanel, BorderLayout.CENTER);

        // Add Home page panel
        JPanel homePanel = createPagePanel("Home Page", new HomePage(), boldFont);
        cardPanel.add(homePanel, "Home_page");

        // Add Daily Sale panel
        JPanel dailySalePanel = createPagePanel("Daily Sale", null, boldFont);
        cardPanel.add(dailySalePanel, "Daily_sale");

        // Initialize profile menu (dropdown)
        JPopupMenu profileMenu = new JPopupMenu();
        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        profileMenu.add(logoutMenuItem);

        profileImagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                profileMenu.show(profileImagePanel, 0, profileImagePanel.getHeight());
            }
        });

        logoutMenuItem.addActionListener(e -> handleLogout());

        // Initialize Daily Sale menu (dropdown)
        JPopupMenu dailySaleMenu = new JPopupMenu();
        List<String> usernames = GetUserName.getUsernames();

        for (String username : usernames) {
            JMenuItem userItem = new JMenuItem(username);
            userItem.addActionListener(evt -> {
                int userId = GetUserName.getUserIdFromUsername(username);
                if (userId != -1) {
                    SessionManager.setCurrentUserId(userId); // Set the current user session
                    showPanel("Daily_sale"); // Show the "Daily Sale" panel
                    fetchDataForCurrentUser(); // Fetch and display the data for the selected user
                }
            });
            dailySaleMenu.add(userItem);
        }

// MouseListener for the Daily Sale dropdown menu
        dailySaleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dailySaleMenu.show(dailySaleLabel, 0, dailySaleLabel.getHeight());
            }
        });

// Add action listeners to menu labels
        homeLabel.addMouseListener(createMenuMouseListener(() -> showPanel("Home_page"), homeLabel));
        adminLabel.addMouseListener(createMenuMouseListener(() -> new LogInAdmin(), adminLabel));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String getUsernameForCurrentUser() {
        int userId = SessionManager.getCurrentUserId(); // Get the current user ID from session
        return GetUserName.getUsernameFromId(userId);   // Fetch the username based on the user ID
    }



    private void fetchDataForCurrentUser() {
        int userId = SessionManager.getCurrentUserId();

        // SQL query to group by user_id and product_name, calculating sum of price, quantity, and total
        String query = "SELECT user_id, pro_name, pro_price, SUM(pro_quantity) AS quantity, SUM(pro_price * pro_quantity) AS total, MAX(date_create) AS date_created " +
                       "FROM product_sale WHERE user_id = ? GROUP BY user_id, pro_name, pro_price";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId); // Set the userId as a parameter in the query
            try (ResultSet rs = pstmt.executeQuery()) {
                // Create table model for displaying data
                DefaultTableModel tableModel = new DefaultTableModel();
                tableModel.addColumn("ID"); // Add ID column
                tableModel.addColumn("Product Name");
                tableModel.addColumn("Price");
                tableModel.addColumn("Quantity");
                tableModel.addColumn("Total");
                tableModel.addColumn("Date Created");

                // Check if the result set contains data
                if (!rs.isBeforeFirst()) {
                    JOptionPane.showMessageDialog(null, "No sales data found for this user.");
                    return; // Exit if no data is found
                }

                // Initialize the ID counter
                int idCounter = 1;

                // Initialize total sum
                double totalSum = 0;

                // Populate the table model with data
                while (rs.next()) {
                    // Generate an auto-incremented ID
                    int id = idCounter++;
                    String proName = rs.getString("pro_name");
                    double proPrice = rs.getDouble("pro_price");
                    int quantity = rs.getInt("quantity");
                    double total = rs.getDouble("total");
                    Timestamp dateCreated = rs.getTimestamp("date_created");

                    // Add total to the sum
                    totalSum += total;

                    tableModel.addRow(new Object[]{id, proName, proPrice, quantity, total, dateCreated});
                }

                // Create JTable with the table model
                table = new JTable(tableModel); // Initialize the class-level table variable
                table.setPreferredScrollableViewportSize(new Dimension(1400, 600));
                table.setFillsViewportHeight(true);

                // Disable column reordering and resizing
                table.getTableHeader().setReorderingAllowed(false);
                table.getColumnModel().getColumn(0).setResizable(false); // ID column
                table.getColumnModel().getColumn(1).setResizable(false);
                table.getColumnModel().getColumn(2).setResizable(false);
                table.getColumnModel().getColumn(3).setResizable(false);
                table.getColumnModel().getColumn(4).setResizable(false);
                table.getColumnModel().getColumn(5).setResizable(false);

                // Create the File label and its dropdown menu
                JLabel fileLabel = new JLabel("File");
                fileLabel.setFont(new Font("Arial", Font.BOLD, 16));
                fileLabel.setForeground(Color.BLACK);
                fileLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
                fileLabel.setOpaque(true);
                fileLabel.setBackground(Color.gray);

                JPopupMenu fileMenu = new JPopupMenu();
                JMenuItem saveMenuItem = new JMenuItem("Save");
                JMenuItem saveAsMenuItem = new JMenuItem("Save As");
                JMenuItem printMenuItem = new JMenuItem("Print");
                JMenuItem sumMenuItem = new JMenuItem("Setting");

                fileMenu.add(saveMenuItem);
                fileMenu.add(saveAsMenuItem);
                fileMenu.add(printMenuItem);
                fileMenu.add(sumMenuItem);
                saveMenuItem.addActionListener(e -> handleSave());
                saveAsMenuItem.addActionListener(e -> handleSaveAs());
                printMenuItem.addActionListener(e -> handlePrint());
                sumMenuItem.addActionListener(e -> handleSetting());

                fileLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        fileMenu.show(fileLabel, 0, fileLabel.getHeight());
                    }
                });

                // Create the Refresh label
                JLabel refreshLabel = new JLabel("Refresh");
                refreshLabel.setFont(new Font("Arial", Font.BOLD, 16));
                refreshLabel.setForeground(Color.BLACK);
                refreshLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
                refreshLabel.setOpaque(true);
                refreshLabel.setBackground(Color.gray);

                refreshLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        refreshData(); // Refresh the data when clicked
                    }
                });

                // Create the Total label
                JLabel totalLabel = new JLabel("Total: $" + String.format("%.2f", totalSum));
                totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
                totalLabel.setForeground(Color.BLACK);
                totalLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
                totalLabel.setOpaque(true);
                totalLabel.setBackground(Color.gray);

                // Create a panel for the labels
                JPanel fileAndTotalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
                fileAndTotalPanel.setOpaque(false);
                fileAndTotalPanel.add(fileLabel);
                fileAndTotalPanel.add(refreshLabel);
                fileAndTotalPanel.add(totalLabel);

                // Add the File label and the table to a panel
                JPanel topPanel = new JPanel(new BorderLayout());
                topPanel.add(fileAndTotalPanel, BorderLayout.WEST);
                JPanel tablePanel = new JPanel(new BorderLayout());
                tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

                // Replace the current content in the Daily Sale panel with the new components
                JPanel dailySalePanel = (JPanel) cardPanel.getComponent(1); // Assuming it's the second component
                dailySalePanel.removeAll(); // Clear old data
                dailySalePanel.setLayout(new BorderLayout()); // Use BorderLayout for the panel
                dailySalePanel.add(topPanel, BorderLayout.NORTH); // Add the top panel with File and Total labels
                dailySalePanel.add(tablePanel, BorderLayout.CENTER); // Add the table panel
                dailySalePanel.revalidate(); // Revalidate to update the UI
                dailySalePanel.repaint(); // Repaint the panel
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching sales data for user", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSaveAs() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Ensure the file has a .pdf extension
            if (!fileToSave.getName().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }

            // Pass the JTable to saveAsPDF
            saveAsPDF(fileToSave, table);
        }
    }

    private void saveAsPDF(File file, JTable table) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());

            // Add table headers
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(new PdfPCell(new Phrase(table.getColumnName(i))));
            }

            // Add table data
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    pdfTable.addCell(table.getValueAt(i, j).toString());
                }
            }

            document.add(pdfTable);
            document.close();
            JOptionPane.showMessageDialog(null, "PDF file saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving PDF file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
 

    private void handlePrint() {
        // Implement print functionality
        JOptionPane.showMessageDialog(null, "Print clicked");
    }

    private void handleSetting() {
        // Implement sum functionality
        JOptionPane.showMessageDialog(null, "handleSetting clicked");
    }
    private void refreshData() {
    // Check if cardPanel has the expected number of components
    if (cardPanel.getComponentCount() > 1) {
        // Access the component at index 1 (if it exists)
        JPanel dailySalePanel = (JPanel) cardPanel.getComponent(1);
        
        // Make sure the component is a JPanel
        if (dailySalePanel instanceof JPanel) {
            // Perform the refresh operations here
            // Example: refresh the data
            JOptionPane.showMessageDialog(null, "click refresh");
            fetchDataForCurrentUser();
        } else {
            System.err.println("Component at index 1 is not a JPanel.");
        }
    } else {
        System.err.println("CardPanel does not contain enough components.");
    }
}

// Implement the action handlers for the menu items
    private void handleSave() {
        // Implement save functionality
        JOptionPane.showMessageDialog(null, "Save clicked");
    }


    private void handleLogout() {
        // Show confirmation dialog
        int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            // Capture the current time
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a"); // 12-hour format with AM/PM
            String formattedNow = now.format(formatter);

            // Print the logout time to the console (or you could store it in a log file or database)
            JOptionPane.showMessageDialog(null, "User logged out at: " + formattedNow);

            // Proceed with logout
            this.dispose();
            new SignUpUser(); // Optionally open the SignUpUser frame
        }
        // If the user chose "No", do nothing
    }

    private JLabel createMenuLabel(String text, Font font) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(font);
        label.setForeground(Color.BLACK);
        label.setOpaque(true);
        label.setBackground(Color.gray);
        label.setPreferredSize(new Dimension(150, 120));
        return label;
    }

    private void mouseCursorPointer(RoundImagePanel roundImagePanel) {
        roundImagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                roundImagePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                roundImagePanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private void mouseCursorPointerJabel(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setForeground(Color.cyan);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                label.setForeground(Color.BLACK);
            }
        });
    }

    private MouseAdapter createMenuMouseListener(Runnable onClick, JLabel label) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.cyan);
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.BLACK);
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        };
    }

    private void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    private JPanel createPagePanel(String title, JPanel contentPanel, Font titleFont) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.gray);
        titleLabel.setForeground(Color.cyan);
        titleLabel.setFont(titleFont);
        panel.add(titleLabel, BorderLayout.NORTH);

        if (contentPanel != null) {
            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.getViewport().setPreferredSize(new Dimension(1400, 2800));
            panel.add(scrollPane, BorderLayout.CENTER);
        }

        return panel;
    }

    static class RoundImagePanel extends JPanel {

        private Image img;
        private int width, height;

        public RoundImagePanel(String imgPath, int width, int height, int x, int y) {
            setOpaque(false);
            try {
                img = new ImageIcon(imgPath).getImage();
                this.width = width;
                this.height = height;
                setPreferredSize(new Dimension(width, height));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int diameter = Math.min(width, height);
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;
            Ellipse2D.Double circle = new Ellipse2D.Double(x, y, diameter, diameter);
            g2d.setClip(circle);
            g2d.drawImage(img, x, y, diameter, diameter, this);
            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        new SignUpUser();

    }
}
