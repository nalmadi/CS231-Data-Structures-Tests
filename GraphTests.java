/*
file name:      GraphTests.java
Authors:        Alex Solano
last modified:  11/15/2025

How to run:     java -ea GraphTests.java
*/

public class GraphTests{

    public static void main(String[] args){
        // test case 1: constructor(), size(), getVertices(), getEdges()
        {
            // given 
            // System.out.println("First let's create a default Graph with no vertices or no initial probability");
            Graph myGraph1 = new Graph();

            // when 
            int vertices = 0;
            int edges = 0;
            
            for(Vertex ver : myGraph1.getVertices()){
                vertices++;
            }
            for(Edge e : myGraph1.getEdges()){
                edges++;
            }

            System.out.println("Number of vertices: " + myGraph1.size() + " == 0");
            System.out.println("Number of vertices: " + vertices + " == 0");
            System.out.println("Number of edges: " + edges + " == 0");

            // then
            assert myGraph1.size() == 0 : "Error in Graph::Graph() or Graph::size()";
            assert vertices == 0 : "Error in Graph::Graph() or Graph::getVertices()";
            assert edges == 0 : "Error in Graph::Graph() or Graph::getEdges()";
        }

        // test case 2: constructor(int), size(), getVertices(), getEdges()
        {
            // given
            // System.out.println("Let's create a Graph with 10 vertices but no initial probability");
            Graph myGraph2 = new Graph(10);

            // when
            int vertices = 0;
            int edges = 0;

            for(Vertex ver : myGraph2.getVertices()){
                vertices++;
            }
            for(Edge e : myGraph2.getEdges()){
                edges++;
            }

            System.out.println("Number of vertices: " + myGraph2.size() + " == 10");
            System.out.println("Number of vertices: " + vertices + " == 10");
            System.out.println("Number of edges: " + edges + " == 0");
            
            // then
            assert myGraph2.size() == 10 : "Error in Graph::Graph() or Graph::size()";
            assert vertices == 10 : "Error in Graph::Graph() or Graph::getVertices()";
            assert edges == 0 : "Error in Graph::Graph() or Graph::getEdges()";
        }

        // test case 3: constructor(int, double), size(), getVertices(), getEdges()
        {
            // given
            // System.out.println("Let's create a Graph with 10 vertices and 100% initial probability for simplicity sake");
            Graph myGraph3 = new Graph(10, 1.0);

            // when
            int vertices = 0;
            int edges = 0;

            for(Vertex ver : myGraph3.getVertices()){
                vertices++;
            }
            for(Edge e : myGraph3.getEdges()){
                edges++;
            }
            
            System.out.println("Number of vertices: " + myGraph3.size() + " == 10");
            System.out.println("Number of vertices: " + vertices + " == 10");
            System.out.println("Number of edges: " + edges + " == 45");
            
            // then 
            assert myGraph3.size() == 10 : "Error in Graph::Graph() or Graph::size()";
            assert vertices == 10 : "Error in Graph::Graph() or Graph::getVertices()";
            assert edges == 45 : "Error in Graph::Graph() or Graph::getEdges()";
        }


        // test case 4: addVertex()
        {
            // given 
            Graph myGraph = new Graph();
            
            // when
            System.out.println("After creating an empty Graph, there should be no vertices or edges");
            System.out.println("Size: " + myGraph.size() + " == 0");

            Vertex u = myGraph.addVertex();
            Vertex v = myGraph.addVertex();
            Vertex w = myGraph.addVertex();

            System.out.println("After adding three vertices {u, v, w}, the size should be 3, but no edges");
            System.out.println("Size: " + myGraph.size() + " == 3");

            System.out.println("To confirm, let's double check with getVertices() and getEdges()");
            
            int size_v = 0;
            for(Vertex c : myGraph.getVertices()){
                size_v++;
            }
            
            System.out.println("Size: " + size + " == 3");
            
            int size_e = 0;
            for(Edge e : myGraph.getEdges()){
                size_e++;
            }
            
            System.out.println("Number of Edges: " + size + " == 0");
            
            // then 
            assert myGraph.size() == 3 : "Erorr in Graph::addVertex()";
            assert size_v == 3 : "Erorr in Graph::addVertex() or Graph::getVertices()";
            assert size_e == 0 : "Erorr in Graph::addVertex() or Graph::getEdges()";
        }
         
        // test case 5: addEdge(), getEdge()
        {   
            // given
            Graph myGraph = new Graph();
            Vertex u = myGraph.addVertex();
            Vertex v = myGraph.addVertex();
            Vertex w = myGraph.addVertex();

            // when
            System.out.println("Given a Graph with only 3 vertices and 0 edges, we are going to add two edges");

            Edge uv = myGraph.addEdge(u, v, 3.0);
            Edge uw = myGraph.addEdge(u, w, 2.0);

            System.out.println("After adding an edge between (u, v) and (u, w), there should 3 vertices and 2 edges in the Graph");
            System.out.println("Size: " + myGraph.size() + " == 3");
            
            int size = 0;
            for(Edge e : myGraph.getEdges()){
                size++;
            }
            
            System.out.println("Number of Edges: " + size + " == 2");

            System.out.println("To confirm the two edges, let's use the getEdge()");
            System.out.println("Edge u to v: " + myGraph.getEdge(u, v).equals(uv) + " == true");
            System.out.println("Edge u to w: " + myGraph.getEdge(u, w).equals(uw) + " == true");
            
            // then
            assert myGraph.size() == 3 : "Erorr in Graph::addEdge()";
            assert size == 2 : "Erorr in Graph::addEdge() or Graph::getEdges()";
            assert myGraph.getEdge(u, v).equals(uv) == true : "Error in Graph::getEdge() or Graph::addEdge()";
            assert myGraph.getEdge(u, w).equals(uw) == true : "Error in Graph::getEdge() or Graph::addEdge()";
        }

        // test case 6: remove(Edge) 
        {
            // given
            Graph myGraph = new Graph();
            Vertex u = myGraph.addVertex();
            Vertex v = myGraph.addVertex();
            Vertex w = myGraph.addVertex();
            Edge uv = myGraph.addEdge(u, v, 3.0);
            Edge uw = myGraph.addEdge(u, w, 2.0);

            // when
            System.out.println("Given a Graph with vertices {u, v, w} and edges {(u, v), (u, w)}, we are going to remove edge (u,v)");
            
            myGraph.remove(uv);

            System.out.println("After removing the edge (u, v):");
            System.out.println("Does edge (u, v) exist? " + (myGraph.getEdge(u, v) != null)+ " == false");
            System.out.println("Let's confirm by checking if the number of edges changed and if number of vertices didn't changed");
            System.out.println("Size: " + myGraph.size() + " == 3");
            
            int size = 0;
            for(Edge e : myGraph.getEdges()){
                size++;
            }
            
            System.out.println("Number of Edges: " + size + " == 1");
            
            assert myGraph.getEdge(u, v) == null : "Error in Graph::remove() or Graph::getEdge()";
            assert myGraph.size() == 3 : "Erorr in Graph::remove()";
            assert size == 1: "Erorr in Graph::remove() or Graph::getEdges()";
        }
        
        // test case 7: remove(Vertex)
        {
            // given
            Graph myGraph = new Graph();
            Vertex u = myGraph.addVertex();
            Vertex v = myGraph.addVertex();
            Vertex w = myGraph.addVertex();
            Edge uv = myGraph.addEdge(u, v, 3.0);
            Edge uw = myGraph.addEdge(u, w, 2.0);

            // when
            System.out.println("Given a Graph with vertices {u, v, w} and edges {(u, v), (u, w)}, we are going to remove vertex v");

            myGraph.remove(v);
            
            System.out.println("After removing the vertex v:");
            System.out.println("Did the size change? " + (myGraph.size() == 2) + " == true");
            System.out.println("Let's confirm by checking if the number of edges didn't change and if the number of vertices changed");
            
            int size_v = 0;
            for(Vertex cur : myGraph.getVertices()){
                size_v++;
            }

            System.out.println("Size: " + size_v + " == 2");

            int size_e = 0;
            for(Edge e : myGraph.getEdges()){
                size_e++;
            }

            System.out.println("Number of Edges: " + size_e + " == 1");

            // then            
            assert myGraph.size() == 2: "Erorr in Graph::remove()";
            assert size_v == 2 : "Error in Graph::remove() or Graph::getVertices()";
            assert size_e == 1: "Erorr in Graph::remove() or Graph::getEdges()";
        }

        // test case 8: distanceFrom()
        {
            // given
            Graph myGraph = new Graph();
            Vertex a = myGraph.addVertex();
            Vertex b = myGraph.addVertex();
            Vertex c = myGraph.addVertex();
            Vertex d = myGraph.addVertex();
            Vertex e = myGraph.addVertex();
            Vertex f = myGraph.addVertex();
            Vertex g = myGraph.addVertex();
            Vertex h = myGraph.addVertex();
            Edge ab = myGraph.addEdge(a, b, 4.5);
            Edge ac = myGraph.addEdge(a, c, 5);
            Edge ad = myGraph.addEdge(a, d, 5);
            Edge bc = myGraph.addEdge(b, c, 4.5);
            Edge be = myGraph.addEdge(b, e, 2);
            Edge cd = myGraph.addEdge(c, d, 6);
            Edge cf = myGraph.addEdge(c, f, 3.5);
            Edge dg = myGraph.addEdge(d, g, 7);
            Edge ef = myGraph.addEdge(e, f, 3.5);
            Edge eh = myGraph.addEdge(e, h, 5.5);
            Edge fh = myGraph.addEdge(f, h, 2.5);
            Edge fg = myGraph.addEdge(f, g, 4.5);
            Edge gh = myGraph.addEdge(g, h, 7);
            Vertex source = a;

            // when 
            System.out.println("We are given a Graph with vertices {a, b, c, d, e, f, g, h} and {(a,b), (a,c), " +
            "(a,d), (b,c), (b,e), (c,d), (c,f), (d,g), (e,f), (e,h), (f,h), (f,g), (g,h)}");
            System.out.println("For simplicity, our source is going to vertex a");
            System.out.println("Let's see the minimumal distance of all the vertices from the source");
            
            HashMap<Vertex, Double> minDistances = myGraph.distanceFrom(source);
            
            System.out.println(minDistances);

            System.out.println("Let's confirm if the minimumal distance of all the vertices from the source is correct:");
            System.out.println("Vertex a: " + a + " --> " + minDistances.get(a) + " == 0.0");
            System.out.println("Vertex b: " + b + " --> " + minDistances.get(b) + " == 4.5");
            System.out.println("Vertex c: " + c + " --> " + minDistances.get(c) + " == 5.0");
            System.out.println("Vertex d: " + d + " --> " + minDistances.get(d) + " == 5.0");
            System.out.println("Vertex e: " + e + " --> " + minDistances.get(e) + " == 6.5");
            System.out.println("Vertex f: " + f + " --> " + minDistances.get(f) + " == 8.5");
            System.out.println("Vertex g: " + g + " --> " + minDistances.get(g) + " == 12.0");
            System.out.println("Vertex h: " + h + " --> " + minDistances.get(h) + " == 11.0");

            // then
            assert minDistances.get(a) == 0.0 : "Error in Graph::distanceFrom()";
            assert minDistances.get(b) == 4.5 : "Error in Graph::distanceFrom()";
            assert minDistances.get(c) == 5.0 : "Error in Graph::distanceFrom()";
            assert minDistances.get(d) == 5.0 : "Error in Graph::distanceFrom()";
            assert minDistances.get(e) == 6.5 : "Error in Graph::distanceFrom()";
            assert minDistances.get(f) == 8.5 : "Error in Graph::distanceFrom()";
            assert minDistances.get(g) == 12.0 : "Error in Graph::distanceFrom()";
            assert minDistances.get(h) == 11.0 : "Error in Graph::distanceFrom()";
        }
        System.out.println("*** Done testing Graph.java! ***");
    }
}