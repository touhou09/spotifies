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

    public List<Map<String, Object>> getTopSongs(String viewName) {
        String createOrUpdateViewSQL = String.format(
                "CREATE OR REPLACE VIEW %s AS " +
                        "SELECT s.Title, COUNT(*) as LikeCount " +
                        "FROM Songs_Liked sl " +
                        "JOIN Songs s ON sl.SongID = s.SongID " +
                        "GROUP BY s.Title " +
                        "ORDER BY COUNT(*) DESC", viewName);

        String selectFromViewSQL = "SELECT Title, LikeCount FROM " + viewName + " FETCH FIRST 10 ROWS ONLY";

        jdbcTemplate.execute(createOrUpdateViewSQL);

        return jdbcTemplate.queryForList(selectFromViewSQL);
    }
}