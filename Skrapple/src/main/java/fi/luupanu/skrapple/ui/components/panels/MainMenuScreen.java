/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.panels;

import fi.luupanu.skrapple.filemanager.FileReader;
import fi.luupanu.skrapple.ui.SkrappleGUI;
import fi.luupanu.skrapple.ui.components.PlayerTextField;
import fi.luupanu.skrapple.ui.listeners.NewGameActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * A custom main menu screen.
 *
 * @author panu
 */
public class MainMenuScreen extends JPanel {

    private Image background;
    private Image logo;
    private final List<PlayerTextField> playerTextFields;
    private final SkrappleGUI gui;
    private final JFrame frame;
    private JButton quitButton;

    /**
     * Creates a new main menu screen.
     *
     * @param gui the gui that handles displaying either the game or the main
     * menu
     * @param frame JFrame
     */
    public MainMenuScreen(SkrappleGUI gui, JFrame frame) {
        this.gui = gui;
        this.frame = frame;

        playerTextFields = new ArrayList<>();
        loadImages();
        createMainMenu();
    }

    /**
     * Why would you want to request focus for that button?
     */
    public void requestFocusForQuitButton() {
        quitButton.requestFocusInWindow();
    }

    private void createMainMenu() {
        setLayout(new GridLayout(2, 1));
        add(emptyPanel());
        add(buttonsPanel());
    }

    private JPanel buttonsPanel() {
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 0, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        bottomPanel.add(emptyPanel());

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel buttons = new JPanel(layout);
        buttons.setOpaque(false);

        for (int i = 1; i <= 4; i++) {
            playerTextFields.add(new PlayerTextField("Player " + i, 12));
        }

        JButton newGameButton = new JButton("New game");
        newGameButton.addActionListener(new NewGameActionListener(gui, frame,
                playerTextFields));

        quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.1;

        for (int i = 0; i < 4; i++) {
            gbc.gridy = i;
            buttons.add(playerTextFields.get(i), gbc);
        }

        gbc.gridy = 4;
        gbc.weighty = 0.2;
        gbc.insets = new Insets(20, 0, 0, 0);
        buttons.add(newGameButton, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 5;
        buttons.add(quitButton, gbc);
        buttons.setBorder(BorderFactory.createCompoundBorder(
                new EtchedBorder(), new EmptyBorder(20, 20, 20, 20)));

        bottomPanel.add(buttons);
        bottomPanel.add(emptyPanel());

        frame.getRootPane().setDefaultButton(newGameButton);

        return bottomPanel;
    }

    private JPanel emptyPanel() {
        JPanel empty = new JPanel();
        empty.setOpaque(false);
        return empty;
    }

    private void loadImages() {
        FileReader f = new FileReader();
        try {
            this.background = f.readImage("images/menubg.png");
            this.logo = f.readImage("images/skrapplelogo.png");
        } catch (IOException ex) {
            Logger.getLogger(RackPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        int x = (this.getWidth() - logo.getWidth(null)) / 2;
        int y = (this.getHeight() - logo.getHeight(null)) / 10;
        g.drawImage(logo, x, y, null);
    }
}
