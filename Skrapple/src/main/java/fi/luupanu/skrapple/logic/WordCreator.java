/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.constants.Direction;
import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Word;
import java.util.ArrayList;
import java.util.List;

/**
 * WordCreator takes letters from the LetterQueue and tries to form a word or
 * multiple words based on their respectful positions. This class utilizes the
 * two sets of neighbours to create either horizontal or vertical Words.
 *
 * @author panu
 */
public class WordCreator {

    private final LetterQueue q;
    private final List<Letter> list;
    private final Neighbours n;
    private final List<Word> words;

    /**
     * Creates a new WordCreator.
     *
     * @param queue the letter queue used by the game
     */
    public WordCreator(LetterQueue queue) {
        this.q = queue;
        this.list = queue.getContents();
        this.n = queue.getNeighbours();
        this.words = new ArrayList<>();
    }

    public List<Word> getContents() {
        return words;
    }

    /**
     * This method constructs all the words from the letters in the LetterQueue.
     * In some cases an artificial neighbour needs to be first added in order to
     * use the same two methods to construct words.
     *
     * @param board the game board
     * @return a list of all the words that were created
     */
    public List<Word> constructWords(Board board) {
        constructArtificialNeighbour(board);
        constructHorizontalWords(board);
        constructVerticalWords(board);
        return words;
    }

    /**
     * This method constructs artificial neighbours, if needed. If the word
     * played is the first word of the game, or the letter queue contains for
     * example a valid horizontal word even though it only has a vertical
     * neighbour, this method will add one artificial neighbour which will be
     * the first coordinate of the first letter in the queue it finds.
     *
     * @param board
     */
    private void constructArtificialNeighbour(Board board) {
        if (q.getDirection() == Direction.HORIZONTAL &&
                n.getHorizontalNeighbours().isEmpty()) {
            Coord c = q.getContents().stream().findFirst().get().getCoord();
            n.addHorizontalNeighbour(c);
        } else if (q.getDirection() == Direction.VERTICAL &&
                n.getVerticalNeighbours().isEmpty()) {
            Coord c = q.getContents().stream().findFirst().get().getCoord();
            n.addVerticalNeighbour(c);
        } else if (board.hasNoLetters() && q.getDirection() == null) {
            Coord c = q.getContents().stream().findFirst().get().getCoord();
            n.addHorizontalNeighbour(c);
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
            words.add(word);
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
            words.add(word);
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
