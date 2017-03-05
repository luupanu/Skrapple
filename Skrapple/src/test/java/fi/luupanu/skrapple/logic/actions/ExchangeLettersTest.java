/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.domain.Rack;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.Updateable;
import fi.luupanu.skrapple.utils.Announcer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author panu
 */
public class ExchangeLettersTest {

    private Announcer a;
    private Updateable u;
    private ExchangeLetters e;
    private List<Letter> toBeExchanged;
    private Game game;
    Rack rack;

    @Before
    public void setUp() throws IOException {
        SkrappleGame s = new SkrappleGame(new Player(""), new Player(""), null, null,
                new Dictionary("kotus-wordlist-fi"));
        game = s.getGame();
        toBeExchanged = new ArrayList<>();
        addAllLettersFromRackToExchangeQueue();
        this.a = new Announcer(s);
        this.u = (String message) -> {
        };
    }

    @Test
    public void exchangingLettersWorks() {
        e = new ExchangeLetters(game, toBeExchanged, a, u);
        assertEquals(ErrorMessage.NO_ERRORS, e.perform(game));
        for (Letter let : rack.getContentsAsList()) {
            assertEquals(false, toBeExchanged.contains(let));
        }
        assertEquals(7, rack.getContents().size());
    }

    @Test
    public void cannotExchangeIfLetterBagHasNoLetters() {
        game.getLetterBag().getContents().clear();
        e = new ExchangeLetters(game, toBeExchanged, a, u);
        assertEquals(ErrorMessage.LETTERBAG_IS_EMPTY, e.perform(game));
        // check that rack content is unchanged
        assertEquals(rack.getContentsAsList(), toBeExchanged);
    }

    @Test
    public void cannotExchangeIfLetterBagHasLessLettersThanQueue() {
        game.getLetterBag().getContents().clear();
        Letter let = new Letter(LetterType.LETTER_E);
        game.getLetterBag().placeLetterInBag(let);
        e = new ExchangeLetters(game, toBeExchanged, a, u);
        assertEquals(ErrorMessage.LETTERS_NOT_EXCHANGED, e.perform(game));
        // check that rack content is unchanged
        assertEquals(rack.getContentsAsList(), toBeExchanged);
        // check that bag content is unchanged
        assertEquals(let, game.getLetterBag().getContents().get(0));
    }

    private void addAllLettersFromRackToExchangeQueue() {
        rack = game.getCurrentPlayer().getPlayerRack();
        for (int i = 0; i < 7; i++) {
            toBeExchanged.add(rack.getContents().get(i));
        }
    }
}
