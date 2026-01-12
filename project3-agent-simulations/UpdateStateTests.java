/*
file name:      AgentStateTests.java
Authors:        Rana Moeez Hassan, Yubin Moon, Rishit Chatterjee, Robbie Bennett
last modified:  11/17/2025

How to run:     java -ea UpdateStateTests
*/

public class UpdateStateTests {

    // Helper to add neighbors
    private static void addNeighbor(Landscape scape, double x, double y, int radius, boolean social) {
        if (social) scape.addAgent(new SocialAgent(x, y, radius));
        else        scape.addAgent(new AntiSocialAgent(x, y, radius));
    }

    // TEST CASES
    public static void main(String[] args) {

        // CASE 1: updateAgents() replaces one agent with an AntiSocialAgent
        {
            // Given
            Landscape scape = new Landscape(200, 200);
            SocialAgent a1 = new SocialAgent(20, 20, 10);
            SocialAgent a2 = new SocialAgent(40, 40, 10);
            SocialAgent a3 = new SocialAgent(60, 60, 10);
            scape.addAgent(a1);
            scape.addAgent(a2);
            scape.addAgent(a3);
            int originalCount = 3;

            // When
            int movedCount = scape.updateAgents();

            // Then
            LinkedList<Agent> list = scape.getNeighbors(0, 0, 1000);
            assert list.size() == originalCount :
                "Error: updateAgents should keep same number of agents";

            int antiCount = 0;
            for (Agent ag : list)
                if (ag instanceof AntiSocialAgent) antiCount++;

            assert antiCount == 1 :
                "Error: updateAgents should replace exactly one agent with an AntiSocialAgent.";

            boolean matchFound = false;
            for (Agent ag : list) {
                if (ag instanceof AntiSocialAgent) {
                    if ((ag.getX() == 20 && ag.getY() == 20 && ag.getRadius() == 10) ||
                        (ag.getX() == 40 && ag.getY() == 40 && ag.getRadius() == 10) ||
                        (ag.getX() == 60 && ag.getY() == 60 && ag.getRadius() == 10))
                        matchFound = true;
                }
            }

            assert matchFound :
                "Error: AntiSocialAgent did not inherit position/radius from deleted agent.";

            int actualMoved = 0;
            for (Agent ag : list)
                if (ag.getMoved()) actualMoved++;

            assert actualMoved == movedCount :
                "Error: updateAgents() returned movedCount != actual moved agents";
        }

        // CASE 2: SocialAgent moves when isolated (boundary-safe)
        {
            // Given
            Landscape scape = new Landscape(100, 100);
            SocialAgent agent = new SocialAgent(95.0, 95.0, 10);
            scape.addAgent(agent);

            double oldX = agent.getX();
            double oldY = agent.getY();

            // When
            agent.updateState(scape);

            // Then
            assert agent.getMoved() : "Error: SocialAgent should move when isolated";
            assert agent.getX() >= 0 && agent.getX() <= 100 : "SocialAgent x out of bounds";
            assert agent.getY() >= 0 && agent.getY() <= 100 : "SocialAgent y out of bounds";
        }

        // CASE 3: AntiSocialAgent should NOT move when 0 neighbors
        {
            // Given
            Landscape scape = new Landscape(100, 100);
            AntiSocialAgent loner = new AntiSocialAgent(20, 20, 10);
            scape.addAgent(loner);

            double oldX = loner.getX();
            double oldY = loner.getY();

            // When
            loner.updateState(scape);

            // Then
            assert !loner.getMoved() : "AntiSocialAgent should NOT move with 0 neighbors.";
            assert loner.getX() == oldX && loner.getY() == oldY :
                "AntiSocialAgent moved even though it should not.";
        }

        // CASE 4: AntiSocialAgent moves when >1 neighbors inside radius
        {
            // Given
            Landscape scape = new Landscape(100, 100);
            AntiSocialAgent center = new AntiSocialAgent(50, 50, 10);
            scape.addAgent(center);

            scape.addAgent(new AntiSocialAgent(52, 52, 10));
            scape.addAgent(new AntiSocialAgent(53, 51, 10));

            double oldX = center.getX();
            double oldY = center.getY();

            // When
            center.updateState(scape);

            // Then
            assert center.getMoved() : "AntiSocialAgent should move when >1 neighbors exist.";
            assert center.getX() >= 0 && center.getX() <= 100 : "AntiSocialAgent x out of bounds";
            assert center.getY() >= 0 && center.getY() <= 100 : "AntiSocialAgent y out of bounds";
        }

        // CASE 5: SocialAgent with >=4 neighbors should NOT move
        {
            // Given
            Landscape scape = new Landscape(100, 100);
            SocialAgent agent = new SocialAgent(10, 10, 5);
            scape.addAgent(agent);

            addNeighbor(scape, 12, 10, 5, true);
            addNeighbor(scape, 8, 12, 5, true);
            addNeighbor(scape, 9, 8.5, 5, true);
            addNeighbor(scape, 11, 13, 5, true);

            // When
            agent.updateState(scape);

            // Then
            assert !agent.getMoved() : "SocialAgent should NOT move with >=4 neighbors.";
            assert Math.abs(agent.getX() - 10) < 1e-6 :
                "SocialAgent X changed when it should not.";
            assert Math.abs(agent.getY() - 10) < 1e-6 :
                "SocialAgent Y changed when it should not.";
        }

        // CASE 6: AntiSocialAgent with many neighbors should move
        {
            // Given
            Landscape scape = new Landscape(100, 100);
            AntiSocialAgent agent = new AntiSocialAgent(10, 10, 5);
            scape.addAgent(agent);

            addNeighbor(scape, 12, 10, 5, true);
            addNeighbor(scape, 8, 12, 5, true);
            addNeighbor(scape, 13, 11, 5, true);
            addNeighbor(scape, 7.5, 9, 5, true);

            double oldX = agent.getX();
            double oldY = agent.getY();

            // When
            agent.updateState(scape);

            // Then
            assert agent.getMoved() : "AntiSocialAgent should move when crowded";
            assert Math.abs(agent.getX() - oldX) > 1e-6 ||
                   Math.abs(agent.getY() - oldY) > 1e-6 :
                   "AntiSocialAgent did not actually move.";
        }

        // CASE 7: SocialAgent isolated should move (duplicate consolidated)
        {
            // Given
            Landscape scape = new Landscape(100, 100);
            SocialAgent agent = new SocialAgent(10, 10, 5);
            scape.addAgent(agent);

            double oldX = agent.getX();
            double oldY = agent.getY();

            // When
            agent.updateState(scape);

            // Then
            assert agent.getMoved() : "SocialAgent should move when isolated.";
            assert Math.abs(agent.getX() - oldX) > 1e-6 ||
                   Math.abs(agent.getY() - oldY) > 1e-6 :
                   "SocialAgent did not actually move.";
        }

        // CASE 8: AntiSocialAgent isolated should NOT move
        {
            // Given
            Landscape scape = new Landscape(100, 100);
            AntiSocialAgent agent = new AntiSocialAgent(10, 10, 5);
            scape.addAgent(agent);

            double oldX = agent.getX();
            double oldY = agent.getY();

            // When
            agent.updateState(scape);

            // Then
            assert !agent.getMoved() : "AntiSocialAgent should stay put when isolated.";
            assert Math.abs(agent.getX() - oldX) < 1e-6 &&
                   Math.abs(agent.getY() - oldY) < 1e-6 :
                   "AntiSocialAgent incorrectly moved.";
        }

        System.out.println("All tests passed!");
    }
}
