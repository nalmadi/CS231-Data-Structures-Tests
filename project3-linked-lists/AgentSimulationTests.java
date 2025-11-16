/*
file name:      AgentSimulationTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea AgentSimulationTests
*/

import java.util.Random;

public class AgentSimulationTests {

    // Mirrors simulation loop without display
    public static int runExpt(int N) {
        Random rand = new Random();
        Landscape scape = new Landscape(500, 500);

        for (int i = 0; i < N; i++) {
            double x = rand.nextDouble() * scape.getWidth();
            double y = rand.nextDouble() * scape.getHeight();
            scape.addAgent(new SocialAgent(x, y, 25));
        }

        int iterations = 0;
        int movedAgents = 1;
        while ((movedAgents > 0) && (iterations < 5000)) {
            movedAgents = scape.updateAgents();
            iterations++;
        }
        return iterations;
    }

    public static void agentSimulationTests() {

        // Averages repeated runs for stability
        {
            int[] sizes = {50, 100};
            int[] expected = {234, 541};
            for (int i = 0; i < sizes.length; i++) {
                int totalIterations = 0;
                for (int trial = 0; trial < 20; trial++) {
                    totalIterations += runExpt(sizes[i]);
                }
                int average = totalIterations / 20;
                System.out.println("Average iterations for N=" + sizes[i] + ": " + average);
                assert (average < expected[i] + 100) && (average > expected[i] - 100)
                        : "Simulation did not converge within expected range for N=" + sizes[i];
            }
        }

        // Checks larger population hits timeout
        {
            int iterations = runExpt(250);
            System.out.println("Iterations for N=250: " + iterations);
            boolean passed = iterations == 5000;
            assert passed : "Simulation should run for the full limit when N=250";
        }
    }

    public static void main(String[] args) {
        agentSimulationTests();
    }
}
