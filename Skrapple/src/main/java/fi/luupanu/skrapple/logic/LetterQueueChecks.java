/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Square;
import java.util.List;

/**
 * This class houses checks that are performed to all letters before they are
 * added to the LetterQueue.
 *
 * @author panu
 */
public class LetterQueueChecks {

    private final LetterQueue q;
    private final List<Letter> list;

    /**
     * Creates the class that checks all the letters to be added to the
     * LetterQueue.
     *
     * @param queue the letter queue used by the game
     */
    public LetterQueueChecks(LetterQueue queue) {
        this.q = queue;
        this.list = queue.getLetterQueue();
    }

    /**
     * Does a variety of checks to a letter before it is added to the queue. The
     * checks are used to guide the player in making a valid move. The body of
     * the method contains more explanations for all the checks.
     *
     * @param let the letter to be added
     * @param c the coordinates for the letter
     * @param board the game board
     * @return true if the letter is allowed to be added to the queue
     */
    public boolean letterCanBeAddedToQueue(Letter let, Coord c, Board board) {
        int x = c.getX();
        int y = c.getY();

        /*  preliminary checks: is this a valid coordinate, is this a valid
            letter, does the queue already have a letter at the coordinate? */
        if (!q.isValidCoordinate(x, y) || q.hasCoord(x, y) || let == null) {
            return false;
        }

        // check that the board doesn't already have a letter at the coordinate
        if (board.getSquare(x, y).hasLetter()) {
            return false;
        }

        // the first letter of the first word must be at x = 7 OR y = 7
        if (board.hasNoLetters() && list.isEmpty()) {
            return x == 7 || y == 7;
        }

        // check that the first letter of the queue is placed correctly
        if (list.isEmpty()) {
            return checkFirstLetterCorrectPlacement(c, board);
        }

        // check that the second letter of the queue is placed correctly
        if (list.size() == 1) {
            return checkSecondLetterCorrectPlacement(c, board);
        }

        // now that we have a direction for the word, we can check the rest
        return checkRemainingLettersCorrectPlacement(c, board, q.getDirection());
    }

    private boolean checkFirstLetterCorrectPlacement(Coord c, Board board) {
        /*  First letter of the word must be on the same row or column as an
            existing letter on the board, or on the neighbouring row/column */
        return rowOrNeighbouringRowHasLetter(c.getY(), board)
                || columnOrNeighbouringColumnHasLetter(c.getX(), board);
    }

    private boolean checkSecondLetterCorrectPlacement(Coord c, Board board) {
        /*  Second letter of the word must be on the same row or column as the
            first letter. The second letter gives us the direction for all the 
            rest of the letters (a player can either place a word vertically or
            horizontally on the board, not both at the same time) */
        if (board.hasNoLetters() && c.getX() != 7 && c.getY() != 7) {
            return false;
        }
        for (Letter let : list) {
            Coord firstLetterCoord = let.getCoord();
            if (firstLetterCoord.getX() == c.getX() && firstLetterCoord.getY() == c.getY()) {
                return false;
            } else if (firstLetterCoord.getX() == c.getX()) {
                q.setDirection(false); // vertical
                return true;
            } else if (firstLetterCoord.getY() == c.getY()) {
                q.setDirection(true); // horizontal
                return true;
            }
        }
        return false;
    }

    private boolean checkRemainingLettersCorrectPlacement(Coord c, Board board, boolean direction) {
        for (Letter let : list) {
            Coord coordinate = let.getCoord();
            if (c.getY() == coordinate.getY() && c.getX() == coordinate.getX()) {
                return false; // don't try to add exact same coordinates
            }
            if (direction) { // horizontal
                if (c.getY() != coordinate.getY()) {
                    return false;
                }
            } else if (!direction) { // vertical
                if (c.getX() != coordinate.getX()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean rowOrNeighbouringRowHasLetter(int row, Board board) {
        Square[][] b = board.getContents();

        for (int y = row - 1; y <= row + 1; y++) {
            for (int x = 0; x < 15; x++) {
                if (!q.isValidCoordinate(x, y)) {
                    continue;
                }
                Square s = b[x][y];
                if (s.hasLetter()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean columnOrNeighbouringColumnHasLetter(int col, Board board) {
        Square[][] b = board.getContents();

        for (int y = 0; y < 15; y++) {
            for (int x = col - 1; x < col + 1; x++) {
                if (!q.isValidCoordinate(x, y)) {
                    continue;
                }
                Square s = b[x][y];
                if (s.hasLetter()) {
                    return true;
                }
            }
        }
        return false;
    }
}
