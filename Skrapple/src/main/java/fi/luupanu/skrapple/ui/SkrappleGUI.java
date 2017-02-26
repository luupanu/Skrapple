/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui;

import fi.luupanu.skrapple.ui.components.PlayerPoints;
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
import fi.luupanu.skrapple.ui.components.PlayerPanel;
import fi.luupanu.skrapple.ui.components.RackPanel;
import fi.luupanu.skrapple.ui.listeners.UndoLetterQueueActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

/**
 * A giant mess of a graphical UI.
 *
 * @author panu
 */
public class SkrappleGUI implements Runnable, Updateable {

    private final int BOARD_MAX_SIZE;
    private final int RACK_MAX_SIZE;

    private final Font normal;
    private final Font bolded;

    private final Announcer announcer;
    private LetterTileActionListener letterActionListener;
    private JFrame frame;
    private JButton moveButton;
    private JButton endTurnButton;
    private JButton exchangeButton;
    private JButton resignButton;
    private JButton undoQueueButton;
    private JPanel undoQueuePanel;
    private CardLayout cards;
    private final LetterTile[][] boardSquares;
    private final LetterTile[] rackLetters;
    private JTextArea infoField;
    private List<PlayerPanel> playerPanels;
    private SkrappleGame s;
    private RackPanel rackPanel;

    public SkrappleGUI(SkrappleGame s) {
        this.s = s;

        BOARD_MAX_SIZE = s.getGame().getBoard().getBoardMaxSize();
        RACK_MAX_SIZE = s.getGame().getCurrentPlayer().getPlayerRack().getRackMaxSize();

        boardSquares = new LetterTile[BOARD_MAX_SIZE][BOARD_MAX_SIZE];
        rackLetters = new LetterTile[RACK_MAX_SIZE];
        normal = new Font("Lucida Grande", Font.PLAIN, 14);
        bolded = new Font(normal.getName(), Font.BOLD, normal.getSize());
        announcer = new Announcer(s);
        UIManager.put("Button.disabledText", new Color(130, 0, 0));
    }

    @Override
    public void run() {
        frame = new JFrame("Skrapple");
        //frame.setPreferredSize(new Dimension(1008, 826));
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addComponents(frame.getContentPane());
        initializeGameGUI();

        frame.pack();
        frame.setVisible(true);
    }

    private void addComponents(Container contentPane) {
        // create LetterTileActionListener
        letterActionListener = new LetterTileActionListener(this, s, frame,
                boardSquares, rackLetters, undoQueueButton);

        // set layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setLayout(layout);

        // create left-side panel with info
        JPanel sidePanel = new JPanel();
        makeSidePanel(sidePanel);

        // create right-side panel with the board and the rack
        JPanel boardPanel = new JPanel();
        makeBoardPanel(boardPanel);

        // add left-side panel
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 0.5;
        frame.add(sidePanel, gbc);

        // add right-side panel
        gbc.gridx = 1;
        frame.add(boardPanel, gbc);
    }

