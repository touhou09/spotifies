package com.example.spotify.model;

public class SongsLiked {
    private int songId;
    private int userId;

    public SongsLiked(int songId, int userId) {
        this.songId = songId;
        this.userId = userId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}