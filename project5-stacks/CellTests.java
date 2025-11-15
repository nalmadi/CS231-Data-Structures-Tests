/*
file name:      CellTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea CellTests
*/

public class CellTests {

    public static void main(String[] args) {
        cellTests();
    }

    public static void cellTests() {

        // Row/column coordinates should remain immutable after value changes
        {
            Cell cell = new Cell(4, 7, 0);
            cell.setValue(9);
            cell.setValue(1);
            assert cell.getRow() == 4 : "Row index should not change";
            assert cell.getCol() == 7 : "Column index should not change";
            assert cell.getValue() == 1 : "Value should reflect last write";
        }

        // Locked flag should survive multiple toggles without corrupting value
        {
            Cell cell = new Cell(0, 0, 5, true);
            assert cell.isLocked() : "Initial locked state should be true";
            cell.setLocked(false);
            cell.setValue(3);
            assert cell.getValue() == 3 : "Unlocked cells should permit writes";
            cell.setLocked(true);
            cell.setLocked(false);
            assert !cell.isLocked() : "Final unlock should succeed";
            assert cell.getValue() == 3 : "Value should persist through toggles";
        }

        // Unlocked candidate cells should support reset to zero mid-solve
        {
            Cell cell = new Cell(8, 8, 9);
            cell.setValue(0);
            assert cell.getValue() == 0 : "Zero should be valid placeholder";
            cell.setValue(4);
            assert cell.getValue() == 4 : "Reset cells should accept new value";
        }

        // toString must faithfully mirror numeric value including zero
        {
            Cell cell = new Cell(1, 1, 0);
            assert "0".equals(cell.toString()) : "Zero string should be '0'";
            cell.setValue(7);
            assert "7".equals(cell.toString()) : "Updated toString should match";
        }

        System.out.println("Finished CellTests!");
    }
}
