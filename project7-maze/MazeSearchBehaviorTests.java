/*
file name:      MazeSearchBehaviorTests.java
Authors:        Rishit Chatterjee, Najam Tariq
last modified:  12/13/2025

How to run:     java -ea MazeSearchBehaviorTests
*/

import java.util.LinkedList;

public class MazeSearchBehaviorTests {

    private static Maze buildSimpleMaze(boolean blockExit) {
        int rows = 4;
        int cols = 4;
        Cell[][] grid = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell(r, c, CellType.FREE);
            }
        }
        // Add a wall to force algorithms to choose specific path
        grid[1][1] = new Cell(1, 1, CellType.OBSTACLE);
        grid[1][2] = new Cell(1, 2, CellType.OBSTACLE);
        grid[2][1] = new Cell(2, 1, CellType.OBSTACLE);
        Cell start = grid[0][0];
        Cell target = blockExit ? new Cell(3, 3, CellType.OBSTACLE) : grid[3][3];
        if (blockExit) {
            grid[3][3] = target;
        }
        return new Maze(grid, start, target);
    }

    public static void main(String[] args) {
        mazeSearchBehaviorTests();
    }

    public static void mazeSearchBehaviorTests() {

        // BFS should find shortest path compared to DFS
        {
            Maze maze = buildSimpleMaze(false);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            LinkedList<Cell> bfsPath = bfs.search(maze.getStart(), maze.getTarget(), false, 0);
            assert bfsPath != null : "BFS should find a path";
            int bfsLength = bfsPath.size();

            maze.reset();
            MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);
            LinkedList<Cell> dfsPath = dfs.search(maze.getStart(), maze.getTarget(), false, 0);
            assert dfsPath != null : "DFS should also find a path";
            assert dfsPath.size() >= bfsLength : "DFS path should be at least as long as BFS";
        }

        // A* should match BFS path length and explore fewer cells than DFS
        {
            Maze maze = buildSimpleMaze(false);
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            LinkedList<Cell> astarPath = astar.search(maze.getStart(), maze.getTarget(), false, 0);
            assert astarPath != null : "A* should find a path";
            int astarLength = astarPath.size();

            maze.reset();
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            LinkedList<Cell> bfsPath = bfs.search(maze.getStart(), maze.getTarget(), false, 0);
            assert bfsPath != null;
            assert astarLength == bfsPath.size() : "A* should match optimal path length";
        }

        // Searches should return null when target is blocked
        {
            Maze maze = buildSimpleMaze(true);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            LinkedList<Cell> bfsPath = bfs.search(maze.getStart(), maze.getTarget(), false, 0);
            assert bfsPath == null : "Blocked maze should yield null";

            maze.reset();
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            LinkedList<Cell> astarPath = astar.search(maze.getStart(), maze.getTarget(), false, 0);
            assert astarPath == null : "A* should also fail on blocked maze";
        }

        // Test with different maze sizes
        {
            Maze maze = new Maze(10, 10, 0.1);
            Cell start = maze.get(0, 0);
            Cell target = maze.get(9, 9);
            
            // Only test if start and target are not obstacles
            if (start.getType() == CellType.FREE && target.getType() == CellType.FREE) {
                MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
                LinkedList<Cell> bfsPath = bfs.search(start, target, false, 0);
                
                if (bfsPath != null) {
                    maze.reset();
                    MazeAStarSearch astar = new MazeAStarSearch(maze);
                    LinkedList<Cell> astarPath = astar.search(start, target, false, 0);
                    
                    assert astarPath != null : "A* should find path if BFS did";
                    assert astarPath.size() == bfsPath.size() : 
                        "A* and BFS should find same length paths";
                }
            }
        }

        // Test cells visited count
        {
            Maze maze = new Maze(8, 8, 0.0); // No obstacles
            Cell start = maze.get(0, 0);
            Cell target = maze.get(7, 7);
            
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            bfs.search(start, target, false, 0);
            int bfsVisited = maze.countVisitedCells();
            
            maze.reset();
            
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            astar.search(start, target, false, 0);
            int astarVisited = maze.countVisitedCells();
            
            assert astarVisited <= bfsVisited : 
                "A* should visit fewer or equal cells than BFS";
        }

        System.out.println("Finished MazeSearchBehaviorTests!");
    }
}
