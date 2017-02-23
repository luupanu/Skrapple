/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.constants;

import javax.swing.ImageIcon;

/**
 *
 * @author panu
 */
public enum SkrappleImageIcon {
    
    LETTER_TILE(new ImageIcon("lettertile_46x46.png")),
    LETTER_TILE_SELECTED(new ImageIcon("lettertile_sel_46x46.png")),
    BONUS_LETTER_2X(new ImageIcon("bonus_letter_2_46x46.png")),
    BONUS_LETTER_3X(new ImageIcon("bonus_letter_3_46x46.png")),
    BONUS_WORD_2X(new ImageIcon("bonus_word_2_46x46.png")),
    BONUS_WORD_3X(new ImageIcon("bonus_word_3_46x46.png"));
    
    private final ImageIcon icon;
    
    private SkrappleImageIcon(ImageIcon icon) {
        this.icon = icon;
    }
    
    public ImageIcon getIcon() {
        return icon;
    }
}
