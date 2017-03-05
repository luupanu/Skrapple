/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui;

import fi.luupanu.skrapple.ui.components.panels.GameScreen;
import fi.luupanu.skrapple.ui.components.panels.MainMenuScreen;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * A giant mess of a graphical UI.
 *
 * @author panu
 */
public class SkrappleGUI implements Runnable {

    private JFrame frame;
    private CardLayout cards;
    private MainMenuScreen mainMenuScreen;

    /**
     * Creates a new SkrappleGUI.
     */
    public SkrappleGUI() {
        UIManager.put("Button.disabledText", new Color(130, 0, 0));
    }

    @Override
    public void run() {
        frame = new JFrame("Skrapple");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setPreferredSize(new Dimension(993, 802));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addComponents();

        frame.pack();
        mainMenuScreen.requestFocusForQuitButton();
        frame.setVisible(true);
    }

    /**
     * Shows a new game screen.
     *
     * @param gameScreen the game screen to be shown
     */
    public void newGameScreen(GameScreen gameScreen) {
        frame.getContentPane().add(gameScreen);
        cards.addLayoutComponent(gameScreen, "game");
        show("game");
    }

    private void addComponents() {
        mainMenuScreen = new MainMenuScreen(this, frame);

        cards = new CardLayout();
        frame.getContentPane().setLayout(cards);

        cards.addLayoutComponent(mainMenuScreen, "menu");

        frame.getContentPane().add(mainMenuScreen);

        show("menu");
    }

    /**
     * Use CardLayout to show this screen.
     *
     * @param screen the string identifier of the screen to be shown
     */
    public void show(String screen) {
        cards.show(frame.getContentPane(), screen);
    }

    /**
     * Main method of the whole program.
     * @param args args 
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SkrappleGUI());
    }
}
