//package jv;
//
//import Layoutpage.Footer;
//import Layoutpage.Header;
//import java.awt.BorderLayout;
//import java.awt.Color;
//import javax.swing.*;
//import Components.Foods;
//import java.awt.Dimension;
//import jv.UiSaleFoodPage;
//
//public class FoodPage extends JPanel { // Changed from JPanen to JPanel
//    public FoodPage() {
//        // Set the preferred size of the panel
//        setPreferredSize(new Dimension(1500, 2470));
//
//
//        // Body Panel with Foods component
//        JPanel jp2 = new JPanel(new BorderLayout());
//        jp2.setBackground(Color.BLUE);
//        jp2.setPreferredSize(new Dimension(1500, 500));
//
//        // Add Foods component to jp2
//        Foods foods = new Foods();
//        jp2.add(foods, BorderLayout.CENTER);
//
//        // UISale Panel
//        JPanel jp3 = new JPanel(new BorderLayout());
//        jp3.setPreferredSize(new Dimension(1500, 1800));
//
//        // Add UISale component to jp3
//        UiSaleFoodPage ui_sale_food_page = new UiSaleFoodPage();
//        jp3.add(ui_sale_food_page, BorderLayout.CENTER);
//
//        // Footer Panel
//        JPanel jp4 = new JPanel(new BorderLayout());
//        jp4.setBackground(Color.RED);
//        jp4.setPreferredSize(new Dimension(1500, 130));
//
//        // Add Footer component to jp4
//        Footer footer = new Footer();
//        jp4.add(footer, BorderLayout.CENTER);
//
//        // Create a panel to hold jp2, jp3, and jp4
//        JPanel jpBody = new JPanel();
//        jpBody.setLayout(new BoxLayout(jpBody, BoxLayout.Y_AXIS));
//        jpBody.setBackground(Color.WHITE);
//        jpBody.add(jp2);
//        jpBody.add(jp3);
//        jpBody.add(jp4);
//
//
//        // Set layout for this JPanel and add components
//        setLayout(new BorderLayout());
//        add(jpBody, BorderLayout.CENTER); // Add scrollPane to the center
//    }
//
//    public static void main(String[] args) {
//      
//        new FoodPage();
//       
//    }
//}
//
