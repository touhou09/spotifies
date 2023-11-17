import java.sql.*;

public class SearchService {
    public void search(String keyword) {
        String searchSQL = "SELECT 'Song' as Type, Title FROM Songs WHERE Title LIKE ? " +
                           "UNION ALL " +
                           "SELECT 'Album' as Type, AlbumName FROM Album WHERE AlbumName LIKE ? " +
                           "UNION ALL " +
                           "SELECT 'Artist' as Type, Artist_Name FROM Artist WHERE Artist_Name LIKE ?";

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
