import java.sql.*;

public class PlaylistService {
    public void createTimeBasedPlaylist(User user, int targetDuration) {
        String getPlaylistSQL = "SELECT Title, Artist_Name, AlbumName, SUM(Duration) as TotalDuration " +
                                "FROM (SELECT Title, Artist_Name, AlbumName, Duration FROM Songs ORDER BY DBMS_RANDOM.VALUE) " +
                                "WHERE ROWNUM <= 10 " + 
                                "GROUP BY Title, Artist_Name, AlbumName " +
                                "HAVING SUM(Duration) <= ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(getPlaylistSQL)) {
            
            pstmt.setInt(1, targetDuration);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Your Playlist:");
            while (rs.next()) {
                System.out.println("Title: " + rs.getString("Title") + 
                                   ", Artist: " + rs.getString("Artist_Name") + 
                                   ", Album: " + rs.getString("AlbumName") + 
                                   ", Duration: " + rs.getInt("TotalDuration"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
