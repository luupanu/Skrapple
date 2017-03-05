/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components;

import fi.luupanu.skrapple.constants.SkrappleImageIcon;
import fi.luupanu.skrapple.domain.Letter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * A custom JButton class that optionally contains a Letter. Basically this
 * class is used to handle rack letter tiles while BoardTile is a subclass for
 * board letter tiles.
 *
 * @author panu
 */
public class LetterTile extends JButton {

    final Border greenThickBorder = new LineBorder(Color.GREEN, 3);
    final Border whiteBorder = new LineBorder(Color.WHITE);

    private Letter letter;

    /**
     * Creates a new LetterTile.
     */
    public LetterTile() {
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
    }

    public Letter getLetter() {
        return letter;
    }

    /**
     * Paints a letter tile.
     *
     * @param selected if true, paints a green thick border, otherwise a white
     * border
     */
    public void paintLetterTile(boolean selected) {
        if (getLetter() != null) {
            if (selected) {
                setBorder(greenThickBorder);
            } else {
                setBorder(whiteBorder);
            }
            setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());
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
