/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components;

import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.logic.SkrappleGame;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author panu
 */
public class PlayerName extends JLabel {
    
    private final Font normal;
    private final Font bolded;
    private final Player owner;
    private final SkrappleGame s;
    
    public PlayerName(SkrappleGame s, Player owner, Font normal) {
        this.s = s;
        this.owner = owner;
        this.normal = normal;
        this.bolded = new Font(normal.getName(), Font.BOLD, normal.getSize());
        setPlayerName();
    }
    
    public void updatePlayerName() {
        if (s.getGame().getCurrentPlayer() == owner) {
            setFont(bolded);
        } else {
            setFont(normal);
        }
    }
       
    private void setPlayerName() {
        setText(owner.getPlayerName());
    }
}
