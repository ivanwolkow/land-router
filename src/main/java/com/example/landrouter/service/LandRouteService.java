package com.example.landrouter.service;

import com.example.landrouter.service.algorithms.LandRoutingAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LandRouteService {

    private final LandRoutingAlgorithm landRoutingAlgorithm;

    @Cacheable(value = "land-routes", cacheManager = "routeCacheManager")
    public List<String> shortestPath(String src, String dst) {
        List<String> landList = landRoutingAlgorithm.findShortestDistance(src, dst);
        log.info("Estimated route from {} to {}: {}", src, dst, landList);
        return landList;
    }

}
