/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.utils;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A custom DocumentFilter that doesn't allow exceeding a certain maximum
 * length.
 *
 * @author panu
 */
public class LengthFilter extends DocumentFilter {

    private final int maxChars;

    /**
     * Creates a new LengthFilter.
     * @param maxChars the maximum length this document can be
     */
    public LengthFilter(int maxChars) {
        this.maxChars = maxChars;
    }

    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
            throws BadLocationException {
        str = str.trim();
        if (fb.getDocument().getLength() + str.length() <= maxChars) {
            super.insertString(fb, offs, str, a);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
            throws BadLocationException {
        str = str.trim();
        if ((fb.getDocument().getLength() + str.length() - length) <= maxChars) {
            super.replace(fb, offs, length, str.trim(), a);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
