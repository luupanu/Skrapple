/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Word;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author panu
 */
public class WordCreatorTest {

    private Board b;
    private LetterQueue q;
    private LetterQueueValidator v;
    private WordCreator wc;
    private List<Letter> word1;

    @Before
    public void setUp() {
        b = new Board();
        q = new LetterQueue();
        v = new LetterQueueValidator(q);
        wc = new WordCreator(q);
        word1 = new ArrayList<>();
        makeWordOne();
    }

    @Test
    public void constructingFirstWordOfTheGameWorksHorizontally() {
        for (int x = 0; x < 8; x++) {
            q.addLetterToQueue(word1.get(x), new Coord(x, 7), b);
        }
        v.letterQueueIsValid(b);
        List<Word> words = wc.constructWords(b);
        assertEquals("SAMMAKKO", words.get(0).toString());
    }

    @Test
    public void constructingFirstWordOfTheGameWorksVertically() {
        for (int y = 0; y < 8; y++) {
            q.addLetterToQueue(word1.get(y), new Coord(7, y), b);
        }
        v.letterQueueIsValid(b);
        List<Word> words = wc.constructWords(b);
        assertEquals("SAMMAKKO", words.get(0).toString());
    }

    @Test
    public void constructingHorizontalWordWorks() {
        b.getSquare(7, 7).placeLetter(new Letter(LetterType.LETTER_O));
        for (int x = 0; x < 7; x++) {
            assertEquals(true, q.addLetterToQueue(word1.get(x), new Coord(x, 7), b));
        }
        v.letterQueueIsValid(b);
        List<Word> words = wc.constructWords(b);
        assertEquals("SAMMAKKO", words.get(0).toString());
    }

    @Test
    public void constructingVerticalWordWorks() {
        b.getSquare(7, 7).placeLetter(new Letter(LetterType.LETTER_O));
        for (int y = 0; y < 7; y++) {
            assertEquals(true, q.addLetterToQueue(word1.get(y), new Coord(7, y), b));
        }
        v.letterQueueIsValid(b);
        List<Word> words = wc.constructWords(b);
        assertEquals("SAMMAKKO", words.get(0).toString());
    }

    private void makeWordOne() {
        word1.add(new Letter(LetterType.LETTER_S));
        word1.add(new Letter(LetterType.LETTER_A));
        word1.add(new Letter(LetterType.LETTER_M));
        word1.add(new Letter(LetterType.LETTER_M));
        word1.add(new Letter(LetterType.LETTER_A));
        word1.add(new Letter(LetterType.LETTER_K));
        word1.add(new Letter(LetterType.LETTER_K));
        word1.add(new Letter(LetterType.LETTER_O));
    }
}
