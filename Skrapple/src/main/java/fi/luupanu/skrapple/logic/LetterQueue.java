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
    private final Set<Letter> set;
    private boolean direction; // true = horizontal, false = vertical
    private final Neighbours n;

    public LetterQueue() {
        set = new HashSet<>(); // the LetterQueue
        checks = new LetterQueueChecks(this);
        n = new Neighbours(this);
    }

    public Set<Letter> getLetterQueue() {
        return set;
    }

    public boolean getDirection() {
        return direction;
    }

    public void setDirection(boolean bool) {
        direction = bool;
    }
    
    public Neighbours getNeighbours() {
        return n;
    }

    public boolean addLetterToQueue(Letter let, Coord c, Board board) {
        if (checks.letterCanBeAddedToQueue(let, c, board)) {
            let.setCoord(c);
            set.add(let);
            return true;
        }
        return false;
    }

    public boolean removeLetterFromQueue(Letter let) {
        if (set.contains(let) && let != null) {
            set.remove(let);
            return true;
        }
        return false;
    }

    public Letter getLetterByCoordinate(int x, int y) {
        for (Letter let : set) {
            if (let.getCoord().getX() == x
                    && let.getCoord().getY() == y) {
                return let;
            }
        }
        return null;
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < 15 && y >= 0 && y < 15;
    }

    public boolean hasCoord(int x, int y) {
        for (Letter let : set) {
            if (let.getCoord().getX() == x
                    && let.getCoord().getY() == y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Letter let : set) {
            Coord c = let.getCoord();
            sb.append(c).append(" - ");
        }
        return sb.toString();
    }
}
