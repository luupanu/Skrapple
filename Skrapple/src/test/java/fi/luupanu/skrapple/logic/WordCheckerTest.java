/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Word;
import java.io.IOException;
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
public class WordCheckerTest {

    private Board b;
    private WordChecker wc;
    private Dictionary d;
    private final Word w1 = new Word();
    private final Word w2 = new Word();
    private final Word w3 = new Word();
    private final Word w4 = new Word();

    @Before
    public void setUp() throws IOException {
        b = new Board();
        d = new Dictionary("kotus-wordlist-fi");
        wc = new WordChecker(d);
    }

    @Test
    public void canFindWordsFromWordList() {
        makeRealWords();
        List<Word> words = new ArrayList<>();
        words.add(w1);
        words.add(w2);
        words.add(w3);
        assertEquals(0, wc.allWordsExistInDictionary(words).size());
    }

    @Test
    public void cantFindMadeUpWordsFromWordList() {
        makeRealWords();
        makeFakeWord();
        List<Word> words = new ArrayList<>();
        words.add(w1);
        words.add(w2);
        words.add(w3);
        words.add(w4);
        assertEquals(w4, wc.allWordsExistInDictionary(words).stream().findAny().orElse(null));
        assertEquals(1, wc.allWordsExistInDictionary(words).size());
    }
    
    private void makeRealWords() {
        w1.addLetter(new Letter(LetterType.LETTER_OE), b, false);
        w1.addLetter(new Letter(LetterType.LETTER_Y), b, false);
        w1.addLetter(new Letter(LetterType.LETTER_L), b, false);
        w1.addLetter(new Letter(LetterType.LETTER_AE), b, false);
        w1.addLetter(new Letter(LetterType.LETTER_T), b, false);
        w1.addLetter(new Letter(LetterType.LETTER_T), b, false);
        w1.addLetter(new Letter(LetterType.LETTER_I), b, false);

        w2.addLetter(new Letter(LetterType.LETTER_A), b, false);
        w2.addLetter(new Letter(LetterType.LETTER_A), b, false);
        w2.addLetter(new Letter(LetterType.LETTER_K), b, false);
        w2.addLetter(new Letter(LetterType.LETTER_K), b, false);
        w2.addLetter(new Letter(LetterType.LETTER_O), b, false);
        w2.addLetter(new Letter(LetterType.LETTER_N), b, false);
        w2.addLetter(new Letter(LetterType.LETTER_E), b, false);
        w2.addLetter(new Letter(LetterType.LETTER_N), b, false);

        w3.addLetter(new Letter(LetterType.LETTER_P), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_U), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_U), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_T), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_A), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_R), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_H), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_A), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_N), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_E), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_U), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_V), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_O), b, false);
        w3.addLetter(new Letter(LetterType.LETTER_S), b, false);
    }

    private void makeFakeWord() {
        w4.addLetter(new Letter(LetterType.LETTER_D), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_O), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_N), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_A), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_L), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_D), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_T), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_R), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_U), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_M), b, false);
        w4.addLetter(new Letter(LetterType.LETTER_P), b, false);
    }
}
