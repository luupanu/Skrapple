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
public enum LetterType {
    LETTER_A("A", 1),
    LETTER_B("B", 8),
    LETTER_C("C", 10),
    LETTER_D("D", 7),
    LETTER_E("E", 1),
    LETTER_F("F", 8),
    LETTER_G("G", 8),
    LETTER_H("H", 4),
    LETTER_I("I", 1),
    LETTER_J("J", 4),
    LETTER_K("K", 2),
    LETTER_L("L", 2),
    LETTER_M("M", 3),
    LETTER_N("N", 1),
    LETTER_O("O", 2),
    LETTER_P("P", 4), // letter Q does not exist in the Finnish version
    LETTER_R("R", 4),
    LETTER_S("S", 1),
    LETTER_T("T", 1),
    LETTER_U("U", 3),
    LETTER_V("V", 4),
    LETTER_W("W", 8),
    LETTER_Y("Y", 4), // nor Z
    LETTER_AE("Ä", 2),
    LETTER_OE("Ö", 7),
    LETTER_WILD("*", 0);
    private final int points;
    private final String name;

    private LetterType(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return this.name;
    }

    public int getPoints() {
        return this.points;
    }
}
