package com.example.spotify.repository;

import com.example.spotify.model.AlbumsLiked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumsLikedRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AlbumsLiked likeAlbum(AlbumsLiked albumsLiked) {
        String sql = "INSERT INTO albums_liked (ALBUMID, USERID) VALUES (?, ?)";
        jdbcTemplate.update(sql, albumsLiked.getAlbumId(), albumsLiked.getUserId());
        return albumsLiked;
    }

    public int unlikeAlbum(int albumId, int userId) {
        String sql = "DELETE FROM albums_liked WHERE ALBUMID = ? AND USERID = ?";
        return jdbcTemplate.update(sql, albumId, userId);
    }

    public List<AlbumsLiked> findAllAlbumsLiked(int userId) {
        String sql = "SELECT * FROM albums_liked WHERE USERID = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (resultSet, i) ->
                new AlbumsLiked(resultSet.getInt("ALBUMID"), resultSet.getInt("USERID"))
        );
    }
}