/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.domain.Rack;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author panu
 */
public class EndTurnTest {
    
    private EndTurn e;
    private Game game;

    @Before
    public void setUp() throws IOException {
        game = new Game(new Player(""), new Player(""), new Dictionary("kotus-wordlist-fi"));
        e = new EndTurn(game);
    }

    @Test
    public void endingTurnSwitchesTurn() {
        assertEquals(ErrorMessage.NO_ERRORS, e.perform(game));
        assertEquals(game.getPlayerTwo(), game.getCurrentPlayer());
        assertEquals(ErrorMessage.NO_ERRORS, e.perform(game));
        assertEquals(game.getPlayerOne(), game.getCurrentPlayer());
    }
    
    @Test
    public void endingTurnPlacesLettersInLetterQueueBackToPlayerRack() {
        Rack rack = game.getCurrentPlayer().getPlayerRack();
        List<Letter> original = rack.getContentsAsList();
        game.getLetterQueue().addLetterToQueue(rack.takeLetter(original.get(0)),
                new Coord(7, 7), game.getBoard());
        game.getLetterQueue().addLetterToQueue(rack.takeLetter(original.get(1)),
                new Coord(7, 8), game.getBoard());
        assertEquals(5, rack.getContents().size());
        assertEquals(2, game.getLetterQueue().getContents().size());
        e.perform(game);
        rack.getContentsAsList().equals(original);
    }
}
