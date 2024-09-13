package Layoutpage; 
 
import java.awt.Color; 
import java.awt.Font; 
import java.awt.GridLayout; 
import javax.swing.*;  
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent; 
 
public class Footer extends JPanel { 
 
    public Footer() { 
        setBackground(Color.gray); 
        setSize(1500, 180); 
        setLayout(null); 
 
        Font boldFont = new Font("Arial", Font.BOLD, 16); 
 
        // Create first panel with GridLayout for the first column of labels 
        JPanel jp1 = new JPanel(new GridLayout(4, 1)); 
        jp1.setBackground(Color.gray); 
        jp1.setBounds(133, 0, 179, 128); 
        addButtonsToPanel(jp1, "About Us", "Contact Us", "Terms & Conditions", "Privacy Policy"); 
 
        // Create second panel with GridLayout for the second column of labels 
        JPanel jp2 = new JPanel(new GridLayout(4, 1)); 
        jp2.setBackground(Color.gray); 
        jp2.setBounds(445, 0, 187, 128); 
        addButtonsToPanel(jp2, "Payment", "Delivery Information", "FAQ", "Refund Policies"); 
 
        // Create third panel with GridLayout for the third column of labels 
        JPanel jp3 = new JPanel(new GridLayout(4, 1)); 
        jp3.setBackground(Color.gray); 
        jp3.setBounds(765, 0, 390, 128); 
        addButtonsToPanel(jp3, "My Profile", "My Orders", "Address: Russian Federation BLVD(110) Phnom Penh", "Email: E1Y2@gmail.com"); 
 
        // Create fourth panel for the exit button 
        JPanel jp4 = new JPanel(); 
        jp4.setBounds(1288, 0, 211, 128); 
        jp4.setBackground(Color.gray); 
        JButton jbtn = new JButton("Exit"); 
        jbtn.setFont(boldFont); 
        jbtn.setBounds(1288, 90, 80, 30); 
        jbtn.addActionListener(e -> SwingUtilities.getWindowAncestor(this).dispose()); 
        jp4.add(jbtn); 
 
        // Add all panels to the footer 
        add(jp1); 
        add(jp2); 
        add(jp3); 
        add(jp4); 
 
        setVisible(true); 
    } 
 
    private void addButtonsToPanel(JPanel panel, String... buttonNames) { 
        for (String name : buttonNames) { 
            panel.add(createFooterButton(name)); 
        } 
    } 
 
    private JButton createFooterButton(String text) { 
        JButton button = new JButton(text); 
        button.setFont(new Font("Arial", Font.BOLD, 16)); 
        button.setContentAreaFilled(false); 
        button.setBorderPainted(false); 
        button.setFocusPainted(false); 
        button.setForeground(Color.BLACK); 
 
        // Add hover effect 
        button.addMouseListener(new MouseAdapter() { 
            @Override 
            public void mouseEntered(MouseEvent e) { 
                button.setForeground(Color.cyan); 
            } 
 
            @Override 
            public void mouseExited(MouseEvent e) { 
                button.setForeground(Color.BLACK); 
            } 
        }); 
 
        // Add action listener to show dialogs 
        button.addActionListener(e -> showDialog(text)); 
 
        return button; 
    } 
 
    private void showDialog(String text) { 
        String message; 
        String title = text; 
         
        switch (text) { 
            case "About Us": 
                message = "Welcome to RUPPE1.asia, your first online supermarket in Phnom-Penh!"; 
                break; 
            case "Contact Us": 
                message = "RUPP.asia\n\nRUPP #71A, Street 179, Phnom Penh Cambodia\nPhone: 017 274 701\nEmail: contact@RUPPE1.asia"; 
                break; 
            case "Terms & Conditions": 
                message = "By using the Delishop.asia (“Website”) you accept these terms and conditions."; 
                break; 
            case "Privacy Policy": 
                message = "By using the Delishop.asia (“Website”) you accept our Privacy Policy."; 
                break; 
            case "Payment": 
                message = "We offer the following payment options:\n• Cash on delivery\n• Online banking\n• Wing, Pi Pay, Pay&Go\n• ABA, ACLEDA, Sathapana";
                break; 
            case "Delivery Information": 
                message = "We offer delivery services from Monday to Saturday, 10 AM to 8 PM."; 
                break; 
            case "FAQ": 
                message = "FAQ:\n\nQ: How do I place an order?\nA: Browse, add to cart, checkout.\n\nQ: What are the delivery hours?\nA: Monday to Saturday, 10 AM to 8 PM."; 
                break; 
            case "Refund Policies": 
                message = "If not satisfied, request a refund within 7 days of delivery. Contact support@RUPPE1.asia."; 
                break; 
            default: 
                message = "Information not available."; 
        } 
 
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE); 
        JDialog dialog = optionPane.createDialog(title); 
        dialog.setLocationRelativeTo(null);  // Center the dialog 
        dialog.setVisible(true); 
    } 
}



