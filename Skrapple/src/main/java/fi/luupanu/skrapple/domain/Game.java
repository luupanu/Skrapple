/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.logic.LetterQueue;
import fi.luupanu.skrapple.logic.LetterQueueValidator;
import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.logic.WordChecker;
import fi.luupanu.skrapple.logic.WordCreator;
import java.util.ArrayList;
import java.util.List;

/**
 * Game stores and returns all the classes required for a SkrappleGame.
 *
 * @author panu
 */
public class Game {

    private final List<Player> players;
    private final Board board;
    private final LetterBag bag;
    private final LetterQueue queue;
    private final LetterQueueValidator validator;
    private final WordChecker wordChecker;
    private final WordCreator wordCreator;
    private GameState state;
    private int whoseTurn;

    /**
     * A new two player game.
     *
     * @param p1 player one
     * @param p2 player two
     * @param p3 player three
     * @param p4 player four
     * @param d the dictionary
     */
    public Game(Player p1, Player p2, Player p3, Player p4, Dictionary d) {
        this.players = new ArrayList<>(4);
        this.board = new Board();
        this.bag = new LetterBag();
        this.queue = new LetterQueue();
        this.validator = new LetterQueueValidator(queue);
        this.wordChecker = new WordChecker(d);
        this.wordCreator = new WordCreator(queue);
        createGame(p1, p2, p3, p4);
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
    
    public List<Player> getPlayerList() {
        return players;
    }

    /**
     * This method returns the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return players.get(whoseTurn - 1);
    }
    
    /**
     * This method switches the turn.
     */
    public void switchTurn() {
        whoseTurn++;
        if (whoseTurn > players.size()) {
            whoseTurn = 1;
        }
        while (getCurrentPlayer().isResigned() &&
                getGameState() == GameState.PLAYING
                && atLeastOnePlayerIsNotResigned()) { // avoid infinite loops
            switchTurn();
        }
    }

    public GameState getGameState() {
        return state;
    }

    public void setGameState(GameState s) {
        state = s;
    }

    private void createGame(Player p1, Player p2, Player p3, Player p4) {
        addPlayer(p1);
        addPlayer(p2);
        addPlayer(p3);
        addPlayer(p4);

        state = GameState.PLAYING;
        whoseTurn = 1; // 1 = player one's turn
        for (Player p : players) {
            p.getPlayerRack().refillRack(bag);
        }
    }

    private void addPlayer(Player p) {
        if (p != null) {
            players.add(p);
        }
    }

    private boolean atLeastOnePlayerIsNotResigned() {
        for (Player p : players) {
            if (!p.isResigned()) {
                return true;
            }
        }
        return false;
    }
}
