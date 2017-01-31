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

    public Square(SquareType type) {
        this.type = type;
        occupied = false;
    }

    public SquareType getType() {
        return type;
    }

    public boolean hasLetter() {
        return occupied;
    }

    public void setOccupied() {
        occupied = true;
    }

    @Override
    public String toString() {
        return type.getValue();
    }
}
