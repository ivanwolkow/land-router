package com.example.landrouter.service.algorithms.custom;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class CustomNode<T> implements Comparable<CustomNode<T>> {
    T val;
    Set<CustomNode<T>> neighbours = new HashSet<>();

    ThreadLocal<Integer> pathLength = ThreadLocal.withInitial(() -> Integer.MAX_VALUE);
    ThreadLocal<CustomNode<T>> prevNode = ThreadLocal.withInitial(() -> null);

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
                ", pathLength=" + pathLength.get() +
                ", prevNode=" + prevNode.get() +
                ", neighbours cnt=" + neighbours.size() +
                '}';
    }

    @Override
    public int compareTo(CustomNode<T> o) {
        return Integer.compare(this.pathLength.get(), o.pathLength.get());
    }
}
