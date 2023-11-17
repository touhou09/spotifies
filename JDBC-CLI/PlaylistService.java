public class PlaylistService {
    public void createTimeBasedPlaylist(User user, int targetDuration) {
        String getPlaylistSQL = "SELECT Title, Artist_Name, AlbumName, SUM(Duration) as TotalDuration " +
                                "FROM (SELECT Title, Artist_Name, AlbumName, Duration FROM Songs ORDER BY DBMS_RANDOM.VALUE) " +
                                "WHERE ROWNUM <= 10 " + // 임의로 10곡을 선택합니다. 실제로는 목표 시간에 맞게 조정해야 합니다.
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
            // Save playlist 로직 추가 필요
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
