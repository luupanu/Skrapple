/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.dialogs;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.components.panels.GameScreen;
import fi.luupanu.skrapple.utils.Announcer;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A game over dialog. Returns to main menu screen when closed.
 *
 * @author panu
 */
public class GameOverDialog {

    private final JFrame frame;
    private final SkrappleGame s;
    private final Announcer announcer;
    private final GameScreen gameScreen;

    /**
     * Creates a new GameOverDialog.
     *
     * @param a the announcer
     * @param gameScreen the game screen
     * @param s SkrappleGame
     * @param frame the frame to be displayed on
     */
    public GameOverDialog(Announcer a, GameScreen gameScreen,
            SkrappleGame s, JFrame frame) {
        this.announcer = a;
        this.frame = frame;
        this.gameScreen = gameScreen;
        this.s = s;
    }

    /**
     * Show the game over dialog.
     */
    public void showDialog() {
        List<Player> players = s.doFinalScoring();
        String message = generateGameOverMessage(players);
        gameScreen.update(announcer.announce(Announcement.GAME_OVER_MESSAGE));
        int response = JOptionPane.showConfirmDialog(frame, message, "Game Over :-(",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        gameScreen.showMainMenu();
    }

    private String generateGameOverMessage(List<Player> players) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><b>Game over<b><html>!\n\n");
        sb.append("Final score:\n\n");

        for (Player p : players) {
            sb.append(p.getPlayerName()).append(": ");
            if (p.isResigned()) {
                sb.append("RESIGNED");
            } else {
                sb.append(p.getPlayerPoints()).append(" points");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
