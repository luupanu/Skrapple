/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui;

import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 * A custom JButton class that optionally contains a Letter.
 *
 * @author panu
 */
public class JButtonLetter extends JButton {

    private Coord c;
    private Letter let;
    private boolean sel;
    private final boolean isBoardLetter;

    public JButtonLetter(boolean isBoardLetter) {
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
        this.let = let;
        if (let != null) {
            this.setText(let.toString() + " " + let.getPointsAsUnicodeSubScript());
        } else {
            this.setText("");
        }
        paintLetter();
    }

    public Letter getLetter() {
        return let;
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

    public void setSel(boolean b) {
        sel = b;
        paintLetter();
    }

    private void paintLetter() {
        if (getLetter() != null) {
            if (sel) {
                setBorder(new LineBorder(Color.GREEN, 3));
                setIcon(new ImageIcon("lettertile_sel_46x46.png"));
            } else {
                setBorder(new LineBorder(Color.WHITE));
                setIcon(new ImageIcon("lettertile_46x46.png"));
            }
        }
    }

    public void paintBoardIcon(String[] layout) {
        if (getIsBoardLetter()) {
            setLetter(null);
            if (layout[c.getY()].charAt(c.getX()) == '.') {
                setIcon(null);
            } else if (layout[c.getY()].charAt(c.getX()) == 'l') {
                setIcon(new ImageIcon("bonus_letter_2_46x46.png"));
            } else if (layout[c.getY()].charAt(c.getX()) == 'w') {
                setIcon(new ImageIcon("bonus_word_2_46x46.png"));
            } else if (layout[c.getY()].charAt(c.getX()) == 'L') {
                setIcon(new ImageIcon("bonus_letter_3_46x46.png"));
            } else if (layout[c.getY()].charAt(c.getX()) == 'W') {
                setIcon(new ImageIcon("bonus_word_3_46x46.png"));
            }
        }
    }

    private void createJButtonLetter() {
        setBorder(new LineBorder(Color.WHITE));
        setFont(new Font("serif", Font.BOLD, 20));
        setLayout(new BorderLayout());
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.CENTER);
    }
}
