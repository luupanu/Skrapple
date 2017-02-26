/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.constants;

import fi.luupanu.skrapple.domain.Word;

/**
 * Error messages are used to return feedback to the game logic of what went
 * wrong while trying to perform an action.
 *
 * @author panu
 */
public enum ErrorMessage implements Message {
    NO_ERRORS, LETTERQUEUE_IS_EMPTY, LETTERQUEUE_IS_NOT_VALID,
    WORD_IS_NOT_VALID, LETTERS_NOT_EXCHANGED, LETTERBAG_IS_EMPTY, GAME_IS_OVER;

    private Word word;

    public void setMessage(Word word) {
        this.word = word;
    }

    @Override
    public Word getMessage() {
        return word;
    }
}
