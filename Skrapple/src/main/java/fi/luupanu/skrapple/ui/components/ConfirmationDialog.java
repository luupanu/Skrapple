/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author panu
 */
public abstract class ConfirmationDialog {

    public int askConfirmation(JFrame frame, String msg) {
        String defaultMessage = "Are you sure you want to " + msg.toLowerCase() + "?";
        int response = JOptionPane.showConfirmDialog(frame, defaultMessage, msg + "?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return response;
    }
}
