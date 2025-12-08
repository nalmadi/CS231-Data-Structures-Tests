/*
file name:      SocialAgentTests.java
Authors:        Yubin Moon, Rana Moeez Hassan
last modified:  11/17/2025

How to run:     java -ea SocialAgentTests
*/

public class SocialAgentTests {

    public static void socialAgentTests() {

        // case 1: Testing SocialAgent constructor
        {
            SocialAgent agent = new SocialAgent(10.5, 5.5, 5);

            System.out.println("=== case 1: constructor ===");
            System.out.println("SocialAgent = " + agent + "; (expected around (10.5, 5.5))");

            assert agent != null : "Error: SocialAgent object nonexistent";
            assert agent.getX() == 10.5 : "Error: getX() returned " + agent.getX() + " instead of 10.5";
            assert agent.getY() == 5.5 : "Error: getY() returned " + agent.getY() + " instead of 5.5";
            assert agent.getRadius() == 5 : "Error: getRadius() returned " + agent.getRadius() + " instead of 5";
            assert agent.getMoved() == false : "Error: new SocialAgent should not be marked as moved";
        }

        // case 2: Testing draw when agent has NOT moved
        {
            SocialAgent agent = new SocialAgent(2.0, 2.0, 5);
            BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();

            agent.draw(g2);
            g2.dispose();

            int sampleX = (int) agent.getX() + 2;
            int sampleY = (int) agent.getY() + 2;
            int rgb = img.getRGB(sampleX, sampleY);
            Color c = new Color(rgb, true);

            System.out.println("\n=== case 2: draw (not moved) ===");
            System.out.println("Color for not-moved SocialAgent at (" + sampleX + ", " + sampleY + "): " + c);

            assert c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 255
                    : "Error: draw() for not-moved SocialAgent should be dark blue (0,0,255), but was " + c;
        }

        // case 3: Testing draw when agent HAS moved
        {
            SocialAgent agent = new SocialAgent(2.0, 2.0, 5);
            agent.setMove(true); // Use proper setter instead of direct field access

            BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();

            agent.draw(g2);
            g2.dispose();

            int sampleX = (int) agent.getX() + 2;
            int sampleY = (int) agent.getY() + 2;
            int rgb = img.getRGB(sampleX, sampleY);
            Color c = new Color(rgb, true);

            System.out.println("\n=== case 3: draw (moved) ===");
            System.out.println("Color for moved SocialAgent at (" + sampleX + ", " + sampleY + "): " + c);

            assert c.getRed() == 125 && c.getGreen() == 125 && c.getBlue() == 255
                    : "Error: draw() for moved SocialAgent should be light blue (125,125,255), but was " + c;
        }
        
        System.out.println("\n*** Done testing SocialAgent! ***\n");
    }

    public static void main(String[] args) {
        socialAgentTests();
    }
}