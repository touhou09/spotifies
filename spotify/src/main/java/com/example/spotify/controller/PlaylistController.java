package com.example.spotify.controller;

import com.example.spotify.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createTimeBasedPlaylist(@RequestParam int targetDuration, @RequestParam String playlistName, @RequestParam String userId) {
        playlistService.createTimeBasedPlaylist(targetDuration, playlistName, userId);
    }

    @PostMapping("/create-empty")
    public void createEmptyPlaylist(@RequestParam String playlistName, @RequestParam String userId) {
        playlistService.createEmptyPlaylist(playlistName, userId);
    }

    @DeleteMapping("/delete/{playlistId}")
    public void deletePlaylist(@PathVariable int playlistId) {
        playlistService.deletePlaylist(playlistId);
    }

}