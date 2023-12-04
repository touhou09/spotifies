package com.example.spotify.service;

import com.example.spotify.model.Artist;
import com.example.spotify.model.SearchResult;
import com.example.spotify.model.User;
import com.example.spotify.repository.SearchRepository;
import com.example.spotify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;

    public List<SearchResult> searchSong(String keyword) {
        return searchRepository.searchSong(keyword);
    }

    public List<SearchResult> searchAlbum(String keyword) {
        return searchRepository.searchAlbum(keyword);
    }

    public List<SearchResult> searchArtist(String keyword) {
        return searchRepository.searchArtist(keyword);
    }

    public List<SearchResult> searchGenre(String genre) {
        return searchRepository.searchGenre(genre);
    }

    public List<User> searchUser(String keyword) {
        return UserRepository.searchUser(keyword);
    }

}