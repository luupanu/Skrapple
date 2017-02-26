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
import fi.luupanu.skrapple.ui.SkrappleGUI;
import fi.luupanu.skrapple.ui.components.ConfirmationDialog;
import fi.luupanu.skrapple.ui.components.GameOverDialog;
import fi.luupanu.skrapple.utils.Announcer;
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
    private final JButton resignButton;
    private final Announcer announcer;
    private final SkrappleGUI gui;
    private final JButton endTurnButton;

    public ResignActionListener(Announcer a, SkrappleGUI gui, SkrappleGame s,
            JFrame frame, JButton resignButton, JButton endTurnButton) {
        this.announcer = a;
        this.gui = gui;
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

    public void resignCurrentPlayer() {
        if (s.doAction(new Resign(s.getGame())) != ErrorMessage.GAME_IS_OVER) {
            String msg = announcer.announce(Announcement.PLAYER_RESIGNED_MESSAGE);
            gui.update(msg);
            if (s.getGame().getGameState() == GameState.GAMEOVER) {
                // end game screen
                GameOverDialog god = new GameOverDialog(announcer, gui, s,
                        frame);
            } else {
                endTurnButton.doClick();
            }
        }
    }
}
