public class PlaylistService {
    public void createTimeBasedPlaylist(User user, int duration) {
        // 이 메소드는 주어진 시간에 따라 플레이리스트를 생성합니다.
        // 예를 들어, 아래 쿼리는 플레이리스트에 추가할 노래들을 임의로 선택합니다.
        String createPlaylistSQL = "SELECT * FROM Songs WHERE Duration <= ? ORDER BY DBMS_RANDOM.VALUE FETCH FIRST 10 ROWS ONLY";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(createPlaylistSQL)) {
                
                pstmt.setInt(1, duration);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    // 플레이리스트에 노래 추가 로직
                }
                // ResultSet을 닫는 코드 필요
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
