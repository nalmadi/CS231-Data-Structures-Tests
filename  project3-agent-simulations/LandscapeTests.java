import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class LandscapeTests {

    // Testing Landscape(int, int) constructor, getWidth(), getHeight()
    {
        // Given
        Landscape scape = new Landscape(100, 80);

        // When
        int w = scape.getWidth();
        int h = scape.getHeight();
        System.out.println("Landscape created: width = " + w + ", height = " + h);

        // Then
        assert scape != null : "Error: Landscape object nonexistent";
        assert w == 100 : "Error: getWidth() returned " + w + " instead of 100";
        assert h == 80  : "Error: getHeight() returned " + h + " instead of 80";
    }

    // Testing addAgent(Agent) and toString()
    {
        // Given
        Landscape scape = new Landscape(100, 80);
        Agent a1 = new SocialAgent(10.0, 10.0, 5);

        // When
        scape.addAgent(a1);
        String desc = scape.toString();
        System.out.println("Landscape description after addAgent: " + desc);

        // Then
        // We expect at least 1 agent now.
        LinkedList<Agent> neighbors = scape.getNeighbors(10.0, 10.0, 1.0);
        assert neighbors.size() == 1 :
                "Error: Expected 1 agent in neighbors after addAgent, but got " + neighbors.size();
        assert neighbors.get(0) == a1 :
                "Error: The neighbor is not the same Agent that was added.";

        // should at least mention the number of agents:
        assert desc.contains("1") :
                "Warning: toString() should probably indicate number of agents; got: " + desc;
    }

    // Testing getNeighbors(double, double, int) with multiple agents
    {
        // Given
        Landscape scape = new Landscape(200, 200);

        Agent center = new SocialAgent(10.0, 10.0, 5);
        Agent close  = new SocialAgent(13.0, 14.0, 5);  
        Agent far    = new SocialAgent(30.0, 30.0, 5);  

        scape.addAgent(center);
        scape.addAgent(close);
        scape.addAgent(far);

        // When
        LinkedList<Agent> neighbors = scape.getNeighbors(10.0, 10.0, 5.0);
        System.out.println("Neighbors of (10,10) within radius 5: size = " + neighbors.size());

        // Then
        // Expect: center and close are neighbors, far is not.
        int nSize = neighbors.size();
        assert nSize == 2 :
                "Error: getNeighbors returned " + nSize + " neighbors, expected 2";

        boolean foundCenter = false;
        boolean foundClose  = false;
        for (int i = 0; i < nSize; i++) {
            Agent a = neighbors.get(i);
            if (a == center) foundCenter = true;
            if (a == close)  foundClose  = true;
            assert a != far : "Error: 'far' agent should not be in neighbors list.";
        }

        assert foundCenter : "Error: center agent not found in neighbors list.";
        assert foundClose  : "Error: close agent not found in neighbors list.";
    }

    // Testing draw(Graphics g), should call draw on all agents
    {
        // Given
        Landscape scape = new Landscape(50, 50);
        SocialAgent agent = new SocialAgent(2.0, 2.0, 5);
        scape.addAgent(agent);

        BufferedImage img = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        // When
        scape.draw(g2);
        g2.dispose();

        int sampleX = (int) agent.getX() + 2;  
        int sampleY = (int) agent.getY() + 2;
        int rgb = img.getRGB(sampleX, sampleY);
        Color c = new Color(rgb, true);

        System.out.println("Color drawn at (" + sampleX + ", " + sampleY + "): " + c);

        // Then
        // A new SocialAgent should be "not moved", so it is dark blue
        // We expect a non-transparent pixel with blue component.
        assert c.getAlpha() != 0 :
                "Error: draw(Graphics) did not modify the image at the expected location.";
        assert c.getBlue() > 0 :
                "Error: Expected a blue-ish color at the agent location, but got " + c;
    }
}
