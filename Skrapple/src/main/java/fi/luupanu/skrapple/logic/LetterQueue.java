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

    public LetterQueue() {
        list = new ArrayList<>(7); // the LetterQueue
        checks = new LetterQueueChecks(this);
        n = new Neighbours(this);
    }

    public List<Letter> getLetterQueue() {
        return list;
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
            list.add(let);
            return true;
        }
        return false;
    }

    public Letter takeLetterFromQueue(Letter let) {
        if (list.contains(let) && let != null) {
            list.remove(let);
            return let;
        }
        return null;
    }
    
    public List<Letter> cancelLetterQueue() {
        List<Letter> canceled = new ArrayList<>(list);
        list.clear();
        return canceled;
    }

    public Letter getLetterByCoordinate(int x, int y) {
        for (Letter let : list) {
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
        for (Letter let : list) {
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
        for (Letter let : list) {
            Coord c = let.getCoord();
            sb.append(c).append(" - ");
        }
        return sb.toString();
    }
}
