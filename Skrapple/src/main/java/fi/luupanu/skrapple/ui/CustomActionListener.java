/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui;

import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.logic.SkrappleGame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author panu
 */
public class CustomActionListener implements ActionListener {

    private JButton move;
    private JButton skip;
    private JButton exchange;
    private JButton resign;
    private JButtonLetter[][] boardSquares;
    private JButtonLetter[] rackLetters;
    private JButtonLetter selected;
    private SkrappleGame s;

    public CustomActionListener(SkrappleGame s, JButtonLetter[][] boardSquares,
            JButtonLetter[] rackLetters, JButton move, JButton skip, JButton exchange,
            JButton resign) {
        this.s = s;
        this.boardSquares = boardSquares;
        this.rackLetters = rackLetters;
        this.move = move;
        this.skip = skip;
        this.exchange = exchange;
        this.resign = resign;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButtonLetter) {
            // the button the user clicked
            JButtonLetter b = (JButtonLetter) e.getSource();

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
                s.getGame().getCurrentPlayer().getPlayerRack().addLetter(let);
                for (JButtonLetter jbl : rackLetters) {
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
                System.out.println(b.getCoord());
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

    private void paintValidMoves() {
        Letter fakeLetter = new Letter(LetterType.LETTER_E);
        LineBorder greenBorder = new LineBorder(Color.GREEN);
        LineBorder whiteBorder = new LineBorder(Color.WHITE);
        for (int y = 0; y < boardSquares.length; y++) {
            for (int x = 0; x < boardSquares[y].length; x++) {
                if (selected != null) {
                    if (s.getGame().getLetterQueue().addLetterToQueue(fakeLetter, new Coord(x, y), s.getGame().getBoard())) {
                        boardSquares[x][y].setBorder(greenBorder);
                        s.getGame().getLetterQueue().takeLetterFromQueue(fakeLetter);
                    }
                } else {
                    boardSquares[x][y].setBorder(whiteBorder);
                }
            }
        }
    }
}