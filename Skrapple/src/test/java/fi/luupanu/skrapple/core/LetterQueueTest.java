/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

import java.awt.Point;
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
    private Point p;

    @Before
    public void setUp() {
        q = new LetterQueue();
        b = new Board();
        let = new Letter(LetterType.LETTER_E);
        p = new Point();
    }

    @Test
    public void letterQueueIsEmptyWhenCreated() {
        assertEquals(0, q.getLetterQueue().size());
    }

    @Test
    public void addingALetterToQueueWithBadCoordinatesFails() {
        for (int y = -1; y < 16; y += 16) {
            for (int x = -1; x < 16; x += 16) {
                p.setLocation(x, y);
                assertEquals(false, q.addLetterToQueue(let, p, b));
                assertEquals(0, q.getLetterQueue().size());
            }
        }
    }

    @Test
    public void addingALetterToQueueWhenSquareIsOccupiedFails() {
        b.getSquare(0, 0).placeLetter(let);
        assertEquals(false, q.addLetterToQueue(new Letter(LetterType.LETTER_F), p, b));
        assertEquals(0, q.getLetterQueue().size());
    }

    @Test
    public void addingALetterToQueueWhenBoardIsEmptyFailsIfTheWordIsNotConstructedTowardsTheCenterSquare() {
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                if (x == 7 || y == 7) {
                    continue;
                }
                assertEquals(false, q.addLetterToQueue(let, new Point(x, y), b));
                assertEquals(0, q.getLetterQueue().size());
            }
        }
    }

    @Test
    public void addingALetterToQueueWhenBoardIsEmptySucceedsIfTheWordIsConstructedTowardsTheCenterSquareVertically() {
        for (int y = 0; y < 15; y++) {
            assertEquals(true, q.addLetterToQueue(let, new Point(7, y), b));
            assertEquals(1, q.getLetterQueue().size());
        }
    }

    @Test
    public void addingALetterToQueueWhenBoardIsEmptySucceedsIfTheWordIsConstructedTowardsTheCenterSquareHorizontally() {
        for (int x = 0; x < 15; x++) {
            p.setLocation(x, 7);
            assertEquals(true, q.addLetterToQueue(let, new Point(x, 7), b));
            System.out.println(q.getLetterQueue().size());
            assertEquals(1, q.getLetterQueue().size());
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
                p.setLocation(x, y);
                assertEquals(false, q.addLetterToQueue(let, p, b));
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
                p.setLocation(x, y);
                assertEquals(true, queue.addLetterToQueue(let, p, b));
            }
        }
    }

//    @Test
//    public void addingSecondLetterFailsIfNotOnTheSameRowOrColumnAsTheFirstLetter() {
//        b.getSquare(7, 7).placeLetter(let);
//        assertEquals(true, q.addLetterToQueue(let, new Point(8, 8), b));
//        for (int y = 0; y < 15; y++) {
//            for (int x = 0; x < 15; x++) {
//                if (x == 8 || y == 8 || (x == 7 && y == 7)) {
//                    continue;
//                }
//                p.setLocation(x, y);
//                assertEquals(false, q.addLetterToQueue(let, p, b));
//            }
//        }
//    }
//
//    @Test
//    public void addingSecondLetterSucceedsIfOnTheSameRowAsTheFirstLetter() {
//        q.addLetterToQueue(let, new Point(7, 7), b);
//        for (int x = 0; x < 15; x++) {
//            for (int y = 0; y < 15; y++) {
//                assertEquals(true, q.addLetterToQueue(let, p, b));
//            }
//        }
//    }
}
