package com.example.spotify.controller;

import com.example.spotify.model.SearchResult;
import com.example.spotify.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/songs")
    public List<SearchResult> searchSong(@RequestParam String keyword) {
        return searchService.searchSong(keyword);
    }

    @GetMapping("/albums")
    public List<SearchResult> searchAlbum(@RequestParam String keyword) {
        return searchService.searchAlbum(keyword);
    }

    @GetMapping("/artists")
    public List<SearchResult> searchArtist(@RequestParam String keyword) {
        return searchService.searchArtist(keyword);
    }

    @GetMapping("/genres")
    public List<SearchResult> searchGenre(@RequestParam String genre) {
        return searchService.searchGenre(genre);
    }
}