/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.components.panels;

import fi.luupanu.skrapple.constants.SkrappleImageIcon;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.components.BoardTile;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 * A custom JPanel that displays the game board.
 *
 * @author panu
 */
public class BoardPanel extends JPanel {

    private final BoardTile[][] boardSquares;
    private final SkrappleGame s;

    /**
     * Creates a new BoardPanel.
     * @param s the SkrappleGame
     * @param boardSquares a double array of board tiles
     * @param letterActionListener the action listener to be used for the tiles
     */
    public BoardPanel(SkrappleGame s, BoardTile[][] boardSquares,
            ActionListener letterActionListener) {
        this.boardSquares = boardSquares;
        this.s = s;
        createBoardPanel(letterActionListener);
    }

    /**
     * After a move has been made, this method sets the correct icon to all new
     * letter tiles and makes them unclickable.
     */
    public void setBoardLettersUnClickable() {
        for (BoardTile[] tiles : boardSquares) {
            for (BoardTile tile : tiles) {
                if (tile.getLetter() != null) {
                    tile.setIcon(SkrappleImageIcon.LETTER_TILE.getIcon());
                    tile.setUnClickable();
                }
            }
        }
    }

    /**
     * After a turn ends, this method removes the letter icon from all board
     * squares where a letter was not placed.
     */
    public void updateHangingLetterTiles() {
        for (BoardTile[] tiles : boardSquares) {
            for (BoardTile tile : tiles) {
                if (tile.getIcon() == SkrappleImageIcon.LETTER_TILE_SELECTED.getIcon()) {
                    tile.paintBoardIcon(s.getGame().getBoard().getLayout());
                }
            }
        }
    }

    /**
     * This method disables or enables all board tiles.
     *
     * @param bool true to enable board tiles, false to disable
     */
    public void setLetterTilesEnabled(boolean bool) {
        for (BoardTile[] tiles : boardSquares) {
            for (BoardTile tile : tiles) {
                tile.setDisabledIcon(tile.getIcon());
                tile.setEnabled(bool);
            }
        }
    }

    private void createBoardPanel(ActionListener letterActionListener) {
        setLayout(new GridLayout(0, s.getGame().getBoard().getBoardMaxSize()));

        // set background
        setBackground(new Color(154, 146, 125));

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        String[] layout = s.getGame().getBoard().getLayout();
        for (int y = 0; y < boardSquares.length; y++) {
            for (int x = 0; x < boardSquares[y].length; x++) {
                BoardTile b = new BoardTile();
                b.setCoord(new Coord(x, y));
                b.setMargin(buttonMargin);

                b.paintBoardIcon(layout);

                b.addActionListener(letterActionListener);

                boardSquares[x][y] = b;
                add(boardSquares[x][y]);
            }
        }
    }

}
