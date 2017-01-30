/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

/**
 *
 * @author panu
 */
public enum LetterType {
    LETTER_A(1),
    LETTER_B(8),
    LETTER_C(10),
    LETTER_D(7),
    LETTER_E(1),
    LETTER_F(8),
    LETTER_G(8),
    LETTER_H(4),
    LETTER_I(1),
    LETTER_J(4),
    LETTER_K(2),
    LETTER_L(2),
    LETTER_M(3),
    LETTER_N(1),
    LETTER_O(2),
    LETTER_P(4), // huom. Q-kirjainta ei suomenkielisessä Scrabblessä ole ollenkaan
    LETTER_R(4),
    LETTER_S(1),
    LETTER_T(1),
    LETTER_U(3),
    LETTER_V(4),
    LETTER_W(8),
    LETTER_Y(4),
    LETTER_AE(2), // ä
    LETTER_OE(7), // ö
    LETTER_WILD(0);
    private final int points;
    
    private LetterType(int points) {
        this.points = points;
    }
    
    public int getPoints() {
        return this.points;
    }
}
