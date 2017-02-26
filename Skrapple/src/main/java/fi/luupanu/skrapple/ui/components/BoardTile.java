/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components;

import fi.luupanu.skrapple.constants.SkrappleImageIcon;
import fi.luupanu.skrapple.domain.Coord;

/**
 * A subclass of LetterTile for board letter tiles. BoardTile contains a
 * coordinate that should be equal to the coordinate of the corresponding game
 * logic board square. BoardTiles are special also in that they can be set
 * "final" with the boolean unClickable, which the action listener should
 * respect and not do anything.
 *
 * @author panu
 */
public class BoardTile extends LetterTile {

    private Coord c;
    private boolean unClickable;

    public BoardTile() {
        super();
    }

    public boolean isUnClickable() {
        return unClickable;
    }

    /**
     * Call this function to set this board tile unclickable. I.e. after a move
     * has been made, this tile is final, and no actions can be performed to it.
     *
     */
    public void setUnClickable() {
        unClickable = true;
    }

    public Coord getCoord() {
        return c;
    }

    public void setCoord(Coord c) {
        this.c = c;
    }

    @Override
    public void paintLetterTile(boolean selected) {
        if (getLetter() != null) {
            setBorder(whiteBorder);
            setIcon(SkrappleImageIcon.LETTER_TILE_SELECTED.getIcon());
        }
    }

    public void paintBoardIcon(String[] layout) {
        setLetter(null);
        if (layout[c.getY()].charAt(c.getX()) == '.') {
            setIcon(null);
        } else if (layout[c.getY()].charAt(c.getX()) == 'l') {
            setIcon(SkrappleImageIcon.BONUS_LETTER_2X.getIcon());
        } else if (layout[c.getY()].charAt(c.getX()) == 'w') {
            setIcon(SkrappleImageIcon.BONUS_WORD_2X.getIcon());
        } else if (layout[c.getY()].charAt(c.getX()) == 'L') {
            setIcon(SkrappleImageIcon.BONUS_LETTER_3X.getIcon());
        } else if (layout[c.getY()].charAt(c.getX()) == 'W') {
            setIcon(SkrappleImageIcon.BONUS_WORD_3X.getIcon());
        }
    }
}
