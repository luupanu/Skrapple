/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.LetterType;
import java.util.ArrayList;
import java.util.List;
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
public class WordTest {

    private Word word;
    private Board b;
    private Letter let;
    private List<Letter> list1;

    @Before
    public void setUp() {
        word = new Word();
        b = new Board();
        let = new Letter(LetterType.LETTER_E);
        addLettersToList1();
    }

    @Test
    public void addingBoardLetterWorks() {
        assertEquals(true, word.addLetter(let, b, false));
        assertEquals(1, word.getWord().size());
    }

    @Test
    public void addingQueueLetterFailsIfNoCoordinates() {
        assertEquals(false, word.addLetter(let, b, true));
        assertEquals(0, word.getWord().size());
    }

    @Test
    public void addingQueueLetterSucceeds() {
        let.setCoord(new Coord(7, 0));
        assertEquals(true, word.addLetter(let, b, true));
        assertEquals(1, word.getWord().size());
    }

    @Test
    public void noBonusPointsIfOnlyBoardLettersUsed() {
        for (Letter l : list1) {
            word.addLetter(l, b, false);
        }
        assertEquals(15, word.getPoints());
    }

    @Test
    public void correctPointsIfOnlyQueueLettersUsed() {
        for (int y = 0; y < 15; y++) {
            Letter l = list1.get(y);
            l.setCoord(new Coord(7, y));
            word.addLetter(l, b, true);
        }
        // two double letter squares -> letter points = 13 + 2 + 2 = 17
        // two triple word, one double word square = 17 * 3 * 3 * 2 = 306
        assertEquals(306, word.getPoints());
    }

    @Test
    public void correctPointsIfBothQueueAndBoardLettersUsed() {
        for (int y = 0; y < 7; y++) {
            Letter l = list1.get(y);
            l.setCoord(new Coord(7, y));
            word.addLetter(l, b, false);
        }
        for (int y = 7; y < 15; y++) {
            Letter l = list1.get(y);
            l.setCoord(new Coord(7, y));
            word.addLetter(l, b, true);
        }
        // one double letter square -> letter points = 14 + 2 = 16
        // one double word, one triple word square = 16 * 2 * 3 = 96
        assertEquals(96, word.getPoints());
    }

    @Test
    public void test2xLetterBonus() {
        for (int x = 0; x < 6; x++) {
            Letter l = list1.get(x);
            l.setCoord(new Coord(x, 5));
            word.addLetter(l, b, false);
        }
        for (int x = 6; x < 15; x++) {
            Letter l = list1.get(x);
            l.setCoord(new Coord(x, 5));
            word.addLetter(l, b, true);
        }
        // two triple letter bonus squares -> 19 points
        assertEquals(19, word.getPoints());
    }

    private void addLettersToList1() {
        list1 = new ArrayList<>();
        for (int y = 0; y < 15; y++) {
            Letter l = new Letter(LetterType.LETTER_A);
            list1.add(l);
        }
    }
}
