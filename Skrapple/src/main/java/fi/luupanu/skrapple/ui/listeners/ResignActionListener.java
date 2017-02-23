/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.SkrappleGameState;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.logic.actions.Resign;
import fi.luupanu.skrapple.ui.components.ConfirmationDialog;
import fi.luupanu.skrapple.ui.components.GameOverDialog;
import fi.luupanu.skrapple.ui.components.PlayerName;
import fi.luupanu.skrapple.ui.components.PlayerPoints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author panu
 */
public class ResignActionListener extends ConfirmationDialog implements ActionListener {

    private final SkrappleGame s;
    private final JFrame frame;
    private final JButton resign;
    private final PlayerName playerOneName;
    private final PlayerName playerTwoName;
    private final PlayerPoints playerOnePoints;
    private final PlayerPoints playerTwoPoints;

    public ResignActionListener(SkrappleGame s, JFrame frame, JButton resign,
            PlayerName playerOneName, PlayerName playerTwoName,
            PlayerPoints playerOnePoints, PlayerPoints playerTwoPoints) {
        this.s = s;
        this.frame = frame;
        this.resign = resign;
        this.playerOneName = playerOneName;
        this.playerTwoName = playerOneName;
        this.playerOnePoints = playerOnePoints;
        this.playerTwoPoints = playerTwoPoints;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resign && s.getGame().getGameState() == SkrappleGameState.PLAYING) {
            int response = super.askConfirmation(frame, "Resign");
            if (response == JOptionPane.YES_OPTION) {
                resignCurrentPlayer();
            }
        }
    }

    public void resignCurrentPlayer() {
        if (s.doAction(new Resign(s.getGame())) != ErrorMessage.GAME_IS_OVER) {
            // end game screen
            GameOverDialog god = new GameOverDialog(s, frame, playerOneName,
                    playerTwoName, playerOnePoints, playerTwoPoints);
        }
    }
}
