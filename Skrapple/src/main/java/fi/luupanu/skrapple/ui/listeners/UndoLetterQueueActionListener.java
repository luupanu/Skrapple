/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.WildLetter;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.SkrappleGUI;
import fi.luupanu.skrapple.ui.components.RackPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JButton;

/**
 *
 * @author panu
 */
public class UndoLetterQueueActionListener implements ActionListener, MouseListener {

    private final SkrappleGame s;
    private final JButton undoQueueButton;
    private final SkrappleGUI gui;
    private final RackPanel rackPanel;

    public UndoLetterQueueActionListener(SkrappleGame s, SkrappleGUI gui,
            RackPanel rackPanel, JButton undoQueueButton) {
        this.gui = gui;
        this.rackPanel = rackPanel;
        this.s = s;
        this.undoQueueButton = undoQueueButton;
        addMouseListener();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == undoQueueButton &&
                s.getGame().getGameState() == GameState.PLAYING) {
            List<Letter> canceled = s.getGame().getLetterQueue().cancelLetterQueue();
            for (Letter let : canceled) {
                resetWildLetterType(let);
            }
            s.getGame().getCurrentPlayer().getPlayerRack().addLetters(canceled);
            gui.updateHangingLetterTiles();
            gui.updatePlayerRack();
            gui.setUndoQueueButtonVisible(false);
        }
    }
    
    private void resetWildLetterType(Letter let) {
        if (let.getType() == LetterType.LETTER_WILD) {
            WildLetter wLet = (WildLetter) let;
            wLet.setWildLetterType(null);
        }
    }

    /**
     * Adding the mouse listener is unfortunately necessary to squash a bug
     * where the background of the JButton doesn't stay transparent but turns
     * to black instead when pressed but not released.
     *
     */
    private void addMouseListener() {
        undoQueueButton.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        rackPanel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        rackPanel.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        rackPanel.repaint();
    }
}
