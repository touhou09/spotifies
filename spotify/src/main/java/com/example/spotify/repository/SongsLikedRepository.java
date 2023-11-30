package com.example.spotify.repository;

import com.example.spotify.model.SongsLiked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SongsLikedRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SongsLiked likeSong(SongsLiked songsLiked) {
        String sql = "INSERT INTO songs_liked (SONGID, USERID) VALUES (?, ?)";
        jdbcTemplate.update(sql, songsLiked.getSongId(), songsLiked.getUserId());
        return songsLiked;
    }

    public int unlikeSong(int songId, int userId) {
        String sql = "DELETE FROM songs_liked WHERE SONGID = ? AND USERID = ?";
        return jdbcTemplate.update(sql, songId, userId);
    }

    public List<SongsLiked> findAllSongsLiked(int userId) {
        String sql = "SELECT * FROM songs_liked WHERE USERID = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (resultSet, i) ->
                new SongsLiked(resultSet.getInt("SONGID"), resultSet.getInt("USERID"))
        );
    }

}