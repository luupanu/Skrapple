/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui;

import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.domain.Rack;
import fi.luupanu.skrapple.logic.SkrappleGame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * A giant mess of a graphical UI.
 *
 * @author panu
 */
public class Skrapple implements Runnable {

    private final String[] layout = {
        "W..l...W...l..W",
        ".w...L...L...w.",
        "..w...l.l...w..",
        "l..w...l...w..l",
        "....w.....w....",
        ".L...L...L...L.",
        "..l...l.l...l..",
        "W..l...w...l..W",
        "..l...l.l...l..",
        ".L...L...L...L.",
        "....w.....w....",
        "l..w...l...w..l",
        "..w...l.l...w..",
        ".w...L...L...w.",
        "W..l...W...l..W"};

    private JFrame frame;
    private JButton[][] boardSquares;
    private JButton[] rackLetters;
    private SkrappleGame s;

    public Skrapple() {
        s = new SkrappleGame(new Player("Jussi Pattitussi"), new Player("Kikka Korea"), new Dictionary("kotus-wordlist-fi"));
        boardSquares = new JButton[15][15];
        rackLetters = new JButton[7];
    }

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
        // set layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setLayout(layout);

        // create left-side panel with info
        JPanel sidePanel = new JPanel();
        makeSidePanel(sidePanel);

        // create right-side panel with the board and rack
        JPanel boardPanel = new JPanel();
        makeBoardPanel(boardPanel);

        // add left-side panel
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 0.15;
        frame.add(sidePanel, gbc);

