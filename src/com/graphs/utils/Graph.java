package com.graphs.utils;

public class Graph {

    private static final int INFINITY = Integer.MAX_VALUE;
    private int numNodes;
    private int[][] adjacencyMatrix;
    private boolean[] marks;

    public Graph() {
        this.numNodes = 0;
        this.adjacencyMatrix = new int[100][100];
        this.marks = new boolean[100];
    }

    public int addVertex(int data) {
        Node node = new Node(data);
        adjacencyMatrix[0]= new int[23];

        return  adjacencyMatrix[0][data];
    }
}
