package com.example.spotify.service;

import com.example.spotify.repository.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartService {
    private final ChartRepository chartRepository;

    @Autowired
    public ChartService(ChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

    public List<Map<String, Object>> displayTopSongs(String timeFrame) {
        String viewName = "TopSongsView_" + timeFrame;
        return chartRepository.getTopSongs(viewName);
    }
}
