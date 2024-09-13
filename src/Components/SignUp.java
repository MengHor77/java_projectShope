//package Components;
//
//import java.awt.Color;
//import java.awt.Cursor;
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class SignUp extends JFrame implements ActionListener {
//
//    private JTextField jTextFieldUsername;
//    private JTextField jTextFieldEmail;
//    private JPasswordField jTextFieldPassword;
//    private JButton jButtonSign_in;
//    private JButton jButtonExit;
//    private JCheckBox jCheckBoxShowPassword;
//
//    // Database credentials
//    private static final String URL = "jdbc:mysql://localhost:3306/stock";
//    private static final String USER = "root";
//    private static final String PASSWORD = "";
//    private static Connection cn;
//
//    // Constructor
//    public SignUp() {
//       
//        // Initialize database connection
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            cn = DriverManager.getConnection(URL, USER, PASSWORD);
//
//            // Create table if it does not exist, with a primary key
//            String createTableSQL = "CREATE TABLE IF NOT EXISTS signup (" +
//                                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
//                                    "userName_sign VARCHAR(255), " +
//                                    "email_sign VARCHAR(255), " +
//                                    "password_sign VARCHAR(255))";
//
//            try (PreparedStatement pstmt = cn.prepareStatement(createTableSQL)) {
//                pstmt.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Failed to create or check the table!", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Failed to connect to the database!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//   
//        // Set up the JFrame
//        setTitle("Sign Up");
//        setSize(330, 220);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        // Initialize components
//        JPanel jPanel = new JPanel();
//        JLabel jLabelUsername = new JLabel("User Name");
//        JLabel jLabelEmail = new JLabel("Email");
//        JLabel jLabelPassword = new JLabel("Password");
//        jButtonSign_in = new JButton("Sign Up");
//        mouseCursorPointer(jButtonSign_in);
//        jTextFieldUsername = new JTextField(20);
//        jTextFieldEmail = new JTextField(20);
//        jTextFieldPassword = new JPasswordField(20);
//        jCheckBoxShowPassword = new JCheckBox("Show Password");
//        mouseCursorPointerJCheckBox(jCheckBoxShowPassword);
//        jButtonExit = new JButton("Exit");
//        mouseCursorPointer(jButtonExit);
//        
//        // Set button backgrounds
//        jButtonExit.setBackground(Color.cyan);
//        jButtonSign_in.setBackground(Color.cyan);
//
//        // Add components to panel
//        jPanel.add(jLabelUsername);
//        jPanel.add(jTextFieldUsername);
//        jPanel.add(jLabelEmail);
//        jPanel.add(jTextFieldEmail);
//        jPanel.add(jLabelPassword);
//        jPanel.add(jTextFieldPassword);
//        jPanel.add(jCheckBoxShowPassword);
//        jPanel.add(jButtonSign_in);
//        jPanel.add(jButtonExit);
//
//        // Add panel to frame
//        add(jPanel);
//
//        // Set layout (null layout)
//        jPanel.setLayout(null);
//
//        // Set bounds for components
//        jLabelUsername.setBounds(10, 10, 80, 25);
//        jTextFieldUsername.setBounds(100, 10, 160, 25);
//        jLabelEmail.setBounds(10, 40, 80, 25);
//        jTextFieldEmail.setBounds(100, 40, 160, 25);
//        jLabelPassword.setBounds(10, 70, 80, 25);
//        jTextFieldPassword.setBounds(100, 70, 160, 25);
//        jCheckBoxShowPassword.setBounds(100, 100, 160, 25);
//        jButtonSign_in.setBounds(180, 140, 80, 25);
//        jButtonExit.setBounds(10, 140, 70, 25);
//
//        // Add action listeners for the buttons
//        jButtonSign_in.addActionListener(this);
//        jButtonExit.addActionListener(e -> dispose());
//
//        // Toggle password visibility with checkbox
//        jCheckBoxShowPassword.addActionListener(e -> togglePasswordVisibility());
//
//        // Make frame visible
//        setVisible(true);
//    }
//     private void mouseCursorPointerJCheckBox(JCheckBox J_CheckBox) {
//        J_CheckBox.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent e) {
//                J_CheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
//            }
//
//            public void mouseExited(MouseEvent e) {
//                J_CheckBox.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//            }
//        });
//    }
//
//    private void mouseCursorPointer(JButton button) {
//        button.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent e) {
//                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
//            }
//
//            public void mouseExited(MouseEvent e) {
//                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//            }
//        });
//    }
//
//    // Toggle password visibility
//    private void togglePasswordVisibility() {
//        if (jCheckBoxShowPassword.isSelected()) {
//            jTextFieldPassword.setEchoChar((char) 0); // Show password
//        } else {
//            jTextFieldPassword.setEchoChar('*'); // Hide password
//        }
//    }
//
//    // Method for signing up
//    private void signUp() {
//        // Check if any of the fields are empty
//        if (jTextFieldUsername.getText().isEmpty() ||
//            jTextFieldPassword.getPassword().length == 0 ||
//            jTextFieldEmail.getText().isEmpty()) {
//
//            // Show a message indicating that all fields are required
//            JOptionPane.showMessageDialog(null, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
//        } else {
//            // Insert data into the database
//            String sql = "INSERT INTO signup (userName_sign, email_sign, password_sign) VALUES (?, ?, ?)";
//            try (PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
//                preparedStatement.setString(1, jTextFieldUsername.getText());
//                preparedStatement.setString(2, jTextFieldEmail.getText());
//                preparedStatement.setString(3, new String(jTextFieldPassword.getPassword()));
//
//                int rowsInserted = preparedStatement.executeUpdate();
//                if (rowsInserted > 0) {
//                    JOptionPane.showMessageDialog(null, "You are signed up successfully");
//                    dispose();  // Dispose of the current window
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Error inserting data into the database", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent ae) {
//        if (ae.getSource() == jButtonSign_in) {
//            signUp();
//        }
//    }
//
//    public static void main(String[] args) {
//        new SignUp();
//    }
//}
