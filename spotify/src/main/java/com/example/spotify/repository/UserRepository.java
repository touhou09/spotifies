package com.example.spotify.repository;

import com.example.spotify.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        String sql = "INSERT INTO Users (UserName, Real_Name, Email, Password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserName(), user.getRealName(), user.getEmail(), user.getPassword());
        return user;
    }

    public User update(User user) {
        String sql = "UPDATE Users SET Real_Name = ?, Email = ?, Password = ? WHERE UserName = ?";
        jdbcTemplate.update(sql, user.getRealName(), user.getEmail(), user.getPassword(), user.getUserName());
        return user;
    }
}

