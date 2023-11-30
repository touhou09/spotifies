package com.example.spotify.service;

import com.example.spotify.model.SongsLiked;
import com.example.spotify.repository.SongsLikedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongsLikedService {
    @Autowired
    private SongsLikedRepository songsLikedRepository;

    public SongsLiked likeSong(SongsLiked songsLiked) {
        return songsLikedRepository.likeSong(songsLiked);
    }

    public int unlikeSong(int songId, int userId) {
        return songsLikedRepository.unlikeSong(songId, userId);
    }

    public List<SongsLiked> findAllSongsLiked(int userId) {
        return songsLikedRepository.findAllSongsLiked(userId);
    }
}