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

    /**
     * This method is a "dirty" way of getting the points to show up on the
     * bottom right corner of a letter in the GUI.
     *
     * @return points as a small Unicode subscript representation
     */  
    public String getPointsAsUnicodeSubScript() {
        String number = String.valueOf(getPoints());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < number.length(); i++) {
            char u = 8320;
            int x = Integer.parseInt(number.substring(i, i + 1));
            for (int j = 0; j < x; j++) {
                u++;
            }
            sb.append(Character.toString(u));
        }
        return sb.toString();
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
