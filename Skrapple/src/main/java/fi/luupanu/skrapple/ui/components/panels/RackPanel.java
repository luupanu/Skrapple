/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.panels;

import fi.luupanu.skrapple.constants.SkrappleImageIcon;
import fi.luupanu.skrapple.filemanager.FileReader;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.components.LetterTile;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 * A custom JPanel for the area to display rack letters on.
 *
 * @author panu
 */
public class RackPanel extends JPanel {

    private Image image;
    private final LetterTile[] rackLetters;
    private final SkrappleGame s;

    /**
     * Creates a new RackPanel.
     *
     * @param s SkrappleGame
     * @param rackLetters the rack letters to be shown on the panel
     * @param letterActionListener a actionlistener for the lettertiles
     */
    public RackPanel(SkrappleGame s,
            LetterTile[] rackLetters, ActionListener letterActionListener) {
        this.rackLetters = rackLetters;
        this.s = s;
        loadImage();
        createRackPanel(letterActionListener);
    }

    /**
     * Updates the rack panel to show the current player's rack letters.
     */
    public void update() {
        for (int x = 0; x < rackLetters.length; x++) {
            rackLetters[x].setLetter(s.getGame().getCurrentPlayer().
                    getPlayerRack().getContents().get(x));
        }
    }

    /**
     * Hides all the letter tiles so when switching turns the previous player
     * doesn't see them.
     *
     * @param bool true = visible, false = invisible
     */
    public void setLetterTilesVisible(boolean bool) {
        for (LetterTile tile : rackLetters) {
            tile.setVisible(bool);
        }
    }

    /**
     * This method disables or enables all letter tiles.
     *
     * @param bool true to enable letter tiles, false to disable
     */
    public void setLetterTilesEnabled(boolean bool) {
        for (LetterTile tile : rackLetters) {
            tile.setDisabledIcon(tile.getIcon());
            tile.setEnabled(bool);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiled(g);
    }

    private void drawTiled(Graphics g) {
        Dimension d = getSize();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        for (int x = 0; x < d.width; x += width) {
            for (int y = 0; y < d.height; y += height) {
                g.drawImage(image, x, y, null, null);
            }
        }
    }

    private void loadImage() {
        try {
            this.image = new FileReader().readImage("images/rackbg.png");
        } catch (IOException ex) {
            Logger.getLogger(RackPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createRackPanel(ActionListener letterActionListener) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        // add invisible rigid width to balance the Undo Move button
        add(Box.createRigidArea(new Dimension(115, 0)));

        // add letter tiles
        for (int x = 0; x < rackLetters.length; x++) {
            LetterTile b = new LetterTile();
            b.setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());

            b.addActionListener(letterActionListener);

            rackLetters[x] = b;
            add(rackLetters[x]);
        }
        // add some rigid height
        add(Box.createRigidArea(new Dimension(0, 78)));
    }
}
