/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.WildLetter;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.components.BoardTile;
import fi.luupanu.skrapple.ui.components.LetterTile;
import fi.luupanu.skrapple.ui.components.dialogs.WildLetterTypeDialog;
import fi.luupanu.skrapple.ui.components.panels.GameScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * A custom action listener for all letter tiles.
 *
 * @author panu
 */
public class LetterTileActionListener implements ActionListener {

    private final JFrame frame;
    private final LetterTile[] rackLetters;
    private LetterTile selected;
    private final SkrappleGame s;
    private final GameScreen gameScreen;

    /**
     * Creates a new LetterTileActionListener.
     * @param gameScreen the game screen
     * @param s SkrappleGame
     * @param frame the frame to display dialogs on
     * @param rackLetters the rack letters
     */
    public LetterTileActionListener(GameScreen gameScreen, SkrappleGame s,
            JFrame frame, LetterTile[] rackLetters) {
        this.gameScreen = gameScreen;
        this.s = s;
        this.frame = frame;
        this.rackLetters = rackLetters;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (s.getGame().getGameState() != GameState.PLAYING) {
            return;
        }

        if (e.getSource() instanceof LetterTile) {
            LetterTile tile = (LetterTile) e.getSource();

            /// if the lettertile clicked is a board tile
            if (tile instanceof BoardTile) {
                if (!((BoardTile) tile).isUnClickable()) {
                    handleBoardTilePress(tile);
                }
                return;
            }
            // if the lettertile clicked is a rack tile
            handleRackTilePress(tile);
        }
    }

    private void handleRackTilePress(LetterTile tile) {
        // if the rack button has no letter, do nothing
        if (tile.getLetter() == null) {
            return;
        }
        // unselect the previous rack letter (remove visuals)
        if (selected != null) {
            selected.paintLetterTile(false);
        }
        // if we select the same letter twice, set selected to null and return
        if (tile == selected) {
            selected = null;
        } else {
            // select the clicked rack letter
            selected = tile;
            tile.paintLetterTile(true);
        }
        paintValidMoves();
    }

    private void handleBoardTilePress(LetterTile tile) {
        // if the clicked tile already has a letter, put it back to rack
        if (tile.getLetter() != null) {
            putLetterTileBackToRack(tile);
        }

        // if a rack letter has been selected, try to place it in the board
        if (selected != null) {
            Letter let = selected.getLetter();
            if (let.getType() == LetterType.LETTER_WILD) {
                // if the user does not provide a wild letter type, do nothing
                if (!askWildLetterType(let)) {
                    return;
                }
            }
            if (s.getGame().getLetterQueue().addLetterToQueue(let,
                    ((BoardTile) tile).getCoord(), s.getGame().getBoard())) {
                s.getGame().getCurrentPlayer().getPlayerRack().takeLetter(let);
                tile.setLetter(let);
                selected.paintLetterTile(false);
                tile.paintLetterTile(false);
                selected.setLetter(null);
                selected = null;
                gameScreen.setUndoQueueButtonVisible(true);
                paintValidMoves();
            }
        }
    }

    private void putLetterTileBackToRack(LetterTile tile) {
        Letter let = s.getGame().getLetterQueue().takeLetterFromQueue(tile.getLetter());
        // reset a wild letter before placing it back
        if (let.getType() == LetterType.LETTER_WILD) {
            resetWildLetterType(let);
        }
        s.getGame().getCurrentPlayer().getPlayerRack().addLetter(let);
        if (s.getGame().getLetterQueue().getContents().isEmpty()) {
            gameScreen.setUndoQueueButtonVisible(false);
        }
        for (LetterTile rackTile : rackLetters) {
            if (rackTile.getLetter() == null) {
                rackTile.setLetter(let);
                ((BoardTile) tile).paintBoardIcon(s.getGame().getBoard().getLayout());
                break;
            }
        }
    }

    private boolean askWildLetterType(Letter let) {
        WildLetter wLet = (WildLetter) let;
        WildLetterTypeDialog wltd = new WildLetterTypeDialog();
        LetterType type = wltd.askWildLetterType(frame);
        if (type == null) {
            return false;
        }
        wLet.setWildLetterType(type);
        return true;
    }

    private void resetWildLetterType(Letter let) {
        WildLetter wLet = (WildLetter) let;
        wLet.setWildLetterType(null);
    }

    /*  painting of borders for so many objects does not work that well, things
        don't get drawn, still searching for a better solution */
    private void paintValidMoves() {
//        Letter fakeLetter = new Letter(LetterType.LETTER_E);
//        for (int y = 0; y < boardSquares.length; y++) {
//            for (int x = 0; x < boardSquares[y].length; x++) {
//                if (selected != null) {
//                    if (s.getGame().getLetterQueue().addLetterToQueue(fakeLetter, new Coord(x, y), s.getGame().getBoard())) {
//                        boardSquares[x][y].setBorder(greenBorder);
//                        s.getGame().getLetterQueue().takeLetterFromQueue(fakeLetter);
//                    }
//                } else if (boardSquares[x][y].getBorder() == greenBorder) {
//                    boardSquares[x][y].setBorder(whiteBorder);
//                }
//            }
//        }
    }
}
