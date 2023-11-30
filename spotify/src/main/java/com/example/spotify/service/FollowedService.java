package com.example.spotify.service;

import com.example.spotify.model.Followed;
import com.example.spotify.repository.FollowedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowedService {
    @Autowired
    private FollowedRepository followedRepository;

    public Followed follow(Followed followed) {
        return followedRepository.follow(followed);
    }

    public int unfollow(int artistId, int userId) {
        return followedRepository.unfollow(artistId, userId);
    }

    public List<Followed> findAllFollowed(int userId) {
        return followedRepository.findAllFollowed(userId);
    }
}