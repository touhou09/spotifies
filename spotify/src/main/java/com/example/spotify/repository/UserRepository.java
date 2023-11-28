package com.example.spotify.repository;

import com.example.spotify.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Random;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByUsername(String userName) {
        String sql = "SELECT * FROM Users WHERE UserName = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new UserMapper());
    }

    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE UserName = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    public User save(User user) {
        String sql = "INSERT INTO Users (UserID, UserName, Real_Name, Email, Password, Gender, Date_Birth) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int randomId = generateRandomID();
        jdbcTemplate.update(sql, randomId, user.getUserName(), user.getRealName(), user.getEmail(), user.getPassword(), user.getGender(), user.getDateOfBirth());
        user.setUserID(String.valueOf(randomId));
        return user;
    }

    private int generateRandomID() {
        Random random = new Random();
        return random.nextInt(1000000000);
    }


    public User update(User user) {
        String sql = "UPDATE Users SET Real_Name = ?, Email = ?, Password = ? WHERE UserName = ?";
        jdbcTemplate.update(sql, user.getRealName(), user.getEmail(), user.getPassword(), user.getUserName());
        return user;
    }

    public User getUser(String userId) {
        String sql = "SELECT * FROM Users WHERE UserId = ?";
        Map<String, Object> userResult = jdbcTemplate.queryForMap(sql, userId);

        User user = new User();

        user.setRealName((String) userResult.get("real_Name"));
        user.setGender((String) userResult.get("gender"));
        user.setEmail((String) userResult.get(("email")));
        user.setNationality((String) userResult.get("nationality"));
        user.setLocation((String) userResult.get("location"));

        Timestamp dateOfBirth = (Timestamp) userResult.get("date_birth");
        user.setDateOfBirth(new Date(dateOfBirth.getTime()));

        return user;
    }
}

