//package jv;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class DataSaleAll extends JPanel {
//
//    private JPanel dropdownPanel;
//    private JPanel contentPanel;
//    private CardLayout cardLayout;
//    private JButton hamburgerButton;
//
//    public DataSaleAll() {
//        setSize(1400, 800);
//        setLayout(new BorderLayout());
//
//        // Initialize menu1 panel
//        JPanel menu1 = new JPanel();
//        menu1.setLayout(new BorderLayout());
//        menu1.setPreferredSize(new Dimension(200, getHeight())); 
//
//        // Initialize hamburger button
//        hamburgerButton = new JButton("☰ List Page"); 
//        hamburgerButton.setPreferredSize(new Dimension(50, 30));
//        hamburgerButton.setBackground(Color.LIGHT_GRAY);
//        hamburgerButton.setForeground(Color.black);
//        
//        //call funtion to set curserpointer on button hamburgerButton
//        mouseCursorPointer(hamburgerButton);
//        
//        // Initialize dropdown panel
//        dropdownPanel = createDropdownPanel();
//        dropdownPanel.setPreferredSize(new Dimension(200, 300)); // Adjust size as needed
//
//        // Add the hamburger button and dropdown panel to the menu1 panel
//        menu1.add(hamburgerButton, BorderLayout.NORTH);
//         // Place dropdownPanel below the button
//        menu1.add(dropdownPanel, BorderLayout.CENTER);
//
//        // Initialize content panels with CardLayout
//        cardLayout = new CardLayout();
//        contentPanel = new JPanel(cardLayout);
//        // Set size of content panels
//        contentPanel.setPreferredSize(new Dimension(1300, 800)); 
//
//        //  Create and add content panels
//        
//        // Add DataSaleHomePage panel
//        contentPanel.add(new DataSaleHomePage(), "DataSaleHomePage");
//          // Add DataSaleDrinkPage panel
//        contentPanel.add(new DataSaleDrinkPage(), "DataSaleDrinkPage");
//        
//        // Add DataSaleFoodPage panel
//        contentPanel.add(new DataSaleFoodPage(), "DataSaleFoodPage");
//
//        // Add DataSaleHealthAndBeautyPage panel
//        contentPanel.add(new DataSaleHealthAndBeautyPage(), "DataSaleHealthAndBeautyPage");
//
//      
//        // Add components to the frame
//        add(menu1, BorderLayout.WEST);
//        add(contentPanel, BorderLayout.CENTER); 
//
//        // Add action listener to the hamburger button
//        hamburgerButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                toggleDropdownMenu();
//            }
//        });
//
//        // Add action listeners to menu items
//        addDropdownMenuItemListeners();
//
//        setVisible(true);
//    }
//
//    // Method to create the dropdown menu panel
//    private JPanel createDropdownPanel() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBackground(Color.LIGHT_GRAY);
//        panel.setVisible(false); // Initially hidden
//
//        // Add menu items to the dropdown panel
//        panel.add(createMenuItem(" Home Page"));
//        panel.add(createMenuItem("Drink Page"));
//        panel.add(createMenuItem("Food Page"));
//        panel.add(createMenuItem("Health And Beauty Page"));
//
//
//        return panel;
//    }
//
//    // Method to create a menu item
//   private JButton createMenuItem(String text) {
//    JButton menuItem = new JButton(text);
//    menuItem.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to the left
//    menuItem.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Fill the width of the panel
//    menuItem.setBackground(Color.cyan);
//    menuItem.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//
//    // Set mouse pointer to hand cursor when hovering
//    menuItem.addMouseListener(new MouseAdapter() {
//        @Override
//        public void mouseEntered(MouseEvent e) {
//            menuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e) {
//            menuItem.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//        }
//    });
//
//    return menuItem;
//}
//private void mouseCursorPointer(JButton button) {
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
//    // Method to create a content panel with a specific color and label
//    private JPanel createContentPanel(Color color, String label) {
//        JPanel panel = new JPanel();
//        panel.setBackground(color);
//        panel.setLayout(new BorderLayout());
//        JLabel lbl = new JLabel(label, JLabel.CENTER);
//        lbl.setFont(new Font("Arial", Font.BOLD, 30));
//        lbl.setForeground(Color.black);
//        panel.add(lbl, BorderLayout.CENTER);
//        return panel;
//    }
//   
//
//
//    // Method to toggle the visibility of the dropdown panel
//    private void toggleDropdownMenu() {
//        dropdownPanel.setVisible(!dropdownPanel.isVisible());
//        revalidate(); // Revalidate the layout to update the display
//    }
//
//    // Method to add action listeners to menu items
//    private void addDropdownMenuItemListeners() {
//        JButton[] menuItems = {
//            (JButton) dropdownPanel.getComponent(0), // Item 1 DataSaleHomePage
//            (JButton) dropdownPanel.getComponent(1), // Item 2 DataSaleHealthAndBeautyPage
//            (JButton) dropdownPanel.getComponent(2), // Item 3 DataSaleDrinkPage
//            (JButton) dropdownPanel.getComponent(3) // Item 4 DataSaleFoodPage
//        };
//
//        for (int i = 0; i < menuItems.length; i++) {
//            int index = i;
//            menuItems[i].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    switch (index) {
//                        case 0:
//                            cardLayout.show(contentPanel, "DataSaleHomePage");
//                            break;
//                        case 1:
//                            cardLayout.show(contentPanel, "DataSaleDrinkPage");
//
//                            break;
//                        case 2:
//                           cardLayout.show(contentPanel, "DataSaleFoodPage");
//
//                            break;
//                        case 3:
//                             cardLayout.show(contentPanel, "DataSaleHealthAndBeautyPage");
//
//                            break;
//                    }
//                }
//            });
//        }
//    }
//
//    public static void main(String[] args) {
//        new DataSaleAll();
//    }
//}
