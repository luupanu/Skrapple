/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.domain.Game;

/**
 * The abstract class GameAction. A player can make one of four different actions
 * during a turn:
 *
 * - make a Move (place words on the board) - x points
 * - exchange letters - 0 points
 * - end the turn - 0-* points
 * - resign
 *
 * @author panu
 */
public abstract class GameAction {

    private final Game game;

    /**
     * Create a new action.
     *
     * @param game the game that is being played
     */
    public GameAction(Game game) {
        this.game = game;
    }

    /**
     * Perform an abstract action.
     *
     * @param game the game that is being played
     * @return true if the action was successful
     */
    public abstract ErrorMessage perform(Game game);
}
