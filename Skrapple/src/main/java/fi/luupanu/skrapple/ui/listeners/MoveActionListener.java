/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.SkrappleGameState;
import fi.luupanu.skrapple.domain.Word;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.logic.actions.Move;
import fi.luupanu.skrapple.utils.Announcer;
import fi.luupanu.skrapple.ui.SkrappleGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author panu
 */
public class MoveActionListener implements ActionListener {

    private final Announcer announcer;
    private final SkrappleGame s;
    private final JButton moveButton;
    private final JButton exchangeButton;
    private final SkrappleGUI gui;
    private final JFrame frame;

    public MoveActionListener(Announcer a, SkrappleGUI gui,
            SkrappleGame s, JFrame frame, JButton move, JButton exchange) {
        this.announcer = a;
        this.frame = frame;
        this.s = s;
        this.moveButton = move;
        this.exchangeButton = exchange;
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (s.getGame().getGameState() != SkrappleGameState.PLAYING) {
            return;
        }
        Move move = new Move(s.getGame(), announcer, gui);
        ErrorMessage errorMsg = s.doAction(move);
        if (errorMsg == ErrorMessage.NO_ERRORS) {
            gui.updateBoardLettersColour();
            gui.updatePlayerRack();
            gui.setLetterTilesEnabled(false);
            gui.setUndoQueueButtonVisible(false);
            moveButton.setEnabled(false);
            exchangeButton.setEnabled(false);
        } else if (errorMsg == ErrorMessage.LETTERQUEUE_IS_EMPTY) {
            String msg = announcer.announce(Announcement.LETTERQUEUE_EMPTY_ERROR);
            JOptionPane.showMessageDialog(frame, msg);
        } else if (errorMsg == ErrorMessage.LETTERQUEUE_IS_NOT_VALID) {
            String msg = announcer.announce(Announcement.LETTERQUEUE_NOT_VALID_ERROR);
            JOptionPane.showMessageDialog(frame, msg);
        } else if (errorMsg == ErrorMessage.WORD_IS_NOT_VALID) {
            Word word = errorMsg.getMessage();
            String msg = announcer.announce(Announcement.WORD_NOT_VALID_ERROR, word);
            JOptionPane.showMessageDialog(frame, msg);
        }
    }

}
