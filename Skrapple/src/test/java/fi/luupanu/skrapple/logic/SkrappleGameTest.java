/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.SkrappleGameState;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.LetterBag;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.logic.actions.EndTurn;
import fi.luupanu.skrapple.logic.actions.ExchangeLetters;
import fi.luupanu.skrapple.logic.actions.Move;
import fi.luupanu.skrapple.logic.actions.Resign;
import fi.luupanu.skrapple.ui.Updateable;
import fi.luupanu.skrapple.utils.Announcer;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author panu
 */
public class SkrappleGameTest {
    
    private Player p1;
    private Player p2;
    private SkrappleGame s;
    
    @Before
    public void setUp() throws IOException {
        p1 = new Player("p1");
        p2 = new Player("p2");
        s = new SkrappleGame(p1, p2, new Dictionary("kotus-wordlist-fi"));
        p1.getPlayerRack().getContents().clear();
        p2.getPlayerRack().getContents().clear();
    }
    
    @Test
    public void winnerReturnedCorrectlyWhenResigned() {
        s.getGame().setGameState(SkrappleGameState.PLAYER_1_RESIGNED);
        assertEquals(p2, s.declareWinner());
        s.getGame().setGameState(SkrappleGameState.PLAYER_2_RESIGNED);
        assertEquals(p1, s.declareWinner());
    }
    
    @Test
    public void winnerReturnedCorrectlyWhenGameOver() {
        p1.addPoints(25);
        p2.addPoints(20);
        s.getGame().setGameState(SkrappleGameState.GAMEOVER);
        assertEquals(p1, s.declareWinner());
        p1.getPlayerRack().refillRack(new LetterBag());
        assertEquals(p2, s.declareWinner());
    }
    
    @Test
    public void winnerReturnsNullWhenGameIsDrawn() {
        s.getGame().setGameState(SkrappleGameState.GAMEOVER);
        assertEquals(null, s.declareWinner());
    }
    
    @Test
    public void getCurrentPlayerReturnsPlayerOneWhenGameStarted() {
        assertEquals(true, s.getGame().getTurn());
        assertEquals(p1, s.getGame().getCurrentPlayer());
    }
    
    @Test
    public void switchingTurnWorks() {
        s.getGame().switchTurn();
        assertEquals(false, s.getGame().getTurn());
        assertEquals(p2, s.getGame().getCurrentPlayer());
    }
    
    @Test
    public void noActionsCanBePerformedWhenGameIsOver() {
        s.getGame().setGameState(SkrappleGameState.GAMEOVER);
        Announcer a = new Announcer(s);
        Updateable u = (String message) -> {};
        assertEquals(ErrorMessage.GAME_IS_OVER, s.doAction(new Move(s.getGame(), a, u)));
        assertEquals(ErrorMessage.GAME_IS_OVER, s.doAction(new ExchangeLetters(s.getGame(), new ArrayList<>())));
        assertEquals(ErrorMessage.GAME_IS_OVER, s.doAction(new Resign(s.getGame())));
        assertEquals(ErrorMessage.GAME_IS_OVER, s.doAction(new EndTurn(s.getGame())));
    }
}
