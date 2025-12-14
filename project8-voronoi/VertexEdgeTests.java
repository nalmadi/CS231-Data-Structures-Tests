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

        // Vertex value should be gettable and settable (required for Voronoi Game)
        {
            Vertex a = new Vertex();
            a.setValue(75);
            assert a.getValue() == 75 : "getValue should return set value";
            
            a.setValue(0);
            assert a.getValue() == 0 : "Value should update to zero";
            
            a.setValue(100);
            assert a.getValue() == 100 : "Value should update to max";
        }

        // Multiple vertices should maintain independent values
        {
            Vertex a = new Vertex();
            Vertex b = new Vertex();
            Vertex c = new Vertex();
            
            a.setValue(10);
            b.setValue(50);
            c.setValue(90);
            
            assert a.getValue() == 10 : "Vertex a should have value 10";
            assert b.getValue() == 50 : "Vertex b should have value 50";
            assert c.getValue() == 90 : "Vertex c should have value 90";
            
            // Changing one shouldn't affect others
            a.setValue(99);
            assert b.getValue() == 50 : "Changing a should not affect b";
            assert c.getValue() == 90 : "Changing a should not affect c";
        }

        System.out.println("Finished VertexEdgeTests!");
    }
}
