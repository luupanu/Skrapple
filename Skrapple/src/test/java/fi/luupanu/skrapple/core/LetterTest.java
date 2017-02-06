/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.LetterType;
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
public class LetterTest {

    private List<Letter> listOfLetters;

    @Before
    public void setUp() {
        listOfLetters = new ArrayList<>();
        for (LetterType t : LetterType.values()) {
            listOfLetters.add(new Letter(t));
        }
    }

    @Test
    public void lettersHaveCorrectPointValues() {
        for (Letter l : listOfLetters) {
            int i = 1;
            LetterType t = l.getType();
            if (t == LetterType.LETTER_K
                    || t == LetterType.LETTER_L
                    || t == LetterType.LETTER_O
                    || t == LetterType.LETTER_AE) {
                i = 2;
            } else if (t == LetterType.LETTER_M
                    || t == LetterType.LETTER_U) {
                i = 3;
            } else if (t == LetterType.LETTER_H
                    || t == LetterType.LETTER_J
                    || t == LetterType.LETTER_P
                    || t == LetterType.LETTER_R
                    || t == LetterType.LETTER_V
                    || t == LetterType.LETTER_Y) {
                i = 4;
            } else if (t == LetterType.LETTER_D
                    || t == LetterType.LETTER_OE) {
                i = 7;
            } else if (t == LetterType.LETTER_B
                    || t == LetterType.LETTER_F
                    || t == LetterType.LETTER_G
                    || t == LetterType.LETTER_W) {
                i = 8;
            } else if (t == LetterType.LETTER_C) {
                i = 10;
            } else if (t == LetterType.LETTER_WILD) {
                i = 0;
            }
            assertEquals(i, l.getPoints());
        }
    }

}
