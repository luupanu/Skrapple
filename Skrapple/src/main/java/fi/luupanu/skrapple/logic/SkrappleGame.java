/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.constants.SkrappleGameState;
import fi.luupanu.skrapple.logic.actions.Action;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Player;

/**
 * SkrappleGame is the class that is called to create a new game. It creates a
 * new instance of Game and handles the final declaration of a winner.
 *
 * @author panu
 */
public class SkrappleGame {

    private final Game game;
    private final Player p1;
    private final Player p2;

    public SkrappleGame(Player p1, Player p2, Dictionary d) {
        this.p1 = p1;
        this.p2 = p2;
        this.game = new Game(p1, p2, d);
    }

    public Game getGame() {
        return game;
    }

    /**
     * Does an action if the game is still going on.
     *
     * @param action
     */
    public void doAction(Action action) {
        if (game.getGameState() != SkrappleGameState.PLAYING) {
            action.perform(game);
            game.switchTurn();
        }
    }

    /**
     * This method returns the winner of the game. If none of the players
     * resigned, need to subtract all remaining letters in the racks of the
     * players before comparing scores.
     *
     * @return the winning player of the game, null if drawn
     */
    public Player declareWinner() {
        if (game.getGameState() == SkrappleGameState.PLAYER_2_RESIGNED) {
            return p1;
        } else if (game.getGameState() == SkrappleGameState.PLAYER_1_RESIGNED) {
            return p2;
        }
        subtractRemainingLetters();
        if (p1.getPlayerPoints() > p2.getPlayerPoints()) {
            return p1;
        } else if (p1.getPlayerPoints() < p2.getPlayerPoints()) {
            return p2;
        }
        return null;
    }

    private void subtractRemainingLetters() {
        p1.addPoints(-p1.getPlayerRack().getRackPoints());
        p2.addPoints(-p2.getPlayerRack().getRackPoints());
    }

}
