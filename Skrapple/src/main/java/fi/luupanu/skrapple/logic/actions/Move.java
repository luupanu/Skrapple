/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.domain.Game;

/**
 * Move is used by the player to confirm the letters placed on the board. This
 * starts a sequence of events where we need to:
 * 
 * 1) validate the LetterQueue,
 * 2) create Words,
 * 3) check the Words using a Dictionary,
 * 4) update the current player's score,
 * 5) refill the current player's Rack and
 * 6) switch the turn.
 *
 * @author panu
 */
public class Move extends Action {

    /**
     * Creates a new action Move.
     * 
     * @param game the game that is being played
     */
    public Move(Game game) {
        super(game);
    }

    @Override
    public void perform(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
