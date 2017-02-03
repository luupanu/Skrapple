/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

import java.util.ArrayList;
import java.util.Iterator;
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
public class LetterBagTest {

    private LetterBag bag;
    private LetterBag emptyBag;
    private Letter let;

    @Before
    public void setUp() {
        bag = new LetterBag();
        emptyBag = new LetterBag();
        let = new Letter(LetterType.LETTER_E);
        emptyBag.getContents().clear();
    }

    @Test
    public void whenCreatedLetterBagHasCorrectSize() {
        assertEquals(101, bag.getSize());
    }

    @Test
    public void whenCreatedLetterBagHasCorrectContents() {
        for (LetterType t : LetterType.values()) {
            int n = 0;

            if (t == LetterType.LETTER_A
                    || t == LetterType.LETTER_I) {
                n = 10;
            } else if (t == LetterType.LETTER_N
                    || t == LetterType.LETTER_T) {
                n = 9;
            } else if (t == LetterType.LETTER_E) {
                n = 8;
            } else if (t == LetterType.LETTER_S) {
                n = 7;
            } else if (t == LetterType.LETTER_K
                    || t == LetterType.LETTER_L
                    || t == LetterType.LETTER_O
                    || t == LetterType.LETTER_AE) {
                n = 5;
            } else if (t == LetterType.LETTER_U) {
                n = 4;
            } else if (t == LetterType.LETTER_M) {
                n = 3;
            } else if (t == LetterType.LETTER_H
                    || t == LetterType.LETTER_J
                    || t == LetterType.LETTER_P
                    || t == LetterType.LETTER_R
                    || t == LetterType.LETTER_V
                    || t == LetterType.LETTER_Y
                    || t == LetterType.LETTER_WILD) {
                n = 2;
            } else if (t == LetterType.LETTER_B
                    || t == LetterType.LETTER_C
                    || t == LetterType.LETTER_D
                    || t == LetterType.LETTER_F
                    || t == LetterType.LETTER_G
                    || t == LetterType.LETTER_W
                    || t == LetterType.LETTER_OE) {
                n = 1;
            }

            assertEquals(n, bag.removeAllLettersByType(t));
        }
        assertEquals(0, bag.getSize());
    }
    
    @Test
    public void removeLetterByIndexDoesNotAllowRemovingOutOfBounds() {
        assertEquals(false, bag.removeLetterByIndex(-1));
        assertEquals(false, bag.removeLetterByIndex(101));
        assertEquals(101, bag.getSize());
    }
    
    @Test
    public void removeLetterByIndexWorks() {
        assertEquals(false, emptyBag.removeLetterByIndex(0));
        emptyBag.placeLetterInBag(let);
        assertEquals(true, emptyBag.removeLetterByIndex(0));
        assertEquals(0, emptyBag.getSize());
    }
    
    @Test
    public void removeLetterByTypeWorks() {
        assertEquals(true, bag.removeLetterByType(LetterType.LETTER_B));
        assertEquals(true, bagHasNoLettersOfType(bag, LetterType.LETTER_B));
        assertEquals(100, bag.getSize());
        
        assertEquals(true, bag.removeLetterByType(LetterType.LETTER_A));
        assertEquals(9, numberOfLettersByTypeInBag(bag, LetterType.LETTER_A));
        assertEquals(99, bag.getSize());
    }
    
    @Test
    public void removeAllLettersByTypeWorks() {
        bag.removeAllLettersByType(LetterType.LETTER_A);
        assertEquals(true, bagHasNoLettersOfType(bag, LetterType.LETTER_A));
        assertEquals(91, bag.getSize());
    }
    
    @Test
    public void placingLetterWorks() {
        int i = numberOfLettersByTypeInBag(bag, LetterType.LETTER_E);
        bag.placeLetterInBag(let);
        assertEquals(i+1, numberOfLettersByTypeInBag(bag, LetterType.LETTER_E));
        assertEquals(102, bag.getSize());
    }
    
    /* Helper methods */
    
    private boolean bagHasNoLettersOfType(LetterBag b, LetterType t) {
        for (Letter let : bag.getContents()) {
            if (let.getType() == t) {
                return false;
            }
        }
        return true;
    }
    
    private int numberOfLettersByTypeInBag(LetterBag b, LetterType t) {
        int i = 0;
        for (Letter let : bag.getContents()) {
            if (let.getType() == t) {
                i++;
            } 
        }
        return i;
    }
}
