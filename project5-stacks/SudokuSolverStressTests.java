/*
file name:      SudokuSolverStressTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea SudokuSolverStressTests
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SudokuSolverStressTests {

    private static final String EASY_BOARD =
            "5 3 0 0 7 0 0 0 0\n" +
            "6 0 0 1 9 5 0 0 0\n" +
            "0 9 8 0 0 0 0 6 0\n" +
            "8 0 0 0 6 0 0 0 3\n" +
            "4 0 0 8 0 3 0 0 1\n" +
            "7 0 0 0 2 0 0 0 6\n" +
            "0 6 0 0 0 0 2 8 0\n" +
            "0 0 0 4 1 9 0 0 5\n" +
            "0 0 0 0 8 0 0 7 9\n";

    private static final String CONTRADICTORY_BOARD =
            "5 5 0 0 7 0 0 0 0\n" +
            "6 0 0 1 9 5 0 0 0\n" +
            "0 9 8 0 0 0 0 6 0\n" +
            "8 0 0 0 6 0 0 0 3\n" +
            "4 0 0 8 0 3 0 0 1\n" +
            "7 0 0 0 2 0 0 0 6\n" +
            "0 6 0 0 0 0 2 8 0\n" +
            "0 0 0 4 1 9 0 0 5\n" +
            "0 0 0 0 8 0 0 7 9\n";

    public static void main(String[] args) {
        sudokuSolverStressTests();
    }

    public static void sudokuSolverStressTests() {

        // A standard board should solve successfully
        {
            String filename = "sudoku-easy.txt";
            writeFile(filename, EASY_BOARD);
            Sudoku sudoku = new Sudoku(filename, 0);
            assert sudoku.solve() : "Known solvable board should return true";
            new java.io.File(filename).delete();
        }

        // A contradictory board should fail fast
        {
            String filename = "sudoku-contradictory.txt";
            writeFile(filename, CONTRADICTORY_BOARD);
            Sudoku sudoku = new Sudoku(filename, 0);
            assert !sudoku.solve() : "Contradictory board should return false";
            new java.io.File(filename).delete();
        }

        // Random board with requested locked count should be solvable (delay=0)
        {
            Sudoku sudoku = new Sudoku(5, 0);
            assert sudoku.solve() : "Random board with few locks should solve";
        }

        System.out.println("Finished SudokuSolverStressTests!");
    }

    private static void writeFile(String filename, String contents) {
        try {
            Files.writeString(Path.of(filename), contents);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write sudoku fixture " + filename, e);
        }
    }
}
