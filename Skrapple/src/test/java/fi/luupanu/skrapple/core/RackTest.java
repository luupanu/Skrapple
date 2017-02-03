/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

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
public class RackTest {

    private Rack rack;
    private LetterBag bag;

    @Before
    public void setUp() {
        rack = new Rack();
        bag = new LetterBag();
        bag.getContents().clear();
    }

    @Test
    public void rackIsCreatedEmpty() {
        assertEquals(0, rack.getContents().size());
    }

    @Test
    public void getRackPointsReturnsCorrectValue() {
        addLettersToBag();
        rack.refillRack(bag);
        assertEquals(43, rack.getRackPoints());
    }

    @Test
    public void refillingRackTakesMaxSevenLetters() {
        addLettersToBag();
        bag.placeLetterInBag(new Letter(LetterType.LETTER_H));
        rack.refillRack(bag);
        assertEquals(7, rack.getContents().size());
        assertEquals(1, bag.getContents().size());
    }

    @Test
    public void refillingRackWhenBagIsEmptyDoesNothing() {
        rack.refillRack(bag);
        assertEquals(0, rack.getContents().size());
    }

    @Test
    public void refillingRackWhenBagHasLessThanSevenLettersWorks() {
        bag.placeLetterInBag(new Letter(LetterType.LETTER_E));
        bag.placeLetterInBag(new Letter(LetterType.LETTER_F));
        rack.refillRack(bag);
        assertEquals(2, rack.getContents().size());
    }

    @Test
    public void takeLetterReturnsNullWhenTakingFromEmptyRack() {
        assertEquals(null, rack.takeLetter(0));
    }

    @Test
    public void takeLetterReturnsNullWhenTakingOutOfBounds() {
        assertEquals(null, rack.takeLetter(-1));
        assertEquals(null, rack.takeLetter(8));
    }

    private void addLettersToBag() {
        List<Letter> letters = new ArrayList<>();
        letters.add(new Letter(LetterType.LETTER_A));
        letters.add(new Letter(LetterType.LETTER_B));
        letters.add(new Letter(LetterType.LETTER_C));
        letters.add(new Letter(LetterType.LETTER_D));
        letters.add(new Letter(LetterType.LETTER_E));
        letters.add(new Letter(LetterType.LETTER_F));
        letters.add(new Letter(LetterType.LETTER_G));

        for (Letter l : letters) {
            bag.placeLetterInBag(l);
        }
    }
}
