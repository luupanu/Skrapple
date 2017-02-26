/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Player;

/**
 * A player can choose to resign the game during her turn.
 *
 * @author panu
 */
public class Resign extends GameAction {

    /**
     * Creates a new action Resign.
     *
     * @param game the game that is being played
     */
    public Resign(Game game) {
        super(game);
    }

    /**
     * Checks which player's turn it is, and changes the game state accordingly.
     *
     * @param game the game being played
     * @return true
     */
    @Override
    public ErrorMessage perform(Game game) {
        if (game.getGameState() == GameState.GAMEOVER) {
            return ErrorMessage.GAME_IS_OVER;
        }
        game.getCurrentPlayer().resign();
        if (gameIsOver(game)) {
            game.setGameState(GameState.GAMEOVER);
        }     
        return ErrorMessage.NO_ERRORS;
    }

    private boolean gameIsOver(Game game) {
        int resignedPlayers = 0;
        for (Player p : game.getPlayerList()) {
            if (p.isResigned()) {
                resignedPlayers++;
            }
        }
        return resignedPlayers == game.getPlayerList().size() - 1;
    }
}
