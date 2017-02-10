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

    // don't need two, remove this function or the next?
    public boolean removeLetterByIndex(int i) {
        if (i >= 0 && i < bag.size()) {
            bag.remove(i);
            return true;
        }
        return false;
    }

    // don't need two, remove this function or the previous?
    public boolean removeLetterByType(LetterType t) {
        for (Letter let : bag) {
            if (let.getType() == t) {
                return bag.remove(let);
            }
        }
        return false;
    }

    // optional method for banning certain letters
    public int removeAllLettersByType(LetterType t) {
        // returns how many were removed
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
