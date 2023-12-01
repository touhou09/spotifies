package com.example.spotify.model;

public class Playlist {
    private int playlistId;
    private int likedCount;

    // Constructor
    public Playlist(int playlistId, int likedCount) {
        this.playlistId = playlistId;
        this.likedCount = likedCount;
    }

    // Getters
    public int getPlaylistId() {
        return playlistId;
    }

    public int getLikedCount() {
        return likedCount;
    }

    // Setters
    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public void setLikedCount(int likedCount) {
        this.likedCount = likedCount;
    }
}

