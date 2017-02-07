/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Letter;
import java.util.Set;

/**
 *
 * @author panu
 */
public class LetterQueueValidator {

    /*  The final judge whether or not we accept the LetterQueue as valid. */
    private final LetterQueue q;
    private final Set<Letter> set;

    public LetterQueueValidator(LetterQueue queue) {
        this.q = queue;
        this.set = queue.getLetterQueue();
    }

    public boolean letterQueueIsValid(Board board) {
        if (set.size() < 1) {
            return false;
        }

        if (set.size() != 1 && queueHasNoGaps(board)) {
            return atLeastOneLetterTouchesANeighbour(board);
        }
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
            if (!queueHasCoord(x, y) && (!board.getSquare(x, y).hasLetter())) {
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
            if (!queueHasCoord(x, y) && (!board.getSquare(x, y).hasLetter())) {
                return false;
            }
        }
        return true;
    }

    private boolean queueHasCoord(int x, int y) {
        for (Letter let : set) {
            if (x == let.getCoord().getX()
                    && y == let.getCoord().getY()) {
                return true;
            }
        }
        return false;
    }

    private boolean atLeastOneLetterTouchesANeighbour(Board board) {
        // neighbour = x-1, x+1, y-1, y+1. Needs to be already placed on the board.
        if (board.hasNoLetters()) {
            return true; // don't need to touch a letter if there's none
        }
        int[][] neighbours = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (Letter let : set) {
            for (int[] offset : neighbours) {
                int x = let.getCoord().getX() + offset[0];
                int y = let.getCoord().getY() + offset[1];
                if (isValidCoordinate(x, y) && board.getSquare(x, y).hasLetter()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < 15 && y >= 0 && y < 15;
    }
}
