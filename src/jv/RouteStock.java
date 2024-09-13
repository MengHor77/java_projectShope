//package jv;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.geom.Ellipse2D;
//import jv.StockHome;
//
//public class RouteStock extends JFrame {
//
//    private CardLayout cardLayout;
//    private JPanel cardPanel;
//
//    public RouteStock() {
//        setTitle("Route Stock Admin");
//        setPreferredSize(new Dimension(1500, 800)); // Adjusted preferred size for better fit
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new BorderLayout()); // Using BorderLayout to simplify centering
//        setBackground(Color.gray);
//
//        Font boldFont = new Font("Arial", Font.BOLD, 18);
//
//        // Menu panel in a row layout
//        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // FlowLayout to align components horizontally
//        menuPanel.setPreferredSize(new Dimension(1500, 120)); // Adjusted size to fit the row layout
//        menuPanel.setBackground(Color.gray);
//
//        // Logo panel
//        RoundImagePanel logoPanel = new RoundImagePanel("src/Images/logo/logo.jpg", 100, 100, 0, 0);
//        menuPanel.add(logoPanel);
//
//        // Add menu items to the panel
//        JLabel jlb1 = createMenuLabel("Home Stock", boldFont);
//        JLabel jlb2 = createMenuLabel("Drink Stock", boldFont);
//        JLabel jlb3 = createMenuLabel("Food Stock", boldFont);
//        JLabel jlb4 = createMenuLabel("Health & Beauty Stock", boldFont);
//        JLabel jlb5 = createMenuLabel("Sale Total", boldFont);
//
//        menuPanel.add(jlb1);
//        menuPanel.add(jlb2);
//        menuPanel.add(jlb3);
//        menuPanel.add(jlb4);
//        menuPanel.add(jlb5);
//
//        add(menuPanel, BorderLayout.NORTH);
//
//        // CardLayout to switch between panels
//        cardLayout = new CardLayout();
//        cardPanel = new JPanel(cardLayout);
//        cardPanel.setBackground(Color.pink);
//        cardPanel.setPreferredSize(new Dimension(1400, 700)); // Adjusted size for better fit
//        add(cardPanel, BorderLayout.CENTER);
//
//        
//        
//        // Create the home  panels
//        JPanel p1 = new JPanel(new BorderLayout()); // Use BorderLayout to manage components
//        p1.setBackground(Color.gray);
//        JLabel StockHome_TitleLabel = new JLabel("Home Stock", JLabel.CENTER);
//        StockHome_TitleLabel.setOpaque(true); 
//        StockHome_TitleLabel.setBackground(Color.gray);
//        StockHome_TitleLabel.setForeground(Color.cyan);
//        StockHome_TitleLabel.setFont(boldFont);
//        p1.add(StockHome_TitleLabel, BorderLayout.NORTH);
//        // Create a JScrollPane with StockHome
//        JScrollPane js_p1 = new JScrollPane(new StockHome());
//        js_p1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_p1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        js_p1.getViewport().setPreferredSize(new Dimension(1400, 700)); // Adjust the preferred size if needed
//        p1.add(js_p1, BorderLayout.CENTER);
//
//        
//        // Create the Drink panels
//        JPanel p2 = new JPanel(new BorderLayout()); 
//        p2.setBackground(Color.gray);
//        JLabel StockDrink_TitleLabel = new JLabel("Drink Stock", JLabel.CENTER);
//        StockDrink_TitleLabel.setOpaque(true); 
//        StockDrink_TitleLabel.setBackground(Color.gray);
//        StockDrink_TitleLabel.setForeground(Color.cyan);
//        StockDrink_TitleLabel.setFont(boldFont);
//        p2.add(StockDrink_TitleLabel, BorderLayout.NORTH);
//        // Create a JScrollPane with StockHome
//        JScrollPane js_p2 = new JScrollPane(new StockDrink());
//        js_p2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_p2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        js_p2.getViewport().setPreferredSize(new Dimension(1400, 700)); // Adjust the preferred size if needed
//        p2.add(js_p2, BorderLayout.CENTER);
//
//        
//        
//        // Create the food panels
//        JPanel p3 = new JPanel(new BorderLayout()); // Use BorderLayout to manage components
//        p3.setBackground(Color.gray);
//        JLabel food_TitleLabel = new JLabel("Food Stock", JLabel.CENTER);
//         food_TitleLabel.setOpaque(true); 
//        food_TitleLabel.setBackground(Color.gray);
//        food_TitleLabel.setForeground(Color.cyan);
//        food_TitleLabel.setFont(boldFont);
//        p3.add(food_TitleLabel, BorderLayout.NORTH);
//        // Create a JScrollPane with StockHome
//        JScrollPane js_p3 = new JScrollPane(new StockFood());
//        js_p3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_p3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        js_p3.getViewport().setPreferredSize(new Dimension(1400, 700)); // Adjust the preferred size if needed
//        p3.add(js_p3, BorderLayout.CENTER);
//
//        
//        
//        // Create the Health & Beauty Stock panels
//        JPanel p4 = new JPanel(new BorderLayout());
//        p4.setBackground(Color.gray);
//        JLabel Title_Health_Beauty = new JLabel("Health & Beauty Stock", JLabel.CENTER);
//        Title_Health_Beauty.setOpaque(true); 
//        Title_Health_Beauty.setBackground(Color.gray);
//        Title_Health_Beauty.setForeground(Color.cyan);
//        Title_Health_Beauty.setFont(boldFont);
//        p4.add(Title_Health_Beauty, BorderLayout.NORTH);
//        // Create a JScrollPane with StockHome
//        JScrollPane js_p4 = new JScrollPane(new StockHealthAndBeauty());
//        js_p4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_p4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        js_p4.getViewport().setPreferredSize(new Dimension(1400, 700)); // Adjust the preferred size if needed
//        p4.add(js_p4, BorderLayout.CENTER);
//
//        
//        
//        // Create the Dta SAle ALL panels
// 
//        JPanel p5 = new JPanel(new BorderLayout()); // Use BorderLayout to manage components
//        p5.setBackground(Color.gray);
//        JLabel DataSaleAll_TitleLabel = new JLabel("Sale Total", JLabel.CENTER);
//        DataSaleAll_TitleLabel.setOpaque(true); 
//        DataSaleAll_TitleLabel.setBackground(Color.gray);
//        DataSaleAll_TitleLabel.setForeground(Color.cyan);
//        DataSaleAll_TitleLabel.setFont(boldFont);
//        p5.add(DataSaleAll_TitleLabel, BorderLayout.NORTH);
//        JScrollPane js_p5 = new JScrollPane(new DataSaleAll());
//        js_p5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        js_p5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        js_p5.getViewport().setPreferredSize(new Dimension(1400, 700)); // Adjust the preferred size if needed
//        p5.add(js_p5, BorderLayout.CENTER);
//
//        // Add the panels to the cardPanel with identifiers
//        cardPanel.add(p1, "HomeStock");
//        cardPanel.add(p2, "DrinkStock");
//        cardPanel.add(p3, "FoodStock");
//        cardPanel.add(p4, "HealthBeautyStock");
//        cardPanel.add(p5, "SaleView");
//
//        // Add mouse click listeners to the menu labels
//        jlb1.addMouseListener(createMenuMouseListener(() -> showPanel("HomeStock"), jlb1));
//        jlb2.addMouseListener(createMenuMouseListener(() -> showPanel("DrinkStock"), jlb2));
//        jlb3.addMouseListener(createMenuMouseListener(() -> showPanel("FoodStock"), jlb3));
//        jlb4.addMouseListener(createMenuMouseListener(() -> showPanel("HealthBeautyStock"), jlb4));
//        jlb5.addMouseListener(createMenuMouseListener(() -> showPanel("SaleView"), jlb5));
//
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//
//    private JLabel createMenuLabel(String text, Font font) {
//        JLabel label = new JLabel(text, JLabel.CENTER);
//        label.setFont(font);
//        label.setForeground(Color.BLACK);
//        label.setOpaque(true);
//        label.setBackground(Color.gray);
//        label.setPreferredSize(new Dimension(230, 130));
//        return label;
//    }
//
//    private MouseAdapter createMenuMouseListener(Runnable onClick, JLabel label) {
//        return new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                onClick.run();
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//               label.setForeground(Color.cyan);
//               label.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                label.setForeground(Color.BLACK);
//                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//
//            }
//        };
//    }
//
//    private void showPanel(String panelName) {
//        cardLayout.show(cardPanel, panelName);
//    }
//
//    static class RoundImagePanel extends JPanel {
//
//        private Image image;
//        private int imageWidth;
//        private int imageHeight;
//        private int xPos;
//        private int yPos;
//
//        public RoundImagePanel(String imagePath, int width, int height, int x, int y) {
//            setOpaque(false);
//            ImageIcon imageIcon = new ImageIcon(imagePath);
//            image = imageIcon.getImage();
//            imageWidth = width;
//            imageHeight = height;
//            xPos = x;
//            yPos = y;
//            image = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
//        }
//
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            if (image != null) {
//                int diameter = Math.min(imageWidth, imageHeight);
//                g.setClip(new Ellipse2D.Float(xPos, yPos, diameter, diameter));
//                g.drawImage(image, xPos, yPos, diameter, diameter, this);
//            }
//        }
//
//        @Override
//        public Dimension getPreferredSize() {
//            return new Dimension(imageWidth, imageHeight);
//        }
//    }
//
//    public static void main(String[] args) {
//        new RouteStock();
//    }
//}
