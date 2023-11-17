public class UserManager {
    public void displaySubscriptionInfo(User user) {
        // 임의로 할인을 계산합니다. 실제로는 사용 기간을 계산하고 할인율을 적용해야 합니다.
        String subscriptionInfoSQL = "SELECT SubType, SubStartDate, SYSDATE - SubStartDate AS SubscriptionDays " +
                                     "FROM Subscription WHERE UserID = ?";
        int discount = 10; // 임의의 할인율을 적용합니다.

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(subscriptionInfoSQL)) {
            
            pstmt.setInt(1, user.getUserId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int daysSubscribed = rs.getInt("SubscriptionDays");
                int discountAmount = discount * daysSubscribed; // 할인 금액 계산
                System.out.println("Subscription Type: " + rs.getString("SubType") + 
                                   ", Discounted Amount: " + discountAmount);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserInfo(User user, String newEmail, String newRealName) {
        String updateUserInfoSQL = "UPDATE Users SET Email = ?, RealName = ? WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateUserInfoSQL)) {
            
            pstmt.setString(1, newEmail);
            pstmt.setString(2, newRealName);
            pstmt.setInt(3, user.getUserId());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User information updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
