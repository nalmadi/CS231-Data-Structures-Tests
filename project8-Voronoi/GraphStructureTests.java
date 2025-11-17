/*
file name:      GraphStructureTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea GraphStructureTests
*/

import java.util.ArrayList;

public class GraphStructureTests {

    public static void main(String[] args) {
        graphStructureTests();
    }

    public static void graphStructureTests() {

        // Graph(int n, double probability) should create approximately expected edges
        {
            Graph graph = new Graph(20, 0.0);
            assert graph.size() == 20 : "Graph should initialize requested vertices";
            assert graph.getEdges().isEmpty() : "Probability zero should yield no edges";
        }

        // addVertex should increase size and be retrievable via getVertex
        {
            Graph graph = new Graph();
            Vertex v0 = graph.addVertex();
            Vertex v1 = graph.addVertex();
            assert graph.size() == 2 : "Graph size should reflect newly added vertices";
            assert graph.getVertices().contains(v0) && graph.getVertices().contains(v1) : "Vertices should be tracked";
            assert graph.getVertex(0) == v0 : "getVertex should return inserted vertex";
        }

        // addEdge should register edges on vertices and graph list
        {
            Graph graph = new Graph();
            Vertex v0 = graph.addVertex();
            Vertex v1 = graph.addVertex();
            Edge edge = graph.addEdge(v0, v1, 3.0);
            assert graph.getEdges().contains(edge) : "Graph should store created edge";
            assert v0.getEdgeTo(v1) == edge : "Endpoints should reference edge";
            assert v1.getEdgeTo(v0) == edge : "Edge should be bidirectional";
        }

        // remove(Edge) should detach from endpoints and list
        {
            Graph graph = new Graph();
            Vertex v0 = graph.addVertex();
            Vertex v1 = graph.addVertex();
            Edge edge = graph.addEdge(v0, v1, 1.0);
            boolean removed = graph.remove(edge);
            assert removed : "remove(edge) should succeed";
            assert !graph.getEdges().contains(edge) : "Edge list should shrink";
            assert v0.getEdgeTo(v1) == null : "Endpoints should drop edge";
        }

        // remove(Vertex) should erase incident edges
        {
            Graph graph = new Graph();
            Vertex v0 = graph.addVertex();
            Vertex v1 = graph.addVertex();
            graph.addEdge(v0, v1, 1.0);
            boolean removed = graph.remove(v0);
            assert removed : "remove(vertex) should succeed";
            assert graph.size() == 1 : "Graph should have remaining vertex";
            assert v1.getEdgeTo(v0) == null : "Incident edges should disappear";
        }

        // file-based constructor should parse vertices and edges
        {
            String filename = "graph-test.txt";
            TestGraphFileWriter.writeSimpleGraph(filename);
            Graph graph = new Graph(filename);
            assert graph.size() == 4 : "File should define vertex count";
            assert graph.getEdges().size() == 3 : "File edges should be created";
            new java.io.File(filename).delete();
        }

        System.out.println("Finished GraphStructureTests!");
    }

    private static class TestGraphFileWriter {
        static void writeSimpleGraph(String filename) {
            try (java.io.PrintWriter out = new java.io.PrintWriter(filename)) {
                out.println("Vertices: 4");
                out.println("Edges:");
                out.println("0,1");
                out.println("1,2");
                out.println("2,3");
            } catch (java.io.IOException e) {
                throw new RuntimeException("Unable to write temporary graph file", e);
            }
        }
    }
}
