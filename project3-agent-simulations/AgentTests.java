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
        public TestAgent(double x, double y, int r) { super(x, y, r); }
        public void updateState(Landscape scape) { /* do nothing */ }
        public void draw(Graphics g) { /* do nothing */ }
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
            boolean after  = a.hasMoved();

            System.out.println("\n=== case 3: hasMoved() ===");
            System.out.println(before + " == false");
            System.out.println(after  + " == true");

            assert before == false : "Error in Agent::hasMoved() default";
            assert after  == true  : "Error in Agent::hasMoved() after setMove(true)";
        }


        // case 4: testing toString()
        {
            TestAgent a = new TestAgent(0.0, 0.0, 5);

            System.out.println("\n=== case 4: toString() ===");
            System.out.println(a.toString() + " == X:  0.0  Y: 0.0");

            assert a.toString().equals("X:  0.0  Y: 0.0") 
                : "Error in Agent::toString() formatting";
        }

        System.out.println("\n*** Done testing Agent! ***\n");
    }

    

    public static void main(String[] args) {
        agentTests();
    }
}
