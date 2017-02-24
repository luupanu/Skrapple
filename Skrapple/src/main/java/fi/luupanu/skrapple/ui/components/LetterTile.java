/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components;

import fi.luupanu.skrapple.constants.SkrappleImageIcon;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * A custom JButton class that optionally contains a Letter.
 *
 * @author panu
 */
public class LetterTile extends JButton {

    private final Border greenThickBorder = new LineBorder(Color.GREEN, 3);
    private final Border whiteBorder = new LineBorder(Color.WHITE);
    
    private Coord c;
    private Letter letter;
    private boolean selected;
    private final boolean isBoardLetter;

    public LetterTile(boolean isBoardLetter) {
        this.isBoardLetter = isBoardLetter;
        createJButtonLetter();
    }

    /**
     * Sets a letter to this JButton, sets the correct text (the letter and the
     * points as a Unicode subscript).
     *
     * @param let the letter to be set
     */
    public void setLetter(Letter let) {
        this.letter = let;
        if (let != null) {
            this.setText(let.toString() + " " + let.getPointsAsUnicodeSubScript());
        } else {
            this.setText("");
        }
        paintLetterSelected();
    }

    public Letter getLetter() {
        return letter;
    }

    public boolean getIsBoardLetter() {
        return isBoardLetter;
    }

    public Coord getCoord() {
        return c;
    }

    public void setCoord(Coord c) {
        this.c = c;
    }

    public void setSelectedVisualEffect(boolean b) {
        selected = b;
        paintLetterSelected();
    }

    public void paintLetterSelected() {
        if (getLetter() != null) {
            if (selected) {
                setBorder(greenThickBorder);
                setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());
            } else {
                setBorder(whiteBorder);
                if (isBoardLetter) {
                    setIcon(SkrappleImageIcon.LETTER_TILE_SELECTED.getIcon());
                } else {
                    setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());
                }
            }
        }
    }

    public void paintBoardIcon(String[] layout) {
        if (getIsBoardLetter()) {
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

    private void createJButtonLetter() {
        setBorder(new LineBorder(Color.WHITE));
        setFont(new Font("serif", Font.BOLD, 20));
        setLayout(new BorderLayout());
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
        setForeground(new Color(40, 0, 0));
    }
}
