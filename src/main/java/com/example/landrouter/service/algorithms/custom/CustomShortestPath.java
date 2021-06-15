package com.example.landrouter.service.algorithms.custom;

import java.util.*;

public class CustomShortestPath {

    private final Queue<CustomNode<String>> queue;
    private final Set<CustomNode<String>> settledNodes;
    private final Map<String, CustomNode<String>> knownNodes;
    private final CustomGraph<String> graph;

    public CustomShortestPath(CustomGraph<String> graph) {
        this.graph = graph;
        queue = new PriorityQueue<>();
        settledNodes = new HashSet<>();
        knownNodes = new HashMap<>();
    }

    public List<String> findShortestPath(String src, String dst) {
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

        return null;
    }

    private void processNeighbours(CustomNode<String> node) {
        int newLength = node.pathLength.get() + 1;

        for (CustomNode<String> n : node.neighbours) {
            if (settledNodes.contains(n)) continue;
            if (n.pathLength.get() <= newLength) continue;

            n.pathLength.set(newLength);
            n.prevNode.set(node);

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
            node = node.prevNode.get();
        }
        Collections.reverse(path);
        return path;
    }

}
