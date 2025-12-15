/*
file name:      LandscapeTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea LandscapeTests
*/

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class LandscapeTests {

    public static void main(String[] args) {
        basicLandscapeAccessTests();
        landscapeBehaviorTests();
    }

    // Covers width/height accessors for constructor pairs
    private static void basicLandscapeAccessTests() {

        // Confirms constructor wires width height
        // case 1: testing Landscape(int, int)
        {
            Landscape small = new Landscape(25, 50);
            Landscape large = new Landscape(200, 150);

            boolean passed = (small != null) && (large != null);
            System.out.println("Constructed landscapes: " + passed);
            assert passed : "Error in Landscape::Landscape(int, int)";
        }

        // Verifies accessor returns supplied width
        // case 2: testing getWidth()
        {
            Landscape landscape = new Landscape(125, 60);
            boolean passed = landscape.getWidth() == 125;
            System.out.println("Landscape width is preserved: " + passed);
            assert passed : "Error in Landscape::getWidth()";
        }

        // Verifies accessor returns supplied height
        // case 3: testing getHeight()
        {
            Landscape landscape = new Landscape(80, 140);
            boolean passed = landscape.getHeight() == 140;
            System.out.println("Landscape height is preserved: " + passed);
            assert passed : "Error in Landscape::getHeight()";
        }
    }

    // Exercises addAgent, getNeighbors, draw, toString
    private static void landscapeBehaviorTests() {
        constructorEchoesDimensions();
        addingAgentsUpdatesNeighbors();
        getNeighborsDistancesWork();
        drawingInvokesAgentRendering();
    }

    // Ensures constructor stores width/height for getters
    private static void constructorEchoesDimensions() {
        Landscape scape = new Landscape(100, 80);
        int w = scape.getWidth();
        int h = scape.getHeight();
        System.out.println("Landscape created: width = " + w + ", height = " + h);
        assert scape != null : "Error: Landscape object nonexistent";
        assert w == 100 : "Error: getWidth() returned " + w + " instead of 100";
        assert h == 80 : "Error: getHeight() returned " + h + " instead of 80";
    }

    // Confirms addAgent surfaces through neighbors/toString
    private static void addingAgentsUpdatesNeighbors() {
        Landscape scape = new Landscape(100, 80);
        Agent a1 = new SocialAgent(10.0, 10.0, 5);
        scape.addAgent(a1);
        String desc = scape.toString();
        System.out.println("Landscape description after addAgent: " + desc);

        LinkedList<Agent> neighbors = scape.getNeighbors(10.0, 10.0, 1.0);
        assert neighbors.size() == 1 :
                "Error: Expected 1 agent in neighbors after addAgent, but got " + neighbors.size();
        assert neighbors.get(0) == a1 :
                "Error: The neighbor is not the same Agent that was added.";
        assert desc.contains("1") : "Landscape::toString should reflect agent count";
    }

    // Validates getNeighbors filters by distance accurately
    private static void getNeighborsDistancesWork() {
        Landscape scape = new Landscape(200, 200);
        Agent center = new SocialAgent(10.0, 10.0, 5);
        Agent close = new SocialAgent(13.0, 14.0, 5);
        Agent far = new SocialAgent(30.0, 30.0, 5);

        scape.addAgent(center);
        scape.addAgent(close);
        scape.addAgent(far);

        LinkedList<Agent> neighbors = scape.getNeighbors(10.0, 10.0, 5.0);
        System.out.println("Neighbors of (10,10) within radius 5: size = " + neighbors.size());

        int nSize = neighbors.size();
        assert nSize == 2 :
                "Error: getNeighbors returned " + nSize + " neighbors, expected 2";

        boolean foundCenter = false;
        boolean foundClose = false;
        for (int i = 0; i < nSize; i++) {
            Agent a = neighbors.get(i);
            if (a == center) foundCenter = true;
            if (a == close) foundClose = true;
            assert a != far : "Far agent appeared in neighbor list";
        }

        assert foundCenter : "Center agent missing from neighbors";
        assert foundClose : "Close agent missing from neighbors";
    }

    // Ensures draw() hits Graphics context via agents
    private static void drawingInvokesAgentRendering() {
        Landscape scape = new Landscape(50, 50);
        SocialAgent agent = new SocialAgent(2.0, 2.0, 5);
        scape.addAgent(agent);

        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        scape.draw(g2);
        g2.dispose();

        int sampleX = (int) agent.getX() + 2;
        int sampleY = (int) agent.getY() + 2;
        int rgb = img.getRGB(sampleX, sampleY);
        Color c = new Color(rgb, true);
        System.out.println("Color drawn at (" + sampleX + ", " + sampleY + "): " + c);

        assert c.getAlpha() != 0 :
                "Error: draw(Graphics) did not modify the image at the expected location.";
        assert c.getBlue() > 0 :
                "Error: Expected a blue-ish color at the agent location, but got " + c;
    }
}