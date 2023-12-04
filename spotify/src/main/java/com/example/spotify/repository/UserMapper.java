package com.example.spotify.repository;

import com.example.spotify.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserID(rs.getString("UserID"));
        user.setUserName(rs.getString("UserName"));
        user.setRealName(rs.getString("Real_Name"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setGender(rs.getString("Gender"));
        user.setDateOfBirth(rs.getDate("Date_Birth"));
        user.setLocation(rs.getString("Location"));
        user.setNationality(rs.getString("Nationality"));
        // Set other fields as necessary
        return user;
    }

    static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserID(rs.getString("UserID"));
            user.setUserName(rs.getString("UserName"));
            user.setRealName(rs.getString("Real_Name"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("Password"));
            user.setGender(rs.getString("Gender"));
            user.setDateOfBirth(rs.getDate("Date_Birth"));
            user.setLocation(rs.getString("Location"));
            user.setNationality(rs.getString("Nationality"));
            user.setAdmin(rs.getString("SUBSCRIPTIONID"));
            return user;
        }
    }
}

