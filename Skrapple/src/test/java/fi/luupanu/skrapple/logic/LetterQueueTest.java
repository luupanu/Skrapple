/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.logic.LetterQueue;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.constants.LetterType;
import java.util.List;
import java.util.Set;
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
public class LetterQueueTest {

    private LetterQueue q;
    private Board b;
    private Letter let1;
    private Letter let2;

    @Before
    public void setUp() {
        q = new LetterQueue();
        b = new Board();
        let1 = new Letter(LetterType.LETTER_E);
        let2 = new Letter(LetterType.LETTER_F);
    }

    @Test
    public void letterQueueIsEmptyWhenCreated() {
        assertEquals(0, q.getContents().size());
    }

    @Test
    public void addingALetterToQueueWithBadCoordinatesFails() {
        for (int y = -1; y < 16; y += 16) {
            for (int x = -1; x < 16; x += 16) {
                assertEquals(false, q.addLetterToQueue(let1, new Coord(x, y), b));
                assertEquals(0, q.getContents().size());
            }
        }
    }

    @Test
    public void addingALetterToQueueWhenSquareIsOccupiedFails() {
        b.getSquare(0, 0).placeLetter(let1);
        assertEquals(false, q.addLetterToQueue(let2, new Coord(0, 0), b));
        assertEquals(0, q.getContents().size());
    }

    @Test
    public void takingALetterWorks() {
        assertEquals(true, q.addLetterToQueue(let1, new Coord(7, 7), b));
        assertEquals(let1, q.takeLetterFromQueue(let1));
        assertEquals(0, q.getContents().size());
    }

    @Test
    public void cancelingAQueueReturnsSameLetters() {
        assertEquals(true, q.addLetterToQueue(let1, new Coord(7, 7), b));
        assertEquals(true, q.addLetterToQueue(let2, new Coord(7, 8), b));
        List<Letter> canceled = q.cancelLetterQueue();
        assertEquals(true, canceled.contains(let1));
        assertEquals(true, canceled.contains(let2));
        assertEquals(0, q.getContents().size());
    }

    @Test
    public void addingTheSameLetterTwiceToSameCoordinatesSucceedsIfRemovedFirst() {
        assertEquals(true, q.addLetterToQueue(let1, new Coord(7, 7), b));
        assertEquals(let1, q.takeLetterFromQueue(let1));
        assertEquals(0, q.getContents().size());
        assertEquals(true, q.addLetterToQueue(let1, new Coord(7, 7), b));
        assertEquals(1, q.getContents().size());
    }

    @Test
    public void addingALetterToSameCoordinatesFails() {
        assertEquals(true, q.addLetterToQueue(let1, new Coord(7, 7), b));
        assertEquals(false, q.addLetterToQueue(let2, new Coord(7, 7), b));
    }

    @Test
    public void changingDirectionSucceeds() {
        assertEquals(true, q.addLetterToQueue(let1, new Coord(7, 7), b));
        assertEquals(true, q.addLetterToQueue(let2, new Coord(7, 8), b));
        assertEquals(false, q.getDirection());
        assertEquals(let1, q.takeLetterFromQueue(let1));
        assertEquals(let2, q.takeLetterFromQueue(let2));
        assertEquals(true, q.addLetterToQueue(let1, new Coord(7, 7), b));
        assertEquals(true, q.addLetterToQueue(let2, new Coord(8, 7), b));
        assertEquals(true, q.getDirection());
    }

