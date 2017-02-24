/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.constants;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * SkrappleImageIcon houses all ImageIcons used by the game.
 *
 * @author panu
 */
public enum SkrappleImageIcon {

    LETTER_TILE("images/lettertile_46x46.png"),
    LETTER_TILE_SELECTED("images/lettertile_sel_46x46.png"),
    BONUS_LETTER_2X("images/bonus_letter_2_46x46.png"),
    BONUS_LETTER_3X("images/bonus_letter_3_46x46.png"),
    BONUS_WORD_2X("images/bonus_word_2_46x46.png"),
    BONUS_WORD_3X("images/bonus_word_3_46x46.png");

    private final ImageIcon icon;

    private SkrappleImageIcon(String filename) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        BufferedImage bf = null;
        try {
            bf = ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(SkrappleImageIcon.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.icon = new ImageIcon(bf);
    }

    public ImageIcon getIcon() {
        return icon;
    }
}
