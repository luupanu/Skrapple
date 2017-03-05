/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 * An info screen that displays the move history.
 *
 * @author panu
 */
public class MoveHistoryPanel extends JPanel {

    private JTextArea moveHistory;

    /**
     * Create a new move history panel.
     *
     * @param font the font to be used for the text displayed
     */
    public MoveHistoryPanel(Font font) {
        createMoveHistoryPanel(font);
    }

    /**
     * Append a message to the JTextArea inside this panel.
     *
     * @param message the message to be appended
     */
    public void append(String message) {
        moveHistory.append(message);
    }

    /**
     * Removes the added new letters x,y,z message so that other player's don't
     * learn of this player's rack content.
     */
    public void removeAddedLettersMessage() {
        moveHistory.setText(moveHistory.getText().
                replaceAll("\n.*Added new letters .* to the rack.", ""));
    }

    private void createMoveHistoryPanel(Font font) {
        setLayout(new BorderLayout());

        // set background & border
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLoweredBevelBorder());

        // create move history text area
        moveHistory = new JTextArea(10, 18);
        moveHistory.setEditable(false);
        moveHistory.setLineWrap(true);
        moveHistory.setWrapStyleWord(true);
        moveHistory.setFont(font);

        // create vertical scrollpane
        JScrollPane sp = new JScrollPane(moveHistory,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // auto-scroll to bottom
        DefaultCaret caret = (DefaultCaret) moveHistory.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // add text area with scrollpane
        add(sp);
    }
}
