/*
file name:      VertexEdgeTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea VertexEdgeTests
*/

import java.util.ArrayList;

public class VertexEdgeTests {

    public static void main(String[] args) {
        vertexEdgeTests();
    }

    public static void vertexEdgeTests() {

        // addEdge should register incident edges without duplicates
        {
            Vertex a = new Vertex();
            Vertex b = new Vertex();
            Vertex c = new Vertex();
            Edge ab = new Edge(a, b, 1.0);
            Edge ac = new Edge(a, c, 1.0);
            a.addEdge(ab);
            a.addEdge(ac);
            b.addEdge(ab);
            c.addEdge(ac);
            ArrayList<Edge> incident = a.incidentEdges();
            assert incident.size() == 2 : "Vertex should track each unique edge";
            ArrayList<Vertex> adj = a.adjacentVertices();
            assert adj.contains(b) && adj.contains(c) : "Adjacent vertices should include edge endpoints";
        }

        // getEdgeTo should return specific edge reference
        {
            Vertex a = new Vertex();
            Vertex b = new Vertex();
            Edge ab = new Edge(a, b, 2.5);
            a.addEdge(ab);
            b.addEdge(ab);
            assert a.getEdgeTo(b) == ab : "getEdgeTo should return shared edge";
            assert b.getEdgeTo(a) == ab : "Symmetric edge lookup should work";
        }

        // removeEdge should detach edge from vertex adjacency list
        {
            Vertex a = new Vertex();
            Vertex b = new Vertex();
            Edge ab = new Edge(a, b, 1.0);
            a.addEdge(ab);
            b.addEdge(ab);
            boolean removedA = a.removeEdge(ab);
            boolean removedB = b.removeEdge(ab);
            assert removedA && removedB : "removeEdge should return true when edge incident";
            assert a.getEdgeTo(b) == null : "Edge should be gone after removal";
        }

        // Edge.other should return opposite endpoint or null
        {
            Vertex a = new Vertex();
            Vertex b = new Vertex();
            Vertex c = new Vertex();
            Edge ab = new Edge(a, b, 1.0);
            assert ab.other(a) == b : "other should return other endpoint";
            assert ab.other(b) == a : "Symmetric other should work";
            assert ab.other(c) == null : "Non-incident vertex should return null";
        }

        // incidentEdges should be defensive: modifications to returned list shouldn???t affect vertex
        {
            Vertex a = new Vertex();
            Vertex b = new Vertex();
            Edge ab = new Edge(a, b, 1.0);
            a.addEdge(ab);
            ArrayList<Edge> incident = a.incidentEdges();
            incident.clear();
            assert a.incidentEdges().size() == 1 : "Returned list should be a copy";
        }

        System.out.println("Finished VertexEdgeTests!");
    }
}
