/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.LetterType;

/**
 * The Letter class is a representation of letter tiles in Skrapple. Different
 * letters give various amounts of points when placed on the Board.
 *
 * @author panu
 */
public class Letter {

    private final LetterType type;
    private Coord coord;

    public Letter(LetterType type) {
        this.type = type;
    }

    public LetterType getType() {
        return type;
    }

    public int getPoints() {
        return type.getPoints();
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord c) {
        this.coord = c;
    }

    @Override
    public String toString() {
        return type.getName();
    }
}
