package com.example.spotify.model;

public class SearchResult {
    private String type;
    private String title;
    private String artistName;
    private int albumCount;

    // Constructor
    public SearchResult() {
        this.type = type;
        this.title = title;
        this.artistName = artistName;
        this.albumCount = albumCount;
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getAlbumCount() {
        return albumCount;
    }

    // Setters
    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAlbumCount(int albumCount) {
        this.albumCount = albumCount;
    }
}
