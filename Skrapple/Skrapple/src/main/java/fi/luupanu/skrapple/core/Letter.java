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
public class Letter {
    
    private final LetterType type;
    
    public Letter(LetterType type) {
        this.type = type;
    }
    
    public LetterType getType() {
        return type;
    }
    
    public int getPoints() {
        return type.getPoints();
    }
    
    public String getName() {
        return type.getName();
    }
}
