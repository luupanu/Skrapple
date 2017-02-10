/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Letter;
import java.util.ArrayList;
import java.util.List;

/**
 * A player can choose to use her turn to exchange one or more of her letters.
 *
 * @author panu
 */
public class ExchangeLetters extends Action {

    private final List<Integer> indexes;

    public ExchangeLetters(Game game, List<Integer> indexes) {
        super(game);
        this.indexes = indexes;
    }

    @Override
    public void perform(Game game) {
        List<Letter> letters = new ArrayList<>(7);
        for (Integer i : indexes) {
            letters.add(game.getCurrentPlayer().getPlayerRack().takeLetter(i));
        }
        game.getCurrentPlayer().getPlayerRack().refillRack(game.getLetterBag());
        for (Letter let : letters) {
            game.getLetterBag().placeLetterInBag(let);
        }
    }

}
