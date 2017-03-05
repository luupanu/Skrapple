/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Letter;
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
import java.util.List;
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
    private Player p3;
    private Player p4;
    
    @Before
    public void setUp() throws IOException {
        p1 = new Player("p1");
        p2 = new Player("p2");
        p3 = new Player("p3");
        p4 = new Player("p4");
        s = new SkrappleGame(p1, p2, p3, p4, new Dictionary("kotus-wordlist-fi"));
        p1.getPlayerRack().getContents().clear();
        p2.getPlayerRack().getContents().clear();
    }

    @Test
    public void subtractRemainingLettersWorks() {
        p1.getPlayerRack().addLetter(new Letter(LetterType.LETTER_OE)); // 7 points
        int playerThreeSubtracted = p3.getPlayerRack().getRackPoints();
        int playerFourSubtracted = p4.getPlayerRack().getRackPoints();
        s.doFinalScoring();
        assertEquals(-7, p1.getPlayerPoints());
        assertEquals(0, p2.getPlayerPoints());
        assertEquals(-playerThreeSubtracted, p3.getPlayerPoints());
        assertEquals(-playerFourSubtracted, p4.getPlayerPoints());
    }
    
    @Test
    public void zeroResignedPlayerPointsWorks() {
        p1.addPoints(4);
        p2.addPoints(500);
        p3.addPoints(-500);
        p4.addPoints(400);
        p2.resign();
        p3.resign();
        p4.resign();
        s.doFinalScoring();
        assertEquals(4, p1.getPlayerPoints());
        assertEquals(-100, p2.getPlayerPoints());
        assertEquals(-100, p3.getPlayerPoints());
        assertEquals(-100, p4.getPlayerPoints());
    }
    
    @Test
    public void sortingPlayersByScoreWorks() {
        p3.getPlayerRack().getContents().clear();
        p4.getPlayerRack().getContents().clear();
        p1.addPoints(5);
        p2.addPoints(10);
        p3.addPoints(20);
        p4.addPoints(50);
        p4.resign();
        List<Player> sorted = s.doFinalScoring();
        assertEquals(p3, sorted.get(0));
        assertEquals(p2, sorted.get(1));
        assertEquals(p1, sorted.get(2));
        assertEquals(p4, sorted.get(3));
    }
    
    @Test
    public void noActionsCanBePerformedWhenGameIsOver() {
        s.getGame().setGameState(GameState.GAMEOVER);
        Announcer a = new Announcer(s);
        Updateable u = (String message) -> {};
        assertEquals(ErrorMessage.GAME_IS_OVER, s.doAction(new Move(s.getGame(), a, u)));
        assertEquals(ErrorMessage.GAME_IS_OVER, s.doAction(new ExchangeLetters(s.getGame(), new ArrayList<>(), a, u)));
        assertEquals(ErrorMessage.GAME_IS_OVER, s.doAction(new Resign(s.getGame())));
        assertEquals(ErrorMessage.GAME_IS_OVER, s.doAction(new EndTurn(s.getGame())));
    }
}
