package jv;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

import Layoutpage.Footer;
import Components.Slide;
import java.awt.Dimension;
import User.UiSaleHomePage;

public class HomePage extends JPanel {

    public HomePage() {
        // Set size of the panel
        setPreferredSize(new Dimension(1500, 1970));

     

 
        // UISale Panel
        JPanel jp3 = new JPanel(new BorderLayout());
        jp3.setPreferredSize(new Dimension(1500, 1800));

        // Add UISale component to jp3
        UiSaleHomePage uisale_home_page = new UiSaleHomePage();
        jp3.add(uisale_home_page, BorderLayout.CENTER);

        // Footer Panel
        JPanel jp4 = new JPanel(new BorderLayout());
        jp4.setBackground(Color.RED);
        jp4.setPreferredSize(new Dimension(1500, 130));

        // Add Footer component to jp4
        Footer footer = new Footer();
        jp4.add(footer, BorderLayout.CENTER);

        // Create a panel to hold jp2, jp3, and jp4
        JPanel jpBody = new JPanel();
        jpBody.setLayout(new BoxLayout(jpBody, BoxLayout.Y_AXIS));
        jpBody.setBackground(Color.WHITE);
      //  jpBody.add(jp2);
        jpBody.add(jp3);
        jpBody.add(jp4);


        setLayout(new BorderLayout());
        add(jpBody, BorderLayout.CENTER); // Add scrollPane to the center

        // Optionally set this JPanel to be visible (depends on the container/frame it's added to)
        setVisible(true);
    }

    public static void main(String[] args) {
       
       new HomePage();
       
    }
}

