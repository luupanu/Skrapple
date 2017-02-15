/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.SquareType;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Square;
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
public class SquareTest {

    private Square normal;
    private Letter let;

    @Before
    public void setUp() {
        normal = new Square(SquareType.NORMAL);
        let = new Letter(LetterType.LETTER_E);
    }

    @Test
    public void hasLetterReturnsFalseWhenSquareIsCreated() {
        assertEquals(false, normal.hasLetter());
    }

    @Test
    public void hasLetterReturnsTrueWhenSquareHasALetter() {
        normal.placeLetter(let);
        assertEquals(true, normal.hasLetter());
    }

    @Test
    public void getLetterReturnsNullWhenSquareIsEmpty() {
        assertEquals(null, normal.getLetter());
    }

    @Test
    public void getLetterReturnsLetterWhenSquareHasALetter() {
        normal.placeLetter(let);
        assertEquals(let, normal.getLetter());
    }
    
    @Test
    public void placeLetterWhenSquareHasALetterFails() {
        normal.placeLetter(let);
        normal.placeLetter(new Letter(LetterType.LETTER_H));
        assertEquals(let, normal.getLetter());
    }
}
