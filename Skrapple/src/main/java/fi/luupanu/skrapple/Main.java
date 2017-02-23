/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple;

import fi.luupanu.skrapple.ui.SkrappleGUI;
import javax.swing.SwingUtilities;

/**
 *
 * @author panu
 */
public class Main {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new SkrappleGUI());
    }
}
