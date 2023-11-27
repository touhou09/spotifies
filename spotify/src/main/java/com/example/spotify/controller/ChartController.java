package com.example.spotify.controller;

import com.example.spotify.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/charts")
public class ChartController {
    private final ChartService chartService;

    @Autowired
    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/top-songs/{timeFrame}")
    public List<Map<String, Object>> getTopSongs(@PathVariable String timeFrame) {
        return chartService.displayTopSongs(timeFrame);
    }

    @GetMapping("/top-followed")
    public List<Map<String, Object>> getTopFollowed() {
        return chartService.displayTopFollowed();
    }
}
