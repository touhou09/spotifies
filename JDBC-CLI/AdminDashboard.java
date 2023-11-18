import java.sql.*;

public class AdminDashboard {
    public static void displayStats() {
        String userCountSQL = "SELECT COUNT(*) AS UserCount FROM Users";
        String songCountSQL = "SELECT COUNT(*) AS SongCount FROM Songs";
        String noAlbumSQL = "SELECT COUNT(*) AS ArtistCount" +
        		" FROM Artist" +
        		" WHERE ArtistID NOT IN (" +
        		" SELECT DISTINCT ArtistID" +
        		" FROM Featured_Artists_and_Producers)";
        String absentCountSQL = "SELECT COUNT(DISTINCT UserID) AS Absent_Users FROM ("
	        		+ "	SELECT UserID FROM Users WHERE UserID NOT IN"
	        			+ "	(SELECT UserID FROM Followed)"
	        		+ " UNION"
	        		+ "	SELECT UserID FROM Users WHERE UserID NOT IN"
	        			+ "	(SELECT UserID FROM Albums_Liked)"
	        		+ " UNION"
	        		+ "	SELECT UserID FROM Users WHERE UserID NOT IN"
	        			+ "	(SELECT UserID FROM Songs_Liked)"
        		+ "	)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement userStmt = conn.prepareStatement(userCountSQL);
        	PreparedStatement artStmt = conn.prepareStatement(noAlbumSQL);
            PreparedStatement songStmt = conn.prepareStatement(songCountSQL);
        	PreparedStatement absStmt = conn.prepareStatement(absentCountSQL)) {
                ResultSet userRs = userStmt.executeQuery();
                if (userRs.next())  System.out.println("UserCount: " + userRs.getInt("UserCount"));
                
                ResultSet songRs = songStmt.executeQuery();
                if (songRs.next()) System.out.println("SongCount: " + songRs.getInt("SongCount"));
                
                ResultSet artRs = artStmt.executeQuery();
                if (artRs.next()) System.out.println("ArtistsCount(with no album): " + artRs.getInt("ArtistCount"));
                
                ResultSet absRs = absStmt.executeQuery();
                if (absRs.next()) System.out.println("Absent Users: " + absRs.getInt("Absent_Users"));
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
