/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.LetterType;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
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
    
    @Test
    public void pointsAsUnicodeSubscriptReturnsCorrectValues() {
        assertEquals("\u2080", new Letter(LetterType.LETTER_WILD).getPointsAsUnicodeSubScript()); // 0
        assertEquals("\u2081", new Letter(LetterType.LETTER_A).getPointsAsUnicodeSubScript()); // 1
        assertEquals("\u2082", new Letter(LetterType.LETTER_K).getPointsAsUnicodeSubScript()); // 2
        assertEquals("\u2083", new Letter(LetterType.LETTER_M).getPointsAsUnicodeSubScript()); // 3
        assertEquals("\u2084", new Letter(LetterType.LETTER_H).getPointsAsUnicodeSubScript()); // 4
        assertEquals("\u2087", new Letter(LetterType.LETTER_D).getPointsAsUnicodeSubScript()); // 7
        assertEquals("\u2088", new Letter(LetterType.LETTER_B).getPointsAsUnicodeSubScript()); // 8
        assertEquals("\u2081\u2080", new Letter(LetterType.LETTER_C).getPointsAsUnicodeSubScript()); // 10
    }
}
