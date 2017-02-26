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

    /**
     * Creates a new empty square of the given type.
     *
     * @param type the square type (normal or a bonus square)
     */
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
     * Sets the occupied status to be true.
     *
     * @param letter the letter to be placed
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

    /**
     * For text-based user interface of the game. The values for unoccupied
     * squares are listed in SquareType.
     *
     * @return SquareType value if square is unoccupied, otherwise the letter's
     * string representation
     */
    @Override
    public String toString() {
        if (!occupied) {
            return type.getValue();
        }
        return getLetter().toString();
    }
}
