/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.LetterType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * LetterBag stores 101 Letters as per the rules of the game. After placing a
 * word a player can then fill her rack again with random letters. Alternatively
 * it's also possible to exchange letters without making a move.
 *
 * @author panu
 */
public class LetterBag {

    private final List<Letter> bag;
    private final Random random;

    /**
     * Creates a new LetterBag with 101 letters inside as per the rules of the
     * game. Also creates a new Random used by takeRandomLetterFromBag().
     */
    public LetterBag() {
        this.bag = new ArrayList<>(101);
        placeAllLettersInBag();
        this.random = new Random();
    }

    public List<Letter> getContents() {
        return bag;
    }

    public int getSize() {
        return bag.size();
    }

    /**
     * Take one random letter from a bag.
     *
     * @return a random letter, null if the bag is empty.
     */
    public Letter takeRandomLetterFromBag() {
        if (bag.size() < 1) {
            return null;
        }
        int i = 0;
        if (bag.size() > 1) {
            i = random.nextInt(bag.size());
        }
        Letter taken = bag.get(i);
        removeLetterByType(taken.getType());
        return taken;
    }

    /**
     * Remove a letter from the bag with index i. Note: currently unused.
     *
     * @param i index of the letter
     * @return true if the index was within the bag's bounds.
     */
    public boolean removeLetterByIndex(int i) {
        if (i >= 0 && i < bag.size()) {
            bag.remove(i);
            return true;
        }
        return false;
    }

    /**
     * Remove a letter from the bag by letter type.
     *
     * @param t the letter type of the letter to be removed
     * @return true if the removal succeeded
     */
    public boolean removeLetterByType(LetterType t) {
        for (Letter let : bag) {
            if (let.getType() == t) {
                return bag.remove(let);
            }
        }
        return false;
    }

    /**
     * Optional method for banning certain letters from the game.
     *
     * @param t the letter type
     * @return how many letters were removed
     */
    public int removeAllLettersByType(LetterType t) {
        int i = 0;
        Iterator<Letter> iter = bag.iterator();
        while (iter.hasNext()) {
            Letter let = iter.next();
            if (let.getType() == t) {
                iter.remove();
                i++;
            }
        }
        return i;
    }

    /**
     * Place a letter in the bag.
     *
     * @param l the letter to be added
     */
    public void placeLetterInBag(Letter l) {
        bag.add(l);
    }

    private void placeAllLettersInBag() {
        for (LetterType t : LetterType.values()) {
            int n = 1; // B, C, D, F, G, W, Ö

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
            }

            createAndPlaceMultipleLettersOfTypeInBag(t, n);
        }
    }

    private void createAndPlaceMultipleLettersOfTypeInBag(LetterType t, int n) {
        for (int i = 0; i < n; i++) {
            placeLetterInBag(new Letter(t));
        }
    }
}
