package fi.luupanu.skrapple.logic.actions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import fi.luupanu.skrapple.constants.SkrappleGameState;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.logic.SkrappleGame;
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
public class ResignTest {

    private Player p1;
    private Player p2;
    private SkrappleGame s;
    private Resign r;

    @Before
    public void setUp() {
        p1 = new Player("p1");
        p2 = new Player("p2");
        s = new SkrappleGame(p1, p2, new Dictionary("kotus-wordlist-fi"));
        r = new Resign(s.getGame());
    }

    @Test
    public void playerOneResignWorks() {
        r.perform(s.getGame());
        assertEquals(SkrappleGameState.STATE_PLAYER_1_RESIGNED, s.getGame().getGameState());
        assertEquals(p2, s.declareWinner());
    }

    @Test
    public void playerTwoResignWorks() {
        s.getGame().switchTurn();
        r.perform(s.getGame());
        assertEquals(SkrappleGameState.STATE_PLAYER_2_RESIGNED, s.getGame().getGameState());
        assertEquals(p1, s.declareWinner());
    }
}
