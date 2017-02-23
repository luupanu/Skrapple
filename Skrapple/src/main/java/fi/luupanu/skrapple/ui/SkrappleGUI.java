/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui;

import fi.luupanu.skrapple.ui.components.PlayerPoints;
import fi.luupanu.skrapple.ui.components.PlayerName;
import fi.luupanu.skrapple.ui.components.LetterTile;
import fi.luupanu.skrapple.utils.Announcer;
import fi.luupanu.skrapple.ui.listeners.EndTurnActionListener;
import fi.luupanu.skrapple.ui.listeners.ResignActionListener;
import fi.luupanu.skrapple.ui.listeners.MoveActionListener;
import fi.luupanu.skrapple.ui.listeners.LetterTileActionListener;
import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.constants.SkrappleImageIcon;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.components.GUIRack;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 * A giant mess of a graphical UI.
 *
 * @author panu
 */
public class SkrappleGUI implements Runnable, Updateable {

    private Font normal;
    private Font bolded;

    private Announcer announcer;
    private LetterTileActionListener cal;
    private JFrame frame;
    private JButton moveButton;
    private JButton endTurnButton;
    private JButton exchangeButton;
    private JButton resignButton;
    private JButton undoQueueButton;
    private LetterTile[][] boardSquares;
    private LetterTile[] rackLetters;
    private JTextArea infoField;
    private PlayerName playerOneName;
    private PlayerName playerTwoName;
    private PlayerPoints playerOnePoints;
    private PlayerPoints playerTwoPoints;
    private SkrappleGame s;

    public SkrappleGUI() {
        s = new SkrappleGame(new Player("Jussi Pattitussi"), new Player("Kikka Korea"), new Dictionary("kotus-wordlist-fi"));
        boardSquares = new LetterTile[15][15];
        rackLetters = new LetterTile[7];
        normal = new Font("Lucida Grande", Font.PLAIN, 13);
        bolded = new Font(normal.getName(), Font.BOLD, normal.getSize());
        announcer = new Announcer(s);
        UIManager.put("Button.disabledText", new Color(130, 0, 0));
    }

    @Override
    public void run() {
        frame = new JFrame("Skrapple");
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addComponents(frame.getContentPane());
        initializeGameGUI();

        frame.pack();
        frame.setVisible(true);
    }

    private void addComponents(Container contentPane) {
        // create ActionListener
        cal = new LetterTileActionListener(s, frame, boardSquares, rackLetters,
                moveButton, endTurnButton, exchangeButton, resignButton);

        // set layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setLayout(layout);

        // create left-side panel with info
        JPanel sidePanel = new JPanel();
        makeSidePanel(sidePanel);

        // create right-side panel with the board and the rack
        JLayeredPane boardPanel = new JLayeredPane();
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

    private void makeBoardPanel(JLayeredPane boardPanel) {
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
        GUIRack rack = new GUIRack(new FlowLayout(FlowLayout.CENTER, 10, 0));
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
        String[] layout = s.getGame().getBoard().getLayout();
        for (int y = 0; y < boardSquares.length; y++) {
            for (int x = 0; x < boardSquares[y].length; x++) {
                LetterTile b = new LetterTile(true);
                b.setCoord(new Coord(x, y));
                b.setMargin(buttonMargin);

                b.paintBoardIcon(layout);

                b.addActionListener(cal);

                boardSquares[x][y] = b;
                board.add(boardSquares[x][y]);
            }
        }
    }

    private void makeRack(GUIRack rack) {
        for (int x = 0; x < rackLetters.length; x++) {
            LetterTile b = new LetterTile(false);
            b.setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());

            b.addActionListener(cal);

            rackLetters[x] = b;
            rack.add(rackLetters[x]);
        }
        undoQueueButton = new JButton("Undo");
        undoQueueButton.setVisible(false);
        rack.add(undoQueueButton);
        rack.add(Box.createVerticalStrut(80));
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
        makePlayerInfo(sidePanelPlayers);

        // create center-left move history infobox
        JPanel sidePanelMoves = new JPanel(new BorderLayout());
        makeInfoBox(sidePanelMoves);

        // create bottom-left buttons
        JPanel sidePanelButtons = new JPanel();
        makeSidePanelButtons(sidePanelButtons);

        // add all to side panel
        sidePanel.add(sidePanelPlayers);
        sidePanel.add(sidePanelMoves);
        sidePanel.add(sidePanelButtons);
    }

