/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

/**
 * Each letter to be placed on the Board has a Coord(inate), a (x,y)
 * destination.
 *
 * @author panu
 */
public class Coord {

    private final int x;
    private final int y;

    /**
     * X and Y coordinates for a Letter.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
