/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Word;
import java.util.Set;

/**
 *
 * @author panu
 */
public class WordCreator {

    /*  TO-DO: a working class WordCreator. WordCreator takes the LetterQueue
        and tries to form a word or multiple words based on the letter positions.
        
        - Need to check if there is at least one letter that touches
        an existing letter on the board. (Unless first word of the game!)
        - Need to check whether the found letter touches an existing letter
        vertically or horizontally.
        - If the letter touches both vertically & horizontally, both directions
        have a word to be created.
        - Horizontally aligned words are always from left to right.
        - Vertically aligned words are always from top to bottom.
    
        Maybe split to two classes, e.g. "LetterQueueValidator" and
        WordCreator?
     */
    private final LetterQueue q;
    private final Set<Letter> set;
    private final Neighbours n;

    public WordCreator(LetterQueue queue, Neighbours n) {
        this.q = queue;
        this.set = queue.getLetterQueue();
        this.n = n;
    }

    public void constructWords(Board board) {
        if (board.hasNoLetters()) {
            constructFirstWordOfTheGame(board);
        } else {
            constructHorizontalWords(board);
            constructVerticalWords(board);
        }
    }

    private void constructFirstWordOfTheGame(Board board) {
        if (q.getDirection()) {
            Coord c = q.getLetterQueue().stream().findFirst().get().getCoord();
            n.addHorizontalNeighbour(c);
            constructHorizontalWords(board);
        } else if (!q.getDirection()) {
            Coord c = q.getLetterQueue().stream().findFirst().get().getCoord();
            n.addVerticalNeighbour(c);
            constructVerticalWords(board);
        }
    }

    private void constructHorizontalWords(Board board) {
        for (Coord c : n.getHorizontalNeighbours()) {
            int y = c.getY();
            int left = getLeftMostCoordinate(c.getX(), y, board);
            Word word = new Word();
            for (int x = left; x < 15; x++) {
                if (q.hasCoord(x, y)) {
                    Letter let = q.getLetterByCoordinate(x, y);
                    word.addLetter(let, board, true);
                } else if (board.getSquare(x, y).hasLetter()) {
                    Letter let = board.getSquare(x, y).getLetter();
                    word.addLetter(let, board, false);
                } else {
                    break;
                }
            }
            System.out.println(word.toString());
            System.out.println(word.getPoints());
        }
    }

    private void constructVerticalWords(Board board) {
        for (Coord c : n.getVerticalNeighbours()) {
            int x = c.getX();
            int top = getTopMostCoordinate(x, c.getY(), board);
            Word word = new Word();
            for (int y = top; y < 15; y++) {
                if (q.hasCoord(x, y)) {
                    Letter let = q.getLetterByCoordinate(x, y);
                    word.addLetter(let, board, true);
                } else if (board.getSquare(x, y).hasLetter()) {
                    Letter let = board.getSquare(x, y).getLetter();
                    word.addLetter(let, board, false);
                } else {
                    break;
                }
            }
            System.out.println(word.toString());
            System.out.println(word.getPoints());
        }
    }

    private int getLeftMostCoordinate(int left, int y, Board board) {
        for (int x = left; x >= 0; x--) {
            if (q.hasCoord(x, y) || board.getSquare(x, y).hasLetter()) {
                left = x;
            } else {
                break;
            }
        }
        return left;
    }

    private int getTopMostCoordinate(int x, int top, Board board) {
        for (int y = top; y >= 0; y--) {
            if (q.hasCoord(x, y) || board.getSquare(x, y).hasLetter()) {
                top = y;
            } else {
                break;
            }
        }
        return top;
    }
}
