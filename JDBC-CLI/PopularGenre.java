import java.sql.*;

public class PopularGenre {
    public static void DisplayPopularGenre() {
            String query = "SELECT Genre, COUNT(ArtistID) AS ArtistCount "
                    + "FROM Artist "
                    + "WHERE Genre IS NOT NULL "
                    + "GROUP BY Genre "
                    + "ORDER BY ArtistCount DESC "
                    + "FETCH FIRST 3 ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String genre = rs.getString("Genre");
                int artistCount = rs.getInt("ArtistCount");
                
                
                String output = String.format("* %-41s *", genre + " with " + artistCount + " artists");
                System.out.println(output);
                
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
