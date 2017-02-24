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
public class PlayerPoints extends JLabel {

    private final Font normal;
    private final Font bolded;
    private final SkrappleGame s;
    private final Player owner;

    public PlayerPoints(SkrappleGame s, Player owner, Font normal) {
        this.s = s;
        this.owner = owner;
        this.normal = normal;
        this.bolded = new Font(normal.getName(), Font.BOLD, normal.getSize());
    }

    public void updatePlayerPoints() {
        setText(Integer.toString(owner.getPlayerPoints()) + " points");
    }
}
