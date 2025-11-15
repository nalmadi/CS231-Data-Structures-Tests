/*
file name:      AgentTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea AgentTests
*/

public class AgentTests {

    public static void agentTests() {

        // Verifies constructors seed initial state
        // case 1: testing constructors
        {
            SocialAgent social = new SocialAgent(10.0, 20.0, 5);
            AntiSocialAgent anti = new AntiSocialAgent(30.5, 40.5, 8);

            boolean passed = (social.getX() == 10.0) && (social.getY() == 20.0) && (social.getRadius() == 5)
                    && (anti.getX() == 30.5) && (anti.getY() == 40.5) && (anti.getRadius() == 8);

            System.out.println("Constructors initialize position and radius: " + passed);
            assert passed : "Error in SocialAgent/AntiSocialAgent constructors";
        }

        // Confirms getX exposes stored coordinate
        // case 2: testing getX()
        {
            SocialAgent social = new SocialAgent(5.5, 7.5, 3);
            AntiSocialAgent anti = new AntiSocialAgent(2.25, 4.75, 6);

            boolean passed = (Math.abs(social.getX() - 5.5) < 1e-6) && (Math.abs(anti.getX() - 2.25) < 1e-6);
            System.out.println("getX returns the stored x-position: " + passed);
            assert passed : "Error in Agent::getX()";
        }

        // Confirms getY mirrors constructor position
        // case 3: testing getY()
        {
            SocialAgent social = new SocialAgent(1.0, 9.0, 4);
            AntiSocialAgent anti = new AntiSocialAgent(8.0, 3.5, 5);

            boolean passed = (Math.abs(social.getY() - 9.0) < 1e-6) && (Math.abs(anti.getY() - 3.5) < 1e-6);
            System.out.println("getY returns the stored y-position: " + passed);
            assert passed : "Error in Agent::getY()";
        }

        // Validates radius getter returns latest
        // case 4: testing getRadius()
        {
            SocialAgent social = new SocialAgent(0.0, 0.0, 12);
            AntiSocialAgent anti = new AntiSocialAgent(0.0, 0.0, 20);

            boolean passed = (social.getRadius() == 12) && (anti.getRadius() == 20);
            System.out.println("getRadius returns the stored radius: " + passed);
            assert passed : "Error in Agent::getRadius()";
        }

        // Ensures setX mutator persists updates
        // case 5: testing setX()
        {
            SocialAgent social = new SocialAgent(0.0, 0.0, 5);
            AntiSocialAgent anti = new AntiSocialAgent(0.0, 0.0, 5);
            social.setX(15.75);
            anti.setX(22.25);

            boolean passed = (Math.abs(social.getX() - 15.75) < 1e-6) && (Math.abs(anti.getX() - 22.25) < 1e-6);
            System.out.println("setX updates the x-position: " + passed);
            assert passed : "Error in Agent::setX()";
        }

        // Ensures setY mutator persists updates
        // case 6: testing setY()
        {
            SocialAgent social = new SocialAgent(0.0, 0.0, 5);
            AntiSocialAgent anti = new AntiSocialAgent(0.0, 0.0, 5);
            social.setY(31.5);
            anti.setY(42.25);

            boolean passed = (Math.abs(social.getY() - 31.5) < 1e-6) && (Math.abs(anti.getY() - 42.25) < 1e-6);
            System.out.println("setY updates the y-position: " + passed);
            assert passed : "Error in Agent::setY()";
        }

        // Confirms setRadius adjusts accessors
        // case 7: testing setRadius()
        {
            SocialAgent social = new SocialAgent(0.0, 0.0, 5);
            AntiSocialAgent anti = new AntiSocialAgent(0.0, 0.0, 5);
            social.setRadius(18);
            anti.setRadius(7);

            boolean passed = (social.getRadius() == 18) && (anti.getRadius() == 7);
            System.out.println("setRadius updates the radius: " + passed);
            assert passed : "Error in Agent::setRadius()";
        }

        // Checks default moved flag remains
        // case 8: testing getMoved()
        {
            SocialAgent social = new SocialAgent(0.0, 0.0, 5);
            AntiSocialAgent anti = new AntiSocialAgent(0.0, 0.0, 5);

            boolean passed = (!social.getMoved()) && (!anti.getMoved());
            System.out.println("Agents default to not having moved: " + passed);
            assert passed : "Error in Agent::getMoved()";
        }
    }

    public static void main(String[] args) {
        agentTests();
    }
}
