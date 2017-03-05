/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.dialogs;

import fi.luupanu.skrapple.constants.SkrappleImageIcon;
import fi.luupanu.skrapple.ui.components.LetterTile;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * A custom dialog to handle exchanging letters.
 *
 * @author panu
 */
public class ExchangeLettersDialog {

    private JPanel exchangeLetters;
    private final JFrame frame;

    /**
     * Creates a new ExchangeLettersDialog.
     *
     * @param frame the frame to be displayed on
     * @param rackLetters the rack letters that we can exchange
     * @param exchangeLettersActionListener the ActionListener to be used
     */
    public ExchangeLettersDialog(JFrame frame, LetterTile[] rackLetters,
            ActionListener exchangeLettersActionListener) {
        this.frame = frame;
        createExchangeLettersPanel(rackLetters, exchangeLettersActionListener);
    }

    /**
     * Displays a dialog that asks the user which letters should be exchanged.
     *
     * @return the user response
     */
    public int exchangeLetters() {
        return JOptionPane.showConfirmDialog(frame, exchangeLetters,
                "Exchange which letters?", JOptionPane.YES_NO_OPTION);
    }

    private void createExchangeLettersPanel(LetterTile[] rackLetters,
            ActionListener exchangeLettersActionListener) {
        exchangeLetters = new JPanel();

        // add letter tiles
        for (LetterTile tile : rackLetters) {
            LetterTile exTile = new LetterTile();
            exTile.setLetter(tile.getLetter());
            exTile.setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());
            exTile.addActionListener(exchangeLettersActionListener);
            exchangeLetters.add(exTile);
        }
    }
}
