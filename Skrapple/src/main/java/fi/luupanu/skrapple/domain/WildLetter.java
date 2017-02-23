/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.LetterType;

/**
 *
 * @author panu
 */
public class WildLetter extends Letter {

    private LetterType wildLetterType;

    public WildLetter(LetterType type) {
        super(type);
    }

    public LetterType getWildLetterType() {
        return wildLetterType;
    }

    public void setWildLetterType(LetterType wildLetterType) {
        this.wildLetterType = wildLetterType;
    }
    
    @Override
    public String toString() {
        if (wildLetterType != null) {
            return wildLetterType.getName();
        }
        return this.getType().getName();
    }

}
