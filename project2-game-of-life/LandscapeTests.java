/*
file name:      LandscapeTests.java
Authors:        Naser Al Madi
last modified:  11/13/2025

How to run:     java -ea LandscapeTests
*/


import java.util.ArrayList;

public class LandscapeTests {

    public static void landscapeTests() {

        // case 1: testing Landscape(int, int)
        {
            // given
            Landscape l1 = new Landscape(2, 4);
            Landscape l2 = new Landscape(10, 10);

            // when
            System.out.println(l1);
            System.out.println("\n");
            System.out.println(l2);

            // then
            assert l1 != null : "Error in Landscape::Landscape(int, int)";
            assert l2 != null : "Error in Landscape::Landscape(int, int)";
            assert l1.getRows() == 2 : "Error in Landscape::Landscape(int, int) - rows";
            assert l1.getCols() == 4 : "Error in Landscape::Landscape(int, int) - cols";
            assert l2.getRows() == 10 : "Error in Landscape::Landscape(int, int) - rows";
            assert l2.getCols() == 10 : "Error in Landscape::Landscape(int, int) - cols";
            // All cells should be dead by default
            for (int i = 0; i < l1.getRows(); i++) {
                for (int j = 0; j < l1.getCols(); j++) {
                    assert l1.getCell(i, j).getAlive() == false : "Error in Landscape::Landscape(int, int) - cells should be dead";
                }
            }
        }

        // case 2: testing Landscape(int, int, double) with chance = 0
        {
            // given
            Landscape l1 = new Landscape(5, 5, 0.0);

            // when - all cells should be dead with 0% chance

            // then
            assert l1 != null : "Error in Landscape::Landscape(int, int, double)";
            for (int i = 0; i < l1.getRows(); i++) {
                for (int j = 0; j < l1.getCols(); j++) {
                    assert l1.getCell(i, j).getAlive() == false : "Error in Landscape::Landscape(int, int, double) - 0 chance should mean all dead";
                }
            }
        }

        // case 3: testing Landscape(int, int, double) with chance = 1
        {
            // given
            Landscape l1 = new Landscape(5, 5, 1.0);

            // when - all cells should be alive with 100% chance

            // then
            assert l1 != null : "Error in Landscape::Landscape(int, int, double)";
            for (int i = 0; i < l1.getRows(); i++) {
                for (int j = 0; j < l1.getCols(); j++) {
                    assert l1.getCell(i, j).getAlive() == true : "Error in Landscape::Landscape(int, int, double) - 1.0 chance should mean all alive";
                }
            }
        }

        // case 4: testing Landscape(boolean[][])
        {
            // given
            boolean[][] grid = {
                {true, false, true},
                {false, true, false},
                {true, true, false}
            };
            Landscape l1 = new Landscape(grid);

            // when
            System.out.println("Testing Landscape(boolean[][]):");
            System.out.println(l1);

            // then
            assert l1 != null : "Error in Landscape::Landscape(boolean[][])";
            assert l1.getRows() == 3 : "Error in Landscape::Landscape(boolean[][]) - rows";
            assert l1.getCols() == 3 : "Error in Landscape::Landscape(boolean[][]) - cols";
            assert l1.getCell(0, 0).getAlive() == true : "Error in Landscape::Landscape(boolean[][])";
            assert l1.getCell(0, 1).getAlive() == false : "Error in Landscape::Landscape(boolean[][])";
            assert l1.getCell(0, 2).getAlive() == true : "Error in Landscape::Landscape(boolean[][])";
            assert l1.getCell(1, 0).getAlive() == false : "Error in Landscape::Landscape(boolean[][])";
            assert l1.getCell(1, 1).getAlive() == true : "Error in Landscape::Landscape(boolean[][])";
            assert l1.getCell(1, 2).getAlive() == false : "Error in Landscape::Landscape(boolean[][])";
            assert l1.getCell(2, 0).getAlive() == true : "Error in Landscape::Landscape(boolean[][])";
            assert l1.getCell(2, 1).getAlive() == true : "Error in Landscape::Landscape(boolean[][])";
            assert l1.getCell(2, 2).getAlive() == false : "Error in Landscape::Landscape(boolean[][])";
        }

        // case 5: testing reset() with basic constructor
        {
            // given
            Landscape l1 = new Landscape(3, 3);
            l1.getCell(0, 0).setAlive(true);
            l1.getCell(1, 1).setAlive(true);

            // when
            l1.reset();

            // then - all cells should be dead after reset
            for (int i = 0; i < l1.getRows(); i++) {
                for (int j = 0; j < l1.getCols(); j++) {
                    assert l1.getCell(i, j).getAlive() == false : "Error in Landscape::reset() - cells should be dead after reset";
                }
            }
        }

        // case 6: testing getRows()
        {
            // given
            Landscape l1 = new Landscape(5, 10);
            Landscape l2 = new Landscape(1, 1);
            Landscape l3 = new Landscape(100, 50);

            // when
            int rows1 = l1.getRows();
            int rows2 = l2.getRows();
            int rows3 = l3.getRows();

            System.out.println("getRows() test: " + rows1 + " == 5");
            System.out.println("getRows() test: " + rows2 + " == 1");
            System.out.println("getRows() test: " + rows3 + " == 100");

            // then
            assert rows1 == 5 : "Error in Landscape::getRows()";
            assert rows2 == 1 : "Error in Landscape::getRows()";
            assert rows3 == 100 : "Error in Landscape::getRows()";
        }

        // case 7: testing getCols()
        {
            // given
            Landscape l1 = new Landscape(5, 10);
            Landscape l2 = new Landscape(1, 1);
            Landscape l3 = new Landscape(100, 50);

            // when
            int cols1 = l1.getCols();
            int cols2 = l2.getCols();
            int cols3 = l3.getCols();

            System.out.println("getCols() test: " + cols1 + " == 10");
            System.out.println("getCols() test: " + cols2 + " == 1");
            System.out.println("getCols() test: " + cols3 + " == 50");

            // then
            assert cols1 == 10 : "Error in Landscape::getCols()";
            assert cols2 == 1 : "Error in Landscape::getCols()";
            assert cols3 == 50 : "Error in Landscape::getCols()";
        }

        // case 8: testing getCell(int, int)
        {
            // given
            boolean[][] grid = {
                {true, false},
                {false, true}
            };
            Landscape l1 = new Landscape(grid);

            // when
            Cell c00 = l1.getCell(0, 0);
            Cell c01 = l1.getCell(0, 1);
            Cell c10 = l1.getCell(1, 0);
            Cell c11 = l1.getCell(1, 1);

            // then
            assert c00 != null : "Error in Landscape::getCell() - returned null";
            assert c01 != null : "Error in Landscape::getCell() - returned null";
            assert c10 != null : "Error in Landscape::getCell() - returned null";
            assert c11 != null : "Error in Landscape::getCell() - returned null";
            assert c00.getAlive() == true : "Error in Landscape::getCell()";
            assert c01.getAlive() == false : "Error in Landscape::getCell()";
            assert c10.getAlive() == false : "Error in Landscape::getCell()";
            assert c11.getAlive() == true : "Error in Landscape::getCell()";
        }

        // case 9: testing getNeighbors() - corner cell (top-left)
        {
            // given
            Landscape l1 = new Landscape(3, 3);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(0, 0);

            System.out.println("getNeighbors(0,0) size: " + neighbors.size() + " == 3");

            // then - corner should have 3 neighbors
            assert neighbors.size() == 3 : "Error in Landscape::getNeighbors() - corner should have 3 neighbors, got " + neighbors.size();
        }

        // case 10: testing getNeighbors() - corner cell (top-right)
        {
            // given
            Landscape l1 = new Landscape(3, 3);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(0, 2);

            System.out.println("getNeighbors(0,2) size: " + neighbors.size() + " == 3");

            // then - corner should have 3 neighbors
            assert neighbors.size() == 3 : "Error in Landscape::getNeighbors() - corner should have 3 neighbors, got " + neighbors.size();
        }

        // case 11: testing getNeighbors() - corner cell (bottom-left)
        {
            // given
            Landscape l1 = new Landscape(3, 3);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(2, 0);

            System.out.println("getNeighbors(2,0) size: " + neighbors.size() + " == 3");

            // then - corner should have 3 neighbors
            assert neighbors.size() == 3 : "Error in Landscape::getNeighbors() - corner should have 3 neighbors, got " + neighbors.size();
        }

        // case 12: testing getNeighbors() - corner cell (bottom-right)
        {
            // given
            Landscape l1 = new Landscape(3, 3);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(2, 2);

            System.out.println("getNeighbors(2,2) size: " + neighbors.size() + " == 3");

            // then - corner should have 3 neighbors
            assert neighbors.size() == 3 : "Error in Landscape::getNeighbors() - corner should have 3 neighbors, got " + neighbors.size();
        }

        // case 13: testing getNeighbors() - edge cell (top edge, not corner)
        {
            // given
            Landscape l1 = new Landscape(3, 3);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(0, 1);

            System.out.println("getNeighbors(0,1) size: " + neighbors.size() + " == 5");

            // then - edge should have 5 neighbors
            assert neighbors.size() == 5 : "Error in Landscape::getNeighbors() - edge should have 5 neighbors, got " + neighbors.size();
        }

        // case 14: testing getNeighbors() - edge cell (left edge, not corner)
        {
            // given
            Landscape l1 = new Landscape(3, 3);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(1, 0);

            System.out.println("getNeighbors(1,0) size: " + neighbors.size() + " == 5");

            // then - edge should have 5 neighbors
            assert neighbors.size() == 5 : "Error in Landscape::getNeighbors() - edge should have 5 neighbors, got " + neighbors.size();
        }

        // case 15: testing getNeighbors() - center cell
        {
            // given
            Landscape l1 = new Landscape(3, 3);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(1, 1);

            System.out.println("getNeighbors(1,1) size: " + neighbors.size() + " == 8");

            // then - center should have 8 neighbors
            assert neighbors.size() == 8 : "Error in Landscape::getNeighbors() - center should have 8 neighbors, got " + neighbors.size();
        }

        // case 16: testing getNeighbors() - verifying correct neighbors returned
        {
            // given
            boolean[][] grid = {
                {true, true, true},
                {true, false, true},
                {true, true, true}
            };
            Landscape l1 = new Landscape(grid);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(1, 1);

            // then - all 8 neighbors should be alive
            int aliveCount = 0;
            for (Cell c : neighbors) {
                if (c.getAlive()) {
                    aliveCount++;
                }
            }
            System.out.println("getNeighbors alive count: " + aliveCount + " == 8");
            assert aliveCount == 8 : "Error in Landscape::getNeighbors() - expected 8 alive neighbors, got " + aliveCount;
        }

        // case 17: testing getNeighbors() - should not include the cell itself
        {
            // given
            boolean[][] grid = {
                {false, false, false},
                {false, true, false},
                {false, false, false}
            };
            Landscape l1 = new Landscape(grid);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(1, 1);

            // then - no neighbors should be alive (center cell is alive but shouldn't be in list)
            int aliveCount = 0;
            for (Cell c : neighbors) {
                if (c.getAlive()) {
                    aliveCount++;
                }
            }
            System.out.println("getNeighbors should not include self: alive count " + aliveCount + " == 0");
            assert aliveCount == 0 : "Error in Landscape::getNeighbors() - should not include self in neighbors";
        }

        // case 18: testing advance() - blinker pattern (oscillator)
        {
            // given - horizontal blinker
            boolean[][] grid = {
                {false, false, false, false, false},
                {false, false, false, false, false},
                {false, true, true, true, false},
                {false, false, false, false, false},
                {false, false, false, false, false}
            };
            Landscape l1 = new Landscape(grid);

            System.out.println("Before advance (horizontal blinker):");
            System.out.println(l1);

            // when
            l1.advance();

            System.out.println("After advance (should be vertical blinker):");
            System.out.println(l1);

            // then - should become vertical blinker
            assert l1.getCell(1, 2).getAlive() == true : "Error in Landscape::advance() - blinker pattern failed";
            assert l1.getCell(2, 2).getAlive() == true : "Error in Landscape::advance() - blinker pattern failed";
            assert l1.getCell(3, 2).getAlive() == true : "Error in Landscape::advance() - blinker pattern failed";
            assert l1.getCell(2, 1).getAlive() == false : "Error in Landscape::advance() - blinker pattern failed";
            assert l1.getCell(2, 3).getAlive() == false : "Error in Landscape::advance() - blinker pattern failed";
        }

        // case 19: testing advance() - block pattern (still life)
        {
            // given - block pattern (should not change)
            boolean[][] grid = {
                {false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}
            };
            Landscape l1 = new Landscape(grid);

            System.out.println("Before advance (block):");
            System.out.println(l1);

            // when
            l1.advance();

            System.out.println("After advance (block should remain):");
            System.out.println(l1);

            // then - block should remain unchanged
            assert l1.getCell(1, 1).getAlive() == true : "Error in Landscape::advance() - block pattern failed";
            assert l1.getCell(1, 2).getAlive() == true : "Error in Landscape::advance() - block pattern failed";
            assert l1.getCell(2, 1).getAlive() == true : "Error in Landscape::advance() - block pattern failed";
            assert l1.getCell(2, 2).getAlive() == true : "Error in Landscape::advance() - block pattern failed";
        }

        // case 20: testing advance() - single cell dies
        {
            // given - single cell (should die from loneliness)
            boolean[][] grid = {
                {false, false, false},
                {false, true, false},
                {false, false, false}
            };
            Landscape l1 = new Landscape(grid);

            // when
            l1.advance();

            // then - cell should die
            assert l1.getCell(1, 1).getAlive() == false : "Error in Landscape::advance() - single cell should die";
        }

        // case 21: testing advance() - overcrowding death
        {
            // given - center cell surrounded by too many (should die)
            boolean[][] grid = {
                {true, true, true},
                {true, true, true},
                {true, true, true}
            };
            Landscape l1 = new Landscape(grid);

            // when
            l1.advance();

            // then - center cell should die (has 8 neighbors)
            assert l1.getCell(1, 1).getAlive() == false : "Error in Landscape::advance() - overcrowded cell should die";
        }

        // case 22: testing toString()
        {
            // given
            boolean[][] grid = {
                {true, false},
                {false, true}
            };
            Landscape l1 = new Landscape(grid);

            // when
            String str = l1.toString();

            // then - should contain newlines and represent the grid
            assert str != null : "Error in Landscape::toString() - returned null";
            assert str.length() > 0 : "Error in Landscape::toString() - returned empty string";
            assert str.contains("\n") : "Error in Landscape::toString() - should contain newline";
            System.out.println("toString() output:\n" + str);
        }

        // case 23: testing 1x1 landscape edge case
        {
            // given
            Landscape l1 = new Landscape(1, 1);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(0, 0);

            // then - single cell has no neighbors
            assert neighbors.size() == 0 : "Error in Landscape::getNeighbors() - 1x1 grid should have 0 neighbors";
        }

        // case 24: testing getNeighbors on larger grid center
        {
            // given
            Landscape l1 = new Landscape(5, 5);

            // when
            ArrayList<Cell> neighbors = l1.getNeighbors(2, 2);

            // then - center of 5x5 should still have 8 neighbors
            assert neighbors.size() == 8 : "Error in Landscape::getNeighbors() - center of 5x5 should have 8 neighbors";
        }

        System.out.println("*** Done testing Landscape! ***\n");
    }


    public static void main(String[] args) {

        landscapeTests();
    }
}
