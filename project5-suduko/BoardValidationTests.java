/*
file name:      BoardValidationTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea BoardValidationTests
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BoardValidationTests {

    private static final String PARTIAL =
            "0 0 0 3 0 1 0 0 0\n" +
            "0 0 0 0 0 0 0 0 0\n" +
            "9 0 0 0 0 0 0 0 0\n" +
            "0 0 0 0 0 0 9 0 0\n" +
            "0 0 6 0 0 0 0 0 7\n" +
            "2 0 0 0 0 0 0 0 0\n" +
            "0 0 0 0 5 0 0 0 0\n" +
            "0 0 0 0 7 0 8 0 0\n" +
            "0 0 0 0 0 0 0 0 0\n";

    private static final String INVALID_ROW =
            "1 1 0 0 0 0 0 0 0\n" + PARTIAL.substring(PARTIAL.indexOf('\n') + 1);

    public static void main(String[] args) {
        boardValidationTests();
    }

    public static void boardValidationTests() {

        // numLocked should match non-zero entries from file input
        {
            String filename = "board-partial.txt";
            writeFile(filename, PARTIAL);
            Board board = new Board(filename);
            int nonZero = countNonZero(PARTIAL);
            assert board.numLocked() == nonZero : "Locked cells should match non-zero values";
            new java.io.File(filename).delete();
        }

        // validValue must reject duplicates in row, column, and block
        {
            Board board = new Board();
            board.set(0, 0, 5, true);
            board.set(1, 1, 5, true);
            board.set(1, 4, 9, true);
            assert !board.validValue(0, 3, 5) : "Row duplicates invalid";
            assert !board.validValue(2, 1, 5) : "Column duplicates invalid";
            assert !board.validValue(2, 2, 5) : "Subgrid duplicates invalid";
            assert board.validValue(0, 3, 4) : "Unused values should be valid";
        }

        // validSolution should return false for row conflict board
        {
            String filename = "board-invalid-row.txt";
            writeFile(filename, INVALID_ROW);
            Board board = new Board(filename);
            assert !board.validSolution() : "Row duplicates should fail solution check";
            new java.io.File(filename).delete();
        }

        // Random constructor should generate requested locked count
        {
            Board board = new Board(15);
            assert board.numLocked() == 15 : "Random constructor should lock requested count";
            for (int r = 0; r < board.getRows(); r++) {
                for (int c = 0; c < board.getCols(); c++) {
                    int val = board.value(r, c);
                    assert val >= 0 && val <= 9 : "Generated values must be digits";
                }
            }
        }

        System.out.println("Finished BoardValidationTests!");
    }

    private static void writeFile(String filename, String contents) {
        try {
            Files.writeString(Path.of(filename), contents);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write test fixture " + filename, e);
        }
    }

    private static int countNonZero(String text) {
        int count = 0;
        for (String token : text.split("\\s+")) {
            if (!token.equals("0") && !token.isEmpty()) {
                count++;
            }
        }
        return count;
    }
}
