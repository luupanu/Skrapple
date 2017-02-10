/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.constants;

/**
 * SquareType contains the different types of square tiles included in the game.
 * 
 * @author panu
 */
public enum SquareType {
    SQUARE_NORMAL("."),
    SQUARE_BONUS_LETTER_2X("2"),
    SQUARE_BONUS_LETTER_3X("3"),
    SQUARE_BONUS_WORD_2X("4"),
    SQUARE_BONUS_WORD_3X("5");

    private final String value;

    private SquareType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
