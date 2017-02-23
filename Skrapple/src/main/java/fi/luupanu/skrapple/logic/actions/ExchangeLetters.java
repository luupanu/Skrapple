/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Letter;
import java.util.ArrayList;
import java.util.List;

/**
 * A player can choose to use her turn to exchange one or more of her letters.
 *
 * @author panu
 */
public class ExchangeLetters extends GameAction {

    private final List<Letter> letters;

    /**
     * Creates a new action ExchangeLetters.
     *
     * @param game the game being played
     * @param letters the letters to be exchanged
     */
    public ExchangeLetters(Game game, List<Letter> letters) {
        super(game);
        this.letters = letters;
    }

    /**
     * This method exchanges all letters selected by the player. It first puts
     * the letters to be exchanged aside, refills the rack, and then puts the
     * letters back to the letter bag.
     *
     * @param game the game that is being played
     * @return always true
     */
    @Override
    public ErrorMessage perform(Game game) {
        if (letters.size() > game.getLetterBag().getSize()) {
            return ErrorMessage.LETTERS_NOT_EXCHANGED;
        }
        
        List<Letter> aside = new ArrayList<>();
        for (Letter let : letters) {
            aside.add(game.getCurrentPlayer().getPlayerRack().takeLetter(let));
        }    
        game.getCurrentPlayer().getPlayerRack().refillRack(game.getLetterBag());
        for (Letter let : aside) {
            game.getLetterBag().placeLetterInBag(let);
        }
        return ErrorMessage.NO_ERRORS;
    }

}
