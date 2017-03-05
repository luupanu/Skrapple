/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.SkrappleGUI;
import fi.luupanu.skrapple.ui.components.PlayerTextField;
import fi.luupanu.skrapple.ui.components.panels.GameScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * An action listener to listen to the New Game button on the main menu screen.
 *
 * @author panu
 */
public class NewGameActionListener implements ActionListener {

    private final List<PlayerTextField> playerTextFields;
    private final JFrame frame;
    private final SkrappleGUI gui;

    /**
     * Creates a new NewGameActionListener.
     *
     * @param gui the gui
     * @param frame the frame to display dialogs on
     * @param playerTextFields the player text fields
     */
    public NewGameActionListener(SkrappleGUI gui, JFrame frame,
            List<PlayerTextField> playerTextFields) {
        this.playerTextFields = playerTextFields;
        this.frame = frame;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int n = countNewPlayers();
        if (n < 2) {
            JOptionPane.showMessageDialog(frame,
                    "We need at least 2 players to start ^_^", "Nope!",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            createNewSkrappleGame();
        }
    }

    private void createNewSkrappleGame() {
        List<Player> players = new ArrayList<>();
        for (PlayerTextField ptf : playerTextFields) {
            if (!ptf.getText().isEmpty()) {
                players.add(new Player(ptf.getText()));
            } else {
                players.add(null);
            }
        }
        try {
            SkrappleGame s = new SkrappleGame(players.get(0), players.get(1),
                    players.get(2), players.get(3),
                    new Dictionary("kotus-wordlist-fi"));
            gui.newGameScreen(new GameScreen(gui, s, frame));
            frame.pack();
        } catch (IOException ex) {
            Logger.getLogger(NewGameActionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int countNewPlayers() {
        int n = 0;
        for (PlayerTextField ptf : playerTextFields) {
            if (!ptf.getText().isEmpty()) {
                n++;
            }
        }
        return n;
    }
}
