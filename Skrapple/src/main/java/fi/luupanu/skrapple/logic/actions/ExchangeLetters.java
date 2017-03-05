/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.ui.Updateable;
import fi.luupanu.skrapple.utils.Announcer;
import java.util.ArrayList;
import java.util.List;

/**
 * A player can choose to use her turn to exchange one or more of her letters.
 *
 * @author panu
 */
public class ExchangeLetters extends GameAction {

    private final List<Letter> letters;
    private final Announcer announcer;
    private final Updateable updateable;

    /**
     * Creates a new action ExchangeLetters.
     *
     * @param game the game being played
     * @param letters the letters to be exchanged
     * @param a announcer
     * @param u updateable
     */
    public ExchangeLetters(Game game, List<Letter> letters, Announcer a, Updateable u) {
        super(game);
        this.letters = letters;
        this.announcer = a;
        this.updateable = u;
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
        if (game.getLetterBag().getContents().isEmpty()) {
            return ErrorMessage.LETTERBAG_IS_EMPTY;
        }
        if (letters.size() > game.getLetterBag().getSize()) {
            return ErrorMessage.LETTERS_NOT_EXCHANGED;
        }
        String msg;
        msg = announcer.announce(Announcement.PLAYER_EXCHANGED_MESSAGE);
        updateable.update(announcer.addIndentation(msg));
        // put letters aside
        List<Letter> aside = new ArrayList<>();
        for (Letter let : letters) {
            aside.add(game.getCurrentPlayer().getPlayerRack().takeLetter(let));
        }
        // refill player's rack
        List<Letter> refilled = game.getCurrentPlayer().getPlayerRack().
                refillRack(game.getLetterBag());
        // announce the new letters
        msg = announcer.announce(Announcement.REFILL_RACK_MESSAGE,
                refilled);
        updateable.update(announcer.addIndentation(msg));
        // put letters back to the letter bag
        for (Letter let : aside) {
            game.getLetterBag().placeLetterInBag(let);
        }
        return ErrorMessage.NO_ERRORS;
    }

}
