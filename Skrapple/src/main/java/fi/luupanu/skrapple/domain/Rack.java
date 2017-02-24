/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Every player has a unique rack that stores up to 7 letters. Using the letters
 * players can form words on the board.
 *
 * @author panu
 */
public class Rack {

    private final SortedMap<Integer, Letter> sortedRack;
    //private final List<Letter> rack;
    private static final int RACK_MAX_SIZE = 7;

    /**
     * Create a new rack. Maximum size of the rack is 7.
     */
    public Rack() {
        //this.rack = new ArrayList<>(RACK_MAX_SIZE);
        this.sortedRack = new TreeMap<>();
    }

    public SortedMap<Integer, Letter> getContents() {
        return sortedRack;
    }

    /**
     * Get contents of the rack as a list.
     *
     * @return a list of letters in the rack
     */
    public List<Letter> getContentsAsList() {
        List<Letter> rack = new ArrayList<>(RACK_MAX_SIZE);
        for (Letter let : sortedRack.values()) {
            rack.add(let);
        }
        return rack;
    }

    public int getRackSize() {
        return RACK_MAX_SIZE;
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
        for (Letter let : sortedRack.values()) {
            sum += let.getPoints();
        }
        return sum;
    }

    /**
     * Refills the rack so that the player always has the maximum (7) letters in
     * her rack, if possible.
     *
     * @param bag the letter bag
     * @return a list of the new letters from the LetterBag
     */
    public List<Letter> refillRack(LetterBag bag) {
        List<Letter> newLetters = new ArrayList<>(7);
        while (bag.getSize() > 0 && sortedRack.size() < RACK_MAX_SIZE) {
            Letter taken = bag.takeRandomLetterFromBag();
            addLetter(taken);
            newLetters.add(taken);
        }
        return newLetters;
    }

    /**
     * Takes a letter from the rack.
     *
     * @param let the letter to be taken
     * @return the letter taken, null if not found
     */
    public Letter takeLetter(Letter let) {
        for (Integer i : sortedRack.keySet()) {
            if (sortedRack.get(i) == let) {
                Letter taken = let;
                sortedRack.remove(i);
                return taken;
            }
        }
        return null;
    }

    /**
     * Adds a list of letters back to the rack. This method is used when
     * canceling a LetterQueue.
     *
     * @param letters a list of letters to be added
     * @return true only if all letters can be added
     */
    public boolean addLetters(List<Letter> letters) {
        if (sortedRack.size() + letters.size() > 7) {
            return false;
        }
        for (Letter let : letters) {
            addLetter(let);
        }
        return true;
    }

    /**
     * Adds a single letter back to the rack.
     *
     * @param let a single letter to be added
     * @return true if the letter could be added to the rack
     */
    public boolean addLetter(Letter let) {
        if (sortedRack.size() < RACK_MAX_SIZE) {
            int i = 0;
            while (i < RACK_MAX_SIZE) {
                if (sortedRack.get(i) == null) {
                    sortedRack.put(i, let);
                    return true;
                }
                i++;
            }
        }
        return false;
    }
}
