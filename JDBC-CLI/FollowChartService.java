import java.sql.*;

public class FollowChartService {
    public static void displayTopFollowed() {

        String query = "SELECT A.Artist_Name, COUNT(F.UserID) AS FollowerCount " +
                "FROM Artist A " +
                "JOIN Followed F ON A.ArtistID = F.ArtistID " +
                "GROUP BY A.Artist_Name " +
                "ORDER BY COUNT(F.UserID) DESC " +
                "FETCH FIRST 10 ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String artistName = rs.getString("Artist_Name");
                int followerCount = rs.getInt("FollowerCount");
                System.out.println("Artist: " + artistName + ", Followers: " + followerCount);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
