/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author panu
 */
public class Rack {

    private ArrayList<Letter> rack;
    private static final int RACK_MAX_SIZE = 7;

    public Rack() {
        this.rack = new ArrayList<>(7);
    }

    public ArrayList<Letter> getContents() {
        return rack;
    }

    public int getTotalPoints() {
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
}
