/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.dialogs;

import fi.luupanu.skrapple.constants.LetterType;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A custom dialog that Asks the user for what this wild letter should be.
 *
 * @author panu
 */
public class WildLetterTypeDialog {

    private final String[] selectionValues;

    /**
     * Creates a new WildLetterTypeDialog.
     */
    public WildLetterTypeDialog() {
        selectionValues = new String[LetterType.values().length - 1];
        getAllLetterTypesButWildLetter();
    }

    /**
     * What letter should this wild letter represent?
     * @param frame the frame to be displayed on
     * @return the letter type the user chose
     */
    public LetterType askWildLetterType(JFrame frame) {
        final String msg = "What letter should this wild letter represent?";
        final String title = "Select wild letter type";

        String response = (String) JOptionPane.showInputDialog(frame, msg,
                title, JOptionPane.QUESTION_MESSAGE, null, selectionValues,
                selectionValues[0]);

        for (LetterType type : LetterType.values()) {
            if (type.getName().equals(response)) {
                return type;
            }
        }
        return null;
    }

    private String[] getAllLetterTypesButWildLetter() {
        int i = 0;
        for (LetterType type : LetterType.values()) {
            if (type != LetterType.LETTER_WILD) {
                selectionValues[i] = type.getName();
                i++;
            }
        }
        return selectionValues;
    }
}
