package com.example.landrouter.service.algorithms.custom;

import java.util.HashMap;
import java.util.Map;

public class CustomGraph<T> {
    private Map<T, CustomNode<T>> nodes;

    public CustomGraph() {
        nodes = new HashMap<>();
    }

    public void addNode(T val) {
        if (nodes.containsKey(val)) return;
        nodes.put(val, new CustomNode<>(val));
    }

    public void addEdge(T val1, T val2) {
        if (!nodes.containsKey(val1) || !nodes.containsKey(val2)) throw new IllegalArgumentException();

        CustomNode<T> node1 = nodes.get(val1);
        CustomNode<T> node2 = nodes.get(val2);

        node1.neighbours.add(node2);
        node2.neighbours.add(node1);
    }

    public boolean containsNode(T val) {
        return nodes.containsKey(val);
    }

    public CustomNode<T> getNode(T val) {
        return nodes.get(val);
    }

    public void resetNodes() {
        nodes.forEach((val, node) -> {
            node.prevNode = null;
            node.pathLength = Integer.MAX_VALUE;
        });
    }

}
