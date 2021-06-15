package com.example.landrouter.service.algorithms.custom;

import com.example.landrouter.exception.RouteNotFoundException;
import com.example.landrouter.exception.UnknownLandException;
import com.example.landrouter.service.algorithms.LandRoutingAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnBean(CustomGraph.class)
public class CustomDijkstraRoutingAlgorithm implements LandRoutingAlgorithm {

    private final CustomGraph<String> graph;

    @Override
    public List<String> findShortestDistance(String src, String dst) {
        if (!graph.containsNode(src)) throw new UnknownLandException(src);
        if (!graph.containsNode(dst)) throw new UnknownLandException(dst);

        var customShortestPath = new CustomShortestPath(graph);
        List<String> path = customShortestPath.findShortestPath(src, dst);

        if (path == null) throw new RouteNotFoundException(String.format("%s/%s", src, dst));
        return path;
    }

}
