/*
file name:      CellTests.java
Authors:        Najam Tariq
last modified:  12/13/2025

How to run:     java -ea CellTests
*/

public class CellTests {

    public static void main(String[] args) {
        cellTypeTests();
        cellConstructorTests();
        cellGetterTests();
        cellStateTests();
        cellResetTests();
        System.out.println("All CellTests passed!");
    }

    public static void cellTypeTests() {
        // CellType enum should have FREE and OBSTACLE values
        {
            CellType free = CellType.FREE;
            CellType obstacle = CellType.OBSTACLE;
            assert free != null : "CellType.FREE should exist";
            assert obstacle != null : "CellType.OBSTACLE should exist";
            assert free != obstacle : "FREE and OBSTACLE should be different";
        }

        // CellType values should be retrievable
        {
            CellType[] values = CellType.values();
            assert values.length == 2 : "CellType should have exactly 2 values";
        }

        System.out.println("Finished cellTypeTests!");
    }

    public static void cellConstructorTests() {
        // Constructor should set row, col, and type correctly
        {
            Cell cell = new Cell(3, 5, CellType.FREE);
            assert cell.getRow() == 3 : "Row should be 3";
            assert cell.getCol() == 5 : "Col should be 5";
            assert cell.getType() == CellType.FREE : "Type should be FREE";
        }

        // Constructor should initialize prev to null and visited to false
        {
            Cell cell = new Cell(0, 0, CellType.OBSTACLE);
            assert cell.getPrev() == null : "prev should be null initially";
            assert cell.visited() == false : "visited should be false initially";
        }

        // Constructor should work with OBSTACLE type
        {
            Cell cell = new Cell(2, 2, CellType.OBSTACLE);
            assert cell.getType() == CellType.OBSTACLE : "Type should be OBSTACLE";
        }

        System.out.println("Finished cellConstructorTests!");
    }

    public static void cellGetterTests() {
        // getRow() should return correct row
        {
            Cell cell = new Cell(7, 3, CellType.FREE);
            assert cell.getRow() == 7 : "getRow should return 7";
        }

        // getCol() should return correct column
        {
            Cell cell = new Cell(4, 9, CellType.FREE);
            assert cell.getCol() == 9 : "getCol should return 9";
        }

        // getType() should return correct type
        {
            Cell freeCell = new Cell(0, 0, CellType.FREE);
            Cell obstacleCell = new Cell(1, 1, CellType.OBSTACLE);
            assert freeCell.getType() == CellType.FREE : "getType should return FREE";
            assert obstacleCell.getType() == CellType.OBSTACLE : "getType should return OBSTACLE";
        }

        // visited() should return false for new cell
        {
            Cell cell = new Cell(0, 0, CellType.FREE);
            assert cell.visited() == false : "visited should be false for new cell";
        }

        // getPrev() should return null for new cell
        {
            Cell cell = new Cell(0, 0, CellType.FREE);
            assert cell.getPrev() == null : "getPrev should be null for new cell";
        }

        System.out.println("Finished cellGetterTests!");
    }

    public static void cellStateTests() {
        // setPrev() should set visited to true and prev to specified cell
        {
            Cell cell1 = new Cell(0, 0, CellType.FREE);
            Cell cell2 = new Cell(1, 0, CellType.FREE);
            cell2.setPrev(cell1);
            assert cell2.visited() == true : "visited should be true after setPrev";
            assert cell2.getPrev() == cell1 : "getPrev should return cell1";
        }

        // setPrev() with same cell (for start cell)
        {
            Cell start = new Cell(0, 0, CellType.FREE);
            start.setPrev(start);
            assert start.visited() == true : "start should be visited after setPrev(self)";
            assert start.getPrev() == start : "start's prev should be itself";
        }

        // Chain of setPrev calls
        {
            Cell cell1 = new Cell(0, 0, CellType.FREE);
            Cell cell2 = new Cell(0, 1, CellType.FREE);
            Cell cell3 = new Cell(0, 2, CellType.FREE);
            
            cell1.setPrev(cell1); // start
            cell2.setPrev(cell1);
            cell3.setPrev(cell2);
            
            assert cell3.getPrev() == cell2 : "cell3's prev should be cell2";
            assert cell2.getPrev() == cell1 : "cell2's prev should be cell1";
            assert cell1.getPrev() == cell1 : "cell1's prev should be cell1 (start)";
        }

        System.out.println("Finished cellStateTests!");
    }

    public static void cellResetTests() {
        // reset() should set visited to false and prev to null
        {
            Cell cell1 = new Cell(0, 0, CellType.FREE);
            Cell cell2 = new Cell(1, 0, CellType.FREE);
            cell2.setPrev(cell1);
            
            assert cell2.visited() == true : "Should be visited before reset";
            assert cell2.getPrev() == cell1 : "Should have prev before reset";
            
            cell2.reset();
            
            assert cell2.visited() == false : "visited should be false after reset";
            assert cell2.getPrev() == null : "prev should be null after reset";
        }

        // reset() should not affect row, col, or type
        {
            Cell cell = new Cell(5, 7, CellType.OBSTACLE);
            Cell other = new Cell(0, 0, CellType.FREE);
            cell.setPrev(other);
            cell.reset();
            
            assert cell.getRow() == 5 : "Row should still be 5 after reset";
            assert cell.getCol() == 7 : "Col should still be 7 after reset";
            assert cell.getType() == CellType.OBSTACLE : "Type should still be OBSTACLE after reset";
        }

        // reset() on never-visited cell should work
        {
            Cell cell = new Cell(0, 0, CellType.FREE);
            cell.reset(); // Should not throw
            assert cell.visited() == false : "visited should still be false";
            assert cell.getPrev() == null : "prev should still be null";
        }

        System.out.println("Finished cellResetTests!");
    }
}
