/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.panels;

import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.components.PlayerPoints;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * An info panel that shows whose turn it is, player names and player points.
 *
 * @author panu
 */
public class PlayerInfoPanel extends JPanel {

    private List<PlayerPanel> playerPanels;
    private final SkrappleGame s;
    private final Font bolded;
    private final Font normal;

    /**
     * Creates a new PlayerInfoPanel.
     *
     * @param s the SkrappleGame
     * @param normal the font that is used
     * @param bolded the font to highlight the current player
     */
    public PlayerInfoPanel(SkrappleGame s, Font normal, Font bolded) {
        this.bolded = bolded;
        this.normal = normal;
        this.s = s;
        createPlayerInfoPanel();
    }

    /**
     * Highlights the current player by using the bolded font and adding a
     * border.
     */
    public void updatePlayerInfo() {
        for (PlayerPanel panel : playerPanels) {
            if (s.getGame().getCurrentPlayer() == panel.getOwner()) {
                panel.setBorder(BorderFactory.createCompoundBorder(
                        new EtchedBorder(), new EmptyBorder(20, 20, 20, 20)));
                for (Component c : panel.getComponents()) {
                    c.setFont(bolded);
                }
            } else {
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                for (Component c : panel.getComponents()) {
                    c.setFont(normal);
                }
            }
        }
    }

    /**
     * Updates all player points to display current points.
     */
    public void updatePlayerPoints() {
        for (PlayerPanel panel : playerPanels) {
            for (Component c : panel.getComponents()) {
                if (c instanceof PlayerPoints) {
                    PlayerPoints points = (PlayerPoints) c;
                    points.updatePlayerPoints();
                }
            }
        }
    }

    private void createPlayerInfoPanel() {
        // set layout
        GridLayout layout = new GridLayout(2, 2);
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(layout);

        playerPanels = new ArrayList<>();

        // create all player panels, names & points
        for (Player p : s.getGame().getPlayerList()) {
            JLabel name = new JLabel(p.getPlayerName());
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            PlayerPoints points = new PlayerPoints(p);
            points.setAlignmentX(Component.CENTER_ALIGNMENT);

            PlayerPanel panel = new PlayerPanel(p);
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            panel.add(name);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(points);
            playerPanels.add(panel);
        }

        // add player panels
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        for (int i = 0; i < 4; i++) {
            if (i % 2 == 1) {
                gbc.gridx = 0;
            } else {
                gbc.gridx = 1;
            }
            if (i < 2) {
                gbc.gridy = 0;
            } else {
                gbc.gridy = 1;
            }
            if (i < playerPanels.size()) {
                add(playerPanels.get(i));
            } else {
                add(new JPanel());
            }
        }
    }

}
