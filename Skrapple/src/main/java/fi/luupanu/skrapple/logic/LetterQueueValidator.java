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
 *
 * @author panu
 */
public class LetterQueueValidator {
    
    // Maybe another class to handle creation of neighbours?

    /*  The final judge whether or not we accept the LetterQueue as valid. */
    private final LetterQueue q;
    private final Set<Letter> set;
    private Set<Coord> horizontalNeighbours;
    private Set<Coord> verticalNeighbours;

    public LetterQueueValidator(LetterQueue queue) {
        this.q = queue;
        this.set = queue.getLetterQueue();
        horizontalNeighbours = new HashSet<>();
        verticalNeighbours = new HashSet<>();
    }

    public boolean letterQueueIsValid(Board board) {
        // preliminary check: discard empty queues
        if (set.size() < 1) {
            return false;
        }

        // first word of the game: must touch the center square
        if (board.hasNoLetters()) {
            return atLeastOneLetterTouchesTheCenterSquare(board);
        }

        // the letters in the queue cannot leave a gap
        if (set.size() != 1 && queueHasNoGaps(board)) {
            return atLeastOneLetterTouchesANeighbour(board);
        }

        // if only one letter is in the queue, don't need to check for gaps
        return atLeastOneLetterTouchesANeighbour(board);
    }

    private boolean queueHasNoGaps(Board board) {
        boolean direction = q.getDirection();

        if (direction) {
            return horizontalQueueHasNoGaps(board);
        } else if (!direction) {
            return verticalQueueHasNoGaps(board);
        } else {
            return false;
        }
    }

    private boolean horizontalQueueHasNoGaps(Board board) {
        /*  find the leftmost & rightmost letters in the queue, check that all 
            the squares between them are either occupied or in the queue as well */

        // lambda fun
        int left = set.stream().mapToInt(x -> x.getCoord().getX()).min().orElse(-1);
        int right = set.stream().mapToInt(x -> x.getCoord().getX()).max().orElse(-1);
        int y = set.stream().findFirst().get().getCoord().getY();

        for (int x = left + 1; x < right; x++) {
            if (!q.hasCoord(x, y) && (!board.getSquare(x, y).hasLetter())) {
                return false;
            }
        }
        return true;
    }

    private boolean verticalQueueHasNoGaps(Board board) {
        /*  find the topmost & bottommost letters in the queue, check that all
            the squares between them are either occupied or in the queue as well */

        // lambda fun
        int top = set.stream().mapToInt(y -> y.getCoord().getY()).min().orElse(-1);
        int bottom = set.stream().mapToInt(y -> y.getCoord().getY()).max().orElse(-1);
        int x = set.stream().findFirst().get().getCoord().getX();

        for (int y = top + 1; y < bottom; y++) {
            if (!q.hasCoord(x, y) && (!board.getSquare(x, y).hasLetter())) {
                return false;
            }
        }
        return true;
    }

    private boolean atLeastOneLetterTouchesTheCenterSquare(Board board) {
        for (Letter let : set) {
            if (let.getCoord().getX() == 7 && let.getCoord().getY() == 7) {
                return true;
            }
        }
        return false;
    }

    private boolean atLeastOneLetterTouchesANeighbour(Board board) {
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
                        horizontalNeighbours.add(new Coord(x, y));
                    } else if (!direction && !verticalNeighboursContainColumn(x)) {
                        verticalNeighbours.add(new Coord(x, y));
                    }
                    returnable = true;
                }
            }
        }
        return returnable;
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

    public Set<Coord> getHorizontalNeighbours() {
        return horizontalNeighbours;
    }

    public Set<Coord> getVerticalNeighbours() {
        return verticalNeighbours;
    }

}
