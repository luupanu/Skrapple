/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author panu
 */
public class LetterQueue {

    // TO-DO: split this class into two or more parts...
    
    /*  LetterQueue has a collection of letter/coordinate pairs to be added to 
        the board. This class helps the user so that it's (almost) impossible to
        make invalid letter placements. The whole queue can then be canceled so
        that the board remains unchanged if the user makes an invalid move. */
    
    private Map<Letter, Point> lettersToBePlaced;
    private boolean direction; // true = horizontal, false = vertical

    public LetterQueue() {
        lettersToBePlaced = new HashMap<>();
    }
    
    public Map<Letter, Point> getLetterQueue() {
        return lettersToBePlaced;
    }

    public boolean addLetterToQueue(Letter let, Point p, Board board) {
        if (!isValidCoordinate(p.x, p.y)) {
            return false;
        }

        Square s = board.getContents()[p.x][p.y];
        if (s.hasLetter()) {
            return false;
        }

        if (boardHasNoLetters(board) && lettersToBePlaced.isEmpty()) {
            // the first letter of the first word must be at x = 7 OR y = 7
            if (p.x == 7 || p.y == 7) {
                lettersToBePlaced.put(let, p);
                return true;
            }
            return false;
        }

        if (lettersToBePlaced.isEmpty()) {
            if (checkFirstLetterCorrectPlacement(p, board)) {
                lettersToBePlaced.put(let, p);
                return true;
            }
            return false;
        }

        if (lettersToBePlaced.size() == 1) {
            checkSecondLetterCorrectPlacement(p, board);
        }
        
        return checkRemainingLettersCorrectPlacement(p, board, direction);
    }

    private boolean checkFirstLetterCorrectPlacement(Point p, Board board) {
        /*  First letter of the word must be on the same row or column as an
            existing letter on the board, or on the neighbouring row/column */
        return rowOrNeighbouringRowHasLetter(p.y, board) ||
                columnOrNeighbouringColumnHasLetter(p.x, board);
    }

    private boolean checkSecondLetterCorrectPlacement(Point p, Board board) {
        /*  Second letter of the word must be on the same row or column as the
            first letter. The second letter gives us the direction for all the 
            rest of the letters (a player can either place a word vertically or
            horizontally on the board, not both at the same time) */
        for (Point firstLetterCoordinate : lettersToBePlaced.values()) {
            if (firstLetterCoordinate.x == p.x) {
                direction = false; // vertical
                return true;
            } else if (firstLetterCoordinate.y == p.y) {
                direction = true; // horizontal
                return true;
            }
        }
        return false;
    }

    private boolean boardHasNoLetters(Board board) {
        Square[][] b = board.getContents();

        for (int y = 0; y < b.length; y++) {
            for (int x = 0; x < b[y].length; x++) {
                Square s = b[x][y];
                if (s.hasLetter()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < 15 && y >= 0 && y < 15;
    }

    private boolean rowOrNeighbouringRowHasLetter(int row, Board board) {
        Square[][] b = board.getContents();

        for (int y = row - 1; y <= row + 1; y++) {
            for (int x = 0; x < 15; x++) {
                if (!isValidCoordinate(x, y)) {
                    continue;
                }
                Square s = b[x][y];
                if (s.hasLetter()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean columnOrNeighbouringColumnHasLetter(int col, Board board) {
        Square[][] b = board.getContents();

        for (int y = 0; y < 15; y++) {
            for (int x = col - 1; x < col + 1; x++) {
                if (!isValidCoordinate(x, y)) {
                    continue;
                }
                Square s = b[x][y];
                if (s.hasLetter()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkRemainingLettersCorrectPlacement(Point p, Board board, boolean direction) {        
        for (Point coordinate : lettersToBePlaced.values()) {
            if (p.y == coordinate.y && p.x == coordinate.x) {
                return false; // don't try to add exact same coordinates
            }
            
            if (direction) { // horizontal
                if (p.y != coordinate.y) {
                    return false;
                }
            } else if (!direction) { // vertical
                if (p.x != coordinate.x) {
                    return false;
                }
            }
        }
        return true;
    }
}
