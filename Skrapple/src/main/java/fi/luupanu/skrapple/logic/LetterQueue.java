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
public class LetterQueue {
    
    /*  LetterQueue has a set of letters to be paired with coordinates so that
        they can be then added to the board. This class helps the user so that
        it's (almost) impossible to make invalid letter placements. The whole
        queue can then be canceled so that the board remains unchanged if the
        user makes an invalid move. */
    
    private final LetterQueueChecks checks;
    private final Set<Letter> set;
    private boolean direction; // true = horizontal, false = vertical

    public LetterQueue() {
        set = new HashSet<>(); // the LetterQueue
        checks = new LetterQueueChecks(this);
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
    
    public boolean addLetterToQueue(Letter let, Coord c, Board board) {
        if (checks.letterCanBeAddedToQueue(let, c, board)) {
            let.setCoord(c.getX(), c.getY());
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
