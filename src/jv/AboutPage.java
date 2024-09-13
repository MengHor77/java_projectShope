package jv;

import Layoutpage.Footer;
import Layoutpage.Header;
import Components.Abouts;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.Dimension;

public class AboutPage extends JPanel {

    public AboutPage() {
        // Set the preferred size of the panel
        setPreferredSize(new Dimension(1500, 800));


        JPanel jp2 = new JPanel(new BorderLayout());
        jp2.setBackground(Color.BLUE);
        jp2.setPreferredSize(new Dimension(1500, 482));
        
        // Add Abouts component to jp2
        Abouts abouts = new Abouts();
        jp2.add(abouts);

        JPanel jp3 = new JPanel(new BorderLayout());
        jp3.setBackground(Color.RED);
        jp3.setPreferredSize(new Dimension(1500, 130));

        // Add Footer component to jp3
        Footer footer = new Footer();
        jp3.add(footer, BorderLayout.CENTER);

        // Create jpBody with BorderLayout to stack jp2 and jp3 vertically
        JPanel jpBody = new JPanel(new BorderLayout());
        jpBody.setPreferredSize(new Dimension(1500, 862));
        jpBody.add(jp2, BorderLayout.CENTER);
        jpBody.add(jp3, BorderLayout.SOUTH);

        // Set layout for this JPanel and add components
        setLayout(new BorderLayout());
    
        add(jpBody, BorderLayout.CENTER); // Add scrollPane to the center
    }

    public static void main(String[] args) {
     
        new AboutPage();
       
    }
}
