public class SearchService {
    public ResultSet searchSongs(String keyword) {
        String searchSongsSQL = "SELECT * FROM Songs WHERE Title LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(searchSongsSQL)) {
            
                pstmt.setString(1, "%" + keyword + "%");
                return pstmt.executeQuery(); // 호출자가 ResultSet을 닫아야 합니다.
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }
    // 비슷한 메소드를 Albums와 Artists에 대해서도 구현합니다.
}