    private void makePlayerInfo(JPanel sidePanelPlayerInfo) {
        // set layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        sidePanelPlayerInfo.setLayout(layout);

        // create player one name
        playerOneName = new PlayerName(s, s.getGame().getPlayerOne(), normal);
        playerOneName.setHorizontalAlignment(SwingConstants.RIGHT);
        playerOneName.setFont(bolded);

        // create player two name
        playerTwoName = new PlayerName(s, s.getGame().getPlayerTwo(), normal);
        playerTwoName.setHorizontalAlignment(SwingConstants.LEFT);
        playerTwoName.setFont(normal);

        // create player one points
        playerOnePoints = new PlayerPoints(s, s.getGame().getPlayerOne(), normal);
        playerOnePoints.setHorizontalAlignment(SwingConstants.LEFT);

        // create player two points
        playerTwoPoints = new PlayerPoints(s, s.getGame().getPlayerTwo(), normal);
        playerTwoPoints.setHorizontalAlignment(SwingConstants.RIGHT);

        // add player one name
        gbc.weighty = 0.2;
        gbc.insets = new Insets(0, 5, 0, 5);
        sidePanelPlayerInfo.add(playerOneName, gbc);

        // add player two name
        gbc.gridx = 1;
        sidePanelPlayerInfo.add(playerTwoName, gbc);

        // add player one points
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridy = 1;
        sidePanelPlayerInfo.add(playerOnePoints, gbc);

        // add player two points
        gbc.gridx = 1;
        sidePanelPlayerInfo.add(playerTwoPoints, gbc);
    }

    private void makeInfoBox(JPanel sidePanelInfo) {
        // set background & border
        sidePanelInfo.setBackground(Color.LIGHT_GRAY);
        sidePanelInfo.setBorder(BorderFactory.createLoweredBevelBorder());

        // create info field text area
        infoField = new JTextArea();
        infoField.setEnabled(false);
        infoField.setLineWrap(true);
        infoField.setWrapStyleWord(true);

        // create vertical scrollpane
        JScrollPane sp = new JScrollPane(infoField,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // add text area with scrollpane
        sidePanelInfo.add(sp);
    }

    private void makeSidePanelButtons(JPanel sidePanelButtons) {
        // set layout
        GridLayout layout = new GridLayout(4, 1);
        sidePanelButtons.setLayout(layout);

        // create buttons
        moveButton = new JButton("Confirm move");
        endTurnButton = new JButton("End turn");
        exchangeButton = new JButton("Exchange letters");
        resignButton = new JButton("Resign");

        // add ActionListeners
        moveButton.addActionListener(new MoveActionListener(announcer, this, s, frame,
                moveButton, exchangeButton));
        endTurnButton.addActionListener(new EndTurnActionListener(announcer, this, s,
                frame, endTurnButton, moveButton, exchangeButton));
        exchangeButton.addActionListener(cal);
        resignButton.addActionListener(new ResignActionListener(s, frame, resignButton,
        playerOneName, playerTwoName, playerOnePoints, playerTwoPoints));

        // add buttons
        sidePanelButtons.add(moveButton);
        sidePanelButtons.add(endTurnButton);
        sidePanelButtons.add(exchangeButton);
        sidePanelButtons.add(resignButton);
    }

    public void updateBoardLettersColour() {
        for (LetterTile[] jbls : boardSquares) {
            for (LetterTile jbl : jbls) {
                if (jbl.getLetter() != null) {
                    jbl.setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());
                }
            }
        }
    }

    @Override
    public void update(String message) {
        infoField.append(message);
    }

    public void updateSetLetterTilesEnabled(boolean bool) {
        for (LetterTile jbl : rackLetters) {
            jbl.setDisabledIcon(jbl.getIcon());
            jbl.setEnabled(bool);
        }
        for (LetterTile[] jbls : boardSquares) {
            for (LetterTile jbl : jbls) {
                jbl.setDisabledIcon(jbl.getIcon());
                jbl.setEnabled(bool);
            }
        }
    }

    public void updatePlayerNames() {
        playerOneName.updatePlayerName();
        playerTwoName.updatePlayerName();
    }

    public void updatePlayerPoints() {
        playerOnePoints.updatePlayerPoints();
        playerTwoPoints.updatePlayerPoints();
    }

    public void updatePlayerRack() {
        for (int x = 0; x < rackLetters.length; x++) {
            rackLetters[x].setLetter(s.getGame().getCurrentPlayer().
                    getPlayerRack().getContents().get(x));
        }
    }

    public void updateRemoveAddedLettersMessage() {
        infoField.setText(infoField.getText().replaceAll(".*Added new letters .* to the rack.", ""));
    }
    
    private void initializeGameGUI() {
        update(announcer.announce(Announcement.WELCOME_MESSAGE));
        update(announcer.announce(Announcement.TURN_START_MESSAGE));
        updatePlayerPoints();
        updatePlayerRack();
    }
}
