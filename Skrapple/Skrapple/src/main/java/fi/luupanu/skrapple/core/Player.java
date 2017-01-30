/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

/**
 *
 * @author panu
 */
public class Player {
    
    private final String name;
    private int points;
    
    public Player(String name) {
        this.name = name;
        this.points = 0;
    }
    
    public String getPlayerName() {
        return name;
    }
    
    public int getPlayerPoints() {
        return points;
    }
    
    public void addPoints(int n) {
        points += n;
    }
}
