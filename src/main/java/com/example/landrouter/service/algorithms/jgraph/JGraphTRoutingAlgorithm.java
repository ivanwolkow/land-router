package com.example.landrouter.service.algorithms.jgraph;

import com.example.landrouter.exception.RouteNotFoundException;
import com.example.landrouter.exception.UnknownLandException;
import com.example.landrouter.service.algorithms.LandRoutingAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnBean(Graph.class)
public class JGraphTRoutingAlgorithm implements LandRoutingAlgorithm {

    private final Graph<String, DefaultEdge> graph;

    @Override
    public List<String> findShortestDistance(String src, String dst) {
        if (!graph.containsVertex(src)) throw new UnknownLandException(src);
        if (!graph.containsVertex(dst)) throw new UnknownLandException(dst);

        GraphPath<String, DefaultEdge> path = DijkstraShortestPath.findPathBetween(graph, src, dst);
        if (path == null) throw new RouteNotFoundException(String.format("%s/%s", src, dst));

        return path.getVertexList();
    }
}
