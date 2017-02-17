/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Every player has a unique rack that stores up to 7 letters. Using the letters
 * players can form words on the board.
 *
 * @author panu
 */
public class Rack {

    // TO-DO: switching letters
    private final List<Letter> rack;
    private static final int RACK_MAX_SIZE = 7;

    public Rack() {
        this.rack = new ArrayList<>(7);
    }

    public List<Letter> getContents() {
        return rack;
    }

    /**
     * This method counts the score total of all letters in the rack. It's used
     * in the end of the game to subtract remaining letters from the total
     * player score.
     *
     * @return the value of the current letters in the rack
     */
    public int getRackPoints() {
        int sum = 0;
        for (Letter let : rack) {
            sum += let.getPoints();
        }
        return sum;
    }

    /**
     * Refills the rack so that the player always has the maximum (7) letters in
     * her rack, if possible.
     *
     * @param bag
     */
    public void refillRack(LetterBag bag) {
        while (bag.getSize() > 0 && rack.size() < RACK_MAX_SIZE) {
            Letter taken = bag.takeRandomLetterFromBag();
            rack.add(taken);
        }
    }

    /**
     * Takes a letter from the rack with index i.
     *
     * @param i
     * @return the letter taken
     */
    public Letter takeLetter(int i) {
        if (i >= 0 && i < rack.size()) {
            Letter taken = rack.get(i);
            rack.remove(i);
            return taken;
        }
        return null;
    }

    /**
     * Adds a list of letters back to the rack. This method is used when
     * canceling a LetterQueue.
     *
     * @param letters
     * @return true only if all letters can be added
     */
    public boolean addLetters(List<Letter> letters) {
        if (rack.size() + letters.size() > 7) {
            return false;
        }
        for (Letter let : letters) {
            rack.add(let);
        }
        return true;
    }

    /**
     * Adds a single letter back to the rack.
     *
     * @param let
     * @return true if the letter could be added to the rack
     */
    public boolean addLetter(Letter let) {
        if (rack.size() < RACK_MAX_SIZE) {
            return rack.add(let);
        }
        return false;
    }
}
