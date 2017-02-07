/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import java.util.HashSet;
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
    private final LetterQueueValidator v;
    private int points;

    public WordCreator(LetterQueue queue, LetterQueueValidator v) {
        this.q = queue;
        this.set = queue.getLetterQueue();
        this.v = v;
        this.points = 0;
    }

    public void function(Board board) {
        constructHorizontalWords(board);
        constructVerticalWords(board);
    }

    private void constructHorizontalWords(Board board) {
        StringBuilder word = new StringBuilder();

        for (Coord c : v.getHorizontalNeighbours()) {
            int left = c.getX();
            int y = c.getY();
            for (int x = left; x >= 0; x--) {
                if (q.hasCoord(x, y) || board.getSquare(x, y).hasLetter()) {
                    left = x;
                } else {
                    break;
                }
            }
            for (int x = left; x < 15; x++) {
                if (q.hasCoord(x, y)) {
                    Letter let = q.getLetterByCoordinate(x, y);
                    word.append(let);
                    points += let.getPoints();
                } else if (board.getSquare(x, y).hasLetter()) {
                    Letter let = board.getSquare(x, y).getLetter();
                    word.append(let);
                    points += let.getPoints();
                } else {
                    break;
                }
            }
            word.append(", ").append(points).append("| ");
            System.out.println(word.toString());
            points = 0;
        }
    }

    private void constructVerticalWords(Board board) {
        StringBuilder word = new StringBuilder();

        for (Coord c : v.getVerticalNeighbours()) {
            int top = c.getY();
            int x = c.getX();
            for (int y = top; y >= 0; y--) {
                if (q.hasCoord(x, y) || board.getSquare(x, y).hasLetter()) {
                    top = y;
                } else {
                    break;
                }
            }
            for (int y = top; y < 15; y++) {
                if (q.hasCoord(x, y)) {
                    Letter let = q.getLetterByCoordinate(x, y);
                    word.append(let);
                    points += let.getPoints();
                } else if (board.getSquare(x, y).hasLetter()) {
                    Letter let = board.getSquare(x, y).getLetter();
                    word.append(let);
                    points += let.getPoints();
                } else {
                    break;
                }
            }
            word.append(", ").append(points).append("| ");
            System.out.println(word.toString());
            points = 0;
        }
    }
}
