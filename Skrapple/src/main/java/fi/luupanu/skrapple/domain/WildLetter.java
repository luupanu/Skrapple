/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.LetterType;

/**
 * Wild letters are letters that any letter type can be assigned to but they
 * always score 0 points.
 *
 * @author panu
 */
public class WildLetter extends Letter {

    private LetterType wildLetterType;

    /**
     * Creates a new WildLetter.
     *
     * @param type LetterType.WILD
     */
    public WildLetter(LetterType type) {
        super(type);
    }

    public LetterType getWildLetterType() {
        return wildLetterType;
    }

    /**
     * Sets the wild letter type. LetterType.LETTER_WILD cannot be assigned to
     * wild letter type.
     *
     * @param wildLetterType the letter type this wild letter type will be set
     * to
     */
    public void setWildLetterType(LetterType wildLetterType) {
        if (wildLetterType != LetterType.LETTER_WILD) {
            this.wildLetterType = wildLetterType;
        }
    }

    /**
     * toString() representation for WildLetter. If the wild letter type has
     * been set, return it, otherwise return the normal letter type toString()
     * representation.
     *
     * @return wild letter type name if wild letter type has been set, otherwise
     * normal letter type name
     */
    @Override
    public String toString() {
        if (wildLetterType != null) {
            return wildLetterType.getName();
        }
        return this.getType().getName();
    }

}
