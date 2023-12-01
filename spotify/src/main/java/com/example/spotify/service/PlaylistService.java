package com.example.spotify.service;
import com.example.spotify.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public void createTimeBasedPlaylist(int targetDuration, String playlistName, String userId) {
        playlistRepository.createTimeBasedPlaylist(targetDuration, playlistName, userId);
    }

    public void createEmptyPlaylist(String playlistName, String userId) {
        playlistRepository.createEmptyPlaylist(playlistName, userId);
    }

    public void deletePlaylist(int playlistId) {
        playlistRepository.deletePlaylist(playlistId);
    }

    public int incrementLikedCount(int playlistId) {
        return playlistRepository.incrementLikedCount(playlistId);
    }

    public int decrementLikedCount(int playlistId) {
        return playlistRepository.decrementLikedCount(playlistId);
    }

}

