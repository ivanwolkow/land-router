package com.example.landrouter.service.algorithms.custom;

import com.example.landrouter.exception.RouteNotFoundException;
import com.example.landrouter.exception.UnknownLandException;
import com.example.landrouter.service.algorithms.LandRoutingAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnBean(CustomGraph.class)
public class CustomDijkstraRoutingAlgorithm implements LandRoutingAlgorithm {

    private final CustomGraph<String> graph;

    private Queue<CustomNode<String>> queue = new PriorityQueue<>();
    private Set<CustomNode<String>> settledNodes = new HashSet<>();
    private Map<String, CustomNode<String>> knownNodes = new HashMap<>();

    @Override
    public List<String> findShortestDistance(String src, String dst) {
        if (!graph.containsNode(src)) throw new UnknownLandException(src);
        if (!graph.containsNode(dst)) throw new UnknownLandException(dst);

        queue.clear();
        settledNodes.clear();
        knownNodes.clear();
        graph.resetNodes();

        CustomNode<String> srcNode = graph.getNode(src);
        CustomNode<String> dstNode = graph.getNode(dst);

        knownNodes.put(srcNode.val, srcNode);
        queue.add(srcNode);

        while (!queue.isEmpty()) {
            CustomNode<String> cheapestNode = queue.poll();
            if (cheapestNode.equals(dstNode)) return buildFinalPath(cheapestNode);

            settledNodes.add(cheapestNode);
            processNeighbours(cheapestNode);
        }

        throw new RouteNotFoundException(String.format("%s/%s", src, dst));
    }

    private void processNeighbours(CustomNode<String> node) {
        int newLength = node.pathLength + 1;

        for (CustomNode<String> n : node.neighbours) {
            if (settledNodes.contains(n)) continue;
            if (n.pathLength <= newLength) continue;

            n.pathLength = newLength;
            n.prevNode = node;

            if (knownNodes.containsKey(n.val)) {
                queue.remove(n);
            } else {
                knownNodes.put(n.val, n);
            }

            queue.add(n);
        }
    }

    private List<String> buildFinalPath(CustomNode<String> node) {
        List<String> path = new ArrayList<>();
        while (node != null) {
            path.add(node.val);
            node = node.prevNode;
        }
        Collections.reverse(path);
        return path;
    }

}