        // add right-side panel
        gbc.gridx = 1;
        gbc.weightx = 0.85;
        frame.add(boardPanel, gbc);
    }

    private void makeBoardPanel(JPanel boardPanel) {
        /*board.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createLoweredBevelBorder()));*/

        // set layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        boardPanel.setLayout(layout);

        // set border
        boardPanel.setBorder(BorderFactory.createBevelBorder(
                BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));

        // create board
        JPanel board = new JPanel(new GridLayout(0, 15));
        makeBoard(board);

        // create rack
        JPanel rack = new JPanel();
        makeRack(rack);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.9375;
        boardPanel.add(board, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.0625;
        boardPanel.add(rack, gbc);
    }

    private void makeBoard(JPanel board) {
        // set background
        board.setBackground(new Color(154, 146, 125));

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int y = 0; y < boardSquares.length; y++) {
            for (int x = 0; x < boardSquares[y].length; x++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                b.setBorder(new LineBorder(Color.WHITE));

                if (layout[y].charAt(x) == 'l') {
                    b.setIcon(new ImageIcon("bonus_letter_2_46x46.png"));
                } else if (layout[y].charAt(x) == 'w') {
                    b.setIcon(new ImageIcon("bonus_word_2_46x46.png"));
                } else if (layout[y].charAt(x) == 'L') {
                    b.setIcon(new ImageIcon("bonus_letter_3_46x46.png"));
                } else if (layout[y].charAt(x) == 'W') {
                    b.setIcon(new ImageIcon("bonus_word_3_46x46.png"));
                }
                boardSquares[x][y] = b;
                board.add(boardSquares[x][y]);
            }
        }
    }

    private void makeRack(JPanel rack) {
        Rack playerOneRack = s.getGame().getPlayerOne().getPlayerRack();
        playerOneRack.refillRack(s.getGame().getLetterBag());
        for (int x = 0; x < rackLetters.length; x++) {
            JButtonLetter b = new JButtonLetter(playerOneRack.getContents().get(x));
            b.setBorder(new LineBorder(Color.BLACK));
            b.setIcon(new ImageIcon("lettertile_46x46.png"));
            b.setFont(new Font("serif", Font.BOLD, 20));
            b.setLayout(new BorderLayout());

            b.setHorizontalTextPosition(JButton.CENTER);
            b.setVerticalTextPosition(JButton.CENTER);

            rackLetters[x] = b;
            rack.add(rackLetters[x]);
        }
    }

    private void makeSidePanel(JPanel sidePanel) {
        // set layout
        GridLayout layout = new GridLayout(3, 1);
        sidePanel.setLayout(layout);

        // set border
        sidePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "<html><b>Skrapple :-D</b></html>",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        // create top-left player infobox
        JPanel sidePanelPlayers = new JPanel();
        makePlayerInfoBox(sidePanelPlayers);

        // create center-left move history infobox
        JPanel sidePanelMoves = new JPanel(new BorderLayout());
        makeMoveHistoryInfoBox(sidePanelMoves);

        // create bottom-left buttons
        JPanel sidePanelButtons = new JPanel();
        makeButtonsInfoBox(sidePanelButtons);

        // add all to side panel
        sidePanel.add(sidePanelPlayers);
        sidePanel.add(sidePanelMoves);
        sidePanel.add(sidePanelButtons);
    }

    private void makePlayerInfoBox(JPanel sidePanelPlayers) {
        // set layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        sidePanelPlayers.setLayout(layout);

        // create player one name
        JLabel playerOne = new JLabel(s.getGame().getPlayerOne().getPlayerName());
        playerOne.setHorizontalAlignment(SwingConstants.RIGHT);

        // create player two name
        JLabel playerTwo = new JLabel(s.getGame().getPlayerTwo().getPlayerName());
        playerTwo.setHorizontalAlignment(SwingConstants.LEFT);

        // bolded test
        Font normal = playerOne.getFont();
        Font bolded = new Font(normal.getName(), Font.BOLD, normal.getSize());
        if (s.getGame().getTurn()) {
            playerOne.setFont(bolded);
            playerTwo.setFont(normal);
        } else {
            playerOne.setFont(normal);
            playerTwo.setFont(bolded);
        }

        // points test
        s.getGame().getPlayerOne().addPoints(150);
        s.getGame().getPlayerTwo().addPoints(100);

        // create player one points
        JLabel playerOnePoints = new JLabel(Integer.toString(s.getGame().getPlayerOne().getPlayerPoints())
                + " points");
        playerOnePoints.setHorizontalAlignment(SwingConstants.LEFT);

        // create player two points
        JLabel playerTwoPoints = new JLabel(Integer.toString(s.getGame().getPlayerTwo().getPlayerPoints())
                + " points");
        playerTwoPoints.setHorizontalAlignment(SwingConstants.RIGHT);

        // add player one name
        gbc.weighty = 0.2;
        gbc.insets = new Insets(0, 5, 0, 5);
        sidePanelPlayers.add(playerOne, gbc);

        // add player two name
        gbc.gridx = 1;
        sidePanelPlayers.add(playerTwo, gbc);

        // add player one points
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 1;
        sidePanelPlayers.add(playerOnePoints, gbc);

        // add player two points
        gbc.gridx = 1;
        sidePanelPlayers.add(playerTwoPoints, gbc);
    }

    private void makeMoveHistoryInfoBox(JPanel sidePanelMoves) {
        // set background & border
        sidePanelMoves.setBackground(Color.LIGHT_GRAY);
        sidePanelMoves.setBorder(BorderFactory.createLoweredBevelBorder());

        // create move history text area
        JTextArea moveHistory = new JTextArea("here be dragons");
        moveHistory.setEnabled(false);

        // create vertical scrollpane
        JScrollPane sp = new JScrollPane(moveHistory,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // add text area with scrollpane
        sidePanelMoves.add(sp);
    }

    private void makeButtonsInfoBox(JPanel sidePanelButtons) {
        // set layout
        GridLayout layout = new GridLayout(4, 1);
        sidePanelButtons.setLayout(layout);

        // create buttons
        JButton move = new JButton("Confirm move");
        JButton skip = new JButton("Skip turn");
        JButton exchange = new JButton("Exchange letters");
        JButton resign = new JButton("Resign");

        // add buttons
        sidePanelButtons.add(move);
        sidePanelButtons.add(skip);
        sidePanelButtons.add(exchange);
        sidePanelButtons.add(resign);
    }
}