    @Test
    public void addingALetterToQueueWhenBoardIsEmptyFailsIfTheWordIsNotConstructedTowardsTheCenterSquare() {
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if (x == 7 || y == 7) {
                    continue;
                }
                assertEquals(false, q.addLetterToQueue(let1, new Coord(x, y), b));
                assertEquals(0, q.getContents().size());
            }
        }
    }

    @Test
    public void addingALetterToQueueWhenBoardIsEmptySucceedsIfTheWordIsConstructedTowardsTheCenterSquareVertically() {
        for (int y = 0; y < 15; y++) {
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(let1, new Coord(7, y), b));
            assertEquals(1, queue.getContents().size());
        }
    }

    @Test
    public void addingALetterToQueueWhenBoardIsEmptySucceedsIfTheWordIsConstructedTowardsTheCenterSquareHorizontally() {
        for (int x = 0; x < 15; x++) {
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(let1, new Coord(x, 7), b));
            assertEquals(1, queue.getContents().size());
        }
    }

    @Test
    public void addingASecondLetterToQueueWhenBoardIsEmptyFailsIfTheWordIsNotConstructedTowardsTheCenterSquareHorizontally() {
        for (int x = 0; x < 15; x++) {
            if (x == 7) {
                continue;
            }
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(let1, new Coord(x, 7), b));
            assertEquals(false, queue.addLetterToQueue(let2, new Coord(x, 8), b));
            assertEquals(false, queue.addLetterToQueue(let2, new Coord(x, 6), b));
        }
    }

    @Test
    public void addingASecondLetterToQueueWhenBoardIsEmptyFailsIfTheWordIsNotConstructedTowardsTheCenterSquareVertically() {
        for (int y = 0; y < 15; y++) {
            if (y == 7) {
                continue;
            }
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(let1, new Coord(7, y), b));
            assertEquals(false, queue.addLetterToQueue(let2, new Coord(6, y), b));
            assertEquals(false, queue.addLetterToQueue(let2, new Coord(8, y), b));
        }
    }

    @Test
    public void addingALetterToQueueFailsIfNotOnTheSameRowOrColumnOrNeighbouringRowOrColumnAsTheWordOnTheBoard() {
        b.getSquare(7, 7).placeLetter(let1);
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if ((x >= 6 && x <= 8) || y >= 6 && y <= 8) {
                    continue;
                }
                assertEquals(false, q.addLetterToQueue(let2, new Coord(x, y), b));
            }
        }
    }

    @Test
    public void addingALetterToQueueSucceedsIfOnTheSameRowOrColumnOrNeighbouringRowOrColumnAsTheWordOnTheBoard() {
        b.getSquare(7, 7).placeLetter(let1);
        for (int y = 6; y <= 8; y++) {
            for (int x = 6; x <= 8; x++) {
                if (x == 7 && y == 7) {
                    continue;
                }
                LetterQueue queue = new LetterQueue();
                assertEquals(true, queue.addLetterToQueue(let2, new Coord(x, y), b));
            }
        }
    }

    @Test
    public void addingSecondLetterFailsIfNotOnTheSameRowOrColumnAsTheFirstLetterInTheQueue() {
        b.getSquare(7, 7).placeLetter(let1);
        assertEquals(true, q.addLetterToQueue(let1, new Coord(8, 8), b));
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if (x == 8 || y == 8 || (x == 7 && y == 7)) {
                    continue;
                }
                assertEquals(false, q.addLetterToQueue(let2, new Coord(x, y), b));
            }
        }
    }

    @Test
    public void addingSecondLetterSucceedsIfOnTheSameRowAsTheFirstLetterInTheQueue() {
        b.getSquare(7, 7).placeLetter(let1);
        for (int x = 0; x < 15; x++) {
            if (x == 8) {
                continue;
            }
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(let1, new Coord(8, 8), b));
            assertEquals(true, queue.addLetterToQueue(let2, new Coord(x, 8), b));
            assertEquals(2, queue.getContents().size());
        }
    }

    @Test
    public void addingSecondLetterSucceedsIfOnTheSameColumnAsTheFirstLetter() {
        b.getSquare(7, 7).placeLetter(let1);
        for (int y = 0; y < 15; y++) {
            if (y == 8) {
                continue;
            }
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(let1, new Coord(8, 8), b));
            assertEquals(true, queue.addLetterToQueue(let2, new Coord(8, y), b));
            assertEquals(2, queue.getContents().size());
        }
    }

    @Test
    public void addingMoreLettersThanTwoFailsIfHorizontalDirectionNotRespected() {
        b.getSquare(7, 7).placeLetter(let1);
        assertEquals(true, q.addLetterToQueue(let1, new Coord(0, 8), b));
        assertEquals(true, q.addLetterToQueue(let2, new Coord(1, 8), b));
        for (int y = 0; y < 15; y++) {
            if (y == 8) {
                continue;
            }
            assertEquals(false, q.addLetterToQueue(let2, new Coord(8, y), b));
            assertEquals(2, q.getContents().size());
        }
    }

    @Test
    public void addingMoreLettersThanTwoFailsIfVerticalDirectionNotRespected() {
        b.getSquare(7, 7).placeLetter(let1);
        assertEquals(true, q.addLetterToQueue(let1, new Coord(8, 0), b));
        assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(8, 1), b));
        for (int x = 0; x < 15; x++) {
            if (x == 8) {
                continue;
            }
            assertEquals(false, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 8), b));
            assertEquals(2, q.getContents().size());
        }
    }

    @Test
    public void addingMoreLettersThanTwoSucceedsIfHorizontalDirectionRespected() {
        b.getSquare(7, 7).placeLetter(let1);
        assertEquals(true, q.addLetterToQueue(let1, new Coord(0, 8), b));
        assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(1, 8), b));
        for (int x = 2; x < 15; x++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 8), b));
        }
        assertEquals(15, q.getContents().size());
    }

    @Test
    public void addingMoreLettersThanTwoSucceedsIfVerticalDirectionRespected() {
        b.getSquare(7, 7).placeLetter(let1);
        assertEquals(true, q.addLetterToQueue(let1, new Coord(8, 0), b));
        assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(8, 1), b));
        for (int y = 2; y < 15; y++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(8, y), b));
        }
        assertEquals(15, q.getContents().size());
    }

    @Test
    public void isValidCoordinateReturnsFalseWithOutOfBoundsValues() {
        assertEquals(false, q.isValidCoordinate(-1, 0));
        assertEquals(false, q.isValidCoordinate(0, -1));
        assertEquals(false, q.isValidCoordinate(15, 0));
        assertEquals(false, q.isValidCoordinate(0, 15));
    }

    @Test
    public void getLetterByCoordinateWorks() {
        Letter l = new Letter(LetterType.LETTER_E);
        q.addLetterToQueue(l, new Coord(7, 4), b);
        assertEquals(l, q.getLetterByCoordinate(7, 4));
    }

    @Test
    public void takeLetterFromQueueReturnsNullIfLetterNotFound() {
        assertEquals(null, q.takeLetterFromQueue(let1));
    }
}
