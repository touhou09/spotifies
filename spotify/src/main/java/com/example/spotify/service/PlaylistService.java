package com.example.spotify.service;
import com.example.spotify.model.Playlist;
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

    public Playlist createTimeBasedPlaylist(int targetDuration, String playlistName, String userId) {
        playlistRepository.createTimeBasedPlaylist(targetDuration, playlistName, userId);

        return new Playlist(targetDuration, 0); // 예시입니다. 실제 플레이리스트 ID와 관련 정보를 기반으로 객체 생성

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

