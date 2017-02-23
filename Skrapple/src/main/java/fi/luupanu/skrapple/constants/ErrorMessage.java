/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.constants;

import fi.luupanu.skrapple.domain.Word;

/**
 *
 * @author panu
 */
public enum ErrorMessage implements Message {
    NO_ERRORS, LETTERQUEUE_IS_EMPTY, LETTERQUEUE_IS_NOT_VALID, WORD_IS_NOT_VALID,
    LETTERS_NOT_EXCHANGED, GAME_IS_OVER;

    private Word word;

    public void setWord(Word word) {
        this.word = word;
    }
    
    public Word getWord() {
        return word;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
