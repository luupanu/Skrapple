/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui;

import fi.luupanu.skrapple.logic.SkrappleGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author panu
 */
public class Skrapple implements Runnable {

    private JFrame frame;
    private SkrappleGame s;

    @Override
    public void run() {
        frame = new JFrame("Skrapple");
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void addComponents(Container contentPane) {
        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);
        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.BOTH;
        con.weighty = 1;

        JPanel infoBox = new JPanel(new GridLayout(3, 1));
        makeInfoBox(infoBox);
        infoBox.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "<html><b>Skrapple :-D</b></html>",
                TitledBorder.CENTER,
                TitledBorder.TOP));
        con.gridx = 0;
        con.gridy = 0;
        con.weightx = 0.15;
        frame.add(infoBox, con);

        JPanel board = new JPanel();
        board.setBackground(new Color(219, 180, 173));
        /*board.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createLoweredBevelBorder()));*/
        board.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));
        con.gridx = 1;
        con.gridy = 0;
        con.weightx = 0.85;
        frame.add(board, con);
    }

    private void makeInfoBox(JPanel infoBox) {
        JPanel infoBoxPlayers = new JPanel();
        makePlayerInfoBox(infoBoxPlayers);

        JPanel infoBoxMoves = new JPanel(new BorderLayout());
        makeMoveHistoryInfoBox(infoBoxMoves);

        JPanel infoBoxButtons = new JPanel();
        makeButtonsInfoBox(infoBoxButtons);

        infoBox.add(infoBoxPlayers);
        infoBox.add(infoBoxMoves);
        infoBox.add(infoBoxButtons);
    }

    private void makePlayerInfoBox(JPanel infoBoxPlayers) {
        //infoBoxPlayers.add(new JLabel("<html><b>Skrapple :-D</b></html>"));
    }

    private void makeMoveHistoryInfoBox(JPanel infoBoxMoves) {
        infoBoxMoves.setBackground(Color.LIGHT_GRAY);
        infoBoxMoves.setBorder(BorderFactory.createLoweredBevelBorder());
        //infoBoxMoves.add(new JTextArea("    here will be dragons or movehistory", 4, 1));
    }

    private void makeButtonsInfoBox(JPanel infoBoxButtons) {
        GridLayout layout = new GridLayout(4, 1);
        infoBoxButtons.setLayout(layout);

        addCenteredButton("Confirm move", infoBoxButtons);
        addCenteredButton("Skip turn", infoBoxButtons);
        addCenteredButton("Exchange letters", infoBoxButtons);
        addCenteredButton("Resign", infoBoxButtons);
    }

    private void addCenteredButton(String text, Container container) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        container.add(button);
    }
}
