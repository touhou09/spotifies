public class UserManager {
    public void applyDiscount(User user) {
        // 사용자의 사용 기간에 따라 할인율을 적용합니다.
        String applyDiscountSQL = "UPDATE Subscription SET DiscountRate = ? WHERE UserID = ?";
        // 할인 로직 구현
    }

    public void updateUserInformation(User user) {
        String updateUserInfoSQL = "UPDATE Users SET Email = ?, RealName = ? WHERE UserID = ?";
        // 사용자 정보 업데이트 로직 구현
    }
}