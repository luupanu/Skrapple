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
 * existing words either horizontally or vertically. If a letter that touches
 * an existing letter on the board is found, it's added to a set based on the
 * direction.
 * 
 * @author panu
 */
public class Neighbours {

    private final LetterQueue q;
    private final Set<Letter> set;
    private final Set<Coord> horizontalNeighbours;
    private final Set<Coord> verticalNeighbours;

    public Neighbours(LetterQueue queue) {
        this.q = queue;
        this.set = queue.getLetterQueue();
        horizontalNeighbours = new HashSet<>();
        verticalNeighbours = new HashSet<>();
    }

    public boolean findAllNeighbours(Board board) {
        // neighbour = x-1, x+1, y-1, y+1. Needs to be already placed on the board.
        boolean returnable = false;
        boolean direction;

        int[][] neighbours = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (Letter let : set) {
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

    public void addHorizontalNeighbour(Coord c) {
        horizontalNeighbours.add(c);
    }

    public void addVerticalNeighbour(Coord c) {
        verticalNeighbours.add(c);
    }

    public Set<Coord> getHorizontalNeighbours() {
        return horizontalNeighbours;
    }

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
