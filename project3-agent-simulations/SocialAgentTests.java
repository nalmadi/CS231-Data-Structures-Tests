import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SocialAgentTests {

    // Testing SocialAgent(double, double, int) constructor
    {
        // Given
        SocialAgent agent = new SocialAgent(10.5, 5.5, 5);

        // When
        System.out.println("SocialAgent = " + agent + "; (expected around (10.5, 5.5))");

        // Then
        assert agent != null : "Error: SocialAgent object nonexistent";
        assert agent.getX() == 10.5 : "Error: getX() returned " + agent.getX() + " instead of 10.5";
        assert agent.getY() == 5.5 : "Error: getY() returned " + agent.getY() + " instead of 5.5";
        assert agent.getRadius() == 5 : "Error: getRadius() returned " + agent.getRadius() + " instead of 5";

        // Assuming 'moved' is initialized to false in the constructor
        assert agent.getMoved() == false : "Error: new SocialAgent should not be marked as moved";
    }

    // Testing draw(Graphics) when the agent has NOT moved
    {
        // Given
        SocialAgent agent = new SocialAgent(2.0, 2.0, 5);  
        BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        // When
        // Agent has not moved yet, so moved == false.
        agent.draw(g2);
        g2.dispose();

        int sampleX = (int) agent.getX() + 2;  
        int sampleY = (int) agent.getY() + 2;
        int rgb = img.getRGB(sampleX, sampleY);
        Color c = new Color(rgb, true);

        System.out.println("Color for not-moved SocialAgent at (" + sampleX + ", " + sampleY + "): " + c);

        // Then
        // Expect dark blue
        assert c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 255 :
                "Error: draw() for not-moved SocialAgent should be dark blue (0,0,255), but was " + c;
    }

    // Testing draw(Graphics g) when the agent HAS moved
    {
        // Given
        SocialAgent agent = new SocialAgent(2.0, 2.0, 5);

        agent.moved = true;   // <-- if this doesn't compile, you must  trigger 'moved = true' via updateState(...)

        BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();

        // When
        agent.draw(g2);
        g2.dispose();

        int sampleX = (int) agent.getX() + 2;   
        int sampleY = (int) agent.getY() + 2;
        int rgb = img.getRGB(sampleX, sampleY);
        Color c = new Color(rgb, true);

        System.out.println("Color for moved SocialAgent at (" + sampleX + ", " + sampleY + "): " + c);

        // Then
        // Expect lighter blue
        assert c.getRed() == 125 && c.getGreen() == 125 && c.getBlue() == 255 :
                "Error: draw() for moved SocialAgent should be light blue (125,125,255), but was " + c;
    }

}
