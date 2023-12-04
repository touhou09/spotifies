package com.example.spotify.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ChartRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> getTopSongs() {
        String selectTopSongsSQL = "SELECT Title, LikeCount FROM " +
                "(SELECT s.Title, COUNT(*) as LikeCount " +
                "FROM Songs_Liked sl " +
                "JOIN Songs s ON sl.SongID = s.SongID " +
                "GROUP BY s.Title " +
                "ORDER BY COUNT(*) DESC) " +
                "FETCH FIRST 10 ROWS ONLY";

        return jdbcTemplate.queryForList(selectTopSongsSQL);
    }


    public List<Map<String, Object>> getTopFollowed() {
        String query = "SELECT A.Artist_Name, COUNT(F.UserID) AS FollowerCount " +
                "FROM Artist A " +
                "JOIN Followed F ON A.ArtistID = F.ArtistID " +
                "GROUP BY A.Artist_Name " +
                "ORDER BY COUNT(F.UserID) DESC " +
                "FETCH FIRST 10 ROWS ONLY";

        return jdbcTemplate.queryForList(query);
    }
}
