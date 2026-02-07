/*
file name:      MazeSearchersTests.java
Authors:        Yubin Moon
last modified:  12/08/2025

How to run:     java -ea MazeSearchersTests
*/

import java.util.LinkedList;

public class MazeTester {

    
    // Below are helper methods for test cases... 

    /**
     * Build a simple 3x3 all-FREE maze:
     *
     *  (0,0) . .
     *   .    . .
     *   .    . (2,2)
     *
     * start = (0,0), target = (2,2)
     */
    private static Maze buildSimpleOpenMaze() {
        int rows = 3;
        int cols = 3;
        Cell[][] grid = new Cell[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(r, c, CellType.FREE);
            }
        }

        Cell start = grid[0][0];
        Cell target = grid[2][2];

        return new Maze(grid, start, target);
    }

    /**
     * Build a 3x3 maze where start and target are separated by a solid obstacle row:
     *
     *  S . .
     *  X X X
     *  T . .
     *
     * S = (0,0) FREE
     * T = (2,0) FREE
     * entire row 1 is OBSTACLE, so no path exists.
     */
    private static Maze buildBlockedMaze() {
        int rows = 3;
        int cols = 3;
        Cell[][] grid = new Cell[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                CellType type = (r == 1) ? CellType.OBSTACLE : CellType.FREE;
                grid[r][c] = new Cell(r, c, type);
            }
        }

        Cell start = grid[0][0];
        Cell target = grid[2][0];

        return new Maze(grid, start, target);
    }

    /**
     * Verify that a path is reasonable:
     *  - non-null, non-empty
     *  - starts at start, ends at target
     *  - only passes through FREE cells
     *  - each consecutive pair is 4-connected (Manhattan distance 1)
     */
    private static void assertValidPath(LinkedList<Cell> path, Cell start, Cell target, Maze maze) {
        assert path != null : "Error: path should not be null";
        assert !path.isEmpty() : "Error: path should not be empty";

        Cell first = path.getFirst();
        Cell last = path.getLast();

        assert first == start : "Error: path should start at the start cell";
        assert last == target : "Error: path should end at the target cell";

        for (int i = 0; i < path.size(); i++) {
            Cell c = path.get(i);
            assert c.getType() == CellType.FREE :
                    "Error: path should only pass through FREE cells; index " + i +
                    " has type " + c.getType();

            if (i > 0) {
                Cell prev = path.get(i - 1);
                int dr = Math.abs(c.getRow() - prev.getRow());
                int dc = Math.abs(c.getCol() - prev.getCol());
                assert dr + dc == 1 :
                        "Error: cells at indices " + (i - 1) + " and " + i +
                        " are not 4-connected neighbors";
            }
        }
    }

    /* TESTS START!! */

    public static void mazeTests() {

        // case 1: DFS finds some path in a simple open maze
        {
            // given
            Maze maze = buildSimpleOpenMaze();
            Cell start = maze.getStart();
            Cell target = maze.getTarget();
            MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);

            // when
            System.out.println("=== case 1: DFS on simple open maze ===");
            LinkedList<Cell> path = null;
            try {
                path = dfs.search(start, target, false, 0);
            } catch (InterruptedException e) {
                assert false : "DFS search should not throw InterruptedException";
            }

            System.out.println("DFS path length: " + (path == null ? "null" : path.size()));

            // then
            assertValidPath(path, start, target, maze);
        }

        // case 2: BFS finds a shortest path on simple open maze
        {
            // given
            Maze maze = buildSimpleOpenMaze();
            Cell start = maze.getStart();
            Cell target = maze.getTarget();
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);

            // when
            System.out.println("\n=== case 2: BFS shortest path on simple open maze ===");
            LinkedList<Cell> path = null;
            try {
                path = bfs.search(start, target, false, 0);
            } catch (InterruptedException e) {
                assert false : "BFS search should not throw InterruptedException";
            }

            System.out.println("BFS path length: " + (path == null ? "null" : path.size()));

            // then
            assertValidPath(path, start, target, maze);

            // For (0,0) -> (2,2) in a 4-connected grid:
            // steps = |2-0| + |2-0| = 4 steps, so 5 cells in the path total.
            assert path.size() == 5 :
                    "Error: BFS should find shortest path of 5 cells, got " + path.size();
        }

        // case 3: A* also finds a shortest path (same length as BFS)
        {
            // given
            Maze maze = buildSimpleOpenMaze();
            Cell start = maze.getStart();
            Cell target = maze.getTarget();
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            MazeAStarSearch aStar = new MazeAStarSearch(maze);

            // when
            System.out.println("\n=== case 3: A* on simple open maze ===");
            LinkedList<Cell> bfsPath = null;
            LinkedList<Cell> aStarPath = null;

            try {
                bfsPath   = bfs.search(start, target, false, 0);
                aStarPath = aStar.search(start, target, false, 0);
            } catch (InterruptedException e) {
                assert false : "Search should not throw InterruptedException";
            }

            System.out.println("BFS path length: " + (bfsPath == null ? "null" : bfsPath.size()));
            System.out.println("A* path length:  " + (aStarPath == null ? "null" : aStarPath.size()));

            // then
            assertValidPath(bfsPath, start, target, maze);
            assertValidPath(aStarPath, start, target, maze);

            assert aStarPath.size() == bfsPath.size() :
                    "Error: A* should find path of same length as BFS";
        }

        // case 4: unreachable maze, so all searchers should return null and have 0 remaining cells
        {
            // given
            Maze maze = buildBlockedMaze();
            Cell start = maze.getStart();
            Cell target = maze.getTarget();

            MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            MazeAStarSearch aStar = new MazeAStarSearch(maze);

            // when
            System.out.println("\n=== case 4: unreachable maze ===");
            LinkedList<Cell> dfsPath = null;
            LinkedList<Cell> bfsPath = null;
            LinkedList<Cell> aStarPath = null;

            try {
                dfsPath   = dfs.search(start, target, false, 0);
                bfsPath   = bfs.search(start, target, false, 0);
                aStarPath = aStar.search(start, target, false, 0);
            } catch (InterruptedException e) {
                assert false : "Search should not throw InterruptedException";
            }

            System.out.println("DFS path (should be null): " + dfsPath);
            System.out.println("BFS path (should be null): " + bfsPath);
            System.out.println("A* path  (should be null): " + aStarPath);

            // then
            assert dfsPath == null : "Error: DFS should return null on unreachable maze";
            assert bfsPath == null : "Error: BFS should return null on unreachable maze";
            assert aStarPath == null : "Error: A* should return null on unreachable maze";

            assert dfs.numRemainingCells() == 0 :
                    "Error: DFS should have 0 remaining cells after exhausting search";
            assert bfs.numRemainingCells() == 0 :
                    "Error: BFS should have 0 remaining cells after exhausting search";
            assert aStar.numRemainingCells() == 0 :
                    "Error: A* should have 0 remaining cells after exhausting search";
        }

        // case 5: getMaze() returns the Maze passed into the constructor
        {
            // given
            Maze maze = new Maze(5, 5, 0.3);
            AbstractMazeSearch searcher = new MazeAStarSearch(maze);

            // when
            System.out.println("\n=== case 5: getMaze() ===");
            System.out.println("searcher.getMaze() == maze ? " + (searcher.getMaze() == maze));

            // then
            assert searcher.getMaze() == maze :
                    "Error: AbstractMazeSearch::getMaze() should return the same Maze instance";
        }

        // case 6: setTarget() and getTarget()
        {
            // given
            Maze maze = new Maze(5, 5, 0.3);
            AbstractMazeSearch searcher = new MazeAStarSearch(maze);
            Cell newTarget = maze.get(2, 4); // some cell in the grid

            // when
            System.out.println("\n=== case 6: setTarget() / getTarget() ===");
            System.out.println("initial target: " + searcher.getTarget());
            searcher.setTarget(newTarget);
            System.out.println("after setTarget(2,4): " + searcher.getTarget());

            // then
            assert searcher.getTarget() == newTarget :
                    "Error: setTarget() / getTarget() mismatch";
        }

        // case 7: setCur() and getCur()
        {
            // given
            Maze maze = new Maze(5, 5, 0.3);
            AbstractMazeSearch searcher = new MazeAStarSearch(maze);
            Cell curCell = maze.get(1, 1);

            // when
            System.out.println("\n=== case 7: setCur() / getCur() ===");
            System.out.println("initial cur: " + searcher.getCur());
            searcher.setCur(curCell);
            System.out.println("after setCur(1,1): " + searcher.getCur());

            // then
            assert searcher.getCur() == curCell :
                    "Error: setCur() / getCur() mismatch";
        }

        // case 8: setStart() and getStart()
        {
            // given
            Maze maze = new Maze(5, 5, 0.3);
            AbstractMazeSearch searcher = new MazeAStarSearch(maze);
            Cell start = maze.get(0, 3);

            // when
            System.out.println("\n=== case 8: setStart() / getStart() ===");
            System.out.println("initial start: " + searcher.getStart());
            searcher.setStart(start);
            System.out.println("after setStart(0,3): " + searcher.getStart());

            // then
            assert searcher.getStart() == start :
                    "Error: setStart() / getStart() mismatch";
        }

        // case 9: reset() clears start, target, and cur in AbstractMazeSearch
        {
            // given
            Maze maze = new Maze(5, 5, 0.3);
            AbstractMazeSearch searcher = new MazeAStarSearch(maze);

            Cell start  = maze.get(0, 0);
            Cell target = maze.get(4, 4);
            Cell cur    = maze.get(1, 1);

            searcher.setStart(start);
            searcher.setTarget(target);
            searcher.setCur(cur);

            // when
            System.out.println("\n=== case 9: AbstractMazeSearch.reset() ===");
            System.out.println("BEFORE reset():");
            System.out.println("\tstart  = " + searcher.getStart());
            System.out.println("\ttarget = " + searcher.getTarget());
            System.out.println("\tcur    = " + searcher.getCur());

            searcher.reset();

            System.out.println("AFTER reset():");
            System.out.println("\tstart  = " + searcher.getStart());
            System.out.println("\ttarget = " + searcher.getTarget());
            System.out.println("\tcur    = " + searcher.getCur());

            // then
            assert searcher.getStart() == null :
                    "Error: reset() should set start to null";
            assert searcher.getTarget() == null :
                    "Error: reset() should set target to null";
            assert searcher.getCur() == null :
                    "Error: reset() should set cur to null";
        }

        // case 10: Maze.reset() resets visited and prev after a search
        {
            // given
            Maze maze = buildSimpleOpenMaze();
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            Cell start = maze.getStart();
            Cell target = maze.getTarget();

            // when
            System.out.println("\n=== case 10: Maze.reset() after a BFS search ===");
            LinkedList<Cell> path = null;
            try {
                path = bfs.search(start, target, false, 0);
            } catch (InterruptedException e) {
                assert false : "BFS search should not throw InterruptedException";
            }

            assert path != null : "Sanity: BFS should find a path before Maze.reset()";

            maze.reset();

            // then
            for (int r = 0; r < maze.getRows(); r++) {
                for (int c = 0; c < maze.getCols(); c++) {
                    Cell cell = maze.get(r, c);
                    assert !cell.visited() :
                            "Error: Maze.reset() should set visited=false for all cells";
                    assert cell.getPrev() == null :
                            "Error: Maze.reset() should set prev=null for all cells";
                }
            }
        }

        System.out.println("\n*** Done testing Maze searchers! ***\n");
    }

}
