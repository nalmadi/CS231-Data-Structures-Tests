package project5-suduko;

public class BoardTest {
    public static void main() {

        //test 1: Board()
        {
            // given
            Board board1 = new Board();

            // when
            System.out.println(board1 + " != null");

            // then
            assert board1 != null : "Error in Board::Board()";
        }

        //test 2: getCell()
        {
            // given
            Board board1 = new Board();

            // when
            Cell cell = board1.getCell(0, 0);
            System.out.println(cell + " != null");

            // then
            assert cell != null : "Error in Board::getCell(int row, int col)";
        }

        //test 3: set(int row, int col, int value)
        {
            // given
            Board board1 = new Board();
            Board board2 = new Board();
            board1.set(0, 0, 5);
            board2.set(0, 0, 10, true);

            // when
            Cell cell1 = board1.getCell(0, 0);
            System.out.println(cell1 + " == 5");
            Cell cell2 = board2.getCell(0, 0);
            System.out.println(cell2 + " == 10");
            System.out.println(cell2.isLocked() + " == true");

            // then
            assert cell1.getValue() == 5 : "Error in Board::set(int row, int col, int value)";
            assert cell2.getValue() == 10 : "Error in Board::set(int row, int col, int value, boolean locked)";
            assert cell2.isLocked() == true : "Error in Board::set(int row, int col, int value, boolean locked)";
        }   

        //test 4: Board(String filename) loading from file
        {
            // given
            Board board1 = new Board("board1.txt");

            // when
            System.out.println(board1 + " != null");


            // then
            assert board1 != null : "Error in Board::Board(String filename)";
        }

        //test 5: Board(String filename) loading from file with invalid filename
        {
            // given
            Board board1 = new Board("invalid_file.txt");

            // when
            System.out.println(board1 + " == null");

            //then 
            assert board1 == null : "Error in Board::Board(String filename) with invalid filename";
        }

        //test 6: Board(String filename) loading - confirm correct values
        {
            // given
            Board board1 = new Board("board1.txt");

            // when
            Cell cell1 = board1.getCell(0, 0);
            Cell cell2 = board1.getCell(0, 3);
            Cell cell3 = board1.getCell(2, 0);

            System.out.println(cell1 + " == 0");
            System.out.println(cell2 + " == 3");
            System.out.println(cell3 + " == 9");

            // then
            assert cell1.getValue() == 0 : "Error in Board::Board(String filename) loading value at (0,0)";
            assert cell2.getValue() == 3 : "Error in Board::Board(String filename) loading value at (0,3)";
            assert cell3.getValue() == 9 : "Error in Board::Board(String filename) loading value at (3,0)";
        }

        //test 7: toString() (filename)
        {
            //given 
            Board board1 = new Board("board1.txt");

            String board = " 0 0 0 3 0 1 0 0 0\n" + //
                                "0 0 0 0 0 0 0 0 0\n" + //
                                "9 0 0 0 0 0 0 0 0\n" + //
                                "0 0 0 0 0 0 9 0 0\n" + //
                                "0 0 6 0 0 0 0 0 7\n" + //
                                "2 0 0 0 0 0 0 0 0\n" + //
                                "0 0 0 0 5 0 0 0 0\n" + //
                                "0 0 0 0 7 0 8 0 0\n" + //
                                "0 0 0 0 0 0 0 0 0";

            //when
            String boardString = board1.toString();
            System.out.println(boardString + "\n==\n" + board);

            
          
        }


    }
}
