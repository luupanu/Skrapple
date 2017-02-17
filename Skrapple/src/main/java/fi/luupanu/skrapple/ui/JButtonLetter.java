/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui;

import fi.luupanu.skrapple.domain.Letter;
import javax.swing.JButton;

/**
 * A custom JButton class that houses Letters.
 *
 * @author panu
 */
public class JButtonLetter extends JButton {

    private Letter let;

    /**
     * Creates a new custom JButton JButtonLetter.
     *
     * @param let the letter used by the JButton
     */
    public JButtonLetter(Letter let) {
        setLetter(let);
    }

    /**
     * Sets a letter to this JButton, sets the correct text (the letter and
     * the points as a Unicode subscript).
     *
     * @param let the letter to be set
     */
    public void setLetter(Letter let) {
        this.let = let;
        this.setText(let.toString() + " " + let.getPointsAsUnicodeSubScript());
    }

    public Letter getLetter() {
        return let;
    }
}
