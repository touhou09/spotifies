import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class SearchService {
	
    private static Scanner scanner = new Scanner(System.in);
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public static void search() throws IOException {
        System.out.println("Select option for searching:");
        System.out.println("1. Song");
        System.out.println("2. Album");
        System.out.println("3. Artist");
        System.out.println("4. Genre - Artist");
        int searchOption = scanner.nextInt();
        
        String searchSQL = "";
        switch (searchOption) {
            case 1:
                searchSQL = "SELECT 'Song' as Type, Title FROM Songs WHERE Title LIKE ?";
                break;
            case 2:
                searchSQL = "SELECT 'Album' as Type, AlbumName FROM Album WHERE AlbumName LIKE ?";
                break;
            case 3:
                searchSQL = "SELECT 'Artist' as Type, Artist_Name FROM Artist WHERE Artist_Name LIKE ?";
                break;
            case 4:
                searchSQL = "SELECT inview.Artist_Name, inview.AlbumCount "
                        + "FROM ( "
                        + "SELECT A.Artist_Name, COUNT(Al.AlbumID) AS AlbumCount "
                        + "FROM Artist A "
                        + "JOIN Featured_Artists_and_Producers F ON A.ArtistID = F.ArtistID "
                        + "JOIN Album Al ON F.AlbumID = Al.AlbumID "
                        + "WHERE A.Genre LIKE ? "
                        + "GROUP BY A.Artist_Name) inview "
                        + "ORDER BY AlbumCount DESC "
                        + "FETCH FIRST 10 ROWS ONLY";
                break;
            default:
                System.out.println("Invalid Option.");
                return;
        }
        
    	System.out.print("Enter keyword to search: ");
        String keyword = reader.readLine();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(searchSQL)) {
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if(searchOption == 1) {
                    System.out.println(rs.getString("Type") + ": " + rs.getString("Title"));
                } else if (searchOption == 2) {
                    System.out.println(rs.getString("Type") + ": " + rs.getString("AlbumName"));
                } else if (searchOption == 3) {
                    System.out.println(rs.getString("Type") + ": " + rs.getString("Artist_Name"));
                } else if (searchOption == 4) {
                    System.out.println(rs.getString("Artist_Name") +" with albums of:" +
                            rs.getString("AlbumCount"));
                } else {
                    System.out.println("Invalid Option.");
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void adminSearch() throws IOException {

        System.out.println("Select option for searching:");
        System.out.println("1. Song");
        System.out.println("2. Album");
        System.out.println("3. Artist");
        System.out.println("4. Genre - Artist");
        System.out.println("5. User");

        int searchOption = scanner.nextInt();

        String searchSQL = "";

        switch (searchOption) {
            case 1:
                searchSQL = "SELECT 'Song' as Type, Title FROM Songs WHERE Title LIKE ?";
                break;
            case 2:
                searchSQL = "SELECT 'Album' as Type, AlbumName FROM Album WHERE AlbumName LIKE ?";
                break;
            case 3:
                searchSQL = "SELECT 'Artist' as Type, Artist_Name FROM Artist WHERE Artist_Name LIKE ?";
                break;
            case 4:
                searchSQL = "SELECT inview.Artist_Name, inview.AlbumCount "
                        + "FROM ( "
                        + "SELECT A.Artist_Name, COUNT(Al.AlbumID) AS AlbumCount "
                        + "FROM Artist A "
                        + "JOIN Featured_Artists_and_Producers F ON A.ArtistID = F.ArtistID "
                        + "JOIN Album Al ON F.AlbumID = Al.AlbumID "
                        + "WHERE A.Genre LIKE ? "
                        + "GROUP BY A.Artist_Name) inview "
                        + "ORDER BY AlbumCount DESC "
                        + "FETCH FIRST 10 ROWS ONLY";
                break;
            case 5:
                searchSQL = "SELECT * "
                        + "FROM USERS "
                        + "WHERE USERNAME LIKE ? "
                        + "or REAL_NAME LIKE ?";
                break;
            default:
                System.out.println("Invalid Option.");
                return;
        }
        
    	System.out.print("Enter keyword to search: ");
        String keyword = reader.readLine();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(searchSQL)) {

            if(searchOption == 5){
                pstmt.setString(1, "%" + keyword + "%");
                pstmt.setString(2, "%" + keyword + "%");
            } else {
                pstmt.setString(1, "%" + keyword + "%");
            }
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if(searchOption == 1) {
                    System.out.println(rs.getString("Type") + ": " + rs.getString("Title"));
                } else if (searchOption == 2) {
                    System.out.println(rs.getString("Type") + ": " + rs.getString("AlbumName"));
                } else if (searchOption == 3) {
                    System.out.println(rs.getString("Type") + ": " + rs.getString("Artist_Name"));
                } else if (searchOption == 4) {
                    System.out.println(rs.getString("Artist_Name") +" with albums of:" +
                            rs.getString("AlbumCount"));
                } else if (searchOption == 5) {
                    System.out.println("USERID: " + rs.getString("USERID"));
                    System.out.println("USERNAME: " + rs.getString("USERNAME"));
                    System.out.println("PASSWORD: " + rs.getString("PASSWORD"));
                    System.out.println("GENDER: " + rs.getString("GENDER"));
                    System.out.println("DATE_BIRTH: " + rs.getDate("DATE_BIRTH"));
                    System.out.println("EMAIL: " + rs.getString("EMAIL"));
                    System.out.println("REAL_NAME: " + rs.getString("REAL_NAME"));
                    System.out.println("LOCATION: " + rs.getString("LOCATION"));
                    System.out.println("NATIONALITY: " + rs.getString("NATIONALITY"));
                    System.out.println("SUBSCRIPTIONID: " + rs.getString("SUBSCRIPTIONID"));
                    System.out.println("-----------------------------------");
                } else {
                    System.out.println("Invalid Option.");
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}