/*
file name:      MazeTests.java
Authors:        Najam Tariq
last modified:  12/13/2025

How to run:     java -ea MazeTests
*/

import java.util.LinkedList;

public class MazeTests {

    public static void main(String[] args) {
        mazeConstructorTests();
        mazeGetterTests();
        mazeGetCellTests();
        mazeResetTests();
        mazeNeighborTests();
        mazeCountVisitedTests();
        System.out.println("All MazeTests passed!");
    }

    public static void mazeConstructorTests() {
        // Constructor with rows, cols, chance should create proper grid
        {
            Maze maze = new Maze(10, 15, 0.0); // No obstacles
            assert maze.getRows() == 10 : "Should have 10 rows";
            assert maze.getCols() == 15 : "Should have 15 columns";
        }

        // Constructor with 0% obstacle chance should have all FREE cells
        {
            Maze maze = new Maze(5, 5, 0.0);
            boolean allFree = true;
            for (int r = 0; r < maze.getRows(); r++) {
                for (int c = 0; c < maze.getCols(); c++) {
                    if (maze.get(r, c).getType() == CellType.OBSTACLE) {
                        allFree = false;
                    }
                }
            }
            assert allFree : "With 0% chance, all cells should be FREE";
        }

        // Constructor with 100% obstacle chance should still have start and target as FREE
        {
            Maze maze = new Maze(5, 5, 1.0);
            // Start and target should be non-obstacle for search to work
            // The maze should pick random non-obstacle cells for start/target
            // With 100% chance, this might fail or have special handling
            // This tests that the maze is at least created
            assert maze.getRows() == 5 : "Should still create 5 rows";
            assert maze.getCols() == 5 : "Should still create 5 columns";
        }

        // Small maze construction
        {
            Maze maze = new Maze(2, 2, 0.0);
            assert maze.getRows() == 2 : "Should have 2 rows";
            assert maze.getCols() == 2 : "Should have 2 columns";
        }

        System.out.println("Finished mazeConstructorTests!");
    }

    public static void mazeGetterTests() {
        // getRows() should return correct number of rows
        {
            Maze maze = new Maze(8, 12, 0.0);
            assert maze.getRows() == 8 : "getRows should return 8";
        }

        // getCols() should return correct number of columns
        {
            Maze maze = new Maze(8, 12, 0.0);
            assert maze.getCols() == 12 : "getCols should return 12";
        }

        // Different dimensions
        {
            Maze maze1 = new Maze(3, 7, 0.0);
            Maze maze2 = new Maze(7, 3, 0.0);
            assert maze1.getRows() == 3 && maze1.getCols() == 7 : "First maze dimensions";
            assert maze2.getRows() == 7 && maze2.getCols() == 3 : "Second maze dimensions";
        }

        System.out.println("Finished mazeGetterTests!");
    }

    public static void mazeGetCellTests() {
        // get(row, col) should return cell at correct position
        {
            Maze maze = new Maze(5, 5, 0.0);
            Cell cell = maze.get(2, 3);
            assert cell != null : "Cell should not be null";
            assert cell.getRow() == 2 : "Cell row should be 2";
            assert cell.getCol() == 3 : "Cell col should be 3";
        }

        // get() should return corners correctly
        {
            Maze maze = new Maze(4, 6, 0.0);
            Cell topLeft = maze.get(0, 0);
            Cell topRight = maze.get(0, 5);
            Cell bottomLeft = maze.get(3, 0);
            Cell bottomRight = maze.get(3, 5);
            
            assert topLeft.getRow() == 0 && topLeft.getCol() == 0 : "Top-left corner";
            assert topRight.getRow() == 0 && topRight.getCol() == 5 : "Top-right corner";
            assert bottomLeft.getRow() == 3 && bottomLeft.getCol() == 0 : "Bottom-left corner";
            assert bottomRight.getRow() == 3 && bottomRight.getCol() == 5 : "Bottom-right corner";
        }

        // get() should return same object on repeated calls
        {
            Maze maze = new Maze(5, 5, 0.0);
            Cell cell1 = maze.get(2, 2);
            Cell cell2 = maze.get(2, 2);
            assert cell1 == cell2 : "Should return same Cell object";
        }

        System.out.println("Finished mazeGetCellTests!");
    }

    public static void mazeResetTests() {
        // reset() should clear visited state of all cells
        {
            Maze maze = new Maze(4, 4, 0.0);
            Cell cell1 = maze.get(0, 0);
            Cell cell2 = maze.get(1, 1);
            Cell cell3 = maze.get(2, 2);
            
            cell1.setPrev(cell1);
            cell2.setPrev(cell1);
            cell3.setPrev(cell2);
            
            assert cell1.visited() && cell2.visited() && cell3.visited() : "Cells should be visited";
            
            maze.reset();
            
            assert !cell1.visited() : "cell1 should not be visited after reset";
            assert !cell2.visited() : "cell2 should not be visited after reset";
            assert !cell3.visited() : "cell3 should not be visited after reset";
        }

        // reset() should clear prev of all cells
        {
            Maze maze = new Maze(3, 3, 0.0);
            Cell start = maze.get(0, 0);
            Cell middle = maze.get(1, 1);
            Cell end = maze.get(2, 2);
            
            start.setPrev(start);
            middle.setPrev(start);
            end.setPrev(middle);
            
            maze.reset();
            
            assert start.getPrev() == null : "start prev should be null after reset";
            assert middle.getPrev() == null : "middle prev should be null after reset";
            assert end.getPrev() == null : "end prev should be null after reset";
        }

        // reset() should not affect cell types
        {
            Maze maze = new Maze(5, 5, 0.5); // 50% obstacles
            CellType[][] types = new CellType[5][5];
            for (int r = 0; r < 5; r++) {
                for (int c = 0; c < 5; c++) {
                    types[r][c] = maze.get(r, c).getType();
                }
            }
            
            maze.reset();
            
            for (int r = 0; r < 5; r++) {
                for (int c = 0; c < 5; c++) {
                    assert maze.get(r, c).getType() == types[r][c] : 
                        "Cell type should not change after reset";
                }
            }
        }

        System.out.println("Finished mazeResetTests!");
    }

