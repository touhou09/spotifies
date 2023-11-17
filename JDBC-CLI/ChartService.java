public class ChartService {
    public void displayTopSongs(String timeFrame) {
        String viewName = "TopSongsView_" + timeFrame; // 예: TopSongsView_daily, TopSongsView_weekly 등
        String createOrUpdateViewSQL = String.format(
            "CREATE OR REPLACE VIEW %s AS " +
            "SELECT s.Title, COUNT(*) as LikeCount " +
            "FROM SongsLiked sl " +
            "JOIN Songs s ON sl.SongID = s.SongID " +
            "GROUP BY s.Title " +
            "ORDER BY COUNT(*) DESC", viewName);

        String selectFromViewSQL = "SELECT Title, LikeCount FROM " + viewName + " FETCH FIRST 10 ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // 뷰 생성 또는 업데이트
            stmt.execute(createOrUpdateViewSQL);

            // 뷰에서 데이터 조회
            ResultSet rs = stmt.executeQuery(selectFromViewSQL);
            while (rs.next()) {
                System.out.println(rs.getString("Title") + " - Likes: " + rs.getInt("LikeCount"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 비슷한 메소드를 Albums와 Artists에 대해서도 구현합니다.
}
