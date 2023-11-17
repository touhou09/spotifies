import java.sql.*;

public class AuthenticationService {
    public User login(int userId, String password) {
        String loginSQL = "SELECT UserID, UserName, UserType FROM Users WHERE UserID = ? AND Password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(loginSQL)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getString("UserID"), rs.getString("UserName"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
