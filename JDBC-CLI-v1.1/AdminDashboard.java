import java.sql.*;

public class AdminDashboard {
    public static void displayStats() {
        String userCountSQL = "SELECT COUNT(*) AS UserCount FROM Users";
        String songCountSQL = "SELECT COUNT(*) AS SongCount FROM Songs";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement userStmt = conn.prepareStatement(userCountSQL);
            PreparedStatement songStmt = conn.prepareStatement(songCountSQL)) {
                ResultSet userRs = userStmt.executeQuery();
                if (userRs.next()) {
                    System.out.println("UserCount: " + userRs.getInt("UserCount"));
                }
                
                ResultSet songRs = songStmt.executeQuery();
                if (songRs.next()) {
                    System.out.println("SongCount: " + songRs.getInt("SongCount"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
