/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

/**
 * The main Player class. Players have a name, their own Rack and a score
 * counter.
 *
 * @author panu
 */
public class Player {

    private final String name;
    private int points;
    private final Rack rack;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.rack = new Rack();
    }

    public String getPlayerName() {
        return name;
    }

    public int getPlayerPoints() {
        return points;
    }

    public Rack getPlayerRack() {
        return rack;
    }

    public void addPoints(int n) {
        points += n;
    }

    @Override
    public String toString() {
        return name + ": " + points + " points";
    }
}
