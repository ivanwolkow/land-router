package com.example.landrouter.service;

import com.example.landrouter.exception.RouteNotFoundException;
import com.example.landrouter.exception.UnknownLandException;
import lombok.RequiredArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandRouteService {

    private final LandGraphService landGraphService;

    public List<String> shortestPath(String src, String dst) {
        Graph<String, DefaultEdge> landGraph = landGraphService.getLandGraph();
        if (!landGraph.containsVertex(src)) throw new UnknownLandException(src);
        if (!landGraph.containsVertex(dst)) throw new UnknownLandException(dst);

        GraphPath<String, DefaultEdge> path = DijkstraShortestPath.findPathBetween(landGraph, src, dst);
        if (path == null) throw new RouteNotFoundException(String.format("%s/%s", src, dst));
        
        return path.getVertexList();
    }

}
