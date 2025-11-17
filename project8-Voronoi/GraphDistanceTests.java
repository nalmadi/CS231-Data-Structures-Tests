/*
file name:      GraphDistanceTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea GraphDistanceTests
*/

import java.util.HashMap;

public class GraphDistanceTests {

    public static void main(String[] args) {
        graphDistanceTests();
    }

    public static void graphDistanceTests() {

        // Dijkstra should compute shortest paths on weighted graph
        {
            Graph graph = new Graph();
            Vertex s = graph.addVertex();
            Vertex a = graph.addVertex();
            Vertex b = graph.addVertex();
            Vertex c = graph.addVertex();
            Vertex t = graph.addVertex();

            graph.addEdge(s, a, 2);
            graph.addEdge(s, b, 5);
            graph.addEdge(a, b, 1);
            graph.addEdge(a, c, 2);
            graph.addEdge(b, c, 1);
            graph.addEdge(c, t, 3);

            HashMap<Vertex, Double> dist = graph.distanceFrom(s);
            assert Math.abs(dist.get(s)) < 1e-9 : "Source distance should be zero";
            assert Math.abs(dist.get(t) - 7.0) < 1e-9 : "Shortest path should be 2+1+1+3";
        }

        // Unreachable vertices should remain Infinity (or Double.POSITIVE_INFINITY)
        {
            Graph graph = new Graph();
            Vertex s = graph.addVertex();
            Vertex isolated = graph.addVertex();
            HashMap<Vertex, Double> dist = graph.distanceFrom(s);
            assert dist.get(isolated).isInfinite() : "Unreachable vertex should be infinite distance";
        }

        // Removing edges should update future distance computations
        {
            Graph graph = new Graph();
            Vertex s = graph.addVertex();
            Vertex a = graph.addVertex();
            Vertex t = graph.addVertex();
            Edge edge1 = graph.addEdge(s, a, 1);
            Edge edge2 = graph.addEdge(a, t, 1);
            graph.addEdge(s, t, 10);

            HashMap<Vertex, Double> dist1 = graph.distanceFrom(s);
            assert Math.abs(dist1.get(t) - 2.0) < 1e-9 : "Shortest path should use intermediate vertex";

            graph.remove(edge2);
            HashMap<Vertex, Double> dist2 = graph.distanceFrom(s);
            assert Math.abs(dist2.get(t) - 10.0) < 1e-9 : "Removing edge should fall back to long path";
        }

        // Distance map should include every vertex exactly once
        {
            Graph graph = new Graph(6, 0.5);
            HashMap<Vertex, Double> dist = graph.distanceFrom(graph.getVertex(0));
            assert dist.size() == graph.size() : "Distance map must contain entries for all vertices";
        }

        System.out.println("Finished GraphDistanceTests!");
    }
}
