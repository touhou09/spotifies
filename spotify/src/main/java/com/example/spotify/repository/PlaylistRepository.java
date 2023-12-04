package com.example.spotify.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class PlaylistRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlaylistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTimeBasedPlaylist(int targetDuration, String playlistName, String userId) {
        targetDuration = targetDuration * 1000;

        List<String> songIDs = new ArrayList<>();
        int accumulatedDuration = 0;

        String getPlaylistSQL =
                " SELECT Songs.SongID, Songs.Title, art.Artist_Name, al.AlbumName, Songs.Duration " +
                        " FROM Songs SAMPLE (0.01)" +
                        " JOIN Album al ON Songs.AlbumID = al.AlbumID " +
                        " JOIN Featured_Artists_and_Producers fap ON al.AlbumID = fap.AlbumID " +
                        " JOIN Artist art ON fap.ArtistID = art.ArtistID";

        List<Map<String, Object>> songs = jdbcTemplate.queryForList(getPlaylistSQL);




        for (Map<String, Object> song : songs) {
            BigDecimal durationBigDecimal = (BigDecimal) song.get("Duration");
            int duration = durationBigDecimal.intValue();
            if (accumulatedDuration + duration <= targetDuration) {
                accumulatedDuration += duration;
                songIDs.add((String) song.get("SongID"));
            } else {
                break;
            }
        }
        /*
        for (Map<String, Object> song : songs) {
            int duration = (Integer) song.get("Duration");
            if (accumulatedDuration + duration <= targetDuration) {
                accumulatedDuration += duration;
                songIDs.add((String) song.get("SongID"));
            } else {
                break;
            }
        }*/

        int playlistID;
        while (true) {
            playlistID = generateRandomID();
            if (!IDExists(playlistID)) {
                break;
            }
        }

        String insertPlaylistSQL = "INSERT INTO PLAYLIST (PLAYLISTID, PLAYLISTNAME, LIKEDCOUNT, USERID) VALUES (?, ?, 0, ?)";
        jdbcTemplate.update(insertPlaylistSQL, playlistID, playlistName, userId);

        String insertPlaylistMetaSQL = "INSERT INTO PLAYLISTMETA (PLAYLISTID, SONGID) VALUES (?, ?)";
        for (String songID : songIDs) {
            jdbcTemplate.update(insertPlaylistMetaSQL, playlistID, songID);
        }
    }

    public void createEmptyPlaylist(String playlistName, String userId) {
        int playlistID;
        while (true) {
            playlistID = generateRandomID();
            if (!IDExists(playlistID)) {
                break;
            }
        }

        String insertPlaylistSQL = "INSERT INTO PLAYLIST (PLAYLISTID, PLAYLISTNAME, LIKEDCOUNT, USERID) VALUES (?, ?, 0, ?)";
        jdbcTemplate.update(insertPlaylistSQL, playlistID, playlistName, userId);
    }

    public void deletePlaylist(int playlistId) {

        String deletePlaylistMetaSQL = "DELETE FROM PLAYLISTMETA WHERE PLAYLISTID = ?";
        jdbcTemplate.update(deletePlaylistMetaSQL, playlistId);

        String deletePlaylistSQL = "DELETE FROM PLAYLIST WHERE PLAYLISTID = ?";
        jdbcTemplate.update(deletePlaylistSQL, playlistId);
    }

    public int incrementLikedCount(int playlistId) {
        String sql = "UPDATE playlist SET LIKEDCOUNT = LIKEDCOUNT + 1 WHERE PLAYLISTID = ?";
        return jdbcTemplate.update(sql, playlistId);
    }

    public int decrementLikedCount(int playlistId) {
        String sql = "UPDATE playlist SET LIKEDCOUNT = LIKEDCOUNT - 1 WHERE PLAYLISTID = ?";
        return jdbcTemplate.update(sql, playlistId);
    }

    // time-based playlist methods

    private int generateRandomID() {
        Random random = new Random();
        return random.nextInt(1000000000);
    }
    private boolean IDExists(int playlistID) {
        String sql = "SELECT COUNT(*) FROM PLAYLIST WHERE playlistID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, playlistID);
        return count != null && count > 0;
    }
}

