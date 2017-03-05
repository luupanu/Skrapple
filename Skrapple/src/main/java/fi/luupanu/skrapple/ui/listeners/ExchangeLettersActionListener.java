/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.logic.actions.ExchangeLetters;
import fi.luupanu.skrapple.ui.components.LetterTile;
import fi.luupanu.skrapple.ui.components.dialogs.ExchangeLettersDialog;
import fi.luupanu.skrapple.ui.components.panels.GameScreen;
import fi.luupanu.skrapple.utils.Announcer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * An action listener for the exchange letters button and dialog.
 *
 * @author panu
 */
public class ExchangeLettersActionListener implements ActionListener {

    private final JFrame frame;
    private final LetterTile[] rackLetters;
    private final JButton exchangeButton;
    private final List<Letter> selected;
    private final SkrappleGame s;
    private final GameScreen gameScreen;
    private final Announcer announcer;
    private final JButton moveButton;

    /**
     * Creates a new ExchangeLettersActionListener.
     * @param frame the frame to display dialogs on
     * @param exchangeButton the exchange letters button
     * @param moveButton the confirm move button
     * @param rackLetters the rack letters
     * @param s SkrappleGame
     * @param gameScreen the game screen
     * @param a the announcer
     */
    public ExchangeLettersActionListener(JFrame frame, JButton exchangeButton,
            JButton moveButton, LetterTile[] rackLetters, SkrappleGame s,
            GameScreen gameScreen, Announcer a) {
        this.announcer = a;
        this.exchangeButton = exchangeButton;
        this.moveButton = moveButton;
        this.frame = frame;
        this.gameScreen = gameScreen;
        this.rackLetters = rackLetters;
        this.s = s;
        selected = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exchangeButton) {
            createNewExchangeLettersDialog();
        }

        // select this letter to be exchanged
        if (e.getSource() instanceof LetterTile) {
            addLetterToSelected((LetterTile) e.getSource());
        }
    }

    private void createNewExchangeLettersDialog() {
        // don't allow an exchange if queue has letters
        if (!s.getGame().getLetterQueue().getContents().isEmpty()) {
            String msg = announcer.announce(Announcement.EXCHANGE_CANNOT_START_MESSAGE);
            JOptionPane.showMessageDialog(frame, msg, "LOL!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (s.getGame().getLetterBag().getContents().isEmpty()) {
            String msg = announcer.announce(Announcement.LETTERBAG_EMPTY_MESSAGE);
            JOptionPane.showMessageDialog(frame, msg, "LOL!",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        ExchangeLettersDialog eld = new ExchangeLettersDialog(frame,
                rackLetters, this);
        int response = eld.exchangeLetters();

        if (response == JOptionPane.YES_OPTION && !selected.isEmpty()) {
            ExchangeLetters exchange = new ExchangeLetters(s.getGame(),
                    selected, announcer, gameScreen);
            ErrorMessage error = exchange.perform(s.getGame());

            if (error == ErrorMessage.LETTERS_NOT_EXCHANGED) {
                String msg = announcer.announce(Announcement.EXCHANGE_FAILED_MESSAGE);
                JOptionPane.showMessageDialog(frame, msg, "LOL!",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                // all good
                gameScreen.updatePlayerRack();
                gameScreen.setLetterTilesEnabled(false);
                moveButton.setEnabled(false);
                exchangeButton.setEnabled(false);
                gameScreen.updateDefaultButton();
            }
        }
        selected.clear();
    }

    private void addLetterToSelected(LetterTile tile) {
        if (!selected.contains(tile.getLetter())) {
            selected.add(tile.getLetter());
            tile.paintLetterTile(true);
        } else {
            selected.remove(tile.getLetter());
            tile.paintLetterTile(false);
        }
    }
}
