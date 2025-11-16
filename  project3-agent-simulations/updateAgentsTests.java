public class UpdateAgentsTests {

   
    // Testing updateAgents() behavior:
    // 1. Removes one random agent
    // 2. Replace with AntiSocialAgent with same x/y/radius
    // 3. Update all agents and returns number moved
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

        System.out.println("updateAgents() returned moved count = " + movedCount);

        // Then -----------------------------------------------------

        // Still exactly 3 agents
        LinkedList<Agent> list = scape.getNeighbors(0, 0, 1000); // gets all agents
        assert list.size() == originalCount :
                "Error: updateAgents should keep same number of agents";

        // Exactly one AntiSocialAgent must exist
        int antiCount = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof AntiSocialAgent)
                antiCount++;
        }

        assert antiCount == 1 :
                "Error: updateAgents should replace exactly one agent with an AntiSocialAgent; found " + antiCount;

        // AntiSocialAgent should have coordinates and radius matching one of the old agents
        boolean matchFound = false;

        for (int i = 0; i < list.size(); i++) {
            Agent a = list.get(i);

            if (a instanceof AntiSocialAgent) {
                double ax = a.getX();
                double ay = a.getY();
                int ar = a.getRadius();

                // check for matches
                if ((ax == 20 && ay == 20 && ar == 10) ||
                    (ax == 40 && ay == 40 && ar == 10) ||
                    (ax == 60 && ay == 60 && ar == 10))
                {
                    matchFound = true;
                }
            }
        }

        assert matchFound :
                "Error: AntiSocialAgent did not inherit position/radius from deleted agent.";

        // Count should match actual moved-value states
        int actualMoved = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMoved())
                actualMoved++;
        }

        assert actualMoved == movedCount :
                "Error: updateAgents() returned movedCount = " + movedCount +
                " but actually moved = " + actualMoved;
    }
}
