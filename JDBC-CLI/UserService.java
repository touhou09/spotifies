import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.Scanner;
import java.time.LocalDate;

public class UserService {

    private static Scanner scanner = new Scanner(System.in);
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public User registerUser() throws IOException, ParseException, SQLException {
    	// 101 / user101 / User 101 / user101@example.com / password101 / Male / 2020-01-01 / Location 101 / DE
        System.out.println("[Starting Registration..]");

        System.out.print("Enter user ID: ");
        String userID = reader.readLine();

        System.out.print("Enter user name: ");
        String userName = reader.readLine();

        System.out.print("Enter real name: ");
        String realName = reader.readLine();

        System.out.print("Enter email: ");
        String email = reader.readLine();

        System.out.print("Enter password: ");
        String password = reader.readLine();

        System.out.print("Enter gender: ");
        String gender = reader.readLine();

        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dobStr = reader.readLine();
        Date dateOfBirth = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dobStr).getTime());

        System.out.print("Enter location: ");
        String location = reader.readLine();

        System.out.print("Enter nationality: ");
        String nationality = reader.readLine();

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
        sql = "INSERT INTO Subscription (SubID, Subtype, Sub_Period, Sub_Startdate, Premium, Listening_Limit, User_ID) " +
                "VALUES (?, 'Basic', 365, TO_DATE('" + now + "', 'YYYY-MM-DD'), 'No', 100, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUser.getUserID());
            pstmt.setString(2, newUser.getUserID());
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

            // 사용자 ID가 존재하면 비밀번호를 검증합니다.
            if (rs.next()) {
                // 사용자로부터 비밀번호를 입력받습니다.
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                
                // 데이터베이스에 저장된 비밀번호와 입력받은 비밀번호를 비교합니다.
                if (password.equals(rs.getString("Password"))) {
                    // 비밀번호가 일치하면 User 객체를 생성하여 반환합니다.
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
                    // 비밀번호가 일치하지 않으면 로그인 실패 메시지를 출력합니다.
                    System.out.println("Login failed: Incorrect password.");
                }
            } else {
                // 사용자 ID가 존재하지 않으면 로그인 실패 메시지를 출력합니다.
                System.out.println("Login failed: User ID does not exist.");
            }
            
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scanner.close();
        return null;
    }
}