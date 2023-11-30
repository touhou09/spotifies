package com.example.spotify.repository;

import com.example.spotify.model.Followed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowedRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Followed follow(Followed followed) {
        String sql = "INSERT INTO followed (ARTISTID, USERID) VALUES (?, ?)";
        jdbcTemplate.update(sql, followed.getArtistId(), followed.getUserId());
        return followed;
    }

    public int unfollow(int artistId, int userId) {
        String sql = "DELETE FROM followed WHERE ARTISTID = ? AND USERID = ?";
        return jdbcTemplate.update(sql, artistId, userId);
    }

    public List<Followed> findAllFollowed(int userId) {
        String sql = "SELECT * FROM followed WHERE USERID = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (resultSet, i) ->
                new Followed(resultSet.getInt("ARTISTID"), resultSet.getInt("USERID"))
        );
    }
}