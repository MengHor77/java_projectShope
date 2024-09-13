//package jv;
//
//import Layoutpage.Footer;
//import Layoutpage.Header;
//import java.awt.BorderLayout;
//import java.awt.Color;
//import javax.swing.*;
//import Components.Drinks;
//import java.awt.Dimension;
//
//import jv.UiSaleDrinkPage;
//
//public class DrinkPage extends JPanel {
//
//    public DrinkPage() {
//        // Set the size of the panel
//        setPreferredSize(new Dimension(1500, 2450));
//
//
//        // Drinks panel
//        JPanel jp2 = new JPanel(new BorderLayout());
//        jp2.setBackground(Color.BLUE);
//        jp2.setPreferredSize(new Dimension(1500, 480));
//
//        Drinks drinks = new Drinks();
//        jp2.add(drinks, BorderLayout.CENTER);
//
//        // UISale panel
//        JPanel jp3 = new JPanel(new BorderLayout());
//        jp3.setPreferredSize(new Dimension(1500, 1800));
//        UiSaleDrinkPage ui_sale_drink_page = new UiSaleDrinkPage();
//        jp3.add(ui_sale_drink_page, BorderLayout.CENTER);
//
//        // Footer panel
//        JPanel jp4 = new JPanel(new BorderLayout());
//        jp4.setBackground(Color.RED);
//        jp4.setPreferredSize(new Dimension(1500, 130)); // Adjust preferred size
//
//        Footer footer = new Footer();
//        jp4.add(footer, BorderLayout.CENTER);
//
//        // Body panel to stack jp2 and jp3 vertically
//        JPanel jpbody = new JPanel();
//        jpbody.setLayout(new BoxLayout(jpbody, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
//        jpbody.add(jp2);
//        jpbody.add(jp3);
//        jpbody.add(jp4);
//
//  
//        // Set layout for this JPanel and add components
//        setLayout(new BorderLayout());
//        add(jpbody, BorderLayout.CENTER); // Add scrollPane to the center
//
//        // Optionally set this JPanel to be visible (depends on the container/frame it's added to)
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//      
//      new DrinkPage();
//       
//    }
//}
//
//
////package jv;
////
////import Layoutpage.Footer;
////import Layoutpage.Header;
////import java.awt.BorderLayout;
////import java.awt.Color;
////import javax.swing.*;
////import Components.Drinks;
////import java.awt.Dimension;
////
////import jv.UiSaleDrinkPage;
////
////public class DrinkPage extends JFrame {
////
////    public DrinkPage() {
////        setTitle("DrinkPage");
////        setSize(1500, 800);
////
////        // Header panel
////        JPanel jp1 = new JPanel(new BorderLayout());
////        jp1.setBackground(Color.RED);
////        jp1.setPreferredSize(new Dimension(1500, 100)); // Adjust preferred size
////
////        Header header = new Header();
////        jp1.add(header, BorderLayout.CENTER);
////
////        // Drinks panel
////        JPanel jp2 = new JPanel(new BorderLayout());
////        jp2.setBackground(Color.BLUE);
////        jp2.setPreferredSize(new Dimension(1500, 480));
////
////        Drinks drinks = new Drinks();
////        jp2.add(drinks, BorderLayout.CENTER);
////
////        // UISale panel
////        JPanel jp3 = new JPanel(new BorderLayout());
////        jp3.setPreferredSize(new Dimension(1500, 1800));
////        UiSaleDrinkPage ui_sale_drink_page = new UiSaleDrinkPage();
////        jp3.add(ui_sale_drink_page, BorderLayout.CENTER);
////
////        // Footer panel
////        JPanel jp4 = new JPanel(new BorderLayout());
////        jp4.setBackground(Color.RED);
////        jp4.setPreferredSize(new Dimension(1500, 130)); // Adjust preferred size
////
////        Footer footer = new Footer();
////        jp4.add(footer, BorderLayout.CENTER);
////
////        // Body panel to stack jp2 and jp3 vertically
////        JPanel jpbody = new JPanel();
////        jpbody.setLayout(new BoxLayout(jpbody, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
////        jpbody.add(jp2);
////        jpbody.add(jp3);
////        jpbody.add(jp4);
////
////        // JScrollPane to wrap the body panel
////        JScrollPane scrollPane = new JScrollPane(jpbody);
////        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
////
////        // Add panels to the frame
////        getContentPane().setLayout(new BorderLayout());
////        getContentPane().add(jp1, BorderLayout.NORTH); // Add header to the top
////        getContentPane().add(scrollPane, BorderLayout.CENTER); // Add scrollPane to the center
////
////        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////        setVisible(true);
////        setLocationRelativeTo(null); // Center the frame on the screen
////    }
////
////    public static void main(String[] args) {
////        new DrinkPage();
////    }
////}
