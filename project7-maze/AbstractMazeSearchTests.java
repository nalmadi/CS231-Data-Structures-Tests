/*
file name:      AbstractMazeSearchTests.java
Authors:        Najam Tariq
last modified:  12/13/2025

How to run:     java -ea AbstractMazeSearchTests
*/

import java.util.LinkedList;

public class AbstractMazeSearchTests {

    // Helper to create a simple maze without obstacles
    private static Maze createSimpleMaze(int rows, int cols) {
        Cell[][] grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(r, c, CellType.FREE);
            }
        }
        return new Maze(grid, grid[0][0], grid[rows-1][cols-1]);
    }

    // Helper to create a maze with specific obstacles
    private static Maze createMazeWithObstacles() {
        int rows = 5, cols = 5;
        Cell[][] grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(r, c, CellType.FREE);
            }
        }
        // Create a wall that forces a detour
        grid[1][1] = new Cell(1, 1, CellType.OBSTACLE);
        grid[2][1] = new Cell(2, 1, CellType.OBSTACLE);
        grid[3][1] = new Cell(3, 1, CellType.OBSTACLE);
        
        return new Maze(grid, grid[0][0], grid[4][4]);
    }

    public static void main(String[] args) {
        constructorTests();
        getterSetterTests();
        tracebackTests();
        searchMethodTests();
        dfsSpecificTests();
        bfsSpecificTests();
        astarSpecificTests();
        numRemainingCellsTests();
        System.out.println("All AbstractMazeSearchTests passed!");
    }

    public static void constructorTests() {
        // Constructor should initialize cur, start, target to null
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            assert bfs.getCur() == null : "cur should be null after construction";
            assert bfs.getStart() == null : "start should be null after construction";
            assert bfs.getTarget() == null : "target should be null after construction";
        }

        // getMaze() should return the maze passed to constructor
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);
            assert dfs.getMaze() == maze : "getMaze should return the original maze";
        }

        System.out.println("Finished constructorTests!");
    }

    public static void getterSetterTests() {
        // setCur() and getCur() should work correctly
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell cell = maze.get(2, 2);
            bfs.setCur(cell);
            assert bfs.getCur() == cell : "getCur should return the cell set by setCur";
        }

        // getStart() should return start after search begins
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(4, 4);
            bfs.search(start, target, false, 0);
            assert bfs.getStart() == start : "getStart should return the start cell";
        }

        // getTarget() should return target after search begins
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(4, 4);
            bfs.search(start, target, false, 0);
            assert bfs.getTarget() == target : "getTarget should return the target cell";
        }

        // reset() should set cur, start, target to null
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(4, 4);
            bfs.search(start, target, false, 0);
            
            bfs.reset();
            
            assert bfs.getCur() == null : "cur should be null after reset";
            assert bfs.getStart() == null : "start should be null after reset";
            assert bfs.getTarget() == null : "target should be null after reset";
        }

        System.out.println("Finished getterSetterTests!");
    }

    public static void tracebackTests() {
        // traceback() should return path from start to given cell
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            
            Cell start = maze.get(0, 0);
            Cell mid = maze.get(0, 1);
            Cell end = maze.get(0, 2);
            
            start.setPrev(start);
            mid.setPrev(start);
            end.setPrev(mid);
            
            // Need to set start for traceback to work
            bfs.search(start, end, false, 0);
            maze.reset();
            
            // Rebuild path manually for isolated test
            start.setPrev(start);
            mid.setPrev(start);
            end.setPrev(mid);
            
            LinkedList<Cell> path = bfs.traceback(end);
            assert path != null : "traceback should return a path";
            assert path.size() == 3 : "Path should have 3 cells, got " + path.size();
        }

        // traceback() on cell with no prev should return null
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell isolated = maze.get(2, 2);
            // Don't set prev
            LinkedList<Cell> path = bfs.traceback(isolated);
            assert path == null : "traceback on unvisited cell should return null";
        }

        // traceback() should include start and end cells
        {
            Maze maze = createSimpleMaze(3, 3);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            
            Cell start = maze.get(0, 0);
            Cell target = maze.get(2, 2);
            
            LinkedList<Cell> path = bfs.search(start, target, false, 0);
            
            assert path != null : "Should find a path";
            assert path.getFirst() == start || path.getLast() == start : 
                "Path should include start";
            assert path.getFirst() == target || path.getLast() == target : 
                "Path should include target";
        }

        System.out.println("Finished tracebackTests!");
    }

    public static void searchMethodTests() {
        // search() should return path when target is reachable
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(4, 4);
            
            LinkedList<Cell> path = bfs.search(start, target, false, 0);
            
            assert path != null : "search should find a path";
            assert path.size() > 0 : "path should not be empty";
        }

        // search() should return null when target is unreachable
        {
            // Create maze with completely blocked target
            int rows = 5, cols = 5;
            Cell[][] grid = new Cell[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = new Cell(r, c, CellType.FREE);
                }
            }
            // Block all neighbors of target
            grid[3][4] = new Cell(3, 4, CellType.OBSTACLE);
            grid[4][3] = new Cell(4, 3, CellType.OBSTACLE);
            
            Maze maze = new Maze(grid, grid[0][0], grid[4][4]);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            
            LinkedList<Cell> path = bfs.search(maze.get(0, 0), maze.get(4, 4), false, 0);
            
            assert path == null : "search should return null for unreachable target";
        }

        // search() with same start and target should return single-cell path
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell start = maze.get(2, 2);
            
            LinkedList<Cell> path = bfs.search(start, start, false, 0);
            
            // When start == target, should return immediately or return path with just start
            assert path != null : "search with start==target should return a path";
        }

        // search() should work with display=false and delay=0
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(4, 4);
            
            // This should not throw any exceptions
            LinkedList<Cell> path = bfs.search(start, target, false, 0);
            assert path != null : "search with display=false should work";
        }

        System.out.println("Finished searchMethodTests!");
    }

    public static void dfsSpecificTests() {
        // DFS should find a path (may not be shortest)
        {
            Maze maze = createMazeWithObstacles();
            MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(4, 4);
            
            LinkedList<Cell> path = dfs.search(start, target, false, 0);
            
            assert path != null : "DFS should find a path";
            assert path.size() > 0 : "DFS path should not be empty";
        }

        // DFS uses Stack-based behavior (LIFO)
        {
            Maze maze = createSimpleMaze(3, 3);
            MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);
            
            // After construction, numRemainingCells should be 0
            assert dfs.numRemainingCells() == 0 : "Stack should be empty initially";
            
            Cell cell1 = maze.get(0, 0);
            Cell cell2 = maze.get(0, 1);
            
            dfs.addCell(cell1);
            dfs.addCell(cell2);
            
            assert dfs.numRemainingCells() == 2 : "Stack should have 2 cells";
            
            // Stack should return last added first (LIFO)
            Cell next = dfs.findNextCell();
            assert next == cell2 : "DFS should return last added cell first (LIFO)";
        }

        System.out.println("Finished dfsSpecificTests!");
    }

    public static void bfsSpecificTests() {
        // BFS should find shortest path
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(4, 4);
            
            LinkedList<Cell> path = bfs.search(start, target, false, 0);
            
            assert path != null : "BFS should find a path";
            // Manhattan distance from (0,0) to (4,4) is 8, so shortest path is 9 cells
            assert path.size() == 9 : "BFS should find shortest path of length 9, got " + path.size();
        }

        // BFS uses Queue-based behavior (FIFO)
        {
            Maze maze = createSimpleMaze(3, 3);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            
            // After construction, numRemainingCells should be 0
            assert bfs.numRemainingCells() == 0 : "Queue should be empty initially";
            
            Cell cell1 = maze.get(0, 0);
            Cell cell2 = maze.get(0, 1);
            
            bfs.addCell(cell1);
            bfs.addCell(cell2);
            
            assert bfs.numRemainingCells() == 2 : "Queue should have 2 cells";
            
            // Queue should return first added first (FIFO)
            Cell next = bfs.findNextCell();
            assert next == cell1 : "BFS should return first added cell first (FIFO)";
        }

        System.out.println("Finished bfsSpecificTests!");
    }

    public static void astarSpecificTests() {
        // A* should find optimal path
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(4, 4);
            
            LinkedList<Cell> path = astar.search(start, target, false, 0);
            
            assert path != null : "A* should find a path";
            // Should find same length as BFS
            assert path.size() == 9 : "A* should find optimal path of length 9, got " + path.size();
        }

        // A* should explore fewer cells than BFS in some cases
        {
            Maze maze = createSimpleMaze(10, 10);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(9, 9);
            
            // Run BFS
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            bfs.search(start, target, false, 0);
            int bfsVisited = maze.countVisitedCells();
            
            maze.reset();
            
            // Run A*
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            astar.search(start, target, false, 0);
            int astarVisited = maze.countVisitedCells();
            
            // A* should explore fewer or equal cells due to heuristic guidance
            assert astarVisited <= bfsVisited : 
                "A* should explore fewer or equal cells than BFS, A*=" + astarVisited + " BFS=" + bfsVisited;
        }

        // A* uses PriorityQueue-based behavior
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            
            assert astar.numRemainingCells() == 0 : "PriorityQueue should be empty initially";
        }

        // A* updateCell should work (for priority updates)
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            Cell cell = maze.get(2, 2);
            cell.setPrev(maze.get(0, 0)); // Set some prev for priority calculation
            
            astar.addCell(cell);
            // updateCell should not throw
            astar.updateCell(cell);
            
            assert astar.numRemainingCells() == 1 : "Cell should still be in queue after update";
        }

        System.out.println("Finished astarSpecificTests!");
    }

    public static void numRemainingCellsTests() {
        // numRemainingCells() should return 0 for empty structure
        {
            Maze maze = createSimpleMaze(5, 5);
            
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            assert bfs.numRemainingCells() == 0 : "BFS should start with 0 remaining cells";
            
            MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);
            assert dfs.numRemainingCells() == 0 : "DFS should start with 0 remaining cells";
            
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            assert astar.numRemainingCells() == 0 : "A* should start with 0 remaining cells";
        }

        // numRemainingCells() should increment after addCell()
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            
            bfs.addCell(maze.get(0, 0));
            assert bfs.numRemainingCells() == 1 : "Should have 1 cell after add";
            
            bfs.addCell(maze.get(0, 1));
            assert bfs.numRemainingCells() == 2 : "Should have 2 cells after second add";
        }

        // numRemainingCells() should decrement after findNextCell()
        {
            Maze maze = createSimpleMaze(5, 5);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            
            bfs.addCell(maze.get(0, 0));
            bfs.addCell(maze.get(0, 1));
            bfs.addCell(maze.get(0, 2));
            
            assert bfs.numRemainingCells() == 3 : "Should have 3 cells";
            
            bfs.findNextCell();
            assert bfs.numRemainingCells() == 2 : "Should have 2 cells after findNextCell";
            
            bfs.findNextCell();
            assert bfs.numRemainingCells() == 1 : "Should have 1 cell after second findNextCell";
        }

        System.out.println("Finished numRemainingCellsTests!");
    }
}
