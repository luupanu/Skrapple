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
        // onkohan i < RACK_MAX_SIZE turha vai hyvä check varuilta?
        if (i >= 0 && i < rack.size() && i < RACK_MAX_SIZE) {
            Letter taken = rack.get(i);
            rack.remove(i);
            return taken;
        }
        return null;
    }

    public boolean addLetters(Set<Letter> letters) {
        for (Letter let : letters) {
            if (!rack.add(let)) {
                return false;
            }
        }
        return true;
    }

    private boolean addLetter(Letter let) {
        if (rack.size() < RACK_MAX_SIZE) {
            return rack.add(let);
        }
        return false;
    }
}
