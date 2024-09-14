package User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class GetUserName {
    private static final String URL = "jdbc:mysql://localhost:3306/stock";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static List<String> getUsernames() {
        List<String> usernames = new ArrayList<>();
        String query = "SELECT userName_sign FROM user";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                usernames.add(rs.getString("userName_sign"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching usernames from the database", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return usernames;
    }

    public static int getUserIdFromUsername(String username) {
        int userId = -1;
        String query = "SELECT id FROM user WHERE userName_sign = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching user ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return userId;
    }
}
