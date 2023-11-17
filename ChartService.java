public class ChartService {
    public void displayTopSongs(String timeFrame) {
        String topSongsSQL = "SELECT Title FROM Songs ORDER BY PlayCount DESC FETCH FIRST 10 ROWS ONLY";
        // timeFrame을 사용하는 로직이 추가되어야 합니다.
        // 여기서 PlayCount는 노래의 재생 횟수를 나타내는 가상의 컬럼입니다.
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(topSongsSQL)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getString("Title"));
                }
            // ResultSet을 닫는 코드 필요
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 비슷한 메소드를 Albums와 Artists에 대해서도 구현합니다.
}
