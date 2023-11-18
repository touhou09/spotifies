import java.sql.*;

public class ChartService {
    public static void displayTopSongs(String timeFrame) {
        String viewName = "TopSongsView_" + timeFrame;
        String createOrUpdateViewSQL = String.format(
            "CREATE OR REPLACE VIEW %s AS " +
            "SELECT s.Title, COUNT(*) as LikeCount " +
            "FROM Songs_Liked sl " +
            "JOIN Songs s ON sl.SongID = s.SongID " +
            "GROUP BY s.Title " +
            "ORDER BY COUNT(*) DESC", viewName);

        String selectFromViewSQL = "SELECT Title, LikeCount FROM " + viewName + " FETCH FIRST 10 ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createOrUpdateViewSQL);

            ResultSet rs = stmt.executeQuery(selectFromViewSQL);
            while (rs.next()) {
                System.out.println(rs.getString("Title") + " - Likes: " + rs.getInt("LikeCount"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

