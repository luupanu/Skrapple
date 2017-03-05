/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.domain.Word;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.logic.actions.Move;
import fi.luupanu.skrapple.ui.components.dialogs.GameOverDialog;
import fi.luupanu.skrapple.utils.Announcer;
import fi.luupanu.skrapple.ui.components.panels.GameScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A custom action listener for the confirm move button.
 *
 * @author panu
 */
public class MoveActionListener implements ActionListener {

    private final Announcer announcer;
    private final SkrappleGame s;
    private final JButton moveButton;
    private final JButton exchangeButton;
    private final GameScreen gameScreen;
    private final JFrame frame;

    /**
     * Creates a new MoveActionListener.
     * @param a the announcer
     * @param gameScreen the game screen
     * @param s SkrappleGame
     * @param frame the frame to display dialogs on
     * @param move the confirm move button
     * @param exchange the exchange letters button
     */
    public MoveActionListener(Announcer a, GameScreen gameScreen,
            SkrappleGame s, JFrame frame, JButton move, JButton exchange) {
        this.announcer = a;
        this.frame = frame;
        this.s = s;
        this.moveButton = move;
        this.exchangeButton = exchange;
        this.gameScreen = gameScreen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (s.getGame().getGameState() != GameState.PLAYING) {
            return;
        }
        Move move = new Move(s.getGame(), announcer, gameScreen);
        ErrorMessage errorMsg = s.doAction(move);
        if (errorMsg == ErrorMessage.NO_ERRORS) {
            gameScreen.setBoardLettersUnClickable();
            gameScreen.updatePlayerRack();
            gameScreen.setLetterTilesEnabled(false);
            gameScreen.setUndoQueueButtonVisible(false);
            gameScreen.updatePlayerPoints();
            moveButton.setEnabled(false);
            exchangeButton.setEnabled(false);
            gameScreen.updateDefaultButton();

            if (s.getGame().getGameState() == GameState.GAMEOVER) {
                announcer.announce(Announcement.RACK_EMPTY_MESSAGE);
                GameOverDialog god = new GameOverDialog(announcer, gameScreen, s,
                        frame);
                god.showDialog();
            }
        } else if (errorMsg == ErrorMessage.LETTERQUEUE_IS_EMPTY) {
            String msg = announcer.announce(Announcement.LETTERQUEUE_EMPTY_MESSAGE);
            JOptionPane.showMessageDialog(frame, msg, "LOL!",
                    JOptionPane.WARNING_MESSAGE);
        } else if (errorMsg == ErrorMessage.LETTERQUEUE_IS_NOT_VALID) {
            String msg = announcer.announce(Announcement.LETTERQUEUE_NOT_VALID_MESSAGE);
            JOptionPane.showMessageDialog(frame, msg, "LOL!",
                    JOptionPane.WARNING_MESSAGE);
        } else if (errorMsg == ErrorMessage.WORD_IS_NOT_VALID) {
            Word word = errorMsg.getMessage();
            String msg = announcer.announce(Announcement.WORD_NOT_VALID_MESSAGE, word);
            JOptionPane.showMessageDialog(frame, msg, "LOL!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

}
