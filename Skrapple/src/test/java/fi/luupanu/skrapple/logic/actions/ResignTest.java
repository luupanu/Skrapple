package fi.luupanu.skrapple.logic.actions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.logic.SkrappleGame;
import java.io.IOException;
import org.junit.Before;
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
    private Player p3;
    private Player p4;

    @Before
    public void setUp() throws IOException {
        p1 = new Player("p1");
        p2 = new Player("p2");
        p3 = new Player("p3");
        p4 = new Player("p4");
        s = new SkrappleGame(p1, p2, p3, p4,
                new Dictionary("kotus-wordlist-fi"));
        r = new Resign(s.getGame());
    }

    @Test
    public void playerOneResignWorks() {
        assertEquals(ErrorMessage.NO_ERRORS, r.perform(s.getGame()));
        onlyThisPlayerIsResigned(p1);
    }

    @Test
    public void playerTwoResignWorks() {
        s.getGame().switchTurn();
        assertEquals(ErrorMessage.NO_ERRORS, r.perform(s.getGame()));
        onlyThisPlayerIsResigned(p2);
    }

    @Test
    public void playerThreeResignWorks() {
        s.getGame().switchTurn();
        s.getGame().switchTurn();
        assertEquals(ErrorMessage.NO_ERRORS, r.perform(s.getGame()));
        onlyThisPlayerIsResigned(p3);
    }

    @Test
    public void playerFourResignWorks() {
        s.getGame().switchTurn();
        s.getGame().switchTurn();
        s.getGame().switchTurn();
        assertEquals(ErrorMessage.NO_ERRORS, r.perform(s.getGame()));
        onlyThisPlayerIsResigned(p4);
    }

    @Test
    public void settingGameStateWorks() {
        assertEquals(GameState.PLAYING, s.getGame().getGameState());
        assertEquals(ErrorMessage.NO_ERRORS, r.perform(s.getGame()));
        assertEquals(GameState.PLAYING, s.getGame().getGameState());
        s.getGame().switchTurn();
        assertEquals(ErrorMessage.NO_ERRORS, r.perform(s.getGame()));
        assertEquals(GameState.PLAYING, s.getGame().getGameState());
        s.getGame().switchTurn();
        assertEquals(ErrorMessage.NO_ERRORS, r.perform(s.getGame()));
        assertEquals(GameState.GAMEOVER, s.getGame().getGameState());
        assertEquals(ErrorMessage.GAME_IS_OVER, r.perform(s.getGame()));
    }

    private void onlyThisPlayerIsResigned(Player resigned) {
        for (Player p : s.getGame().getPlayerList()) {
            if (p == resigned) {
                assertEquals(true, p.isResigned());
            } else {
                assertEquals(false, p.isResigned());
            }
        }
    }
}
