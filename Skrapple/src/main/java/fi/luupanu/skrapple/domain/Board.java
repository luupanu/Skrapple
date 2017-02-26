/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.SquareType;

/**
 * Board is the main board used by the game. Board has 15x15 = total 225
 * Squares, including 164 normal, 24 double letter, 17 double word, 12 triple
 * letter and 8 triple word bonus squares.
 *
 * @author panu
 */
public class Board {

    private static final int BOARD_MAX_SIZE = 15;
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

    private Square[][] board;

    /**
     * Creates the default Skrapple board based on the layout.
     */
    public Board() {
        createDefaultBoard();
    }

    public Square[][] getContents() {
        return board;
    }
    
    public String[] getLayout() {
        return layout;
    }
    
    public int getBoardMaxSize() {
        return BOARD_MAX_SIZE;
    }

    /**
     * Returns a square based on coordinates if they are valid.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the Square in the coordinates
     */
    public Square getSquare(int x, int y) {
        if (x >= 0 && x < BOARD_MAX_SIZE && y >= 0 && y < BOARD_MAX_SIZE) {
            return board[y][x];
        }
        return null;
    }

    /**
     * This method is useful in evaluating if it is the first word of the game.
     *
     * @return true if board has no letters
     */
    public boolean hasNoLetters() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (getSquare(x, y).hasLetter()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void createDefaultBoard() {
        board = new Square[BOARD_MAX_SIZE][BOARD_MAX_SIZE];
        for (int y = 0; y < board.length; y++) {
            String row = layout[y];
            for (int x = 0; x < board[y].length; x++) {
                SquareType type;
                char c = row.charAt(x);
                if (c == '.') {
                    type = SquareType.NORMAL;
                } else if (c == 'l') {
                    type = SquareType.BONUS_LETTER_2X;
                } else if (c == 'w') {
                    type = SquareType.BONUS_WORD_2X;
                } else if (c == 'L') {
                    type = SquareType.BONUS_LETTER_3X;
                } else {
                    type = SquareType.BONUS_WORD_3X;
                }
                board[y][x] = new Square(type);
            }
        }
    }
}
