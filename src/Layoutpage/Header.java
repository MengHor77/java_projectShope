//package Layoutpage;
//
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.GridLayout;
//import java.awt.Image;
//import jv.HomePage;
//import javax.swing.*;
//import jv.DrinkPage;
//import jv.FoodPage;
//import jv.HealthAndBeautyPage;
//import jv.AboutPage;
//import Components.LogIn;
//import Components.SignUp;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.geom.Ellipse2D;
//
//public class Header extends JPanel {
//
//    public Header() {
//        setBackground(Color.gray);
//        setPreferredSize(new Dimension(1500, 138));
//        setLayout(new BorderLayout());
//
//        // Create a panel for the logo
//        JPanel logoPanel = new JPanel();
//        logoPanel.setOpaque(false); // Make sure the background is transparent
//        logoPanel.setLayout(new BorderLayout());
//        
//        // Add the logo to the logo panel
//        RoundImagePanel logoImagePanel = new RoundImagePanel("src/Images/logo/logo.jpg", 100, 100, 0, 0);
//        logoPanel.add(logoImagePanel, BorderLayout.CENTER);
//        logoPanel.setPreferredSize(new Dimension(150, 100)); // Set a preferred size for the logo panel
//
//        // Create a panel for the menu labels
//        JPanel menuPanel = new JPanel(new GridLayout(1, 7));
//        menuPanel.setOpaque(false); // Make sure the background is transparent
//
//        Font boldFont = new Font("Arial", Font.BOLD, 18);
//        String[] labels = {"Home", "Drink", "Food", "Health & Beauty", "About", "Login", "Sign Up"};
//        
//        for (String label : labels) {
//            JLabel jLabel = new JLabel(label, JLabel.CENTER);
//            jLabel.setFont(boldFont);
//            jLabel.setForeground(Color.BLACK);
//            menuPanel.add(jLabel);
//
//            // Add mouse listener to change color on hover and call appropriate page
//            jLabel.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    switch (label) {
//                        case "Home":
//                            clickHomePage();
//                            break;
//                        case "Drink":
//                            clickDrinkPage();
//                            break;
//                        case "Food":
//                            clickFoodPage();
//                            break;
//                        case "Health & Beauty":
//                            clickHealthAndBeautyPage();
//                            break;
//                        case "About":
//                            clickAboutPage();
//                            break;
//                        case "Login":
//                            clickCreateFormLogin();
//                            break;
//                        case "Sign Up":
//                            clickSigUp();
//                            break;
//                    }
//                }
//
//                @Override
//                public void mouseEntered(MouseEvent e) {
//                    jLabel.setForeground(Color.cyan);
//                }
//
//                @Override
//                public void mouseExited(MouseEvent e) {
//                    jLabel.setForeground(Color.BLACK);
//                }
//            });
//        }
//
//        // Add components to the header
//        add(logoPanel, BorderLayout.WEST);
//        add(menuPanel, BorderLayout.CENTER);
//
//        setVisible(true);
//    }
//
//    public void clickHomePage() {
//        new HomePage();
//    }
//
//    public void clickDrinkPage() {
//        new DrinkPage();
//    }
//
//    public void clickFoodPage() {
//        new FoodPage();
//    }
//
//    public void clickHealthAndBeautyPage() {
//        new HealthAndBeautyPage();
//    }
//
//    public void clickAboutPage() {
//        new AboutPage();
//    }
//
//    public void clickCreateFormLogin() {
//        new LogIn();
//    }
//
//    public void clickSigUp() {
//        new SignUp();
//    }
//
//    static class RoundImagePanel extends JPanel {
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
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(new Header());
//        frame.pack();
//        frame.setVisible(true);
//    }
//}
