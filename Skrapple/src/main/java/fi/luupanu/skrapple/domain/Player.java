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
public class Player implements Comparable<Player> {
    
    private final String name;
    private int points;
    private final Rack rack;
    private boolean resigned;

    /**
     * Create a new player with 0 points and her own Rack.
     *
     * @param name the player name
     */
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
    
    public boolean isResigned() {
        return resigned;
    }
    
    public void resign() {
        resigned = true;
    }

    /**
     * This method is used to add or subtract points from a player.
     *
     * @param n the amount of points to be added or subtracted
     */
    public void addPoints(int n) {
        points += n;
    }
    
    @Override
    public String toString() {
        return name + ": " + points + " points";
    }

    @Override
    public int compareTo(Player other) {
        return other.points - this.points;
    }
}
