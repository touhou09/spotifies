package com.example.spotify.repository;

import com.example.spotify.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SearchRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SearchResult> searchSong(String keyword) {
        String searchSQL = "SELECT 'Song' as Type, Title FROM Songs WHERE Title LIKE ?";

        List<SearchResult> searchResults = jdbcTemplate.query(searchSQL, new Object[]{"%" + keyword + "%"}, (resultSet, i) -> {
            SearchResult searchResult = new SearchResult();
            searchResult.setType(resultSet.getString("Type"));
            searchResult.setTitle(resultSet.getString("Title"));
            return searchResult;
        });
        return searchResults;
    }

    public List<SearchResult> searchAlbum(String keyword) {
        String searchSQL = "SELECT 'Album' as Type, AlbumName FROM Album WHERE AlbumName LIKE ?";

        List<SearchResult> searchResults = jdbcTemplate.query(searchSQL, new Object[]{"%" + keyword + "%"}, (resultSet, i) -> {
            SearchResult searchResult = new SearchResult();
            searchResult.setType(resultSet.getString("Type"));
            searchResult.setTitle(resultSet.getString("AlbumName"));
            return searchResult;
        });
        return searchResults;
    }

    public List<SearchResult> searchArtist(String keyword) {
        String searchSQL = "SELECT 'Artist' as Type, Artist_Name FROM Artist WHERE Artist_Name LIKE ?";

        List<SearchResult> searchResults = jdbcTemplate.query(searchSQL, new Object[]{"%" + keyword + "%"}, (resultSet, i) -> {
            SearchResult searchResult = new SearchResult();
            searchResult.setType(resultSet.getString("Type"));
            searchResult.setTitle(resultSet.getString("Artist_Name"));
            return searchResult;
        });
        return searchResults;
    }
}
