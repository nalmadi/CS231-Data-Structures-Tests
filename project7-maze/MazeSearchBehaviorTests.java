/*
file name:      MazeSearchBehaviorTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

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
            LinkedList<Cell> bfsPath = bfs.search(maze.getStart(), maze.getTarget());
            assert bfsPath != null : "BFS should find a path";
            int bfsLength = bfsPath.size();

            maze.reset();
            MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);
            LinkedList<Cell> dfsPath = dfs.search(maze.getStart(), maze.getTarget());
            assert dfsPath != null : "DFS should also find a path";
            assert dfsPath.size() >= bfsLength : "DFS path should be at least as long as BFS";
        }

        // A* should match BFS path length and explore fewer cells than DFS
        {
            Maze maze = buildSimpleMaze(false);
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            LinkedList<Cell> astarPath = astar.search(maze.getStart(), maze.getTarget());
            assert astarPath != null : "A* should find a path";
            int astarLength = astarPath.size();

            maze.reset();
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            LinkedList<Cell> bfsPath = bfs.search(maze.getStart(), maze.getTarget());
            assert bfsPath != null;
            assert astarLength == bfsPath.size() : "A* should match optimal path length";
        }

        // Searches should return null when target is blocked
        {
            Maze maze = buildSimpleMaze(true);
            MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);
            LinkedList<Cell> bfsPath = bfs.search(maze.getStart(), maze.getTarget());
            assert bfsPath == null : "Blocked maze should yield null";

            maze.reset();
            MazeAStarSearch astar = new MazeAStarSearch(maze);
            LinkedList<Cell> astarPath = astar.search(maze.getStart(), maze.getTarget());
            assert astarPath == null : "A* should also fail on blocked maze";
        }

        System.out.println("Finished MazeSearchBehaviorTests!");
    }
}
