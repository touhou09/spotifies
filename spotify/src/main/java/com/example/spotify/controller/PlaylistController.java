package com.example.spotify.controller;

import com.example.spotify.model.Playlist;
import com.example.spotify.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }


    @PostMapping("/randomcreate")
    public ResponseEntity<?> createTimeBasedPlaylist(@RequestParam int targetDuration, @RequestParam String playlistName, @RequestParam String userId) {
        try {
            Playlist playlist = playlistService.createTimeBasedPlaylist(targetDuration, playlistName, userId);
            return new ResponseEntity<>(playlist, HttpStatus.OK); // 생성된 플레이리스트 정보 반환
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating playlist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @PostMapping("/create-empty")
    public void createEmptyPlaylist(@RequestParam String playlistName, @RequestParam String userId) {
        playlistService.createEmptyPlaylist(playlistName, userId);
    }

    @DeleteMapping("/delete/{playlistId}")
    public void deletePlaylist(@PathVariable int playlistId) {
        playlistService.deletePlaylist(playlistId);
    }

    @PostMapping("/incrementLikedCount")
    public String incrementLikedCount(@RequestParam int playlistId) {
        int rows = playlistService.incrementLikedCount(playlistId);
        return rows > 0 ? "Incremented liked count successfully" : "Increment failed";
    }

    @PostMapping("/decrementLikedCount")
    public String decrementLikedCount(@RequestParam int playlistId) {
        int rows = playlistService.decrementLikedCount(playlistId);
        return rows > 0 ? "Decremented liked count successfully" : "Decrement failed";
    }

}

/*
    @PostMapping("/randomcreate")
    public void createTimeBasedPlaylist(@RequestParam int targetDuration, @RequestParam String playlistName, @RequestParam String userId) {
        playlistService.createTimeBasedPlaylist(targetDuration, playlistName, userId);
    }*/