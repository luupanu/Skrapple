/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic.actions;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.constants.ErrorMessage;
import fi.luupanu.skrapple.constants.GameState;
import fi.luupanu.skrapple.domain.Game;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Word;
import fi.luupanu.skrapple.utils.Announcer;
import java.util.List;
import fi.luupanu.skrapple.ui.Updateable;

/**
 * Move is used by the player to confirm the letters placed on the board. This
 * starts a sequence of events where we need to:
 *
 * 1) validate the LetterQueue,
 * 2) create Words,
 * 3) check the Words using a Dictionary,
 * 4) place all letters in queue to the board,
 * 5) clear LetterQueue
 * 6) update the current player's score,
 * 7) clear WordCreator
 * 8) clear Neighbours and
 * 9) refill the current player's Rack.
 *
 * @author panu
 */
public class Move extends GameAction {

    private final Announcer announcer;
    private final Updateable updateable;
    private List<Word> words;
    
    /**
     * Creates a new action Move.
     *
     * @param game the game that is being played
     * @param a announcer
     * @param u updateable
     */
    public Move(Game game, Announcer a, Updateable u) {
        super(game);
        this.announcer = a;
        this.updateable = u;
    }

    @Override
    public ErrorMessage perform(Game game) {
        if (letterQueueIsEmpty(game)) {
            return ErrorMessage.LETTERQUEUE_IS_EMPTY;
        }
        if (!letterQueueIsValid(game)) {
            clearNeighbours(game);
            return ErrorMessage.LETTERQUEUE_IS_NOT_VALID;
        }
        // generate words
        words = game.getWordCreator().constructWords(game.getBoard());
        // see if the dictionary didn't have some words
        List<Word> notFound = game.getWordChecker().allWordsExistInDictionary(words);    
        if (!notFound.isEmpty()) {
            ErrorMessage e = ErrorMessage.WORD_IS_NOT_VALID;
            Word w = notFound.stream().findAny().get();
            e.setMessage(w);
            clearWordCreator(game);
            clearNeighbours(game);
            return e;
        }
        // all good
        
        // place letters to board
        int placedLetters = placeLettersToBoard(game);
        // clear LetterQueue
        clearLetterQueue(game);
        // give points to the current player
        givePointsToCurrentPlayer(game, placedLetters);
        // clear WordCreator
        clearWordCreator(game);
        // clear Neighbours
        clearNeighbours(game);
        // refill her rack
        if (!game.getLetterBag().getContents().isEmpty()) {
            refillCurrentPlayerRack(game);
        } else { // i used all the letters in my rack, game over
            if (game.getCurrentPlayer().getPlayerRack().getContents().isEmpty()) {
                game.setGameState(GameState.GAMEOVER);
            }
        }
        
        return ErrorMessage.NO_ERRORS;
    }
     
    private int placeLettersToBoard(Game game) {
        int placedLetters = 0;
        for (Letter let : game.getLetterQueue().getContents()) {
            int x = let.getCoord().getX();
            int y = let.getCoord().getY();
            game.getBoard().getSquare(x, y).placeLetter(let);
            placedLetters++;
        }
        return placedLetters;
    }
    
    private void clearLetterQueue(Game game) {
        game.getLetterQueue().getContents().clear();
    }
    
    private void clearNeighbours(Game game) {
        game.getLetterQueue().getNeighbours().getHorizontalNeighbours().clear();
        game.getLetterQueue().getNeighbours().getVerticalNeighbours().clear();
    }
    
    private void clearWordCreator(Game game) {
        game.getWordCreator().getContents().clear();
    }
    
    private void givePointsToCurrentPlayer(Game game, int placedLetters) {
        for (Word w : words) {
            game.getCurrentPlayer().addPoints(w.getPoints());
            String message = announcer.announce(Announcement.WORD_SCORE_MESSAGE, w);
            updateable.update(announcer.addIndentation(message));
        }
        giveBonusPointsToCurrentPlayer(game, placedLetters);
    }
    
    private void giveBonusPointsToCurrentPlayer(Game game, int placedLetters) {
        if (placedLetters == game.getCurrentPlayer().getPlayerRack().getRackMaxSize()) {
            game.getCurrentPlayer().addPoints(50);
            String message = announcer.announce(Announcement.BONUS_SCORE_MESSAGE);
            updateable.update(message);
        }
    }
    
    private void refillCurrentPlayerRack(Game game) {
        List<Letter> refilled = game.getCurrentPlayer().getPlayerRack().
                refillRack(game.getLetterBag());
        String message = announcer.announce(Announcement.REFILL_RACK_MESSAGE, refilled);
        updateable.update(announcer.addIndentation(message));
        if (game.getLetterBag().getContents().isEmpty()) {
            updateable.update(announcer.announce(Announcement.LETTERBAG_EMPTY_MESSAGE));
        }
    }

    private boolean letterQueueIsEmpty(Game game) {
        return game.getLetterQueue().getContents().isEmpty();
    }

    private boolean letterQueueIsValid(Game game) {
        return game.getLetterQueueValidator().letterQueueIsValid(game.getBoard());
    }
}
