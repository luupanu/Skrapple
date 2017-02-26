/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import java.util.ArrayList;
import java.util.List;

/**
 * LetterQueue has a set of letters paired with coordinates so that they can
 * then be added to the board. This class helps the user so that it's (almost)
 * impossible to make invalid letter placements. The whole queue can then be
 * canceled so that the board still remains unchanged if the user makes an
 * invalid move.
 *
 * @author panu
 */
public class LetterQueue {

    private final LetterQueueChecks checks;
    private final List<Letter> list;
    private boolean direction; // true = horizontal, false = vertical
    private final Neighbours n;

    /**
     * Creates a new LetterQueue with the appropriate checks and the class
     * Neighbours. A letter queue can have a maximum of 7 letters (every single
     * letter from the rack).
     */
    public LetterQueue() {
        list = new ArrayList<>(7); // the LetterQueue
        checks = new LetterQueueChecks(this);
        n = new Neighbours(this);
    }

    public List<Letter> getContents() {
        return list;
    }

    /**
     * The direction the queue currently has. During a turn a player can only
     * place letters in one direction, horizontally or vertically, not both at
     * the same time.
     *
     * @return true if horizontal, false if vertical
     */
    public boolean getDirection() {
        return direction;
    }

    public void setDirection(boolean bool) {
        direction = bool;
    }

    /**
     * Returns the current neighbours the letters of the LetterQueue have.
     *
     * @return Neighbours
     */
    public Neighbours getNeighbours() {
        return n;
    }

    /**
     * Does a variety of checks in LetterQueueChecks and adds the letter if
     * everything was fine.
     *
     * @param let the letter to be added
     * @param c the coordinates of the letter
     * @param board the game board
     * @return true if the letter was added to LetterQueue
     */
    public boolean addLetterToQueue(Letter let, Coord c, Board board) {
        if (checks.letterCanBeAddedToQueue(let, c, board)) {
            let.setCoord(c);
            return list.add(let);
        }
        return false;
    }

    /**
     * Takes a single letter from the queue.
     *
     * @param let the letter to be taken
     * @return the letter taken
     */
    public Letter takeLetterFromQueue(Letter let) {
        if (list.contains(let) && let != null) {
            list.remove(let);
            return let;
        }
        return null;
    }

    /**
     * Cancels the whole LetterQueue.
     *
     * @return the letters the queue contained as a list
     */
    public List<Letter> cancelLetterQueue() {
        List<Letter> canceled = new ArrayList<>(list);
        list.clear();
        return canceled;
    }

    /**
     * Gets a letter from the queue by coordinate.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the letter with the same coordinate, null if not found
     */
    public Letter getLetterByCoordinate(int x, int y) {
        for (Letter let : list) {
            if (let.getCoord().getX() == x
                    && let.getCoord().getY() == y) {
                return let;
            }
        }
        return null;
    }

    /**
     * Returns true if the coordinate was valid.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the coordinate was valid
     */
    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < 15 && y >= 0 && y < 15;
    }

    /**
     * Returns true if the queue has a letter with the coordinate.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the queue has a letter with the coordinate
     */
    public boolean hasCoord(int x, int y) {
        for (Letter let : list) {
            if (let.getCoord().getX() == x
                    && let.getCoord().getY() == y) {
                return true;
            }
        }
        return false;
    }
}
