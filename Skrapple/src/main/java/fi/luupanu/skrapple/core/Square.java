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
    
    public void placeLetter(Letter letter) {
        if (!occupied) {
            this.letter = letter;
            occupied = true;
        }
    }

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
