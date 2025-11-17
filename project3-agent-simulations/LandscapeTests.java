/*
file name:      LandscapeTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea LandscapeTests
*/

public class LandscapeTests {

    public static double landscapeTests() {
        double score = 0.;

        // Confirms constructor wires width height
        // case 1: testing Landscape(int, int)
        {
            Landscape small = new Landscape(25, 50);
            Landscape large = new Landscape(200, 150);

            boolean passed = (small != null) && (large != null);
            System.out.println("Constructed landscapes: " + passed);
            assert passed : "Error in Landscape::Landscape(int, int)";
            if (passed) {
                score += 1.;
            }
        }

        // Verifies accessor returns supplied width
        // case 2: testing getWidth()
        {
            Landscape landscape = new Landscape(125, 60);
            boolean passed = landscape.getWidth() == 125;
            System.out.println("Landscape width is preserved: " + passed);
            assert passed : "Error in Landscape::getWidth()";
            if (passed) {
                score += 1.;
            }
        }

        // Verifies accessor returns supplied height
        // case 3: testing getHeight()
        {
            Landscape landscape = new Landscape(80, 140);
            boolean passed = landscape.getHeight() == 140;
            System.out.println("Landscape height is preserved: " + passed);
            assert passed : "Error in Landscape::getHeight()";
            if (passed) {
                score += 1.;
            }
        }

        return score;
    }

    public static void main(String[] args) {
        System.out.println(landscapeTests());
    }
}
