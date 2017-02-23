/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import java.util.HashSet;
import java.util.Set;

/**
 * Neighbours is a class used to keep track of letters in the LetterQueue that
 * touch existing letters on the board. The rules of the game state that if it's
 * not the first word of the game that is being played, each word must touch
 * existing words either horizontally or vertically. If a letter that touches an
 * existing letter on the board is found, it's added to a set based on the
 * direction.
 *
 * @author panu
 */
public class Neighbours {

    private final LetterQueue q;
    private final Set<Coord> horizontalNeighbours;
    private final Set<Coord> verticalNeighbours;

    /**
     * Creates a new Neighbours class.
     *
     * @param queue the letter queue used by the game
     */
    public Neighbours(LetterQueue queue) {
        this.q = queue;
        horizontalNeighbours = new HashSet<>();
        verticalNeighbours = new HashSet<>();
    }

    /**
     * This method finds all the neighbours the letters in the LetterQueue have
     * and returns false if none were found. A neighbour is an already placed
     * letter on the board that touches a letter in the LetterQueue either
     * vertically or horizontally (x-1, x+1, y-1 or y+1).
     *
     * @param board the game board
     * @return true if at least one neighbour was found, otherwise false
     */
    public boolean findAllNeighbours(Board board) {
        boolean returnable = false;
        boolean direction;

        int[][] neighbours = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (Letter let : q.getContents()) {
            for (int[] offset : neighbours) {
                if (offset[1] == 0) {
                    direction = true; // horizontal
                } else {
                    direction = false; // vertical
                }
                int x = let.getCoord().getX() + offset[0];
                int y = let.getCoord().getY() + offset[1];
                if (q.isValidCoordinate(x, y) && board.getSquare(x, y).hasLetter()) {
                    if (direction && !horizontalNeighboursContainRow(y)) {
                        addHorizontalNeighbour(new Coord(x, y));
                    } else if (!direction && !verticalNeighboursContainColumn(x)) {
                        addVerticalNeighbour(new Coord(x, y));
                    }
                    returnable = true; // found at least one neighbour
                }
            }
        }
        return returnable;
    }

    /**
     * Adds a horizontal neighbour with coordinates c.
     *
     * @param c the coordinates of the found neighbour
     */
    public void addHorizontalNeighbour(Coord c) {
        horizontalNeighbours.add(c);
    }

    /**
     * Adds a vertical neighbour with coordinates c.
     *
     * @param c the coordinates of the found neighbour
     */
    public void addVerticalNeighbour(Coord c) {
        verticalNeighbours.add(c);
    }

    /**
     * Returns all horizontal neighbours' coordinates (x-1 or x+1).
     *
     * @return a set of coordinates for all horizontal neighbours
     */
    public Set<Coord> getHorizontalNeighbours() {
        return horizontalNeighbours;
    }

    /**
     * Returns all vertical neighbours' coordinates (x-1 or x+1).
     *
     * @return a set of coordinates for all vertical neighbours
     */
    public Set<Coord> getVerticalNeighbours() {
        return verticalNeighbours;
    }

    private boolean horizontalNeighboursContainRow(int y) {
        for (Coord c : horizontalNeighbours) {
            if (c.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private boolean verticalNeighboursContainColumn(int x) {
        for (Coord c : verticalNeighbours) {
            if (c.getX() == x) {
                return true;
            }
        }
        return false;
    }
}
