/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components;

import fi.luupanu.skrapple.domain.Player;
import javax.swing.JLabel;

/**
 * Player points is a JLabel with an owner, Player.
 *
 * @author panu
 */
public class PlayerPoints extends JLabel {

    private final Player owner;

    /**
     * Creates a new PlayerPoints.
     * @param owner the player that owns this PlayerPoints
     */
    public PlayerPoints(Player owner) {
        this.owner = owner;
    }

    /**
     * Updates this player's points to show their current score.
     */
    public void updatePlayerPoints() {
        setText(Integer.toString(owner.getPlayerPoints()) + " points");
    }
}
