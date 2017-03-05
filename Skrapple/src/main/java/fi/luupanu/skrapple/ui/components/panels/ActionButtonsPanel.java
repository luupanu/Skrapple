/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.panels;

import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.components.LetterTile;
import fi.luupanu.skrapple.ui.listeners.EndTurnActionListener;
import fi.luupanu.skrapple.ui.listeners.ExchangeLettersActionListener;
import fi.luupanu.skrapple.ui.listeners.MoveActionListener;
import fi.luupanu.skrapple.ui.listeners.ResignActionListener;
import fi.luupanu.skrapple.utils.Announcer;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A custom JPanel with JButtons that do Actions.
 *
 * @author panu
 */
public class ActionButtonsPanel extends JPanel {

    private final Announcer announcer;
    private final GameScreen gameScreen;
    private final SkrappleGame s;
    private final JFrame frame;
    private JButton endTurnButton;
    private JButton moveButton;
    private final LetterTile[] rackLetters;

    /**
     * Creates a new ActionButtonsPanel.
     * @param a the announcer
     * @param gameScreen the game screen
     * @param s SkrappleGame
     * @param frame the frame
     * @param rackLetters the rack letters
     */
    public ActionButtonsPanel(Announcer a, GameScreen gameScreen, SkrappleGame s,
            JFrame frame, LetterTile[] rackLetters) {
        this.announcer = a;
        this.gameScreen = gameScreen;
        this.s = s;
        this.frame = frame;
        this.rackLetters = rackLetters;
        createActionButtonsPanel();
    }

    public JButton getEndTurnButton() {
        return endTurnButton;
    }

    public JButton getMoveButton() {
        return moveButton;
    }

    private void createActionButtonsPanel() {
        // set layout
        GridLayout layout = new GridLayout(4, 1);
        setLayout(layout);

        // create buttons
        moveButton = new JButton("Confirm move");
        endTurnButton = new JButton("End turn");
        JButton exchangeButton = new JButton("Exchange letters");
        JButton resignButton = new JButton("Resign");

        // add ActionListeners
        moveButton.addActionListener(new MoveActionListener(announcer,
                gameScreen, s, frame, moveButton, exchangeButton));
        endTurnButton.addActionListener(new EndTurnActionListener(announcer,
                gameScreen, s, frame, endTurnButton, moveButton, exchangeButton));
        exchangeButton.addActionListener(new ExchangeLettersActionListener(frame,
                exchangeButton, moveButton, rackLetters, s, gameScreen, announcer));
        resignButton.addActionListener(new ResignActionListener(announcer, gameScreen,
                s, frame, resignButton, endTurnButton));

        // add buttons
        add(moveButton);
        add(endTurnButton);
        add(exchangeButton);
        add(resignButton);
    }
}
