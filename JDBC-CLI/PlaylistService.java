import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;




public class PlaylistService {
    public void createTimeBasedPlaylist(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your target duration(s): ");
        int targetDuration = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine();

        targetDuration = targetDuration * 1000;

        List<String> songIDs = new ArrayList<>();
        int accumulatedDuration = 0;

        String getPlaylistSQL =
            " SELECT Songs.SongID, Songs.Title, art.Artist_Name, al.AlbumName, Songs.Duration " +
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
                    songIDs.add(rs.getString("SongID"));
                    System.out.println(rs.getString("Title") + " - " + rs.getString("Artist_Name") + " - " + rs.getString("AlbumName"));
                }
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        

        // PlaylistID 고정값 설정
        
        
        
        int playlistID;
        while (true) {
        	playlistID = generateRandomID();
            
            if (!IDExists(playlistID)) {
            	//System.out.println("Success!! "+ playlistID +" make ");
                break;
            }else {
            	//System.out.println("FAIL "+ playlistID +" already exists ");
            }
        }

        // PLAYLIST 테이블에 새 플레이리스트 삽입
        String insertPlaylistSQL = "INSERT INTO PLAYLIST (PLAYLISTID, PLAYLISTNAME, LIKEDCOUNT, USERID) VALUES (?, ?, 0, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertPlaylistSQL)) {
            pstmt.setInt(1, playlistID);
            pstmt.setString(2, playlistName);
            pstmt.setString(3, user.getUserID()); // User 클래스에서 userID 가져오는 메소드 가정
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // PLAYLISTMETA 테이블에 노래 정보 추가
        String insertPlaylistMetaSQL = "INSERT INTO PLAYLISTMETA (PLAYLISTID, SONGID) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertPlaylistMetaSQL)) {
            for (String songID : songIDs) {
                pstmt.setInt(1, playlistID);
                pstmt.setString(2, songID);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





	private int generateRandomID() {
	    Random random = new Random();
	    return random.nextInt(200);  
	}



	private boolean IDExists(int userID) {
	    String sql = "SELECT COUNT(*) FROM PLAYLIST WHERE playlistID = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, userID);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}



}






























/*
public class PlaylistService {
	public void createTimeBasedPlaylist(User user) {
		
		Scanner scanner = new Scanner(System.in);
		
		
		 System.out.print("Enter playlist name: ");
	        String playlistName = scanner.nextLine();
		

		System.out.print("Enter your target duration(s): ");
		 int targetDuration = scanner.nextInt();
        scanner.nextLine();
        
        
        targetDuration= targetDuration * 1000;
		
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
*/
