/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.logic.actions.EndTurn;
import fi.luupanu.skrapple.utils.Announcer;
import fi.luupanu.skrapple.ui.components.dialogs.ConfirmationDialog;
import fi.luupanu.skrapple.ui.components.dialogs.GameOverDialog;
import fi.luupanu.skrapple.ui.components.panels.GameScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * An action listener to watch for end turn button clicks.
 *
 * @author panu
 */
public class EndTurnActionListener extends ConfirmationDialog implements ActionListener {

    private final SkrappleGame s;
    private final JFrame frame;
    private final JButton endTurnButton;
    private final GameScreen gameScreen;
    private final Announcer announcer;
    private final JButton moveButton;
    private final JButton exchangeButton;

    /**
     * Creates a new EndTurnActionListener.
     * @param a the announcer
     * @param gameScreen the game screen
     * @param s SkrappleGame
     * @param frame the frame to display dialogs on
     * @param endTurnButton the end turn button
     * @param moveButton the confirm move button
     * @param exchangeButton the exchange letters button
     */
    public EndTurnActionListener(Announcer a, GameScreen gameScreen,
            SkrappleGame s, JFrame frame, JButton endTurnButton,
            JButton moveButton, JButton exchangeButton) {
        this.announcer = a;
        this.gameScreen = gameScreen;
        this.s = s;
        this.frame = frame;
        this.endTurnButton = endTurnButton;
        this.moveButton = moveButton;
        this.exchangeButton = exchangeButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == endTurnButton && s.getGame().getGameState() == GameState.PLAYING) {
            if (e.getModifiers() == 0 // resign event
                    || !moveButton.isEnabled()) { // already moved
                endTurn();
            } else {
                int response = super.askConfirmation(frame, "End turn");
                if (response == JOptionPane.YES_OPTION) {
                    endTurn();
                }
            }
        }
    }

    private void endTurn() {
        if (s.doAction(new EndTurn(s.getGame())) != ErrorMessage.GAME_IS_OVER) {
            if (s.getGame().getGameState() == GameState.GAMEOVER) {
                GameOverDialog god = new GameOverDialog(announcer, gameScreen, s,
                        frame);
                god.showDialog();
                return;
            }

            gameScreen.updatePlayerInfo();
            gameScreen.updatePlayerRack();
            gameScreen.update(announcer.announce(Announcement.TURN_START_MESSAGE));
            gameScreen.removeAddedLettersMessage();
            gameScreen.setLetterTilesEnabled(true);
            gameScreen.updateHangingLetterTiles();
            gameScreen.setUndoQueueButtonVisible(false);
            moveButton.setEnabled(true);
            exchangeButton.setEnabled(true);
            gameScreen.updateDefaultButton();
            gameScreen.setRackLetterTilesVisible(false);

            String msg = announcer.announce(Announcement.TURN_START_MESSAGE);
            JOptionPane.showMessageDialog(frame, msg, "^_^",
                    JOptionPane.WARNING_MESSAGE);
            gameScreen.setRackLetterTilesVisible(true);
        }
    }
}
