package com.example.spotify.repository;

import com.example.spotify.model.Artist;
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

    public List<SearchResult> searchGenre(String genre) {
        String searchSQL = "SELECT inview.Artist_Name, inview.AlbumCount "
                + "FROM ( "
                + "SELECT A.Artist_Name, COUNT(Al.AlbumID) AS AlbumCount "
                + "FROM Artist A "
                + "JOIN Featured_Artists_and_Producers F ON A.ArtistID = F.ArtistID "
                + "JOIN Album Al ON F.AlbumID = Al.AlbumID "
                + "WHERE A.Genre LIKE ? "
                + "GROUP BY A.Artist_Name) inview "
                + "ORDER BY AlbumCount DESC "
                + "FETCH FIRST 10 ROWS ONLY";

        List<SearchResult> searchResults = jdbcTemplate.query(searchSQL, new Object[]{"%" + genre + "%"}, (resultSet, i) -> {
            SearchResult searchResult = new SearchResult();
            searchResult.setType("Artist");
            searchResult.setArtistName(resultSet.getString("Artist_Name"));
            searchResult.setAlbumCount(resultSet.getInt("AlbumCount"));
            return searchResult;
        });
        return searchResults;
    }
}
