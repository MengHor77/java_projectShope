//package jv;
//
//import Layoutpage.Footer;
//import Layoutpage.Header;
//import java.awt.BorderLayout;
//import java.awt.Color;
//import javax.swing.*;
//import java.awt.Dimension;
//import Components.HealthAndBeautys;
//import jv.UiSaleHealthAndBeautyPge;
//
//public class HealthAndBeautyPage extends JPanel {
//
//    public HealthAndBeautyPage() {
//        // Set the preferred size of the panel
//        setPreferredSize(new Dimension(1500, 2450));
//
//
//        // Health and Beauty panel
//        JPanel jp2 = new JPanel(new BorderLayout());
//        jp2.setBackground(Color.BLUE);
//        jp2.setPreferredSize(new Dimension(1500, 482));
//
//        HealthAndBeautys healthAndBeautys = new HealthAndBeautys();
//        jp2.add(healthAndBeautys, BorderLayout.CENTER);
//
//        // UISale panel
//        UiSaleHealthAndBeautyPge ui_sale_health_and_beauty = new UiSaleHealthAndBeautyPge();
//
//        JPanel jp3 = new JPanel(new BorderLayout());
//        jp3.setPreferredSize(new Dimension(1500, 1800));
//        jp3.add(ui_sale_health_and_beauty, BorderLayout.CENTER);
//
//        // Footer panel
//        JPanel jp4 = new JPanel(new BorderLayout());
//        jp4.setBackground(Color.RED);
//        jp4.setPreferredSize(new Dimension(1500, 130));
//
//        Footer footer = new Footer();
//        jp4.add(footer, BorderLayout.CENTER);
//
//        // Body panel to stack jp2, jp3, and jp4 vertically
//        JPanel jpbody = new JPanel();
//        jpbody.setLayout(new BoxLayout(jpbody, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
//        jpbody.add(jp2);
//        jpbody.add(jp3);
//        jpbody.add(jp4);
//
//
//        setLayout(new BorderLayout());
//        add(jpbody, BorderLayout.CENTER); // Add scrollPane to the center
//
//        // Optionally set this JPanel to be visible (depends on the container/frame it's added to)
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//      new HealthAndBeautyPage();
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
////import java.awt.Dimension;
////import Components.HealthAndBeautys;
////import jv.UiSaleHealthAndBeautyPge;
////
////public class HealthAndBeautyPage extends JFrame {
////    public HealthAndBeautyPage() {
////        setTitle("HealthAndBeauty");
////        setSize(1500, 800);
////
////        JPanel jp1 = new JPanel(new BorderLayout());
////        jp1.setBackground(Color.RED);
////        jp1.setPreferredSize(new Dimension(1500, 100));
////
////        // Header
////        Header header = new Header();
////        jp1.add(header, BorderLayout.CENTER);
////
////        JPanel jp2 = new JPanel(new BorderLayout());
////        jp2.setBackground(Color.BLUE);
////        jp2.setPreferredSize(new Dimension(1500, 482));
////
////        HealthAndBeautys healthAndBeautys = new HealthAndBeautys();
////        jp2.add(healthAndBeautys, BorderLayout.CENTER);
////
////        // UISale panel
////        UiSaleHealthAndBeautyPge ui_sale_health_and_beauty = new UiSaleHealthAndBeautyPge();
////
////        JPanel jp3 = new JPanel(new BorderLayout());
////        jp3.setPreferredSize(new Dimension(1500, 1800));
////        jp3.add(ui_sale_health_and_beauty, BorderLayout.CENTER);
////
////        // Footer panel
////        JPanel jp4 = new JPanel(new BorderLayout());
////        jp4.setBackground(Color.RED);
////        jp4.setPreferredSize(new Dimension(1500, 130));
////
////        Footer footer = new Footer();
////        jp4.add(footer, BorderLayout.CENTER);
////
////        // Create jpbody with BorderLayout to stack jp2 and jp3 vertically
////        JPanel jpbody = new JPanel();
////        jpbody.setLayout(new BoxLayout(jpbody, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
////        jpbody.add(jp2);
////        jpbody.add(jp3);
////        jpbody.add(jp4);
////
////        JScrollPane scrollPane = new JScrollPane(jpbody);
////        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
////
////        getContentPane().setLayout(new BorderLayout()); // Use BorderLayout for the frame's content pane
////
////        getContentPane().add(jp1, BorderLayout.NORTH); // Add jp1 to the top (north) of the frame
////        getContentPane().add(scrollPane, BorderLayout.CENTER); // Add scrollPane to fill the center of the frame
////
////        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////        setVisible(true);
////        setLocationRelativeTo(null); // Center the frame on the screen
////    }
////
////    public static void main(String[] args) {
////        new HealthAndBeautyPage();
////    }
////}
