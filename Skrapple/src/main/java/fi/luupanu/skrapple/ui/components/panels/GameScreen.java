/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.panels;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.SkrappleGUI;
import fi.luupanu.skrapple.ui.Updateable;
import fi.luupanu.skrapple.ui.components.BoardTile;
import fi.luupanu.skrapple.ui.components.LetterTile;
import fi.luupanu.skrapple.ui.listeners.LetterTileActionListener;
import fi.luupanu.skrapple.utils.Announcer;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 * The main game screen.
 *
 * @author panu
 */
public class GameScreen extends JPanel implements Updateable {

    private final Font normal;
    private final Font bolded;

    private final Announcer announcer;
    private final BoardTile[][] boardSquares;
    private final LetterTile[] rackLetters;

    private LetterTileActionListener letterActionListener;

    private ActionButtonsPanel actionButtonsPanel;
    private BoardPanel boardPanel;
    private MoveHistoryPanel moveHistoryPanel;
    private PlayerInfoPanel playerInfoPanel;
    private RackPanel rackPanel;
    private JPanel undoQueuePanel;

    private final SkrappleGame s;
    private final JFrame frame;
    private final SkrappleGUI gui;

    /**
     * Creates a new instance of GameScreen.
     *
     * @param gui the gui handling the switch between game and main menu screens
     * @param s SkrappleGame
     * @param frame JFrame
     */
    public GameScreen(SkrappleGUI gui, SkrappleGame s, JFrame frame) {
        this.frame = frame;
        this.gui = gui;
        this.s = s;

        int boardMax = s.getGame().getBoard().getBoardMaxSize();
        int rackMax = s.getGame().getCurrentPlayer().getPlayerRack().getRackMaxSize();

        boardSquares = new BoardTile[boardMax][boardMax];
        rackLetters = new LetterTile[rackMax];
        normal = new Font("Lucida Grande", Font.PLAIN, 14);
        bolded = new Font(normal.getName(), Font.BOLD, normal.getSize());
        announcer = new Announcer(s);

        addComponents();
        initializeGameWindow();
    }

    private void addComponents() {
        // create LetterTileActionListener
        letterActionListener = new LetterTileActionListener(this, s, frame,
                rackLetters);

        // set layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(layout);

        // create left-side panel with info
        JPanel infoArea = new JPanel();
        createInfoArea(infoArea);

        // create right-side panel with the board and the rack
        JPanel playArea = new JPanel();
        createPlayArea(playArea);

        // add left-side panel
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 0.5;
        add(infoArea, gbc);

        // add right-side panel
        gbc.gridx = 1;
        add(playArea, gbc);
    }

    private void createPlayArea(JPanel playArea) {
        // set layout
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        playArea.setLayout(layout);

        // set border
        playArea.setBorder(BorderFactory.createBevelBorder(
                BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.DARK_GRAY));

        // create board
        boardPanel = new BoardPanel(s, boardSquares, letterActionListener);

        // create rack
        rackPanel = new RackPanel(s, rackLetters, letterActionListener);

        // create undo queue button & panel
        undoQueuePanel = new UndoQueuePanel(s, this);
        // add to rackPanel
        rackPanel.add(undoQueuePanel);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.9375;
        playArea.add(boardPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.0625;
        playArea.add(rackPanel, gbc);
    }

    private void createInfoArea(JPanel sidePanel) {
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
        playerInfoPanel = new PlayerInfoPanel(s, normal, bolded);

        // create center-left move history infobox
        moveHistoryPanel = new MoveHistoryPanel(normal);

        // create bottom-left buttons
        actionButtonsPanel = new ActionButtonsPanel(announcer, this, s, frame,
                rackLetters);

        // add all to side panel
        sidePanel.add(playerInfoPanel);
        sidePanel.add(moveHistoryPanel);
        sidePanel.add(actionButtonsPanel);
    }

    /**
     * Sets board letters unclickable.
     */
    public void setBoardLettersUnClickable() {
        boardPanel.setBoardLettersUnClickable();
    }

    /**
     * Updates the move history text area with a message.
     *
     * @param message
     */
    @Override
    public void update(String message) {
        moveHistoryPanel.append(message);
    }

    /**
     * Removes all not-placed letter tiles from the screen after a turn ends.
     */
    public void updateHangingLetterTiles() {
        boardPanel.updateHangingLetterTiles();
    }

    /**
     * Sets the enabled status of all LetterTiles.
     *
     * @param bool true = enabled, false = disabled
     */
    public void setLetterTilesEnabled(boolean bool) {
        rackPanel.setLetterTilesEnabled(bool);
        boardPanel.setLetterTilesEnabled(bool);
    }

    /**
     * Emboldens and creates a border for the current player.
     */
    public void updatePlayerInfo() {
        playerInfoPanel.updatePlayerInfo();
    }

    /**
     * Updates the text of all player points to their current points.
     */
    public void updatePlayerPoints() {
        playerInfoPanel.updatePlayerPoints();
    }

    /**
     * Reloads the rack to display the contents of the current player's rack.
     */
    public void updatePlayerRack() {
        rackPanel.update();
    }

    /**
     * Show or hide rack letter tiles.
     *
     * @param bool true = visible, false = invisible
     */
    public void setRackLetterTilesVisible(boolean bool) {
        rackPanel.setLetterTilesVisible(bool);
    }

    /**
     * Removes the "Added x,y,z letters" message so that the next player doesn't
     * learn the previous player's rack contents.
     */
    public void removeAddedLettersMessage() {
        moveHistoryPanel.removeAddedLettersMessage();
    }

    /**
     * Sets the visibility of the undo queue button.
     *
     * @param bool true = visible, false = invisible
     */
    public void setUndoQueueButtonVisible(boolean bool) {
        undoQueuePanel.setVisible(bool);
    }

    /**
     * Goes back to the main menu.
     */
    public void showMainMenu() {
        gui.show("menu");
    }

    /**
     * Updates the default action when the user presses enter. Either confirm
     * move or end the turn.
     */
    public void updateDefaultButton() {
        if (actionButtonsPanel.getMoveButton().isEnabled()) {
            frame.getRootPane().setDefaultButton(actionButtonsPanel.getMoveButton());
        } else {
            frame.getRootPane().setDefaultButton(actionButtonsPanel.getEndTurnButton());
        }
    }

    private void initializeGameWindow() {
        update(announcer.announce(Announcement.WELCOME_MESSAGE));
        update(announcer.announce(Announcement.TURN_START_MESSAGE));
        updatePlayerInfo();
        updatePlayerPoints();
        updatePlayerRack();
        updateDefaultButton();
    }
}
