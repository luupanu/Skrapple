/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.logic.actions.EndTurn;
import fi.luupanu.skrapple.ui.SkrappleGUI;
import fi.luupanu.skrapple.utils.Announcer;
import fi.luupanu.skrapple.ui.components.ConfirmationDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author panu
 */
public class EndTurnActionListener extends ConfirmationDialog implements ActionListener {

    private final SkrappleGame s;
    private final JFrame frame;
    private final JButton endTurnButton;
    private final SkrappleGUI gui;
    private final Announcer announcer;
    private final JButton moveButton;
    private final JButton exchangeButton;

    public EndTurnActionListener(Announcer a, SkrappleGUI gui, SkrappleGame s, 
            JFrame frame, JButton endTurnButton, JButton moveButton, 
            JButton exchangeButton) {
        this.announcer = a;
        this.gui = gui;
        this.s = s;
        this.frame = frame;
        this.endTurnButton = endTurnButton;
        this.moveButton = moveButton;
        this.exchangeButton = exchangeButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == endTurnButton) {
            int response = super.askConfirmation(frame, "End turn");
            if (response == JOptionPane.YES_OPTION) {
                endTurn();
            }
        }
    }

    private void endTurn() {
        s.doAction(new EndTurn(s.getGame()));
        gui.updatePlayerNames();
        gui.updatePlayerRack();
        gui.update(announcer.announce(Announcement.TURN_START_MESSAGE));
        gui.updateRemoveAddedLettersMessage();
        gui.updateSetLetterTilesEnabled(true);
        moveButton.setEnabled(true);
        exchangeButton.setEnabled(true);
    }
}
