/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

import java.awt.Point;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author panu
 */
public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        this.board = new Board();
    }

    @Test
    public void boardSizeIsCorrect() {
        assertEquals(15, board.getContents()[0].length);
        assertEquals(15, board.getContents()[1].length);
    }
    
    @Test
    public void getSquareReturnsNullOutOfBounds() {
        for (int y = -1; y < 16; y +=16) {
            for (int x = -1; x < 16; x +=16) {
                assertEquals(null, board.getSquare(x, y));
            }
        }
    }

    @Test
    public void correctAmountOfNormalSquares() {
        assertEquals(164, howManySquaresOfDifferentType(SquareType.SQUARE_NORMAL));
    }

    @Test
    public void correctAmountOfDoubleBonusLetterSquares() {
        assertEquals(24, howManySquaresOfDifferentType(SquareType.SQUARE_BONUS_LETTER_2X));
    }

    @Test
    public void correctAmountOfTripleBonusLetterSquares() {
        assertEquals(12, howManySquaresOfDifferentType(SquareType.SQUARE_BONUS_LETTER_3X));
    }

    @Test
    public void correctAmountOfDoubleBonusWordSquares() {
        assertEquals(17, howManySquaresOfDifferentType(SquareType.SQUARE_BONUS_WORD_2X));
    }

    @Test
    public void correctAmountOfTripleBonusWordSquares() {
        assertEquals(8, howManySquaresOfDifferentType(SquareType.SQUARE_BONUS_WORD_3X));
    }

    @Test
    public void doubleBonusLetterSquaresAreCorrectlyPlaced() {
        ArrayList<Point> validPositions = doubleBonusLetterValidPositions();
        for (Point p : validPositions) {
            int y = p.x;
            int x = p.y;
            assertEquals(SquareType.SQUARE_BONUS_LETTER_2X, board.getSquare(x, y).getType());
            assertEquals(SquareType.SQUARE_BONUS_LETTER_2X, board.getSquare(x, 14 - y).getType());
            assertEquals(SquareType.SQUARE_BONUS_LETTER_2X, board.getSquare(14 - x, y).getType());
            assertEquals(SquareType.SQUARE_BONUS_LETTER_2X, board.getSquare(14 - x, 14 - y).getType());
        }
    }

    @Test
    public void tripleBonusLetterSquaresAreCorrectlyPlaced() {
        for (int y = 1; y < 14; y += 4) {
            for (int x = 1; x < 14; x += 4) {
                if ((x == 1 && (y == 1 || y == 13))
                        || (x == 13 && (y == 1 || y == 13))) {
                    continue;
                }
                assertEquals(SquareType.SQUARE_BONUS_LETTER_3X, board.getSquare(x, y).getType());
            }
        }
    }

    @Test
    public void doubleBonusWordSquaresAreCorrectlyPlaced() {
        for (int y = 1; y < 14; y++) {
            if (y >= 5 && y <= 9 && y != 7) {
                continue;
            }
            int x = y;
            assertEquals(SquareType.SQUARE_BONUS_WORD_2X, board.getSquare(x, y).getType());
            assertEquals(SquareType.SQUARE_BONUS_WORD_2X, board.getSquare(x, 14 - y).getType());
        }
    }

    @Test
    public void tripleBonusWordSquaresAreCorrectlyPlaced() {
        for (int y = 0; y < 15; y += 7) {
            for (int x = 0; x < 15; x += 7) {
                if (x == 7 && y == 7) {
                    continue;
                }
                assertEquals(SquareType.SQUARE_BONUS_WORD_3X, board.getSquare(x, y).getType());
            }
        }
    }

    private ArrayList<Point> doubleBonusLetterValidPositions() {
        ArrayList<Point> validPositions = new ArrayList<>();
        validPositions.add(new Point(0, 3));
        validPositions.add(new Point(2, 6));
        validPositions.add(new Point(3, 0));
        validPositions.add(new Point(3, 7));
        validPositions.add(new Point(6, 2));
        validPositions.add(new Point(6, 6));
        validPositions.add(new Point(7, 3));
        return validPositions;
    }

    private int howManySquaresOfDifferentType(SquareType type) {
        int i = 0;
        for (Square[] row : board.getContents()) {
            for (Square s : row) {
                if (s.getType() == type) {
                    i++;
                }
            }
        }
        return i;
    }
}
