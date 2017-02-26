/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Letter;
import java.util.List;

/**
 * A player can choose to skip the current turn.
 *
 * @author panu
 */
public class EndTurn extends GameAction {

    /**
     * Creates a new GameAction SkipTurn.
     *
     * @param game the game that is being played
     */
    public EndTurn(Game game) {
        super(game);
    }

    /**
     * Cancels the current player's LetterQueue, places the letters from the
     * queue back to the rack if there were any, and switches the turn.
     *
     * @param game the game that is being played
     * @return no errors
     */
    @Override
    public ErrorMessage perform(Game game) {
        List<Letter> canceled = game.getLetterQueue().cancelLetterQueue();
        game.getCurrentPlayer().getPlayerRack().addLetters(canceled);
        game.switchTurn();
        return ErrorMessage.NO_ERRORS;
    }
}
