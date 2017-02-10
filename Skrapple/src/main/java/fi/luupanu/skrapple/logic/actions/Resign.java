/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.constants.SkrappleGameState;

/**
 * A player can choose to resign the game during her turn.
 * 
 * @author panu
 */
public class Resign extends Action {

    public Resign(Game game) {
        super(game);
    }

    @Override
    public void perform(Game game) {
        if (game.getTurn()) {
            game.setGameState(SkrappleGameState.STATE_PLAYER_1_RESIGNED);
        } else {
            game.setGameState(SkrappleGameState.STATE_PLAYER_2_RESIGNED);
        }
    }
}
