/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.logic.LetterQueue;
import fi.luupanu.skrapple.logic.LetterQueueValidator;
import fi.luupanu.skrapple.constants.SkrappleGameState;
import fi.luupanu.skrapple.logic.WordChecker;
import fi.luupanu.skrapple.logic.WordCreator;

/**
 * Game stores and returns all the classes required for a SkrappleGame.
 * 
 * @author panu
 */
public class Game {

    private final Player p1;
    private final Player p2;
    private final Board board;
    private final LetterBag bag;
    private final LetterQueue queue;
    private final LetterQueueValidator validator;
    private final WordChecker wordChecker;
    private final WordCreator wordCreator;
    private SkrappleGameState state;
    private boolean whoseTurn;

    public Game(Player p1, Player p2, Dictionary d) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = new Board();
        this.bag = new LetterBag();
        this.queue = new LetterQueue();
        this.validator = new LetterQueueValidator(queue);
        this.wordChecker = new WordChecker(d);
        this.wordCreator = new WordCreator(queue);
        createGame();
    }

    public Board getBoard() {
        return board;
    }

    public LetterBag getLetterBag() {
        return bag;
    }

    public LetterQueue getLetterQueue() {
        return queue;
    }

    public LetterQueueValidator getLetterQueueValidator() {
        return validator;
    }

    public WordChecker getWordChecker() {
        return wordChecker;
    }

    public WordCreator getWordCreator() {
        return wordCreator;
    }
       
    public Player getCurrentPlayer() {
        if (getTurn()) {
            return p1;
        } else {
            return p2;
        }
    }

    public boolean getTurn() {
        return whoseTurn;
    }

    public void switchTurn() {
        whoseTurn = !getTurn();
    }

    public SkrappleGameState getGameState() {
        return state;
    }

    public void setGameState(SkrappleGameState s) {
        state = s;
    }

    private void createGame() {
        state = SkrappleGameState.STATE_PLAYING;
        whoseTurn = true; // true = player one's turn
    }

}
