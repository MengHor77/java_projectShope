package Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Utility class for password hashing
class PasswordUtils {

    // Hashes the password using SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found", e);
        }
    }
}

public class SignUpAdmin extends JFrame implements ActionListener {

    private JTextField jTextFieldUsername;
    private JTextField jTextFieldEmail;
    private JPasswordField jTextFieldPassword;
    private JPasswordField jTextFieldConfirmPassword;
    private JButton jButtonSign_in;
    private JButton jButtonExit;
    private JCheckBox jCheckBoxShowPassword;

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/stock";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection cn;

    // Constructor
    public SignUpAdmin() {

        // Initialize database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Create table if it does not exist, with a primary key
            String createTableSQL = "CREATE TABLE IF NOT EXISTS admin ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "userName_sign VARCHAR(255), "
                    + "email_sign VARCHAR(255), "
                    + "password_sign VARCHAR(255),"
                    + "date_create TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

            try (PreparedStatement pstmt = cn.prepareStatement(createTableSQL)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to create or check the table!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set up the JFrame
        setTitle("Sign Upfor admin");
        setSize(330, 250); // Increased size for additional fields
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        JPanel jPanel = new JPanel();
        JLabel jLabelUsername = new JLabel("User Name");
        JLabel jLabelEmail = new JLabel("Email");
        JLabel jLabelPassword = new JLabel("Password");
        JLabel jLabelConfirmPassword = new JLabel("Confirm Password");
        jButtonSign_in = new JButton("Sign Up");
        mouseCursorPointer(jButtonSign_in);
        jTextFieldUsername = new JTextField(20);
        jTextFieldEmail = new JTextField(20);
        jTextFieldPassword = new JPasswordField(20);
        jTextFieldConfirmPassword = new JPasswordField(20);
        jCheckBoxShowPassword = new JCheckBox("Show Password");
        mouseCursorPointerJCheckBox(jCheckBoxShowPassword);
        jButtonExit = new JButton("Exit");
        mouseCursorPointer(jButtonExit);

        // Set button backgrounds
        jButtonExit.setBackground(Color.cyan);
        jButtonSign_in.setBackground(Color.cyan);

        // Add components to panel
        jPanel.add(jLabelUsername);
        jPanel.add(jTextFieldUsername);
        jPanel.add(jLabelEmail);
        jPanel.add(jTextFieldEmail);
        jPanel.add(jLabelPassword);
        jPanel.add(jTextFieldPassword);
        jPanel.add(jLabelConfirmPassword);
        jPanel.add(jTextFieldConfirmPassword);
        jPanel.add(jCheckBoxShowPassword);
        jPanel.add(jButtonSign_in);
        jPanel.add(jButtonExit);

        // Add panel to frame
        add(jPanel);

        // Set layout (null layout)
        jPanel.setLayout(null);

        // Set bounds for components
        jLabelUsername.setBounds(10, 10, 120, 25);
        jTextFieldUsername.setBounds(130, 10, 160, 25);
        jLabelEmail.setBounds(10, 40, 120, 25);
        jTextFieldEmail.setBounds(130, 40, 160, 25);
        jLabelPassword.setBounds(10, 70, 120, 25);
        jTextFieldPassword.setBounds(130, 70, 160, 25);
        jLabelConfirmPassword.setBounds(10, 100, 120, 25);
        jTextFieldConfirmPassword.setBounds(130, 100, 160, 25);
        jCheckBoxShowPassword.setBounds(130, 130, 160, 25);
        jButtonSign_in.setBounds(180, 170, 80, 25);
        jButtonExit.setBounds(10, 170, 70, 25);

        // Add action listeners for the buttons
        jButtonSign_in.addActionListener(this);
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

    // Method for signing up
    private void signUp() {
        String password = new String(jTextFieldPassword.getPassword());
        String confirmPassword = new String(jTextFieldConfirmPassword.getPassword());

        // Check if any of the fields are empty
        if (jTextFieldUsername.getText().isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()
                || jTextFieldEmail.getText().isEmpty()) {

            // Show a message indicating that all fields are required
            JOptionPane.showMessageDialog(null, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!password.equals(confirmPassword)) {
            // Show a message if passwords do not match
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!isPasswordValid(password)) {
            // Show a message if the password is not valid
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and contain upper case letters, lower case letters, and digits.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Hash the password
            String hashedPassword = PasswordUtils.hashPassword(password);

            // Insert data into the database
            String sql = "INSERT INTO admin (userName_sign, email_sign, password_sign) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
                preparedStatement.setString(1, jTextFieldUsername.getText());
                preparedStatement.setString(2, jTextFieldEmail.getText());
                preparedStatement.setString(3, hashedPassword);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "You are signed up successfully");
                    dispose();  // Dispose of the current window
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error inserting data into the database", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Validate password strength
    private boolean isPasswordValid(String password) {
        // Password should be at least 8 characters long, contain upper and lower case letters, and digits
        return password.length() >= 8
                && password.matches(".*[a-z].*")
                && password.matches(".*[A-Z].*")
                && password.matches(".*\\d.*");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jButtonSign_in) {
            signUp();
        }
    }

    public static void main(String[] args) {
        new SignUpAdmin();
    }
}
