/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.constants.SkrappleGameState;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.LetterBag;
import fi.luupanu.skrapple.domain.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    public void setUp() {
        p1 = new Player("p1");
        p2 = new Player("p2");
        s = new SkrappleGame(p1, p2, new Dictionary("kotus-wordlist-fi"));
    }
    
    @Test
    public void winnerReturnedCorrectlyWhenResigned() {
        s.getGame().setGameState(SkrappleGameState.STATE_PLAYER_1_RESIGNED);
        assertEquals(p2, s.declareWinner());
        s.getGame().setGameState(SkrappleGameState.STATE_PLAYER_2_RESIGNED);
        assertEquals(p1, s.declareWinner());
    }
    
    @Test
    public void winnerReturnedCorrectlyWhenGameOver() {
        p1.addPoints(25);
        p2.addPoints(20);
        s.getGame().setGameState(SkrappleGameState.STATE_GAMEOVER);
        assertEquals(p1, s.declareWinner());
        p1.getPlayerRack().refillRack(new LetterBag());
        assertEquals(p2, s.declareWinner());
    }
    
    @Test
    public void winnerReturnsNullWhenGameIsDrawn() {
        s.getGame().setGameState(SkrappleGameState.STATE_GAMEOVER);
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
}
