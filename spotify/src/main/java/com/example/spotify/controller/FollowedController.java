package com.example.spotify.controller;

import com.example.spotify.model.Followed;
import com.example.spotify.service.FollowedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/followed")
public class FollowedController {
    @Autowired
    private FollowedService followedService;

    @PostMapping("/add")
    public Followed follow(@RequestBody Followed followed) {
        return followedService.follow(followed);
    }

    @DeleteMapping("/remove")
    public String unfollow(@RequestParam int artistId, @RequestParam int userId) {
        int rows = followedService.unfollow(artistId, userId);
        return rows > 0 ? "Unfollowed successfully" : "Unfollow failed";
    }

    @GetMapping("/all/{userId}")
    public List<Followed> findAllFollowed(@PathVariable int userId) {
        return followedService.findAllFollowed(userId);
    }
}