/*
file name:      VertexTests.java
Authors:        Alex Solano
last modified:  11/15/2025

How to run:     java -ea VertexTests.java
*/

public class VertexTests{

    public static void main(String[] args){
        
        // test case 1: constructor, adjacentVertices(), incidentEdges()
        {
            // given
            Vertex v1 = new Vertex();

            // when 
            System.out.println("Exists? " + (v1 != null));
            System.out.println("Num of Adj. Vertices: " + v1.adjacentVertices().size());
            System.out.println("Num of Inc. Edges: " + v1.incidentEdges().size());

            // then
            assert v1 != null : "Error in Vertex::Vertex()";
            assert v1.adjacentVertices().size() == 0 : "Error in Vertex::Vertex() or Vertex::adjacentVertices()";
            assert v1.incidentEdges().size() == 0 : "Error in Vertex::Vertex() or Vertex::incidentEdges()";
        }

        // test case 2: addEdge(), getEdgeTo(), adjacentVertices(), incidentEdges()
        {
            // given
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();
            Vertex v3 = new Vertex();
            Edge e12 = new Edge(v1, v2, 1);
            Edge e13 = new Edge(v1, v3, 1);

            // when
            System.out.println("We are given a set of vertices V = {v1, v2, v3} and a set of edges E = {(v1, v2), (v1, v3)}");
            System.out.println("We are going to add the edges to its corresponding vertice's list of incident edges and update " + 
            "the vertices' list of adjacent vertices as well");
            
            v1.addEdge(e12);
            v2.addEdge(e12);
            v1.addEdge(e13);
            v3.addEdge(e13);

            System.out.println("Num of Adj. Vertices: " + v1.adjacentVertices().size() + " == 2");
            System.out.println("Num of Inc. Edges: " + v1.incidentEdges().size() + " == 2");
            System.out.println("Num of Adj. Vertices: " + v2.adjacentVertices().size() + " == 1");
            System.out.println("Num of Inc. Edges: " + v2.incidentEdges().size() + " == 1");
            System.out.println("Num of Adj. Vertices: " + v3.adjacentVertices().size() + " == 1");
            System.out.println("Num of Inc. Edges: " + v3.incidentEdges().size() + " == 1");
            System.out.println("Does an edge exist between v1 and v2? " + (v1.getEdgeTo(v2) != null));
            System.out.println("Does an edge exist between v2 and v1? " + (v2.getEdgeTo(v1) != null));
            System.out.println("Does an edge exist between v1 and v3? " + (v1.getEdgeTo(v3) != null));
            System.out.println("Does an edge exist between v3 and v1? " + (v3.getEdgeTo(v1) != null));
            System.out.println("Does an edge exist between v2 and v3? " + (v2.getEdgeTo(v3) != null));
            
            // then
            assert v1.adjacentVertices().size() == 2 : "Error in Vertex::addEdge() or Vertex::adjacentVertices()";
            assert v1.incidentEdges().size() == 2 : "Error in Vertex::addEdge() or Vertex::incidentEdges()";
            assert v2.adjacentVertices().size() == 1 : "Error in Vertex::addEdge() or Vertex::adjacentVertices()";
            assert v2.incidentEdges().size() == 1 : "Error in Vertex::addEdge() or Vertex::incidentEdges()";
            assert v3.adjacentVertices().size() == 1 : "Error in Vertex::addEdge() or Vertex::adjacentVertices()";
            assert v3.incidentEdges().size() == 1 : "Error in Vertex::addEdge() or Vertex::incidentEdges()";
            assert v1.getEdgeTo(v2) != null : "Error in Vertex:getEdgeTo()";
            assert v2.getEdgeTo(v1) != null : "Error in Vertex:getEdgeTo()";
            assert v1.getEdgeTo(v3) != null : "Error in Vertex:getEdgeTo()";
            assert v3.getEdgeTo(v1) != null : "Error in Vertex:getEdgeTo()";
            assert v2.getEdgeTo(v3) == null : "Error in Vertex:getEdgeTo()";
        }
        
        // test case 3: removeEdge()
        {
            // given
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();
            Vertex v3 = new Vertex();
            Edge e12 = new Edge(v1, v2, 1);
            Edge e13 = new Edge(v1, v3, 1);
            v1.addEdge(e12);
            v2.addEdge(e12);
            v1.addEdge(e13);
            v3.addEdge(e13);

            // when
            System.out.println("After creating vertices {v1, v2, v3} and edges {(v1, v2), (v1, v3)}:");
            System.out.println("Num of Adj. Vertices: " + v1.adjacentVertices().size() + " == 2");
            System.out.println("Num of Inc. Edges: " + v1.incidentEdges().size() + " == 2");
            System.out.println("Num of Adj. Vertices: " + v2.adjacentVertices().size() + " == 1");
            System.out.println("Num of Inc. Edges: " + v2.incidentEdges().size() + " == 1");
            System.out.println("Num of Adj. Vertices: " + v3.adjacentVertices().size() + " == 1");
            System.out.println("Num of Inc. Edges: " + v3.incidentEdges().size() + " == 1");
            System.out.println("Does an edge exist between v1 and v2? " + (v1.getEdgeTo(v2) != null));
            System.out.println("Does an edge exist between v1 and v3? " + (v1.getEdgeTo(v3) != null));
            
            boolean r1 = v1.removeEdge(e12);
            boolean r2 = v2.removeEdge(e12);
            
            System.out.println("After removing the edge between v1 and v2:");
            System.out.println("Num of Adj. Vertices: " + v1.adjacentVertices().size() + " == 1");
            System.out.println("Num of Inc. Edges: " + v1.incidentEdges().size() + " == 1");
            System.out.println("Num of Adj. Vertices: " + v2.adjacentVertices().size() + " == 0");
            System.out.println("Num of Inc. Edges: " + v2.incidentEdges().size() + " == 0");
            System.out.println("Num of Adj. Vertices: " + v3.adjacentVertices().size() + " == 1");
            System.out.println("Num of Inc. Edges: " + v3.incidentEdges().size() + " == 1");
            System.out.println("Does an edge exist between v1 and v2? " + (v1.getEdgeTo(v2) != null));
            System.out.println("Does an edge exist between v1 and v3? " + (v1.getEdgeTo(v3) != null));
            
            // then
            assert r1 == true : "Error in Vertex::removeEdge()";
            assert r2 == true : "Error in Vertex::removeEdge()";
            assert v1.adjacentVertices().size() == 1 : "Error in Vertex::addEdge() or Vertex::adjacentVertices()";
            assert v1.incidentEdges().size() == 1 : "Error in Vertex::addEdge() or Vertex::incidentEdges()";
            assert v2.adjacentVertices().size() == 0 : "Error in Vertex::addEdge() or Vertex::adjacentVertices()";
            assert v2.incidentEdges().size() == 0 : "Error in Vertex::addEdge() or Vertex::incidentEdges()";
            assert v1.getEdgeTo(v2) == null : "Error in Vertex::removeEdge() or Vertex::getEdgeTo()";
            assert v2.getEdgeTo(v1) == null : "Error in Vertex::removeEdge() or Vertex::getEdgeTo()";
            assert v1.getEdgeTo(v3) != null : "Error in Vertex::addEdge() or Vertex::getEdgeTo()";
        }
        // test case 4: getValue() and setValue() - REQUIRED FOR VORONOI GAME
        {
            // given
            Vertex v1 = new Vertex();

            // when
            System.out.println("Testing vertex value functionality (required for Voronoi Game scoring):");
            
            // Test default value or setValue
            v1.setValue(50);
            System.out.println("After setting value to 50: " + v1.getValue() + " == 50");

            v1.setValue(0);
            System.out.println("After setting value to 0: " + v1.getValue() + " == 0");

            v1.setValue(100);
            System.out.println("After setting value to 100: " + v1.getValue() + " == 100");

            // then
            assert v1.getValue() == 100 : "Error in Vertex::getValue() or Vertex::setValue()";
        }

        // test case 5: Vertex value range (should support 0-100 as per spec)
        {
            // given
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();
            Vertex v3 = new Vertex();

            // when
            v1.setValue(0);   // minimum value
            v2.setValue(100); // maximum value
            v3.setValue(57);  // typical random value

            System.out.println("Testing vertex value range (0-100 as per Voronoi spec):");
            System.out.println("Min value (0): " + v1.getValue() + " == 0");
            System.out.println("Max value (100): " + v2.getValue() + " == 100");
            System.out.println("Typical value (57): " + v3.getValue() + " == 57");

            // then
            assert v1.getValue() == 0 : "Error in Vertex::getValue() - min value";
            assert v2.getValue() == 100 : "Error in Vertex::getValue() - max value";
            assert v3.getValue() == 57 : "Error in Vertex::getValue() - typical value";
        }

        System.out.println("*** Done testing Vertex.java! ***");
    }
}