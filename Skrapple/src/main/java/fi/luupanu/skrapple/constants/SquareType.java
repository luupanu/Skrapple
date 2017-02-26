/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.constants;

/**
 * SquareType contains the different types of square tiles included in the game.
 * The values are for text-based representation of the game and/or testing
 * purposes.
 *
 * @author panu
 */
public enum SquareType {
    NORMAL("."),
    BONUS_LETTER_2X("2"),
    BONUS_LETTER_3X("3"),
    BONUS_WORD_2X("4"),
    BONUS_WORD_3X("5");

    private final String value;

    private SquareType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
