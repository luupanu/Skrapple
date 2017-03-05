/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.logic.actions.GameAction;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Player;
import java.util.Collections;
import java.util.List;

/**
 * SkrappleGame is the class that is called to create a new game. It creates a
 * new instance of Game and handles the final scoring of points.
 *
 * @author panu
 */
public class SkrappleGame {

    private final Game game;

    /**
     * Creates a new SkrappleGame.
     *
     * @param p1 player one
     * @param p2 player two
     * @param p3 player three
     * @param p4 player four
     * @param d the dictionary
     */
    public SkrappleGame(Player p1, Player p2, Player p3, Player p4, Dictionary d) {
        this.game = new Game(p1, p2, p3, p4, d);
    }

    public Game getGame() {
        return game;
    }

    /**
     * Performs an action if the game is still going on.
     *
     * @param action the action to be performed
     * @return true if the action succeeded
     */
    public ErrorMessage doAction(GameAction action) {
        if (game.getGameState() == GameState.PLAYING) {
            return action.perform(game);
        }
        return ErrorMessage.GAME_IS_OVER;
    }

    /**
     * This method does the final scoring of the game. If a player resigned, her
     * points are zeroed, if not, all remaining letter tiles in her rack are
     * subtracted from the total score.
     *
     * @return a sorted list of players in winning order
     */
    public List<Player> doFinalScoring() {
        subtractRemainingLetters();
        resetResignedPlayersPoints();
        Collections.sort(getGame().getPlayerList());
        return getGame().getPlayerList();
    }

    private void subtractRemainingLetters() {
        for (Player p : getGame().getPlayerList()) {
            p.addPoints(-p.getPlayerRack().getRackPoints());
        }
    }

    private void resetResignedPlayersPoints() {
        for (Player p : getGame().getPlayerList()) {
            if (p.isResigned()) {
                p.addPoints(-p.getPlayerPoints() - 100); // = -100, should always be enough
            }
        }
    }
}
