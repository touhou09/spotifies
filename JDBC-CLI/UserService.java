import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Random;



public class UserService {

    private static Scanner scanner = new Scanner(System.in);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public User registerUser() throws IOException, ParseException, SQLException {
    	// 101 / user101 / User 101 / user101@example.com / password101 / Male / 2020-01-01 / Location 101 / DE
    	
    	
    	
    	
        System.out.println("[Starting Registration..]");

        //System.out.print("Enter user ID: ");
        //String userID = reader.readLine();
        
        int userID2;
        while (true) {
            userID2 = generateRandomUserID();
            System.out.println("FAIL "+ userID2 +" already exists ");
            if (!userIDExists(userID2)) {
            	System.out.println("Success!! "+ userID2 +" make ");
                break;
            }
        }
        String userID = String.valueOf(userID2);
        
        
        System.out.print("Enter user name: ");
        String userName = reader.readLine();
     
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
        
        
        
        

        User newUser = new User(userID, userName, realName, email, password, gender, dateOfBirth, location, nationality);



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
    
    //�븘�씠�뵒 �옖�뜡 �깮�꽦
    private int generateRandomUserID() {
        Random random = new Random();
        return random.nextInt(200);  // 0�뿉�꽌 999999 �궗�씠�쓽 �궃�닔
    }
    
    
 // UserID媛� �씠誘� 議댁옱�븯�뒗吏� �솗�씤�븯�뒗 硫붿냼�뱶
    private boolean userIDExists(int userID) {
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
    
    
    
    
    
    
    public User login() throws IOException, ParseException, SQLException {
    	
        while (true) {
        	
            System.out.print("Enter your user ID(0 for registering): ");
            String userId = scanner.nextLine();
            if ("0".equals(userId)) {
                registerUser();
                continue; // �궗�슜�옄 �벑濡� �썑 �떎�떆 ID �엯�젰�쑝濡� �룎�븘媛�
            }
            String sql = "SELECT * FROM Users WHERE UserID = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, userId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    if (password.equals(rs.getString("Password"))) {
                        // 濡쒓렇�씤 �꽦怨� �떆 User 媛앹껜瑜� 諛섑솚�븯怨� 猷⑦봽 醫낅즺
                    	
                        return new User(
                            rs.getString("UserID"),
                            rs.getString("UserName"),
                            rs.getString("Real_Name"),
                            rs.getString("Email"),
                            rs.getString("Password"),
                            rs.getString("Gender"),
                            rs.getDate("Date_Birth"),
                            rs.getString("Location"),
                            rs.getString("Nationality")
                        );
                    } else {
                        System.out.println("Login failed: Incorrect password.");
                        // 鍮꾨�踰덊샇媛� ��由� 寃쎌슦 猷⑦봽 怨꾩냽
                    }
                } else {
                    System.out.println("Login failed: User ID does not exist.");
                    // ID媛� �뾾�뒗 寃쎌슦 猷⑦봽 怨꾩냽
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // scanner.close(); // 二쇱쓽: 硫붿냼�뱶 �궡�뿉�꽌 Scanner瑜� �떕�쑝硫� �떎瑜� 怨녹뿉�꽌 �궗�슜�븷 �닔 �뾾�쓬
        // return null; // while 猷⑦봽媛� 臾댄븳 猷⑦봽�씠誘�濡� �씠 遺�遺꾩� �룄�떖�븯吏� �븡�쓬
    }
    
    
    
    
    /*

    public User login() throws IOException, ParseException, SQLException {
    	
    
        System.out.print("Enter your user ID(0 for registering): ");
        String userId = scanner.nextLine();
        if ("0".equals(userId)) {
            registerUser();
            System.out.print("Enter your user ID(0 for registering): ");
            userId = scanner.nextLine();
        }
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            
            if (rs.next()) {
                // 占쏙옙占쏙옙米觀占쏙옙占� 占쏙옙橘占싫ｏ옙占� 占쌉력받쏙옙占싹댐옙.
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                // 占쏙옙占쏙옙占싶븝옙占싱쏙옙占쏙옙 占쏙옙占쏙옙占� 占쏙옙橘占싫ｏ옙占� 占쌉력뱄옙占쏙옙 占쏙옙橘占싫ｏ옙占� 占쏙옙占쌌니댐옙.
                if (password.equals(rs.getString("Password"))) {
                    // 占쏙옙橘占싫ｏ옙占� 占쏙옙치占싹몌옙 User 占쏙옙체占쏙옙 占쏙옙占쏙옙占싹울옙 占쏙옙환占쌌니댐옙.
                    return new User(
                        rs.getString("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Real_Name"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Gender"),
                        rs.getDate("Date_Birth"),
                        rs.getString("Location"),
                        rs.getString("Nationality")
                    );
                } else {
                    // 占쏙옙橘占싫ｏ옙占� 占쏙옙치占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占싸깍옙占쏙옙 占쏙옙占쏙옙 占쌨쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙爛求占�.
                    System.out.println("Login failed: Incorrect password.");
                }
            } else {
                // 占쏙옙占쏙옙占� ID占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占싸깍옙占쏙옙 占쏙옙占쏙옙 占쌨쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙爛求占�.
                System.out.println("Login failed: User ID does not exist.");

            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
        return null;
    }

    
    */
    
    
    
    
    
    
    
/*
    public User login() throws IOException, ParseException, SQLException {

        System.out.print("Enter your user ID(0 for registering): ");
        String userId = scanner.nextLine();
        if ("0".equals(userId)) {
            registerUser();
            System.out.print("Enter your user ID(0 for registering): ");
            userId = scanner.nextLine();
        }
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            // 占쏙옙占쏙옙占� ID占쏙옙 占쏙옙占쏙옙占싹몌옙 占쏙옙橘占싫ｏ옙占� 占쏙옙占쏙옙占쌌니댐옙.
            if (rs.next()) {
                // 占쏙옙占쏙옙米觀占쏙옙占� 占쏙옙橘占싫ｏ옙占� 占쌉력받쏙옙占싹댐옙.
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();

                // 占쏙옙占쏙옙占싶븝옙占싱쏙옙占쏙옙 占쏙옙占쏙옙占� 占쏙옙橘占싫ｏ옙占� 占쌉력뱄옙占쏙옙 占쏙옙橘占싫ｏ옙占� 占쏙옙占쌌니댐옙.
                if (password.equals(rs.getString("Password"))) {
                    // 占쏙옙橘占싫ｏ옙占� 占쏙옙치占싹몌옙 User 占쏙옙체占쏙옙 占쏙옙占쏙옙占싹울옙 占쏙옙환占쌌니댐옙.
                    return new User(
                        rs.getString("UserID"),
                        rs.getString("UserName"),
                        rs.getString("Real_Name"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Gender"),
                        rs.getDate("Date_Birth"),
                        rs.getString("Location"),
                        rs.getString("Nationality")
                    );
                } else {
                    // 占쏙옙橘占싫ｏ옙占� 占쏙옙치占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占싸깍옙占쏙옙 占쏙옙占쏙옙 占쌨쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙爛求占�.
                    System.out.println("Login failed: Incorrect password.");
                }
            } else {
                // 占쏙옙占쏙옙占� ID占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占싸깍옙占쏙옙 占쏙옙占쏙옙 占쌨쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙爛求占�.
                System.out.println("Login failed: User ID does not exist.");

            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
        return null;
    }
*/


}