/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.domain.Game;

/**
 * The abstract class Action. A player can make one of four different actions
 * during a turn:
 * 
 * - make a Move (place words on the board) - x points
 * - exchange letters - 0 points
 * - skip the turn - 0 points
 * - resign
 * 
 * @author panu
 */
public abstract class Action {

    private final Game game;

    public Action(Game game) {
        this.game = game;
    }

    public abstract void perform(Game game);
}
