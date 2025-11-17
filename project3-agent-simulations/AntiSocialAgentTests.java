/*
file name:      AntiSocialAgentTests.java
Authors:        Robbie Bennett
last modified:  11/14/2025

How to run:
    1. javac *.java
    2. java -ea AntiSocialAgentTests
*/

public class AntiSocialAgentTests {

    public static void antiSocialAgentTests() {

        // case 1: testing constructor + basic getters (Agent fields via child)
        {
            // given
            AntiSocialAgent a = new AntiSocialAgent(10.0, 20.0, 5);

            // when
            System.out.println("=== case 1: constructor + getters ===");
            System.out.println(a.getX() + " == 10.0");
            System.out.println(a.getY() + " == 20.0");
            System.out.println(a.getRadius() + " == 5");
            System.out.println(a.getMoved() + " == false");

            // then
            assert a != null : "Error in AntiSocialAgent::AntiSocialAgent()";
            assert a.getX() == 10.0 : "Error in Agent::getX() or constructor";
            assert a.getY() == 20.0 : "Error in Agent::getY() or constructor";
            assert a.getRadius() == 5 : "Error in Agent::getRadius() or constructor";
            assert a.getMoved() == false : "Error in Agent::getMoved() default value";
        }

        // case 2: testing setters (setX, setY, setRadius, setMove)
        {
            // given
            AntiSocialAgent a = new AntiSocialAgent(0.0, 0.0, 1);

            // when
            a.setX(5.5);
            a.setY(7.5);
            a.setRadius(10);
            a.setMove(true);

            System.out.println("\n=== case 2: setters ===");
            System.out.println(a.getX() + " == 5.5");
            System.out.println(a.getY() + " == 7.5");
            System.out.println(a.getRadius() + " == 10");
            System.out.println(a.getMoved() + " == true");

            // then
            assert a.getX() == 5.5 : "Error in Agent::setX() or getX()";
            assert a.getY() == 7.5 : "Error in Agent::setY() or getY()";
            assert a.getRadius() == 10 : "Error in Agent::setRadius() or getRadius()";
            assert a.getMoved() == true : "Error in Agent::setMove() or getMoved()";
        }

        // case 3: testing hasMoved() reflects moved flag
        {
            // given
            AntiSocialAgent a = new AntiSocialAgent(1.0, 1.0, 5);

            // when
            boolean moved1 = a.hasMoved();
            a.setMove(true);
            boolean moved2 = a.hasMoved();

            System.out.println("\n=== case 3: hasMoved() ===");
            System.out.println(moved1 + " == false");
            System.out.println(moved2 + " == true");

            // then
            assert moved1 == false : "Error in Agent::hasMoved() or default moved value";
            assert moved2 == true  : "Error in Agent::hasMoved() or setMove(true)";
        }


        // case 4: updateState() with NO neighbors (agent should not move)

        {
            // given
            Landscape scape = new Landscape(100, 100);
            AntiSocialAgent a = new AntiSocialAgent(50.0, 50.0, 10);
            scape.addAgent(a);

            // mark as moved first, so we can see if updateState resets it
            a.setMove(true);

            double oldX = a.getX();
            double oldY = a.getY();

            // when
            a.updateState(scape);

            System.out.println("\n=== case 4: updateState() with no neighbors ===");
            System.out.println(a.getX() + " == " + oldX);
            System.out.println(a.getY() + " == " + oldY);
            System.out.println(a.getMoved() + " == false");

            // then
            assert a.getX() == oldX :
                "Error in AntiSocialAgent::updateState() - position X should not change with no neighbors";
            assert a.getY() == oldY :
                "Error in AntiSocialAgent::updateState() - position Y should not change with no neighbors";
            assert a.getMoved() == false :
                "Error in AntiSocialAgent::updateState() - moved should be false when no neighbors";
        }


        // case 5: updateState() WITH neighbors.

        {
            // given
            Landscape scape = new Landscape(100, 100);
            AntiSocialAgent a1 = new AntiSocialAgent(50.0, 50.0, 10);
            AntiSocialAgent a2 = new AntiSocialAgent(52.0, 50.0, 10);
            AntiSocialAgent a3 = new AntiSocialAgent(48.0, 52.0, 10);

            scape.addAgent(a1);
            scape.addAgent(a2);
            scape.addAgent(a3);

            double oldX = a1.getX();
            double oldY = a1.getY();

            // when
            a1.updateState(scape);

            System.out.println("\n=== case 5: updateState() with neighbors ===");
            System.out.println("oldX = " + oldX + ", newX = " + a1.getX());
            System.out.println("oldY = " + oldY + ", newY = " + a1.getY());
            System.out.println(a1.getMoved() + " == true");
            System.out.println("newX and newY should remain within [0, width/height] bounds");

            // then
            // we cannot predict the exact random movement, but we know:
            // - moved flag should be true
            // - the agent should remain inside the landscape bounds
            assert a1.getMoved() == true :
                "Error in AntiSocialAgent::updateState() - moved should be true when there are neighbors";
            assert a1.getX() >= 0.0 && a1.getX() <= 100.0 :
                "Error in AntiSocialAgent::updateState() - X position out of bounds";
            assert a1.getY() >= 0.0 && a1.getY() <= 100.0 :
                "Error in AntiSocialAgent::updateState() - Y position out of bounds";
        }

        System.out.println("\n*** Done testing AntiSocialAgent! ***\n");
    }


    public static void main(String[] args) {

        antiSocialAgentTests();
    }
}
