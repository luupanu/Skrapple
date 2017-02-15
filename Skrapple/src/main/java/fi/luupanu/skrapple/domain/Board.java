/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.SquareType;

/**
 * Board is the main board used by the game. Board has 15x15 = total 225 Squares,
 * including 164 normal, 24 double letter, 17 double word, 12 triple letter and
 * 8 triple word bonus squares.
 * 
 * @author panu
 */
public class Board {

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

    public Board() {
        createDefaultBoard();
    }

    public Square[][] getContents() {
        return board;
    }

    public Square getSquare(int x, int y) {
        if (x >= 0 && x < 15 && y >= 0 && y < 15) {
            return board[y][x];
        }
        return null;
    }

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
        board = new Square[15][15];
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
