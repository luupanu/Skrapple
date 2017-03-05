/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.panels;

import fi.luupanu.skrapple.domain.Player;
import javax.swing.JPanel;

/**
 * A JPanel with an owner, Player.
 * @author panu
 */
public class PlayerPanel extends JPanel {

    private final Player owner;
    
    /**
     * Creates a new PlayerPanel.
     * @param owner the player that owns this panel
     */
    public PlayerPanel(Player owner) {
        this.owner = owner;
    }
    
    public Player getOwner() {
        return owner;
    }
}
