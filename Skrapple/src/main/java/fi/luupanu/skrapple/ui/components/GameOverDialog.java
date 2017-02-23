/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components;

import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.logic.SkrappleGame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author panu
 */
public class GameOverDialog {

    private final JFrame frame;
    private final SkrappleGame s;

    // TO-DO:
    public GameOverDialog(SkrappleGame s, JFrame frame,
            PlayerName playerOneName, PlayerName playerTwoName,
            PlayerPoints playerOnePoints, PlayerPoints playerTwoPoints) {
        this.frame = frame;
        this.s = s;
        showDialog();
    }

    private void showDialog() {
        Player winner = s.declareWinner();
        Player loser = s.getGame().getPlayerOne().equals(winner)
                ? s.getGame().getPlayerTwo() : s.getGame().getPlayerOne();
        String message;
        if (winner != null) {
            message = winner.getPlayerName() + " won with "
                    + winner.getPlayerPoints() + " points compared to "
                    + loser.getPlayerName() + "'s " + loser.getPlayerPoints()
                    + ".";
        } else {
            message = "Game was drawn!";
        }
        JOptionPane.showConfirmDialog(frame, message, "Game Over :-(",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
    }
}
