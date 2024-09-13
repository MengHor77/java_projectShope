package Admin;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import javax.swing.JOptionPane;
import jv.HomePage;
import Admin.LogInAdmin;
import User.SignUpUser;

public class Mart extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

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
        JLabel adminLabel = createMenuLabel("Admin", boldFont);

        menuItemsPanel.add(homeLabel);
        menuItemsPanel.add(dailySaleLabel);
        menuItemsPanel.add(adminLabel);

        menuPanel.add(logoPanel, BorderLayout.WEST);
        menuPanel.add(menuItemsPanel, BorderLayout.CENTER);

        // Create a panel for the profile and align it to the right
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 50, 10)); // 30px from the right
        profilePanel.setOpaque(false);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 30)); // 80px from the top, 30px from the right

        RoundImagePanel profileImagePanel = new RoundImagePanel("src/Images/profile/profile.png", 50, 30, 0, 0);
        profilePanel.add(profileImagePanel);

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
      JPopupMenu  profileMenu = new JPopupMenu();
        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        profileMenu.add(logoutMenuItem);

        
        profileImagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                profileMenu.show(profileImagePanel, 0, profileImagePanel.getHeight()); 
            }
        });

        
        logoutMenuItem.addActionListener(e -> handleLogout()); 

      

        // Add action listeners to menu labels
        homeLabel.addMouseListener(createMenuMouseListener(() -> showPanel("Home_page"), homeLabel));
        adminLabel.addMouseListener(createMenuMouseListener(() -> new LogInAdmin(), adminLabel));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

private void handleLogout() {
    // Show confirmation dialog
    int response = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to logout?", 
        "Confirm Logout", 
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);

    if (response == JOptionPane.YES_OPTION) {
        // User chose "Yes", proceed with logout
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
