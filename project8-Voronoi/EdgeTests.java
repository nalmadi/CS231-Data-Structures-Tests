/*
file name:      EdgeTests.java
Authors:        Alex Solano
last modified:  11/15/2025

How to run:     java -ea EdgeTests.java
*/

public class EdgeTests{

    public static void main(String[] args){
        
        // test case 1: constuctor and distance()
        {
            // given
            Vertex v1 = new Vertex();   
            Vertex v2 = new Vertex();   
            Edge e12 = new Edge(v1, v2, 1);

            // when 
            System.out.println("After creating an edge between v1 and v2 with a distance of 1.0:");
            System.out.println("Does an edge exist between v1 and v2? " + (e12 != null));
            System.out.println("Distance of Edge: " + e12.distance() + " == 1.0");
            
            // then
            assert e12 != null : "Error in Edge::Edge()";
            assert e12.distance() == 1 : "Error in Edge::Edge()";
        }
        
        // test case 2: other()
        {
            // given 
            Vertex v1 = new Vertex();   
            Vertex v2 = new Vertex();   
            Edge e12 = new Edge(v1, v2, 1);

            // when
            System.out.println("After creating an edge between v1 and v2 with a distance of 1.0:");
            System.out.println("Is the first endpoint of the edge v1? " + (e12.other(v2) == v1));
            System.out.println("Is the second endpoint of the edge v2? " + (e12.other(v1) == v2));
            
            // then
            assert e12.other(v2) == v1 : "Error in Edge::other()";
            assert e12.other(v1) == v2 : "Error in Edge::other()";
        }

        // test case 3: vertices()
        {
            // given 
            Vertex v1 = new Vertex();   
            Vertex v2 = new Vertex();   
            Edge e12 = new Edge(v1, v2, 1);
            
            // when 
            System.out.println("After creating an edge between v1 and v2 with a distance of 1.0:");
            System.out.println("Number of Vertices the edge connects: " + e12.vertices().length + " == 2");
            System.out.println("Is the first endpoint of the edge v1? " + (e12.vertices()[0] == v1));
            System.out.println("Is the second endpoint of the edge v2? " + (e12.vertices()[1] == v2));
            
            // then
            assert e12.vertices().length == 2 : "Error in Edge::vertices()";
            assert e12.vertices()[0] == v1 : "Error in Edge::vertices()";
            assert e12.vertices()[1] == v2 : "Error in Edge::vertices()";
        }
        
        // test case 4: equals()
        {
            // given
            Vertex v1 = new Vertex();   
            Vertex v2 = new Vertex();   
            Edge e12 = new Edge(v1, v2, 1);
            Edge e21 = new Edge(v2, v1, 1);
            
            // when
            System.out.println("First, we created an edge between v1 and v2 with a distance of 1.0:");
            System.out.println("Then, we created a new edge between v1 and v2 with the same magniture, but with the order reverse:");
            System.out.println("Both edges should be equal: " + (e12.equals(e21) == true) + " == true");
            
            // then
            assert e12.equals(e21) == true : "Error in Edge::equals()";
        }
        System.out.println("*** Done testing Edge.java! ***");
    }
}