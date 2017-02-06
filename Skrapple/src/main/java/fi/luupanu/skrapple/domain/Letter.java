/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import java.util.Objects;

/**
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

    public void setCoord(int x, int y) {
        this.coord = new Coord(x, y);
    }

    @Override
    public String toString() {
        return type.getName();
    }
}
