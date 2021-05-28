package com.example.landrouter.service;

import com.example.landrouter.exception.RouteNotFoundException;
import com.example.landrouter.exception.UnknownLandException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LandRouteService {

    private final LandGraphService landGraphService;

    @Cacheable(value = "land-routes", cacheManager = "routeCacheManager")
    public List<String> shortestPath(String src, String dst) {
        Graph<String, DefaultEdge> landGraph = landGraphService.getLandGraph();
        if (!landGraph.containsVertex(src)) throw new UnknownLandException(src);
        if (!landGraph.containsVertex(dst)) throw new UnknownLandException(dst);

        GraphPath<String, DefaultEdge> path = DijkstraShortestPath.findPathBetween(landGraph, src, dst);
        if (path == null) throw new RouteNotFoundException(String.format("%s/%s", src, dst));

        List<String> landList = path.getVertexList();
        log.info("Calculated route from {} to {}: {}", src, dst, landList);
        return landList;
    }

}
