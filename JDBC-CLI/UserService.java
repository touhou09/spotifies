import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Random;


public class UserService {

    private static Scanner scanner = new Scanner(System.in);
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static User registerUser() throws IOException, ParseException, SQLException {
    	// 101 / user101 / User 101 / user101@example.com / password101 / Male / 2020-01-01 / Location 101 / DE
    	
        System.out.println("[Starting Registration..]");

        //System.out.print("Enter user ID: ");
        //String userID = reader.readLine();
        
        int userID2;
        while (true) {
            userID2 = generateRandomUserID();
            
            if (!userIDExists(userID2)) {
            	System.out.println("Success!! "+ userID2 +" make ");
                break;
            }else {
            	
            	System.out.println("FAIL "+ userID2 +" already exists ");
            }
        }
        String userID = String.valueOf(userID2);
        
        
        String userName;
        while (true) {
        	System.out.print("Enter user name: ");
        	userName = reader.readLine();
            
            if (!userNameExists(userName)) {
            	System.out.println("ID duplicate check completed!!");
                break;
            }else {
            	System.out.println("FAIL "+ userName +" already exists ");
            	
            }
        }
        
        
       
     
        System.out.print("Enter real name: ");
        //String realName = reader.readLine();
        String realName="cacao";
        System.out.print("Enter email: ");
        //String email = reader.readLine();
        String email = "cacao"+ userID +"@gmail.com";
        System.out.print("Enter password: ");
        //String password = reader.readLine();
        String password = "password"+userID;
        
        System.out.print("Enter gender: ");
        //String gender = reader.readLine();
        String gender = "Male";
        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dobStr = reader.readLine();
        Date dateOfBirth = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dobStr).getTime());

        System.out.print("Enter location: ");
        //String location = reader.readLine();
        String location="Location"+ userID;
        System.out.print("Enter nationality: ");
        //String nationality = reader.readLine();
        String nationality = "DE";

        User newUser = new User(userID, userName, realName, email, password, gender, dateOfBirth, location, nationality, false);

        String sql = "INSERT INTO Users (UserID, UserName, Real_Name, Email, Password, Gender, Date_Birth, Location, Nationality, SubscriptionID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, null)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUser.getUserID());
            pstmt.setString(2, newUser.getUserName());
            pstmt.setString(3, newUser.getRealName());
            pstmt.setString(4, newUser.getEmail());
            pstmt.setString(5, newUser.getPassword());
            pstmt.setString(6, newUser.getGender());
            pstmt.setDate(7, newUser.getDateOfBirth());
            pstmt.setString(8, newUser.getLocation());
            pstmt.setString(9, newUser.getNationality());
            //pstmt.setString(10, newUser.getUserID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LocalDate now = LocalDate.now();

        sql = "INSERT INTO Subscription (SubID, Subtype, Sub_Period, Sub_Startdate, Premium, Listening_Limit) " +
                "VALUES (?, 'Basic', 365, ?, 'No', 100)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUser.getUserID());
            pstmt.setDate(2, java.sql.Date.valueOf(now));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String updateUserInfoSQL = "UPDATE Users SET SubscriptionID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateUserInfoSQL)) {
        	pstmt.setString(1, newUser.getUserID());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Registered successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newUser;
    }
    
    private static int generateRandomUserID() {
        Random random = new Random();
        return random.nextInt(200);
    }
    
    private static boolean userIDExists(int userID) {
        String sql = "SELECT COUNT(*) FROM Users WHERE UserID = ?";
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
    
    
    private static boolean userNameExists(String userName) {
        String sql = "SELECT COUNT(*) FROM Users WHERE USERNAME = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
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
    
    
    public static Boolean checkAdmin(User user) {
    	String sql = "SELECT A.USERID" +
    	" FROM USERS A" +
    	" WHERE EXISTS" + 
    	" (SELECT 1" +
    	" FROM USERS U" +
    	" WHERE U.EMAIL LIKE '%spotify%'" +
    	" AND U.USERID = A.USERID)" +
    	" AND A.USERID = ?";
         try (Connection conn = DatabaseConnection.getConnection();
              PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	 pstmt.setString(1, user.getUserID());
             ResultSet rs = pstmt.executeQuery();
             if (rs.next()) return true;
         } catch (SQLException e) {
             e.printStackTrace();
         }
		return false;
    }
    
    
    public static User login() throws IOException, ParseException, SQLException {
    	
        while (true) {
        	
            System.out.print("Enter your userName(0 for registering): ");
            String userName = scanner.nextLine();
            
            
            if ("0".equals(userName)) {
                registerUser();
                continue; 
            }
            String sql = "SELECT * FROM Users WHERE USERNAME = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, userName);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    if (password.equals(rs.getString("Password"))) {
                        return new User(
                            rs.getString("UserID"), rs.getString("UserName"),
                            rs.getString("Real_Name"), rs.getString("Email"),
                            rs.getString("Password"), rs.getString("Gender"),
                            rs.getDate("Date_Birth"), rs.getString("Location"),
                            rs.getString("Nationality"),
                            false
                        );
                    } else {
                        System.out.println("Login failed: Incorrect password.");
                    }
                } else {
                    System.out.println("Login failed: User Name does not exist.");
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
   
}