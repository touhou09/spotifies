package com.example.spotify.model;

public class AlbumsLiked {
    private int albumId;
    private int userId;

    public AlbumsLiked(int albumId, int userId) {
        this.albumId = albumId;
        this.userId = userId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}