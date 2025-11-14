public class AgentTests {

    // Testing Agent(double, double, int) constructor and toString()
    {
        // Given
        Agent agent = new Agent(10.5, 5.5, 2);

        // When
        System.out.println(agent + " = (10.5, 5.5)");

        // Then 
        assert agent != null: "Error: Agent object nonexistent";

    }

     // Testing getX()
     {
        // Given
        Agent agent = new Agent(10.5, 5.5, 2);

        // When
        double x = agent.getX();
        System.out.println("getX() = " + x);

        // Then
        assert x == 10.5 : "Error: getX() returned " + x + " instead of 10.5";
    }

    // Testing getY()
    {
        // Given
        Agent agent = new Agent(10.5, 5.5, 2);

        // When
        double y = agent.getY();
        System.out.println("getY() = " + y);

        // Then
        assert y == 5.5 : "Error: getY() returned " + y + " instead of 5.5";
    }

    // Testing getRadius()
    {
        // Given
        Agent agent = new Agent(10.5, 5.5, 2);

        // When
        int r = agent.getRadius();
        System.out.println("getRadius() = " + r);

        // Then
        assert r == 2 : "Error: getRadius() returned " + r + " instead of 2";
    }

    // Testing getMoved()
    {
        // Given
        Agent agent = new Agent(10.5, 5.5, 2);

        // When
        boolean moved = agent.getMoved();
        System.out.println("getMoved() = " + moved);

        // Then
        // Adjust this expectation depending on how you initialize 'moved' in Agent
        assert moved == false : "Error: getMoved() should be false for a new Agent";
    }

    // Testing setX( double newX )
    {
        // Given
        Agent agent = new Agent(10.5, 5.5, 2);

        // When
        agent.setX(20.0);
        double x = agent.getX();
        System.out.println("After setX(20.0), getX() = " + x);

        // Then
        assert x == 20.0 : "Error: setX() failed, x = " + x + " instead of 20.0";
    }

    // Testing setY( double newY )
    {
        // Given
        Agent agent = new Agent(10.5, 5.5, 2);

        // When
        agent.setY(15.0);
        double y = agent.getY();
        System.out.println("After setY(15.0), getY() = " + y);

        // Then
        assert y == 15.0 : "Error: setY() failed, y = " + y + " instead of 15.0";
    }

    // Testing setRadius( int newRadius )
    {
        // Given
        Agent agent = new Agent(10.5, 5.5, 2);

        // When
        agent.setRadius(10);
        int r = agent.getRadius();
        System.out.println("After setRadius(10), getRadius() = " + r);

        // Then
        assert r == 10 : "Error: setRadius() failed, radius = " + r + " instead of 10";
    }

    // Testing toString()
    {
        // Given
        Agent agent = new Agent(3.024, 4.245, 2);

        // When
        String s = agent.toString();
        System.out.println("toString() = " + s + "  (expected something like (3.024, 4.245))");

        // Then
        // This assumes your toString() returns exactly "(3.024, 4.245)".
        // If you format differently, update the expected string.
        String expected = "(3.024, 4.245)";
        assert s.equals(expected) : "Error: toString() returned \"" + s + "\" instead of \"" + expected + "\"";
    }
}
