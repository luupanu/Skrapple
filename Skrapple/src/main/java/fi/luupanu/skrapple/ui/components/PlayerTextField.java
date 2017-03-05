/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components;

import fi.luupanu.skrapple.utils.LengthFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

/**
 * PlayerTextField is a custom text field in the main menu that the user can
 * create player names with. If no text is currently set, it displays a gray
 * "ghost text" that is set upon creation. If the text field has text, the
 * background turns green to signal the user.
 *
 * @author panu
 */
public class PlayerTextField extends JTextField implements FocusListener {

    private final String ghostText;

    /**
     * Creates a new PlayerTextField.
     *
     * @param text the ghost text to be displayed if no text is currently set
     * @param max the maximum length of characters that can be written to the
     * text field
     */
    public PlayerTextField(String text, int max) {
        this.ghostText = text;
        createPlayerTextField(max);
    }

    @Override
    public String getText() {
        if (super.getText().equals(ghostText)) {
            return "";
        }
        return super.getText();
    }

    private void createPlayerTextField(int max) {
        AbstractDocument doc = (AbstractDocument) getDocument();
        doc.setDocumentFilter(new LengthFilter(max));
        addFocusListener(this);
        setTransferHandler(null);
        setGhostText();
        setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        setHorizontalAlignment(JTextField.CENTER);
    }

    private void setGhostText() {
        setForeground(Color.GRAY);
        setText(ghostText);
    }

    @Override
    public void focusGained(FocusEvent e) {
        setForeground(Color.BLACK);
        if (getText().isEmpty()) {
            setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            setGhostText();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getText().isEmpty()) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.GREEN);
        }
        super.paintComponent(g);
    }
}
