/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public int getRackPoints() {
        // tarvittaneen ainakin lopun miinuspisteytyksiä varten
        int sum = 0;
        for (Letter let : rack) {
            sum += let.getPoints();
        }
        return sum;
    }

    public void refillRack(LetterBag bag) {
        while (bag.getSize() > 0 && rack.size() < RACK_MAX_SIZE) {
            Letter taken = bag.takeRandomLetterFromBag();
            rack.add(taken);
        }
    }

    public Letter takeLetter(int i) {
        if (i >= 0 && i < rack.size()) {
            Letter taken = rack.get(i);
            rack.remove(i);
            return taken;
        }
        return null;
    }

    public boolean addLetters(List<Letter> letters) {
        if (rack.size() + letters.size() > 7) {
            return false;
        }
        for (Letter let : letters) {
            rack.add(let);
        }
        return true;
    }

    public boolean addLetter(Letter let) {
        if (rack.size() < RACK_MAX_SIZE) {
            return rack.add(let);
        }
        return false;
    }
}
