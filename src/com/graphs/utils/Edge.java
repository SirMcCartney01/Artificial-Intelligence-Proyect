package com.graphs.utils;

public class Edge {

    private Node first, second;
    private int weight;

    public Edge(Node first, Node second, int weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    public Node fromNode() {
        return first;
    }

    public Node toNode() {
        return second;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isBetween(Node first, Node second) {
        return (this.first == first && this.second == second);
    }
}
