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
import fi.luupanu.skrapple.ui.components.panels.GameScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;

/**
 * An action listener for the Undo Move button.
 *
 * @author panu
 */
public class UndoLetterQueueActionListener implements ActionListener {

    private final SkrappleGame s;
    private final JButton undoQueueButton;
    private final GameScreen gameWindow;

    /**
     * Creates a new UndoLetterQueueActionListener.
     *
     * @param s SkrappleGame
     * @param gameWindow the game window
     * @param undoQueueButton the undo move button
     */
    public UndoLetterQueueActionListener(SkrappleGame s, GameScreen gameWindow,
            JButton undoQueueButton) {
        this.gameWindow = gameWindow;
        this.s = s;
        this.undoQueueButton = undoQueueButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == undoQueueButton
                && s.getGame().getGameState() == GameState.PLAYING) {
            List<Letter> canceled = s.getGame().getLetterQueue().cancelLetterQueue();
            for (Letter let : canceled) {
                resetWildLetterType(let);
            }
            s.getGame().getCurrentPlayer().getPlayerRack().addLetters(canceled);
            gameWindow.updateHangingLetterTiles();
            gameWindow.updatePlayerRack();
            gameWindow.setUndoQueueButtonVisible(false);
        }
    }

    private void resetWildLetterType(Letter let) {
        if (let.getType() == LetterType.LETTER_WILD) {
            WildLetter wLet = (WildLetter) let;
            wLet.setWildLetterType(null);
        }
    }
}
