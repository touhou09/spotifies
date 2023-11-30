package com.example.spotify.controller;

import com.example.spotify.model.AlbumsLiked;
import com.example.spotify.service.AlbumsLikedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albumsLiked")
public class AlbumsLikedController {
    @Autowired
    private AlbumsLikedService albumsLikedService;

    @PostMapping("/add")
    public AlbumsLiked likeAlbum(@RequestBody AlbumsLiked albumsLiked) {
        return albumsLikedService.likeAlbum(albumsLiked);
    }
    @DeleteMapping("/remove")
    public String unlikeAlbum(@RequestParam int albumId, @RequestParam int userId) {
        int rows = albumsLikedService.unlikeAlbum(albumId, userId);
        return rows > 0 ? "Album unlike successfully" : "Album unlike failed";
    }
    @GetMapping("/all/{userId}")
    public List<AlbumsLiked> findAllAlbumsLiked(@PathVariable int userId) {
        return albumsLikedService.findAllAlbumsLiked(userId);
    }
}