

public class CellTest {
    
    public static void CellTests() {
        // case 1: testing Cell()
        {
            // given
            Cell cell1 = new Cell(0, 0, 10);
            Cell cell2 = new Cell(0, 1, 10, true);

            // when
            System.out.println(cell1 + " != null");
            System.out.println(cell2 + " != null");

            // then
            assert cell1 != null : "Error in Cell::Cell(int row, int col, int value)";
            assert cell2 != null : "Error in Cell::Cell(int row, int col, int value, boolean locked)";

        }

        //case 2: testing getRow()
        {
            // given
            Cell cell1 = new Cell(2, 3, 5);
            Cell cell2 = new Cell(2, 3, 5, true);

            // when
            int row1 = cell1.getRow();
            int row2 = cell2.getRow();
            System.out.println("Row1: " + row1);
            System.out.println("Row2: " + row2);
            // then
            assert row1 == 2 : "Error in Cell::getRow()";
            assert row2 == 2 : "Error in Cell::getRow()";
        }

        //case 3: testing getCol()
        {
            // given
            Cell cell1 = new Cell(2, 3, 5);
            Cell cell2 = new Cell(2, 3, 5, true);

            // when
            int col1 = cell1.getCol();
            int col2 = cell2.getCol();
            System.out.println("Col1: " + col1);
            System.out.println("Col2: " + col2);
            // then
            assert col1 == 3 : "Error in Cell::getCol()";
            assert col2 == 3 : "Error in Cell::getCol()";
        }

        //case 4: testing isLocked()
        {
            // given
            Cell cell1 = new Cell(2, 3, 5);
            Cell cell2 = new Cell(2, 3, 5, true);

            // when
            boolean locked1 = cell1.isLocked();
            boolean locked2 = cell2.isLocked();

            System.out.println("Locked1: " + locked1);
            System.out.println("Locked2: " + locked2);

            // then
            assert !locked1 : "Error in Cell::isLocked() for unlocked cell";
            assert locked2 : "Error in Cell::isLocked() for locked cell";
        }

        //case 5: testing getValue()
        {
            // given
            Cell cell1 = new Cell(2, 3, 5);
            Cell cell2 = new Cell(2, 3, 7, true);

            // when
            int value1 = cell1.getValue();
            int value2 = cell2.getValue();

            System.out.println("Value1: " + value1);
            System.out.println("Value2: " + value2);

            // then
            assert value1 == 5 : "Error in Cell::getValue() for cell1";
            assert value2 == 7 : "Error in Cell::getValue() for cell2";
        }

        //case 6: testing setValue()
        {
            // given
            Cell cell1 = new Cell(2, 3, 5);
            Cell cell2 = new Cell(2, 3, 7, true);

            // when
            cell1.setValue(9);
            System.out.println("Cell1 after setValue(9): " + cell1);
            cell2.setValue(10);
            System.out.println("Cell2 after setValue(10): " + cell2);

            // then
            assert cell1.getValue() == 9 : "Error in Cell::setValue() for unlocked cell";
            assert cell2.getValue() == 10 : "Error in Cell::setValue() for locked cell should not change value";
        }

        //case 7: setLocked(boolean lock)
        {
            // given
            Cell cell1 = new Cell(2, 3, 5);
            Cell cell2 = new Cell(2, 3, 7, true);

            // when
            cell1.setLocked(true);
            System.out.println("Cell1 after setLocked(true): " + cell1);
            cell2.setLocked(false);
            System.out.println("Cell2 after setLocked(false): " + cell2);

            // then
            assert cell1.isLocked() : "Error in Cell::setLocked(true) for cell1";
            assert !cell2.isLocked() : "Error in Cell::setLocked(false) for cell2";
        }


    }
}
