/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.SquareType;

/**
 * Board is comprised of 225 Squares. A player can place exactly one letter on a
 * square. There are various type of squares with the possibility to score bonus
 * points.
 *
 * @author panu
 */
public class Square {

    private final SquareType type;
    private boolean occupied;
    private Letter letter;

    public Square(SquareType type) {
        this.type = type;
        occupied = false;
    }

    public SquareType getType() {
        return type;
    }

    public Letter getLetter() {
        return letter;
    }

    /**
     * Places a letter on the Square only if the square isn't already occupied.
     *
     * @param letter
     */
    public void placeLetter(Letter letter) {
        if (!occupied) {
            this.letter = letter;
            occupied = true;
        }
    }

    /**
     * Returns whether or not the square is occupied (has a letter).
     *
     * @return true if the square was occupied
     */
    public boolean hasLetter() {
        return occupied;
    }

    @Override
    public String toString() {
        if (!occupied) {
            return type.getValue();
        }
        return getLetter().toString();
    }
}
