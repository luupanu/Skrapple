/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Board;
import fi.luupanu.skrapple.domain.Player;

/**
 *
 * @author panu
 */
public class SkrappleGame {

    private final Player p1;
    private final Player p2;
    private Board board;
    private SkrappleGameState state;
    private boolean whoseTurn;

    public SkrappleGame(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        createGame();
    }
    // TODO: the main game loop

    public Player play() {
        // still under construction. Returns the winner of the game
        while (true) {
            Turn turn = new Turn();
            turn.doTurn(whoseTurn);
            if (state != SkrappleGameState.STATE_PLAYING) {
                break;
            }
            switchTurn();
        }

        subtractRemainingLetters();
        return declareWinner();
    }

    private void createGame() {
        state = SkrappleGameState.STATE_PLAYING;
        board = new Board();
        whoseTurn = true; // true = player one's turn
    }

    private void subtractRemainingLetters() {
        p1.addPoints(-p1.getPlayerRack().getRackPoints());
        p2.addPoints(-p2.getPlayerRack().getRackPoints());
    }

    private Player declareWinner() {
        if (state == SkrappleGameState.STATE_PLAYER_2_RESIGNED) {
            return p1;
        } else if (state == SkrappleGameState.STATE_PLAYER_1_RESIGNED) {
            return p2;
        }
        if (p1.getPlayerPoints() > p2.getPlayerPoints()) {
            return p1;
        } else if (p1.getPlayerPoints() < p2.getPlayerPoints()) {
            return p2;
        }
        return null;
    }
            
    public boolean getTurn() {
        return whoseTurn;
    }
    
    public void switchTurn() {
        whoseTurn = !getTurn();
    }

}
