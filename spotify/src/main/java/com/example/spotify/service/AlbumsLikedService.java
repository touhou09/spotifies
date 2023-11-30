package com.example.spotify.service;

import com.example.spotify.model.AlbumsLiked;
import com.example.spotify.repository.AlbumsLikedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumsLikedService {
    @Autowired
    private AlbumsLikedRepository albumsLikedRepository;

    public AlbumsLiked likeAlbum(AlbumsLiked albumsLiked) {
        return albumsLikedRepository.likeAlbum(albumsLiked);
    }

    public int unlikeAlbum(int albumId, int userId) {
        return albumsLikedRepository.unlikeAlbum(albumId, userId);
    }

    public List<AlbumsLiked> findAllAlbumsLiked(int userId) {
        return albumsLikedRepository.findAllAlbumsLiked(userId);
    }
}