    private void makeBoardPanel(JPanel boardPanel) {
        // set layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        boardPanel.setLayout(layout);

        // set border
        boardPanel.setBorder(BorderFactory.createBevelBorder(
                BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));

        // create board
        JPanel board = new JPanel(new GridLayout(0, BOARD_MAX_SIZE));
        makeBoard(board);

        // create rack
        rackPanel = new RackPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        makeRack(rackPanel);

        // create undo queue button & panel
        makeUndoQueuePanel(rackPanel);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.9375;
        boardPanel.add(board, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.0625;
        boardPanel.add(rackPanel, gbc);
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

                b.addActionListener(letterActionListener);

                boardSquares[x][y] = b;
                board.add(boardSquares[x][y]);
            }
        }
    }

    private void makeRack(RackPanel rackPanel) {
        // add invisible area to balance Undo Move button
        rackPanel.add(Box.createRigidArea(new Dimension(115, 0)));
        for (int x = 0; x < rackLetters.length; x++) {
            LetterTile b = new LetterTile(false);
            b.setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());

            b.addActionListener(letterActionListener);

            rackLetters[x] = b;
            rackPanel.add(rackLetters[x]);
        }
        rackPanel.add(Box.createRigidArea(new Dimension(0, 78)));
    }

    private void makeUndoQueuePanel(RackPanel rackPanel) {
        // set layout
        undoQueuePanel = new JPanel();
        cards = new CardLayout();
        undoQueuePanel.setLayout(cards);

        // set transparent background
        undoQueuePanel.setBackground(new Color(255, 255, 255, 0));

        // add undo queue button
        undoQueueButton = new JButton("Undo move");
        undoQueueButton.setFocusable(false);
        undoQueueButton.addActionListener(new UndoLetterQueueActionListener(s,
                this, rackPanel, undoQueueButton));

        /*  an empty panel and CardLayout are used instead of setVisible to keep
            the layout from jumping around when the button visibility is being 
            toggled */
        JPanel invisible = new JPanel();

        // set transparent background
        invisible.setBackground(new Color(255, 255, 255, 0));

        // add visible & invisible states to the CardLayout
        cards.addLayoutComponent(undoQueueButton, "visible");
        cards.addLayoutComponent(invisible, "invisible");

        // add components to the panel
        undoQueuePanel.add(undoQueueButton);
        undoQueuePanel.add(invisible);

        // start as invisible
        cards.show(undoQueuePanel, "invisible");

        // add to rackPanel
        rackPanel.add(undoQueuePanel);
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
        makePlayerInfoPanel(sidePanelPlayers);

        // create center-left move history infobox
        JPanel sidePanelInfoBox = new JPanel(new BorderLayout());
        makeInfoBox(sidePanelInfoBox);

        // create bottom-left buttons
        JPanel sidePanelButtons = new JPanel();
        makeSidePanelButtons(sidePanelButtons);

        // add all to side panel
        sidePanel.add(sidePanelPlayers);
        sidePanel.add(sidePanelInfoBox);
        sidePanel.add(sidePanelButtons);
    }

    private void makePlayerInfoPanel(JPanel sidePanelPlayers) {
        // set layout
        GridLayout layout = new GridLayout(2, 2);
        GridBagConstraints gbc = new GridBagConstraints();
        sidePanelPlayers.setLayout(layout);

        playerPanels = new ArrayList<>();

        // create all player panels, names & points
        for (Player p : s.getGame().getPlayerList()) {
            JLabel name = new JLabel(p.getPlayerName());
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            PlayerPoints points = new PlayerPoints(p);
            points.setAlignmentX(Component.CENTER_ALIGNMENT);

            PlayerPanel panel = new PlayerPanel(p);
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            panel.add(name);
            panel.add(Box.createRigidArea(new Dimension(0, 20)));
            panel.add(points);
            playerPanels.add(panel);
        }

        // add player panels
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        //gbc.insets = new Insets(0, 10, 0, 10);
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 1) {
                gbc.gridx = 0;
            } else {
                gbc.gridx = 1;
            }
            if (i < 2) {
                gbc.gridy = 0;
            } else {
                gbc.gridy = 1;
            }
            if (i < playerPanels.size()) {
                sidePanelPlayers.add(playerPanels.get(i));
            } else {
                sidePanelPlayers.add(new JPanel());
            }
        }
    }

    private void makeInfoBox(JPanel sidePanelInfoBox) {
        // set background & border
        sidePanelInfoBox.setBackground(Color.LIGHT_GRAY);
        sidePanelInfoBox.setBorder(BorderFactory.createLoweredBevelBorder());

        // create info field text area
        infoField = new JTextArea(10, 18);
        infoField.setEditable(false);
        infoField.setLineWrap(true);
        infoField.setWrapStyleWord(true);
        infoField.setFont(normal);

        // create vertical scrollpane
        JScrollPane sp = new JScrollPane(infoField,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // auto-scroll to bottom
        DefaultCaret caret = (DefaultCaret) infoField.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // add text area with scrollpane
        sidePanelInfoBox.add(sp);
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
        moveButton.addActionListener(new MoveActionListener(announcer, this, s,
                frame, moveButton, exchangeButton));
        endTurnButton.addActionListener(new EndTurnActionListener(announcer,
                this, s, frame, endTurnButton, moveButton, exchangeButton));
        exchangeButton.addActionListener(letterActionListener);
        resignButton.addActionListener(new ResignActionListener(announcer, this,
                s, frame, resignButton, endTurnButton));

        // add buttons
        sidePanelButtons.add(moveButton);
        sidePanelButtons.add(endTurnButton);
        sidePanelButtons.add(exchangeButton);
        sidePanelButtons.add(resignButton);
    }

    public void updateBoardLettersIcon() {
        for (LetterTile[] tiles : boardSquares) {
            for (LetterTile tile : tiles) {
                if (tile.getLetter() != null) {
                    tile.setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());
                    tile.setUnClickable();
                }
            }
        }
    }

    @Override
    public void update(String message) {
        infoField.append(message);
    }

    public void updateHangingLetterTiles() {
        for (LetterTile[] tiles : boardSquares) {
            for (LetterTile tile : tiles) {
                if (tile.getIcon() == SkrappleImageIcon.LETTER_TILE_SELECTED.getIcon()) {
                    tile.paintBoardIcon(s.getGame().getBoard().getLayout());
                }
            }
        }
    }

    public void setLetterTilesEnabled(boolean bool) {
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

    public void updatePlayerInfo() {
        for (PlayerPanel panel : playerPanels) {
            if (s.getGame().getCurrentPlayer() == panel.getOwner()) {
                panel.setBorder(BorderFactory.createCompoundBorder(
                        new EtchedBorder(), new EmptyBorder(20, 20, 20, 20)));
                for (Component c : panel.getComponents()) {
                    c.setFont(bolded);
                }
            } else {
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                for (Component c : panel.getComponents()) {
                    c.setFont(normal);
                }
            }
        }
    }

    public void updatePlayerPoints() {
        for (PlayerPanel panel : playerPanels) {
            for (Component c : panel.getComponents()) {
                if (c instanceof PlayerPoints) {
                    PlayerPoints points = (PlayerPoints) c;
                    points.updatePlayerPoints();
                }
            }
        }
    }

    public void updatePlayerRack() {
        for (int x = 0; x < rackLetters.length; x++) {
            rackLetters[x].setLetter(s.getGame().getCurrentPlayer().
                    getPlayerRack().getContents().get(x));
        }
    }

    public void removeAddedLettersMessage() {
        infoField.setText(infoField.getText().replaceAll("\n.*Added new letters .* to the rack.", ""));
    }

    public void setUndoQueueButtonVisible(boolean bool) {
        String visibility = "visible";
        if (!bool) {
            visibility = "invisible";
        }
        cards.show(undoQueuePanel, visibility);
        rackPanel.repaint(); // fix a bug where undoQueuePanel background doesn't keep its transparency
    }

    private void initializeGameGUI() {
        update(announcer.announce(Announcement.WELCOME_MESSAGE));
        update(announcer.announce(Announcement.TURN_START_MESSAGE));
        updatePlayerInfo();
        updatePlayerPoints();
        updatePlayerRack();
    }

    public static void main(String[] args) {
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        Player p3 = new Player("Player 3");
        Player p4 = new Player("Player 4");
        Dictionary d = null;
        try {
            d = new Dictionary("kotus-wordlist-fi");
        } catch (IOException ex) {
            Logger.getLogger(SkrappleGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        SkrappleGame s = new SkrappleGame(p1, p2, p3, p4, d);

        SwingUtilities.invokeLater(new SkrappleGUI(s));
    }
}
