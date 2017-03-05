/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.panels;

import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.listeners.UndoLetterQueueActionListener;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A panel that displays the undo queue button using CardLayout.
 *
 * @author panu
 */
public class UndoQueuePanel extends JPanel {

    private JButton undoQueueButton;
    private CardLayout cards;
    private final SkrappleGame s;
    private final GameScreen gameScreen;

    /**
     * Creates a new UndoQueuePanel.
     * @param s the SkrappleGame
     * @param gameScreen the game screen to be added to
     */
    public UndoQueuePanel(SkrappleGame s, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.s = s;
        createUndoQueuePanel();
    }

    @Override
    public void setVisible(boolean bool) {
        String visibility = "visible";
        if (!bool) {
            visibility = "invisible";
        }
        cards.show(this, visibility);
    }

    private void createUndoQueuePanel() {
        cards = new CardLayout();
        setLayout(cards);

        // set transparent background
        setOpaque(false);

        // add undo queue button
        undoQueueButton = new JButton("Undo move");
        undoQueueButton.setFocusable(false);
        undoQueueButton.addActionListener(new UndoLetterQueueActionListener(s,
                gameScreen, undoQueueButton));

        /*  an empty panel and CardLayout are used instead of setVisible to keep
            the layout from jumping around when the button visibility is being 
            toggled */
        JPanel invisible = new JPanel();

        // set transparent background
        invisible.setOpaque(false);

        // add visible & invisible states to the CardLayout
        cards.addLayoutComponent(undoQueueButton, "visible");
        cards.addLayoutComponent(invisible, "invisible");

        // add components to the panel
        add(undoQueueButton);
        add(invisible);

        // start as invisible
        setVisible(false);
    }
}
