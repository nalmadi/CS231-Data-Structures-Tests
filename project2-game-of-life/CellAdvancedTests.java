/*
file name:      CellAdvancedTests.java
authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea CellAdvancedTests

These tests focus on edge cases that are not covered by the baseline suite:
  * They exercise updateState with the full neighborhood (8 cells) so that
    over- and under-population conditions are evaluated with realistic input.
  * They verify that exactly-two and exactly-three live-neighbor scenarios
    behave correctly for both live and dead cells.
  * They ensure updateState has no side-effects on the neighbor list or on the
    state of neighboring cells.
*/

import java.util.ArrayList;
import java.util.Arrays;

public class CellAdvancedTests {

    private static ArrayList<Cell> buildNeighbors(int liveCount) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        // by default create 8 neighbors; toggle the first `liveCount` entries to alive
        for (int i = 0; i < 8; i++) {
            neighbors.add(new Cell(i < liveCount));
        }
        return neighbors;
    }

    private static ArrayList<Cell> buildNeighbors(int... liveIndices) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        Arrays.sort(liveIndices);
        int idx = 0;
        for (int i = 0; i < 8; i++) {
            boolean alive = idx < liveIndices.length && liveIndices[idx] == i;
            if (alive) {
                idx++;
            }
            neighbors.add(new Cell(alive));
        }
        return neighbors;
    }

    public static void cellAdvancedTests() {

        // Living cell with exactly two neighbors should survive (no under/over-population)
        {
            Cell live = new Cell(true);
            ArrayList<Cell> neighbors = buildNeighbors(0, 3); // two live neighbors
            live.updateState(neighbors);
            assert live.getAlive() : "Alive cell with exactly two neighbors must survive";
        }

        // Living cell with more than three neighbors should die (overpopulation)
        {
            Cell live = new Cell(true);
            ArrayList<Cell> neighbors = buildNeighbors(0, 2, 4, 5); // four live neighbors
            live.updateState(neighbors);
            assert !live.getAlive() : "Alive cell with four neighbors should die from overpopulation";
        }

        // Living cell with fewer than two neighbors should die (underpopulation)
        {
            Cell live = new Cell(true);
            ArrayList<Cell> neighbors = buildNeighbors(0); // only one neighbor alive
            live.updateState(neighbors);
            assert !live.getAlive() : "Alive cell with a single neighbor should die from isolation";
        }

        // Dead cell with exactly three neighbors should become alive (reproduction)
        {
            Cell dead = new Cell(false);
            ArrayList<Cell> neighbors = buildNeighbors(1, 4, 7);
            dead.updateState(neighbors);
            assert dead.getAlive() : "Dead cell with three neighbors should become alive";
        }

        // Dead cell with two neighbors must remain dead
        {
            Cell dead = new Cell(false);
            ArrayList<Cell> neighbors = buildNeighbors(2); // only one live neighbor
            dead.updateState(neighbors);
            assert !dead.getAlive() : "Dead cell with fewer than three neighbors must stay dead";
        }

        // updateState should not mutate the neighbor list or its cells
        {
            Cell pivot = new Cell(true);
            ArrayList<Cell> neighbors = buildNeighbors(0, 1, 2); // capture references
            ArrayList<Cell> snapshot = new ArrayList<>(neighbors);
            boolean[] originalStates = new boolean[neighbors.size()];
            for (int i = 0; i < neighbors.size(); i++) {
                originalStates[i] = neighbors.get(i).getAlive();
            }

            pivot.updateState(neighbors);

            // ensure neighbor references and size unchanged
            assert neighbors.size() == snapshot.size() : "Neighbors list size should remain unchanged";
            for (int i = 0; i < neighbors.size(); i++) {
                assert neighbors.get(i) == snapshot.get(i) : "Neighbor references must not be replaced";
                assert neighbors.get(i).getAlive() == originalStates[i]
                        : "Neighbor cell states must not be mutated by updateState";
            }
        }

        // updateState should accept fewer than eight neighbors (e.g., corner cells)
        {
            Cell live = new Cell(true);
            ArrayList<Cell> sparseNeighbors = new ArrayList<>();
            sparseNeighbors.add(new Cell(true));
            sparseNeighbors.add(new Cell(false));
            // two living neighbors total -> survive
            live.updateState(sparseNeighbors);
            assert live.getAlive() : "updateState must work with sparse neighbor lists";
        }

        System.out.println("*** CellAdvancedTests complete ***");
    }

    public static void main(String[] args) {
        cellAdvancedTests();
    }
}


