package com.example.spotify.controller;

import com.example.spotify.model.SongsLiked;
import com.example.spotify.service.SongsLikedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songsLiked")
public class SongsLikedController {

    @Autowired
    private SongsLikedService songsLikedService;

    @PostMapping("/add")
    public SongsLiked likeSong(@RequestBody SongsLiked songsLiked) {
        return songsLikedService.likeSong(songsLiked);
    }
    @DeleteMapping("/remove")
    public String unlikeSong(@RequestParam int songId, @RequestParam int userId) {
        int rows = songsLikedService.unlikeSong(songId, userId);
        return rows > 0 ? "Song unlike successfully" : "Song unlike failed";
    }
    @GetMapping("/all/{userId}")
    public List<SongsLiked> findAllSongsLiked(@PathVariable int userId) {
        return songsLikedService.findAllSongsLiked(userId);
    }
}