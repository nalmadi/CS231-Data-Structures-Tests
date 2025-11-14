/*
file name:      UpdateStateTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea UpdateStateTests
*/

public class UpdateStateTests {

    // Helps spawn neighbor agents for scenarios
    private static void addNeighbor(Landscape scape, double x, double y, int radius, boolean social) {
        if (social) {
            scape.addAgent(new SocialAgent(x, y, radius));
        } else {
            scape.addAgent(new AntiSocialAgent(x, y, radius));
        }
    }

    public static double updateStateTests() {
        double score = 0.;

        // Validates social agents stay crowded
        // case 1: social agent with many neighbors should not move
        {
            Landscape scape = new Landscape(100, 100);
            SocialAgent agent = new SocialAgent(10.0, 10.0, 5);
            scape.addAgent(agent);
            addNeighbor(scape, 12.0, 10.0, 5, true);
            addNeighbor(scape, 8.0, 12.0, 5, true);
            addNeighbor(scape, 9.0, 8.5, 5, true);
            addNeighbor(scape, 11.0, 13.0, 5, true);

            agent.updateState(scape);

            boolean passed = (Math.abs(agent.getX() - 10.0) < 1e-6) && (Math.abs(agent.getY() - 10.0) < 1e-6)
                    && (!agent.getMoved());
            System.out.println("Social agent remains still with >=4 neighbors: " + passed);
            assert passed : "Error in SocialAgent::updateState() when surrounded by neighbors";
            if (passed) {
                score += 0.75;
            }
        }

        // Ensures antisocial agents flee crowds
        // case 2: antisocial agent with many neighbors should move
        {
            Landscape scape = new Landscape(100, 100);
            AntiSocialAgent agent = new AntiSocialAgent(10.0, 10.0, 5);
            scape.addAgent(agent);
            addNeighbor(scape, 12.0, 10.0, 5, true);
            addNeighbor(scape, 8.0, 12.0, 5, true);
            addNeighbor(scape, 13.0, 11.0, 5, true);
            addNeighbor(scape, 7.5, 9.0, 5, true);

            agent.updateState(scape);

            boolean passed = (Math.abs(agent.getX() - 10.0) > 1e-6) && (Math.abs(agent.getY() - 10.0) > 1e-6)
                    && (agent.getMoved());
            System.out.println("Anti-social agent moves with many neighbors: " + passed);
            assert passed : "Error in AntiSocialAgent::updateState() when crowded";
            if (passed) {
                score += 0.75;
            }
        }

        // Confirms lonely social agents wander
        // case 3: social agent with few neighbors should move
        {
            Landscape scape = new Landscape(100, 100);
            SocialAgent agent = new SocialAgent(10.0, 10.0, 5);
            scape.addAgent(agent);

            agent.updateState(scape);

            boolean passed = (Math.abs(agent.getX() - 10.0) > 1e-6) && (Math.abs(agent.getY() - 10.0) > 1e-6)
                    && (agent.getMoved());
            System.out.println("Social agent wanders when isolated: " + passed);
            assert passed : "Error in SocialAgent::updateState() when isolated";
            if (passed) {
                score += 0.75;
            }
        }

        // Checks isolated antisocial agents idle
        // case 4: antisocial agent with few neighbors should stay put
        {
            Landscape scape = new Landscape(100, 100);
            AntiSocialAgent agent = new AntiSocialAgent(10.0, 10.0, 5);
            scape.addAgent(agent);

            agent.updateState(scape);

            boolean passed = (Math.abs(agent.getX() - 10.0) < 1e-6) && (Math.abs(agent.getY() - 10.0) < 1e-6)
                    && (!agent.getMoved());
            System.out.println("Anti-social agent stays put with <=1 neighbor: " + passed);
            assert passed : "Error in AntiSocialAgent::updateState() when isolated";
            if (passed) {
                score += 0.75;
            }
        }

        return score;
    }

    public static void main(String[] args) {
        System.out.println(updateStateTests());
    }
}
