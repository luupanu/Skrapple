/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.dialogs;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A confirmation dialog. Are you sure?
 *
 * @author panu
 */
public abstract class ConfirmationDialog {

    /**
     * Asks for confirmation using a JOptionPane. Use a message with the first
     * letter capitalized to get the best looks.
     *
     * @param frame the frame to be displayed on
     * @param msg the message to be sure of
     * @return returns the response
     */
    public int askConfirmation(JFrame frame, String msg) {
        String defaultMessage = "Are you sure you want to " + msg.toLowerCase() + "?";
        return JOptionPane.showConfirmDialog(frame, defaultMessage, msg + "?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
}
