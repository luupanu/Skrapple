/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.constants.SquareType;
import java.util.ArrayList;
import java.util.List;

/**
 * Words are made of letters. This class is also used to update the score total
 * of a word. The score of a word is the sum of the scores of its letters, each
 * multiplied according to any letter bonus squares newly covered, then finally
 * multiplied according to any word bonus squares newly covered.
 * 
 * @author panu
 */
public class Word {

    private final List<Letter> word;
    private int points;
    private int wordCoefficient;

    public Word() {
        word = new ArrayList<>(15);
        points = 0;
        wordCoefficient = 1;
    }

    public boolean addLetter(Letter let, Board board, boolean isQueueLetter) {
        if (isQueueLetter && let.getCoord() == null) {
            return false;
        }
        updatePoints(let, board, isQueueLetter);
        word.add(let);
        return true;
    }
    
    public int getPoints() {
        return points * wordCoefficient;
    }
    
    public List<Letter> getWord() {
        return word;
    }

    private void updatePoints(Letter let, Board board, boolean isQueueLetter) {
        int letterCoefficient = 1;
        if (isQueueLetter) {
            int x = let.getCoord().getX();
            int y = let.getCoord().getY();
            SquareType t = board.getSquare(x, y).getType();
            if (t == SquareType.NORMAL) {
                letterCoefficient = 1;
            } else if (t == SquareType.BONUS_LETTER_2X) {
                letterCoefficient = 2;
            } else if (t == SquareType.BONUS_WORD_2X) {
                wordCoefficient *= 2;
            } else if (t == SquareType.BONUS_LETTER_3X) {
                letterCoefficient = 3;
            } else if (t == SquareType.BONUS_WORD_3X) {
                wordCoefficient *= 3;
            }
        }
        points += let.getPoints() * letterCoefficient;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Letter let : word) {
            sb.append(let.toString());
        }
        return sb.toString();
    }
}
