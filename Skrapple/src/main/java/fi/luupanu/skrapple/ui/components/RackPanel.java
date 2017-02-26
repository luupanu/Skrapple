/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components;

import fi.luupanu.skrapple.filemanager.FileReader;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author panu
 */
public class RackPanel extends JPanel {

    private Image image;

    public RackPanel(LayoutManager layout) {
        super(layout);
        loadImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
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
}
