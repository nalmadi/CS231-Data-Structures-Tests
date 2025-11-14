// UpdateStateExtraTests.java

public class UpdateStateExtraTests {

    // SocialAgent: moves when it has fewer than 4 neighbors, and stays inside the Landscape bounds.
    {
        // Given
        Landscape scape = new Landscape(100, 100);
        SocialAgent agent = new SocialAgent(95.0, 95.0, 10);
        scape.addAgent(agent);

        double oldX = agent.getX();
        double oldY = agent.getY();

        // When
        agent.updateState(scape);  
        double newX = agent.getX();
        double newY = agent.getY();

        System.out.println("SocialAgent before updateState: (" + oldX + ", " + oldY + ")");
        System.out.println("SocialAgent after  updateState: (" + newX + ", " + newY + ")");
        System.out.println("SocialAgent moved flag: " + agent.getMoved());

        // Then
        assert agent.getMoved() : "Error: SocialAgent should have moved when isolated.";

        assert newX >= 0 && newX <= scape.getWidth() : "Error: SocialAgent x-coordinate out of bounds: " + newX;
        assert newY >= 0 && newY <= scape.getHeight() : "Error: SocialAgent y-coordinate out of bounds: " + newY;
    }

    // AntiSocialAgent: does NOT move when it has 0 neighbors.
    {
        // Given
        Landscape scape = new Landscape(100, 100);
        AntiSocialAgent loner = new AntiSocialAgent(20.0, 20.0, 10);
        scape.addAgent(loner);

        double oldX = loner.getX();
        double oldY = loner.getY();

        // When
        loner.updateState(scape);  
        double newX = loner.getX();
        double newY = loner.getY();

        System.out.println("AntiSocialAgent (no neighbors) before: (" + oldX + ", " + oldY + ")");
        System.out.println("AntiSocialAgent (no neighbors) after : (" + newX + ", " + newY + ")");
        System.out.println("AntiSocialAgent moved flag: " + loner.getMoved());

        // Then
        assert !loner.getMoved() : "Error: AntiSocialAgent should NOT move with 0 neighbors.";
        assert newX == oldX && newY == oldY : "Error: AntiSocialAgent position changed even though it should not move.";
    }

    // AntiSocialAgent: DOES move when it has more than 1 neighbor within its radius.
    {
        // Given
        Landscape scape = new Landscape(100, 100);

        AntiSocialAgent center = new AntiSocialAgent(50.0, 50.0, 10);
        AntiSocialAgent neighbor1 = new AntiSocialAgent(52.0, 52.0, 10);
        AntiSocialAgent neighbor2 = new AntiSocialAgent(53.0, 51.0, 10);

        scape.addAgent(center);
        scape.addAgent(neighbor1);
        scape.addAgent(neighbor2);

        double oldX = center.getX();
        double oldY = center.getY();

        // When
        center.updateState(scape);  // more than 1 neighbor should move
        double newX = center.getX();
        double newY = center.getY();

        System.out.println("AntiSocialAgent center before: (" + oldX + ", " + oldY + ")");
        System.out.println("AntiSocialAgent center after : (" + newX + ", " + newY + ")");
        System.out.println("AntiSocialAgent center moved flag: " + center.getMoved());

        // Then
        assert center.getMoved() : "Error: AntiSocialAgent should move with more than 1 neighbor.";

        assert newX >= 0 && newX <= scape.getWidth() :
                "Error: AntiSocialAgent x-coordinate out of bounds: " + newX;
        assert newY >= 0 && newY <= scape.getHeight() :
                "Error: AntiSocialAgent y-coordinate out of bounds: " + newY;
    }
}
