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
import fi.luupanu.skrapple.logic.actions.Resign;
import fi.luupanu.skrapple.ui.components.dialogs.ConfirmationDialog;
import fi.luupanu.skrapple.ui.components.dialogs.GameOverDialog;
import fi.luupanu.skrapple.ui.components.panels.GameScreen;
import fi.luupanu.skrapple.utils.Announcer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * An action listener for the Resign button.
 *
 * @author panu
 */
public class ResignActionListener extends ConfirmationDialog implements ActionListener {

    private final SkrappleGame s;
    private final JFrame frame;
    private final JButton resignButton;
    private final Announcer announcer;
    private final GameScreen gameScreen;
    private final JButton endTurnButton;

    /**
     * Creates a new ResignActionListener.
     *
     * @param a the announcer
     * @param gameScreen the game screen
     * @param s SkrappleGame
     * @param frame the frame to display dialogs on
     * @param resignButton the resign button
     * @param endTurnButton the end turn button
     */
    public ResignActionListener(Announcer a, GameScreen gameScreen, SkrappleGame s,
            JFrame frame, JButton resignButton, JButton endTurnButton) {
        this.announcer = a;
        this.gameScreen = gameScreen;
        this.s = s;
        this.frame = frame;
        this.endTurnButton = endTurnButton;
        this.resignButton = resignButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resignButton && s.getGame().getGameState() == GameState.PLAYING) {
            int response = super.askConfirmation(frame, "Resign");
            if (response == JOptionPane.YES_OPTION) {
                resignCurrentPlayer();
            }
        }
    }

    /**
     * Resigns the current player. If all but one player has resigned, game
     * over, else switch the turn.
     */
    public void resignCurrentPlayer() {
        if (s.doAction(new Resign(s.getGame())) != ErrorMessage.GAME_IS_OVER) {
            String msg = announcer.announce(Announcement.PLAYER_RESIGNED_MESSAGE);
            gameScreen.update(announcer.addIndentation(msg));
            if (s.getGame().getGameState() == GameState.GAMEOVER) {
                // end game screen
                GameOverDialog god = new GameOverDialog(announcer, gameScreen, s,
                        frame);
                god.showDialog();
            } else {
                endTurnButton.doClick();
            }
        }
    }
}