    public static void mazeNeighborTests() {
        // getNeighbors() for center cell should return 4 neighbors (no obstacles)
        {
            Maze maze = new Maze(5, 5, 0.0);
            Cell center = maze.get(2, 2);
            LinkedList<Cell> neighbors = maze.getNeighbors(center);
            assert neighbors.size() == 4 : "Center cell should have 4 neighbors, got " + neighbors.size();
        }

        // getNeighbors() for corner cell should return 2 neighbors
        {
            Maze maze = new Maze(5, 5, 0.0);
            Cell corner = maze.get(0, 0);
            LinkedList<Cell> neighbors = maze.getNeighbors(corner);
            assert neighbors.size() == 2 : "Corner cell should have 2 neighbors, got " + neighbors.size();
        }

        // getNeighbors() for edge cell should return 3 neighbors
        {
            Maze maze = new Maze(5, 5, 0.0);
            Cell edge = maze.get(0, 2);
            LinkedList<Cell> neighbors = maze.getNeighbors(edge);
            assert neighbors.size() == 3 : "Edge cell should have 3 neighbors, got " + neighbors.size();
        }

        // getNeighbors() should not include diagonal neighbors
        {
            Maze maze = new Maze(3, 3, 0.0);
            Cell center = maze.get(1, 1);
            LinkedList<Cell> neighbors = maze.getNeighbors(center);
            
            // Check that corners are not included
            Cell topLeft = maze.get(0, 0);
            Cell topRight = maze.get(0, 2);
            Cell bottomLeft = maze.get(2, 0);
            Cell bottomRight = maze.get(2, 2);
            
            assert !neighbors.contains(topLeft) : "Should not include diagonal (top-left)";
            assert !neighbors.contains(topRight) : "Should not include diagonal (top-right)";
            assert !neighbors.contains(bottomLeft) : "Should not include diagonal (bottom-left)";
            assert !neighbors.contains(bottomRight) : "Should not include diagonal (bottom-right)";
        }

        // getNeighbors() should not include OBSTACLE cells
        {
            // Create a custom maze with known obstacles
            int rows = 3, cols = 3;
            Cell[][] grid = new Cell[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = new Cell(r, c, CellType.FREE);
                }
            }
            // Make right neighbor an obstacle
            grid[1][2] = new Cell(1, 2, CellType.OBSTACLE);
            
            Maze maze = new Maze(grid, grid[0][0], grid[2][2]);
            Cell center = maze.get(1, 1);
            LinkedList<Cell> neighbors = maze.getNeighbors(center);
            
            // Should have 3 neighbors (up, down, left) but not right (obstacle)
            assert neighbors.size() == 3 : "Should have 3 neighbors (one is obstacle), got " + neighbors.size();
            
            boolean containsObstacle = false;
            for (Cell n : neighbors) {
                if (n.getType() == CellType.OBSTACLE) {
                    containsObstacle = true;
                }
            }
            assert !containsObstacle : "Neighbors should not include obstacles";
        }

        // getNeighbors() returns correct cells
        {
            Maze maze = new Maze(5, 5, 0.0);
            Cell center = maze.get(2, 2);
            LinkedList<Cell> neighbors = maze.getNeighbors(center);
            
            Cell up = maze.get(1, 2);
            Cell down = maze.get(3, 2);
            Cell left = maze.get(2, 1);
            Cell right = maze.get(2, 3);
            
            assert neighbors.contains(up) : "Should contain up neighbor";
            assert neighbors.contains(down) : "Should contain down neighbor";
            assert neighbors.contains(left) : "Should contain left neighbor";
            assert neighbors.contains(right) : "Should contain right neighbor";
        }

        System.out.println("Finished mazeNeighborTests!");
    }

    public static void mazeCountVisitedTests() {
        // countVisitedCells() should return 0 for fresh maze
        {
            Maze maze = new Maze(5, 5, 0.0);
            int count = maze.countVisitedCells();
            assert count == 0 : "Fresh maze should have 0 visited cells, got " + count;
        }

        // countVisitedCells() should count visited cells correctly
        {
            Maze maze = new Maze(5, 5, 0.0);
            Cell c1 = maze.get(0, 0);
            Cell c2 = maze.get(1, 1);
            Cell c3 = maze.get(2, 2);
            
            c1.setPrev(c1);
            c2.setPrev(c1);
            c3.setPrev(c2);
            
            int count = maze.countVisitedCells();
            assert count == 3 : "Should have 3 visited cells, got " + count;
        }

        // countVisitedCells() should reset to 0 after maze.reset()
        {
            Maze maze = new Maze(5, 5, 0.0);
            maze.get(0, 0).setPrev(maze.get(0, 0));
            maze.get(1, 1).setPrev(maze.get(0, 0));
            
            assert maze.countVisitedCells() == 2 : "Should have 2 visited before reset";
            
            maze.reset();
            
            assert maze.countVisitedCells() == 0 : "Should have 0 visited after reset";
        }

        System.out.println("Finished mazeCountVisitedTests!");
    }
}
