/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Letter;
import java.util.List;

/**
 * LetterQueueValidator is the final judge whether or not the LetterQueue is
 * accepted as valid. Unless it's the first word of the game, at least one
 * letter must touch an existing letter on the board. Also - if the queue is
 * empty, contains gaps (with no letters), or if it's the first word of the game
 * and no letters touch the center square - the LetterQueue will be discarded.
 *
 * @author panu
 */
public class LetterQueueValidator {

    private final LetterQueue q;
    private final List<Letter> list;
    private final Neighbours n;

    /**
     * Creates a new LetterQueueValidator.
     *
     * @param queue the letter queue used by the game
     */
    public LetterQueueValidator(LetterQueue queue) {
        this.q = queue;
        this.list = queue.getContents();
        this.n = queue.getNeighbours();
    }

    /**
     * Returns if the LetterQueue is valid. If this method returns true, we can
     * let the player make the move and score her points. This method also uses
     * the Neighbours class in determining if the queue contains at least one
     * letter that touches an existing letter on the board.
     *
     * @param board the game board
     * @return true if the LetterQueue is valid
     */
    public boolean letterQueueIsValid(Board board) {
        // preliminary check: discard empty queues
        if (list.size() < 1) {
            return false;
        }

        // first word of the game: must touch the center square
        if (board.hasNoLetters()) {
            return list.size() != 1
                    && atLeastOneLetterTouchesTheCenterSquare(board)
                    && queueHasNoGaps(board);
        }

        // the letters in the queue cannot leave a gap
        if (list.size() != 1 && queueHasNoGaps(board)) {
            return n.findAllNeighbours(board);
        }

        // if only one letter is in the queue, don't need to check for gaps
        return n.findAllNeighbours(board);
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
        int left = list.stream().mapToInt(x -> x.getCoord().getX()).min().orElse(-1);
        int right = list.stream().mapToInt(x -> x.getCoord().getX()).max().orElse(-1);
        int y = list.stream().findFirst().get().getCoord().getY();

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
        int top = list.stream().mapToInt(y -> y.getCoord().getY()).min().orElse(-1);
        int bottom = list.stream().mapToInt(y -> y.getCoord().getY()).max().orElse(-1);
        int x = list.stream().findFirst().get().getCoord().getX();

        for (int y = top + 1; y < bottom; y++) {
            if (!q.hasCoord(x, y) && (!board.getSquare(x, y).hasLetter())) {
                return false;
            }
        }
        return true;
    }

    private boolean atLeastOneLetterTouchesTheCenterSquare(Board board) {
        for (Letter let : list) {
            if (let.getCoord().getX() == 7 && let.getCoord().getY() == 7) {
                return true;
            }
        }
        return false;
    }
}
