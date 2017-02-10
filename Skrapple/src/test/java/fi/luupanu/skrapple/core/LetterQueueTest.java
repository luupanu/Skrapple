/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

import fi.luupanu.skrapple.logic.LetterQueue;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.constants.LetterType;
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
    private Letter let;

    @Before
    public void setUp() {
        q = new LetterQueue();
        b = new Board();
        let = new Letter(LetterType.LETTER_E);
    }

    @Test
    public void letterQueueIsEmptyWhenCreated() {
        assertEquals(0, q.getLetterQueue().size());
    }

    @Test
    public void addingALetterToQueueWithBadCoordinatesFails() {
        for (int y = -1; y < 16; y += 16) {
            for (int x = -1; x < 16; x += 16) {
                assertEquals(false, q.addLetterToQueue(let, new Coord(x, y), b));
                assertEquals(0, q.getLetterQueue().size());
            }
        }
    }

    @Test
    public void addingALetterToQueueWhenSquareIsOccupiedFails() {
        b.getSquare(0, 0).placeLetter(let);
        assertEquals(false, q.addLetterToQueue(new Letter(LetterType.LETTER_F), new Coord(0, 0), b));
        assertEquals(0, q.getLetterQueue().size());
    }

    @Test
    public void removingALetterWorks() {
        assertEquals(true, q.addLetterToQueue(let, new Coord(7, 7), b));
        assertEquals(true, q.removeLetterFromQueue(let));
        assertEquals(0, q.getLetterQueue().size());
    }

    @Test
    public void addingTheSameLetterTwiceToSameCoordinatesSucceedsIfRemovedFirst() {
        assertEquals(true, q.addLetterToQueue(let, new Coord(7, 7), b));
        assertEquals(true, q.removeLetterFromQueue(let));
        assertEquals(true, q.addLetterToQueue(let, new Coord(7, 7), b));
        assertEquals(1, q.getLetterQueue().size());
    }

    @Test
    public void changingDirectionSucceeds() {
        assertEquals(true, q.addLetterToQueue(let, new Coord(7, 7), b));
        assertEquals(true, q.addLetterToQueue(let, new Coord(7, 8), b));
        assertEquals(false, q.getDirection());
        assertEquals(true, q.removeLetterFromQueue(let));
        assertEquals(true, q.addLetterToQueue(let, new Coord(7, 7), b));
        assertEquals(true, q.addLetterToQueue(let, new Coord(8, 7), b));
        assertEquals(true, q.getDirection());
    }

    @Test
    public void addingALetterToQueueWhenBoardIsEmptyFailsIfTheWordIsNotConstructedTowardsTheCenterSquare() {
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if (x == 7 || y == 7) {
                    continue;
                }
                assertEquals(false, q.addLetterToQueue(let, new Coord(x, y), b));
                assertEquals(0, q.getLetterQueue().size());
            }
        }
    }

    @Test
    public void addingALetterToQueueWhenBoardIsEmptySucceedsIfTheWordIsConstructedTowardsTheCenterSquareVertically() {
        for (int y = 0; y < 15; y++) {
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(7, y), b));
            assertEquals(1, queue.getLetterQueue().size());
        }
    }

    @Test
    public void addingALetterToQueueWhenBoardIsEmptySucceedsIfTheWordIsConstructedTowardsTheCenterSquareHorizontally() {
        for (int x = 0; x < 15; x++) {
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 7), b));
            assertEquals(1, queue.getLetterQueue().size());
        }
    }

    @Test
    public void addingALetterToQueueFailsIfNotOnTheSameRowOrColumnOrNeighbouringRowOrColumnAsTheWordOnTheBoard() {
        b.getSquare(7, 7).placeLetter(let);
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if ((x >= 6 && x <= 8) || y >= 6 && y <= 8) {
                    continue;
                }
                assertEquals(false, q.addLetterToQueue(let, new Coord(x, y), b));
            }
        }
    }

    @Test
    public void addingALetterToQueueSucceedsIfOnTheSameRowOrColumnOrNeighbouringRowOrColumnAsTheWordOnTheBoard() {
        b.getSquare(7, 7).placeLetter(let);
        for (int y = 6; y <= 8; y++) {
            for (int x = 6; x <= 8; x++) {
                if (x == 7 && y == 7) {
                    continue;
                }
                LetterQueue queue = new LetterQueue();
                assertEquals(true, queue.addLetterToQueue(let, new Coord(x, y), b));
            }
        }
    }

    @Test
    public void addingSecondLetterFailsIfNotOnTheSameRowOrColumnAsTheFirstLetterInTheQueue() {
        b.getSquare(7, 7).placeLetter(let);
        assertEquals(true, q.addLetterToQueue(let, new Coord(8, 8), b));
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if (x == 8 || y == 8 || (x == 7 && y == 7)) {
                    continue;
                }
                assertEquals(false, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, y), b));
            }
        }
    }

    @Test
    public void addingSecondLetterSucceedsIfOnTheSameRowAsTheFirstLetterInTheQueue() {
        b.getSquare(7, 7).placeLetter(let);
        for (int x = 0; x < 15; x++) {
            if (x == 8) {
                continue;
            }
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(let, new Coord(8, 8), b));
            assertEquals(true, queue.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 8), b));
            assertEquals(2, queue.getLetterQueue().size());
        }
    }

    @Test
    public void addingSecondLetterSucceedsIfOnTheSameColumnAsTheFirstLetter() {
        b.getSquare(7, 7).placeLetter(let);
        for (int y = 0; y < 15; y++) {
            if (y == 8) {
                continue;
            }
            LetterQueue queue = new LetterQueue();
            assertEquals(true, queue.addLetterToQueue(let, new Coord(8, 8), b));
            assertEquals(true, queue.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(8, y), b));
            assertEquals(2, queue.getLetterQueue().size());
        }
    }

    @Test
    public void addingMoreLettersThanTwoFailsIfHorizontalDirectionNotRespected() {
        b.getSquare(7, 7).placeLetter(let);
        assertEquals(true, q.addLetterToQueue(let, new Coord(0, 8), b));
        assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(1, 8), b));
        for (int y = 0; y < 15; y++) {
            if (y == 8) {
                continue;
            }
            assertEquals(false, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(8, y), b));
            assertEquals(2, q.getLetterQueue().size());
        }
    }

    @Test
    public void addingMoreLettersThanTwoFailsIfVerticalDirectionNotRespected() {
        b.getSquare(7, 7).placeLetter(let);
        assertEquals(true, q.addLetterToQueue(let, new Coord(8, 0), b));
        assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(8, 1), b));
        for (int x = 0; x < 15; x++) {
            if (x == 8) {
                continue;
            }
            assertEquals(false, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 8), b));
            assertEquals(2, q.getLetterQueue().size());
        }
    }

    @Test
    public void addingMoreLettersThanTwoSucceedsIfHorizontalDirectionRespected() {
        b.getSquare(7, 7).placeLetter(let);
        assertEquals(true, q.addLetterToQueue(let, new Coord(0, 8), b));
        assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(1, 8), b));
        for (int x = 2; x < 15; x++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 8), b));
        }
        assertEquals(15, q.getLetterQueue().size());
    }

    @Test
    public void addingMoreLettersThanTwoSucceedsIfVerticalDirectionRespected() {
        b.getSquare(7, 7).placeLetter(let);
        assertEquals(true, q.addLetterToQueue(let, new Coord(8, 0), b));
        assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(8, 1), b));
        for (int y = 2; y < 15; y++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(8, y), b));
        }
        assertEquals(15, q.getLetterQueue().size());
    }
}
