/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.logic.LetterQueue;
import fi.luupanu.skrapple.logic.LetterQueueValidator;
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
public class LetterQueueValidatorTest {

    private LetterQueue q;
    private LetterQueueValidator v;
    private Board b;

    @Before
    public void setUp() {
        this.q = new LetterQueue();
        this.v = new LetterQueueValidator(q);
        this.b = new Board();
    }

    public void firstWordOfTheGameMustTouchCenterSquare() {
        for (int x = 0; x < 7; x++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 7), b));
        }
        assertEquals(false, v.letterQueueIsValid(b));
        q.getLetterQueue().clear();

        for (int x = 8; x < 15; x++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 7), b));
        }
        assertEquals(false, v.letterQueueIsValid(b));
        q.getLetterQueue().clear();

        for (int y = 0; y < 7; y++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(7, y), b));
        }
        assertEquals(false, v.letterQueueIsValid(b));
        q.getLetterQueue().clear();

        for (int y = 8; y < 15; y++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(7, y), b));
        }
        assertEquals(false, v.letterQueueIsValid(b));
        q.getLetterQueue().clear();
    }

    public void validHorizontalLetterQueueIsValid() {
        for (int x = 0; x < 15; x++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 7), b));
        }
        assertEquals(true, v.letterQueueIsValid(b));
    }

    public void invalidHorizontalLetterQueueIsInvalid() {
        for (int x = 0; x < 15; x++) {
            if (x == 7) {
                continue;
            }
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 7), b));
        }
        assertEquals(false, v.letterQueueIsValid(b));
    }

    public void horizontalLetterQueueWithGapIsValidWhenGapIsOccupied() {
        b.getSquare(7, 7).placeLetter(new Letter(LetterType.LETTER_E));
        for (int x = 0; x < 15; x++) {
            if (x == 7) {
                continue;
            }
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(x, 7), b));
        }
        assertEquals(true, v.letterQueueIsValid(b));
    }

    public void validVerticalLetterQueueIsValid() {
        for (int y = 0; y < 15; y++) {
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(7, y), b));
        }
        assertEquals(true, v.letterQueueIsValid(b));
    }

    public void invalidVerticalLetterQueueIsInvalid() {
        for (int y = 0; y < 15; y++) {
            if (y == 7) {
                continue;
            }
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(7, y), b));
        }
        assertEquals(false, v.letterQueueIsValid(b));
    }

    public void verticalLetterQueueWithGapIsValidWhenGapIsOccupied() {
        b.getSquare(7, 7).placeLetter(new Letter(LetterType.LETTER_E));
        for (int y = 0; y < 15; y++) {
            if (y == 7) {
                continue;
            }
            assertEquals(true, q.addLetterToQueue(new Letter(LetterType.LETTER_E), new Coord(7, y), b));
        }
        assertEquals(true, v.letterQueueIsValid(b));
    }
}
