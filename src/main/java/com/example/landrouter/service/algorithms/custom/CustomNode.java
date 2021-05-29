package com.example.landrouter.service.algorithms.custom;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class CustomNode<T> implements Comparable<CustomNode<T>> {
    T val;
    int pathLength = Integer.MAX_VALUE;
    CustomNode<T> prevNode = null;
    Set<CustomNode<T>> neighbours = new HashSet<>();

    public CustomNode(T val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomNode<?> that = (CustomNode<?>) o;
        return val.equals(that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        return "CustomNode{" +
                "val=" + val +
                ", pathLength=" + pathLength +
                ", prevNode=" + prevNode +
                ", neighbours cnt=" + neighbours.size() +
                '}';
    }

    @Override
    public int compareTo(CustomNode<T> o) {
        return Integer.compare(this.pathLength, o.pathLength);
    }
}
