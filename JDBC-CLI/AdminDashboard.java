public class AdminDashboard {
    public static void displayStats() {
        String userCountSQL = "SELECT COUNT(*) AS UserCount FROM Users";
        String songCountSQL = "SELECT COUNT(*) AS SongCount FROM Songs";
        // 여기서 더 많은 통계 SQL 쿼리를 추가할 수 있습니다.
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement userStmt = conn.prepareStatement(userCountSQL);
            PreparedStatement songStmt = conn.prepareStatement(songCountSQL)) {
                ResultSet userRs = userStmt.executeQuery();
                if (userRs.next()) {
                    System.out.println("사용자 수: " + userRs.getInt("UserCount"));
                }
                
                ResultSet songRs = songStmt.executeQuery();
                if (songRs.next()) {
                    System.out.println("음원 수: " + songRs.getInt("SongCount"));
                }
                // ResultSet을 닫는 코드 필요
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
