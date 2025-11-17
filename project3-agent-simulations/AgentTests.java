/*
file name:      AgentTests.java
Authors:        Robbie Bennett, Rishit Chatterjee, Rana Moeez Hassan
last modified:  11/17/2025

How to run:
    javac *.java
    java -ea AgentTests
*/

import java.awt.Graphics;

public class AgentTests {

    // Simple child class to allow instantiation of abstract Agent
    private static class TestAgent extends Agent {
        public TestAgent(double x, double y, int r) {
            super(x, y, r);
        }

        public void updateState(Landscape scape) {
            /* do nothing */ }

        public void draw(Graphics g) {
            /* do nothing */ }
    }

    public static void agentTests() {

        // First four tests test the Agent class
        // case 1: constructor + getters
        {
            // given
            TestAgent a = new TestAgent(10.0, 20.0, 5);

            // when
            System.out.println("=== case 1: constructor + getters ===");
            System.out.println(a.getX() + " == 10.0");
            System.out.println(a.getY() + " == 20.0");
            System.out.println(a.getRadius() + " == 5");
            System.out.println(a.getMoved() + " == false");

            // then
            assert a.getX() == 10.0 : "Error in Agent::getX() or constructor";
            assert a.getY() == 20.0 : "Error in Agent::getY() or constructor";
            assert a.getRadius() == 5 : "Error in Agent::getRadius() or constructor";
            assert a.getMoved() == false : "Error in Agent::getMoved() default";
        }

        // case 2: testing setX(), setY(), setRadius(), setMove()
        {
            TestAgent a = new TestAgent(0.0, 0.0, 1);

            a.setX(7.5);
            a.setY(9.2);
            a.setRadius(12);
            a.setMove(true);

            System.out.println("\n=== case 2: setters ===");
            System.out.println(a.getX() + " == 7.5");
            System.out.println(a.getY() + " == 9.2");
            System.out.println(a.getRadius() + " == 12");
            System.out.println(a.getMoved() + " == true");

            assert a.getX() == 7.5 : "Error in Agent::setX or getX";
            assert a.getY() == 9.2 : "Error in Agent::setY or getY";
            assert a.getRadius() == 12 : "Error in Agent::setRadius or getRadius";
            assert a.getMoved() == true : "Error in Agent::setMove or getMoved";
        }

        // case 3: testing hasMoved()
        {
            TestAgent a = new TestAgent(3.0, 4.0, 5);

            boolean before = a.hasMoved();
            a.setMove(true);
            boolean after = a.hasMoved();

            System.out.println("\n=== case 3: hasMoved() ===");
            System.out.println(before + " == false");
            System.out.println(after + " == true");

            assert before == false : "Error in Agent::hasMoved() default";
            assert after == true : "Error in Agent::hasMoved() after setMove(true)";
        }

        // case 4: testing toString()
        {
            TestAgent a = new TestAgent(0.0, 0.0, 5);

            System.out.println("\n=== case 4: toString() ===");
            System.out.println(a.toString() + " == X:  0.0  Y: 0.0");

            assert a.toString().equals("X:  0.0  Y: 0.0")
                    : "Error in Agent::toString() formatting";
        }

        // case 5: testing setMove() method
        {
            TestAgent a = new TestAgent(0.0, 0.0, 5);

            System.out.println("\n=== case 5: setMove() method ===");
            System.out.println(a.getMoved() + " == false (initial)");

            a.setMove(true);
            System.out.println(a.getMoved() + " == true (after setMove(true))");

            a.setMove(false);
            System.out.println(a.getMoved() + " == false (after setMove(false))");

            assert !a.getMoved() : "Error in Agent::setMove(false)";
            assert a.getMoved() == a.hasMoved() : "Error: getMoved() and hasMoved() should return same value";
        }

        // case 6: testing edge cases (negative values, zero values)
        {
            TestAgent negAgent = new TestAgent(-10.5, -20.3, 0);
            TestAgent zeroSocial = new TestAgent(0.0, 0.0, 0);

            System.out.println("\n=== case 6: edge cases ===");
            System.out.println(negAgent.getX() + " == -10.5");
            System.out.println(negAgent.getY() + " == -20.3");
            System.out.println(negAgent.getRadius() + " == 0");
            System.out.println(zeroSocial.getX() + " == 0.0");
            System.out.println(zeroSocial.getY() + " == 0.0");

            assert negAgent.getX() == -10.5 : "Error handling negative X";
            assert negAgent.getY() == -20.3 : "Error handling negative Y";
            assert negAgent.getRadius() == 0 : "Error handling zero radius";
            assert zeroSocial.getX() == 0.0 : "Error handling zero X";
            assert zeroSocial.getY() == 0.0 : "Error handling zero Y";
        }

        // case 7: testing multiple setters in sequence
        {
            TestAgent agent = new TestAgent(0.0, 0.0, 1);

            agent.setX(5.0);
            agent.setY(10.0);
            agent.setRadius(3);
            agent.setMove(true);

            System.out.println("\n=== case 7: multiple setters ===");
            System.out.println(agent.getX() + " == 5.0");
            System.out.println(agent.getY() + " == 10.0");
            System.out.println(agent.getRadius() + " == 3");
            System.out.println(agent.getMoved() + " == true");

            // Update again
            agent.setX(15.5);
            agent.setY(25.7);
            agent.setRadius(8);
            agent.setMove(false);

            System.out.println(agent.getX() + " == 15.5 (second update)");
            System.out.println(agent.getY() + " == 25.7 (second update)");
            System.out.println(agent.getRadius() + " == 8 (second update)");
            System.out.println(agent.getMoved() + " == false (second update)");

            assert agent.getX() == 15.5 : "Error in multiple setX calls";
            assert agent.getY() == 25.7 : "Error in multiple setY calls";
            assert agent.getRadius() == 8 : "Error in multiple setRadius calls";
            assert !agent.getMoved() : "Error in multiple setMove calls";
        }

        System.out.println("\n*** Done testing Agent! ***\n");
    }

    public static void main(String[] args) {
        agentTests();
    }
}
