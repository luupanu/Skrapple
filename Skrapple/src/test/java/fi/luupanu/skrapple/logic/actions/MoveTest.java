/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.LetterType;
import fi.luupanu.skrapple.domain.Coord;
import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Player;
import fi.luupanu.skrapple.domain.Rack;
import fi.luupanu.skrapple.logic.SkrappleGame;
import fi.luupanu.skrapple.ui.Updateable;
import fi.luupanu.skrapple.utils.Announcer;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author panu
 */
public class MoveTest {

    private Move m;
    private SkrappleGame s;
    private Player p1;
    private Rack rack;

    @Before
    public void setUp() throws IOException {
        p1 = new Player("");
        s = new SkrappleGame(p1, new Player(""), null, null,
                new Dictionary("kotus-wordlist-fi"));
        Updateable u = (String message) -> {};
        Announcer a = new Announcer(s);
        m = new Move(s.getGame(), a, u);
        rack = s.getGame().getCurrentPlayer().getPlayerRack();
        addCustomRackLetters(s.getGame().getCurrentPlayer().getPlayerRack());
    }
    
    @Test
    public void moveDoesNothingIfLetterQueueIsEmpty() {
        List<Letter> original = s.getGame().getCurrentPlayer().getPlayerRack().getContentsAsList();
        assertEquals(ErrorMessage.LETTERQUEUE_IS_EMPTY, m.perform(s.getGame()));
        everythingIsUnchanged(original);
    }
    
    @Test
    public void moveDoesNothingIfLetterQueueIsNotValid() {
        Letter let1 = rack.takeLetter(rack.getContents().get(0));
        Letter let2 = rack.takeLetter(rack.getContents().get(1));
        List<Letter> original = s.getGame().getCurrentPlayer().getPlayerRack().getContentsAsList();
        assertEquals(true, s.getGame().getLetterQueue().addLetterToQueue(let1,
                new Coord(7,7), s.getGame().getBoard()));
        assertEquals(true, s.getGame().getLetterQueue().addLetterToQueue(let2,
                new Coord(7,9), s.getGame().getBoard()));
        assertEquals(ErrorMessage.LETTERQUEUE_IS_NOT_VALID, m.perform(s.getGame()));
        everythingIsUnchanged(original);
    }
    
    @Test
    public void moveDoesNothingIfWordIsNotValid() {
        Letter let1 = rack.takeLetter(rack.getContents().get(0));
        Letter let2 = rack.takeLetter(rack.getContents().get(1));
        List<Letter> original = s.getGame().getCurrentPlayer().getPlayerRack().getContentsAsList();
        assertEquals(true, s.getGame().getLetterQueue().addLetterToQueue(let2,
                new Coord(7,7), s.getGame().getBoard()));
        assertEquals(true, s.getGame().getLetterQueue().addLetterToQueue(let1,
                new Coord(7,8), s.getGame().getBoard())); // ES
        assertEquals(ErrorMessage.WORD_IS_NOT_VALID, m.perform(s.getGame()));
        assertEquals(2, s.getGame().getLetterQueue().getContents().size());
        everythingIsUnchanged(original);
    }
    
    @Test public void moveSucceedsIfWordAndQueueAreValid() {
        List<Letter> original = s.getGame().getCurrentPlayer().getPlayerRack().getContentsAsList();
        for (int x = 0; x < 7; x++) {
            Letter let = rack.takeLetter(rack.getContents().get(x));
            s.getGame().getLetterQueue().addLetterToQueue(let,
                    new Coord(x+1, 7),s.getGame().getBoard());
        }
        assertEquals(ErrorMessage.NO_ERRORS, m.perform(s.getGame()));
        assertEquals(p1, s.getGame().getCurrentPlayer());
        assertEquals(74, s.getGame().getCurrentPlayer().getPlayerPoints());
        neighboursAndWordCreatorAreEmpty();
        assertEquals(7, s.getGame().getCurrentPlayer().getPlayerRack().getContents().size());
        newLettersWereDrawn(original);
    }
    
    private void newLettersWereDrawn(List<Letter> original) {
        for (Letter originalLetter : original) {
            s.getGame().getCurrentPlayer().getPlayerRack().getContentsAsList().
                    contains(originalLetter);
        }
    }
    
    private void everythingIsUnchanged(List<Letter> original) {
        assertEquals(p1, s.getGame().getCurrentPlayer());
        assertEquals(0, s.getGame().getCurrentPlayer().getPlayerPoints());
        assertEquals(original, s.getGame().getCurrentPlayer().getPlayerRack().getContentsAsList());
        neighboursAndWordCreatorAreEmpty();
    }
    
    private void neighboursAndWordCreatorAreEmpty() {
        assertEquals(0, s.getGame().getLetterQueue().getNeighbours().getHorizontalNeighbours().size());
        assertEquals(0, s.getGame().getLetterQueue().getNeighbours().getVerticalNeighbours().size());
        assertEquals(0, s.getGame().getWordCreator().getContents().size());
    }

    private void addCustomRackLetters(Rack playerRack) {
        rack.getContents().clear();
        rack.addLetter(new Letter(LetterType.LETTER_S));
        rack.addLetter(new Letter(LetterType.LETTER_E));
        rack.addLetter(new Letter(LetterType.LETTER_E));
        rack.addLetter(new Letter(LetterType.LETTER_S));
        rack.addLetter(new Letter(LetterType.LETTER_T));
        rack.addLetter(new Letter(LetterType.LETTER_Y));
        rack.addLetter(new Letter(LetterType.LETTER_AE));
    }
}
