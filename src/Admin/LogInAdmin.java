package Admin;

import Components.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.swing.*;


public class LogInAdmin extends JFrame implements ActionListener {

    private JTextField jTextFieldUsername;
    private JTextField jTextFieldEmail;
    private JPasswordField jTextFieldPassword;
    private JPasswordField jTextFieldConfirmPassword; // New Confirm Password field
    private JButton jButtonLogin;
    private JButton jButtonExit;
    private JCheckBox jCheckBoxShowPassword;
    private JLabel jlabel_for_got_password;
    private int error = 0;

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/stock";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection cn;

    // Constructor
    public LogInAdmin() {
        // Initialize database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set up the JFrame
        setTitle("Login Form for admin");
        setSize(330, 300); // Adjusted size for the additional field
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        JPanel jPanel = new JPanel();
        JLabel jLabelUsername = new JLabel("User Name");
        JLabel jLabelEmail = new JLabel("Email");
        JLabel jLabelPassword = new JLabel("Password");
        JLabel jLabelConfirmPassword = new JLabel("Confirm Password"); // New label
        jButtonLogin = new JButton("Login");
        mouseCursorPointer(jButtonLogin);
        jTextFieldUsername = new JTextField(20);
        jTextFieldEmail = new JTextField(20);
        jTextFieldPassword = new JPasswordField(20);
        jTextFieldConfirmPassword = new JPasswordField(20); // New field
        jCheckBoxShowPassword = new JCheckBox("Show Password");
        mouseCursorPointerJCheckBox(jCheckBoxShowPassword);
        jlabel_for_got_password = new JLabel("Forgot Password");
        mouseCursorPointerJLabel(jlabel_for_got_password);
        jButtonExit = new JButton("Exit");
        mouseCursorPointer(jButtonExit);

        // Set button backgrounds
        jButtonExit.setBackground(Color.cyan);
        jButtonLogin.setBackground(Color.cyan);

        // Add components to panel
        jPanel.add(jLabelUsername);
        jPanel.add(jTextFieldUsername);
        jPanel.add(jLabelEmail);
        jPanel.add(jTextFieldEmail);
        jPanel.add(jLabelPassword);
        jPanel.add(jTextFieldPassword);
        jPanel.add(jLabelConfirmPassword); // Add the new label
        jPanel.add(jTextFieldConfirmPassword); // Add the new field
        jPanel.add(jCheckBoxShowPassword);
        jPanel.add(jButtonLogin);
        jPanel.add(jButtonExit);
        jPanel.add(jlabel_for_got_password);

        // Add panel to frame
        add(jPanel);

        // Set layout (null layout)
        jPanel.setLayout(null);

        // Set bounds for components
        jLabelUsername.setBounds(10, 10, 80, 25);
        jTextFieldUsername.setBounds(140, 10, 160, 25);
        jLabelEmail.setBounds(10, 40, 80, 25);
        jTextFieldEmail.setBounds(140, 40, 160, 25);
        jLabelPassword.setBounds(10, 70, 80, 25);
        jTextFieldPassword.setBounds(140, 70, 160, 25);
        jLabelConfirmPassword.setBounds(10, 100, 120, 25); // Bounds for the new label
        jTextFieldConfirmPassword.setBounds(140, 100, 160, 25); // Bounds for the new field
        jCheckBoxShowPassword.setBounds(140, 130, 120, 25);
        jlabel_for_got_password.setBounds(140, 160, 120, 25); 
        jButtonLogin.setBounds(220, 200, 80, 25);
        jButtonExit.setBounds(10, 200, 70, 25);

        // Add action listeners for the buttons
        jButtonLogin.addActionListener(this);
        jButtonExit.addActionListener(e -> dispose());

        // Toggle password visibility with checkbox
        jCheckBoxShowPassword.addActionListener(e -> togglePasswordVisibility());

        // Make frame visible
        setVisible(true);
    }

    private void mouseCursorPointerJCheckBox(JCheckBox J_CheckBox) {
        J_CheckBox.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                J_CheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                J_CheckBox.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public void mouseCursorPointerJLabel(JLabel jlabel) {
        jlabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                jlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                jlabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            public void mouseClicked(MouseEvent e) {

            }
        });
    }

    private void mouseCursorPointer(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    // Toggle password visibility
    private void togglePasswordVisibility() {
        if (jCheckBoxShowPassword.isSelected()) {
            jTextFieldPassword.setEchoChar((char) 0); // Show password
            jTextFieldConfirmPassword.setEchoChar((char) 0); // Show confirm password
        } else {
            jTextFieldPassword.setEchoChar('*'); // Hide password
            jTextFieldConfirmPassword.setEchoChar('*'); // Hide confirm password
        }
    }

    // Function to handle login
    private void login() {
        String username = jTextFieldUsername.getText();
        String email = jTextFieldEmail.getText();
        String password = new String(jTextFieldPassword.getPassword());
        String confirmPassword = new String(jTextFieldConfirmPassword.getPassword()); // New field

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM admin WHERE userName_sign = ? AND email_sign = ? AND password_sign = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, hashPassword(password)); // Hashing the entered password

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "You are Logged In successfully");
                dispose();

                // Create and display StockHome frame
                SwingUtilities.invokeLater(() -> {
                    JFrame stockHomeFrame = new JFrame("Stock Home");
                    stockHomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    stockHomeFrame.setSize(1500, 700);
                    stockHomeFrame.add(new StockHome());
                    stockHomeFrame.setVisible(true);
                });
            } else {
                if (error < 2) {
                    error += 1;
                    JOptionPane.showMessageDialog(null, "Wrong account");
                } else {
                    jTextFieldPassword.setEnabled(false);
                    jTextFieldConfirmPassword.setEnabled(false);
                    jTextFieldUsername.setEnabled(false);
                    jTextFieldEmail.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Max attempts reached");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to hash the password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jButtonLogin) {
            login();
        }
    }

    // Additional methods (forgot password logic, etc.) would go here

    public static void main(String[] args) {
        new LogInAdmin();
    }
}
