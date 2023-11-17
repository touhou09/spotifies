public class SearchService {
    // 여기서는 실제 검색 로직만 포함되어 있으며, 사용자 입력을 받고 키를 통해 결과를 전환하는 UI 로직은 제외되어 있습니다.
    public void search(String keyword) {
        String searchSQL = "SELECT 'Song' as Type, Title FROM Songs WHERE Title LIKE ? " +
                           "UNION ALL " +
                           "SELECT 'Album' as Type, AlbumName FROM Albums WHERE AlbumName LIKE ? " +
                           "UNION ALL " +
                           "SELECT 'Artist' as Type, Artist_Name FROM Artists WHERE Artist_Name LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(searchSQL)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            pstmt.setString(3, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("Type") + ": " + rs.getString("Title"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
