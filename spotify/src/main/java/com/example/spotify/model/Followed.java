package com.example.spotify.model;

public class Followed {
    private int artistId;
    private int userId;

    // Constructor, getters, and setters
    public Followed(int artistId, int userId) {
        this.artistId = artistId;
        this.userId = userId;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

