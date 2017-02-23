/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui.listeners;

import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.WildLetter;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.components.LetterTile;
import fi.luupanu.skrapple.ui.components.WildLetterTypeDialog;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author panu
 */
public class LetterTileActionListener implements ActionListener {

    private Border greenBorder = new LineBorder(Color.GREEN);
    private Border whiteBorder = new LineBorder(Color.WHITE);

    private JFrame frame;
    private LetterTile[][] boardSquares;
    private LetterTile[] rackLetters;
    private LetterTile selected;
    private SkrappleGame s;

    public LetterTileActionListener(SkrappleGame s, JFrame frame, LetterTile[][] boardSquares,
            LetterTile[] rackLetters, JButton move, JButton skip, JButton exchange,
            JButton resign) {
        this.s = s;
        this.frame = frame;
        this.boardSquares = boardSquares;
        this.rackLetters = rackLetters;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof LetterTile) {
            // the button the user clicked
            LetterTile b = (LetterTile) e.getSource();

            /* IF THE BUTTON CLICKED IS A RACK BUTTON */
            if (!b.getIsBoardLetter()) {
                // if the rack button has no letter, do nothing
                if (b.getLetter() == null) {
                    return;
                }
                // unselect the previous rack letter (remove visuals)
                if (selected != null) {
                    selected.setSel(false);
                }
                // if we select the same letter twice, set selected to null and return
                if (b == selected) {
                    selected = null;
                } else {
                    // select this rack letter
                    selected = b;
                    b.setSel(true);
                }
                paintValidMoves();
                return;
            }

            /* IF THE BUTTON CLICKED IS A BOARD BUTTON */
            // if the selected button already has a letter, put it back to rack
            if (b.getLetter() != null) {
                Letter let = s.getGame().getLetterQueue().takeLetterFromQueue(b.getLetter());
                // reset a wild letter before placing it back
                if (let.getType() == LetterType.LETTER_WILD) {
                    resetWildLetterType(let);
                }
                s.getGame().getCurrentPlayer().getPlayerRack().addLetter(let);
                for (LetterTile jbl : rackLetters) {
                    if (jbl.getLetter() == null) {
                        jbl.setLetter(let);
                        b.paintBoardIcon(s.getGame().getBoard().getLayout());
                        return;
                    }
                }
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
                if (s.getGame().getLetterQueue().addLetterToQueue(let, b.getCoord(), s.getGame().getBoard())) {
                    s.getGame().getCurrentPlayer().getPlayerRack().takeLetter(let);
                    b.setLetter(let);
                    selected.setSel(false);
                    selected.setLetter(null);
                    selected = null;
                    paintValidMoves();
                }
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

    private void paintValidMoves() {
        Letter fakeLetter = new Letter(LetterType.LETTER_E);
        for (int y = 0; y < boardSquares.length; y++) {
            for (int x = 0; x < boardSquares[y].length; x++) {
                if (selected != null) {
                    if (s.getGame().getLetterQueue().addLetterToQueue(fakeLetter, new Coord(x, y), s.getGame().getBoard())) {
                        boardSquares[x][y].setBorder(greenBorder);
                        s.getGame().getLetterQueue().takeLetterFromQueue(fakeLetter);
                    }
                } else if (boardSquares[x][y].getBorder() == greenBorder) {
                    boardSquares[x][y].setBorder(whiteBorder);
                }
            }
        }
    }
}
