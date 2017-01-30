/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

import java.util.ArrayList;

/**
 *
 * @author panu
 */
public class LetterBag {
    
    ArrayList<Letter> bag;
    
    public LetterBag() {
        this.bag = new ArrayList<>();
        placeAllLettersInBag();
    }
    
    public ArrayList<Letter> getContents() {
        return bag;
    }
    
    public void removeLetter(int k) {
        if (k >= 0 && k < bag.size()) {
            bag.remove(k);
        }
    }
    
    public void placeLetterInBag(Letter l) {
        bag.add(l);
    }
    
    private void placeMultipleLettersInBag(Letter l, int n) {
        for (int i = 0; i < n; i++) {
            placeLetterInBag(l);
        }
    }
    
     private void placeAllLettersInBag() {
        for (LetterType lt : LetterType.values()) {
            int n = 1; // B, C, D, F, G, W, Ö
            Letter letter = new Letter(lt);
            LetterType t = letter.getType();
            
            if (t == LetterType.LETTER_A ||
                    t == LetterType.LETTER_I) {
                n = 10;
            } else if (t == LetterType.LETTER_N ||
                    t == LetterType.LETTER_T) {
                n = 9;
            } else if (t == LetterType.LETTER_E) {
                n = 8;
            } else if (t == LetterType.LETTER_S) {
                n = 7;
            } else if (t == LetterType.LETTER_K ||
                    t == LetterType.LETTER_L ||
                    t == LetterType.LETTER_O ||
                    t == LetterType.LETTER_AE) {
                n = 5;
            } else if (t == LetterType.LETTER_U) {
                n = 4;
            } else if (t == LetterType.LETTER_M) {
                n = 3;
            } else if (t == LetterType.LETTER_H ||
                    t == LetterType.LETTER_J ||
                    t == LetterType.LETTER_P ||
                    t == LetterType.LETTER_R ||
                    t == LetterType.LETTER_V ||
                    t == LetterType.LETTER_Y ||
                    t == LetterType.LETTER_WILD) {
                n = 2;
            }
            
            placeMultipleLettersInBag(letter, n);
        }
    }
}
