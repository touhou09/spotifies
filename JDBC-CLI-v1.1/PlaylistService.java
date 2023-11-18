import java.sql.*;
import java.util.*;

public class PlaylistService {
	public void createTimeBasedPlaylist(User user, int targetDuration) {
	    List<String> playlist = new ArrayList<>();
	    int accumulatedDuration = 0;

	    String getPlaylistSQL =
	        " SELECT Songs.Title, art.Artist_Name, al.AlbumName, Songs.Duration " +
	        " FROM Songs SAMPLE (0.01)" +
	        " JOIN Album al ON Songs.AlbumID = al.AlbumID " +
	        " JOIN Featured_Artists_and_Producers fap ON al.AlbumID = fap.AlbumID " +
	        " JOIN Artist art ON fap.ArtistID = art.ArtistID";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(getPlaylistSQL)) {
	            
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next() && accumulatedDuration <= targetDuration) {
	            int duration = rs.getInt("Duration");
	            if (accumulatedDuration + duration <= targetDuration) {
	                accumulatedDuration += duration;
	                playlist.add(rs.getString("Title") + " - " + rs.getString("Artist_Name") + " - " + rs.getString("AlbumName"));
	            }
	        }
	        rs.close();

	        System.out.println("Your Playlist (Total Duration: " + accumulatedDuration + " seconds):");
	        for (String song : playlist) {
	            System.out.println(song);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

   }
