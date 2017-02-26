/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.LetterType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author panu
 */
public class WildLetterTest {
    
    private WildLetter wildLetter;
    
    @Before
    public void setUp() {
        wildLetter = new WildLetter(LetterType.LETTER_WILD);
    }
    
    @Test
    public void wildLetterTypeStartsAsNull() {
        assertEquals(null, wildLetter.getWildLetterType());
    }
    
    @Test
    public void canSetWildLetterType() {
        wildLetter.setWildLetterType(LetterType.LETTER_E);
        assertEquals(LetterType.LETTER_E, wildLetter.getWildLetterType());
    }
    
    @Test
    public void settingWildLetterTypeDoesNotChangeLetterType() {
        wildLetter.setWildLetterType(LetterType.LETTER_E);
        assertEquals(LetterType.LETTER_WILD, wildLetter.getType());
    }
    
    @Test
    public void canSetWildLetterTypeMultipleTimes() {
        wildLetter.setWildLetterType(LetterType.LETTER_E);
        wildLetter.setWildLetterType(LetterType.LETTER_F);
        assertEquals(LetterType.LETTER_F, wildLetter.getWildLetterType());
    }
    
    @Test
    public void wildLetterReturnsCorrectToStringRepresentation() {
        assertEquals("*", wildLetter.toString());
        wildLetter.setWildLetterType(LetterType.LETTER_E);
        assertEquals("E", wildLetter.toString());
    }
}
