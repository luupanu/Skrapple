/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.constants;

/**
 *
 * @author panu
 */
public enum Announcement implements Message {
    WELCOME_MESSAGE("Welcome to Skrapple!\n"),
    TURN_START_MESSAGE("It's <PLAYER>'s turn."),
    LETTERQUEUE_EMPTY_ERROR("You didn't place any letters, silly you! ^_^"),
    LETTERQUEUE_NOT_VALID_ERROR("Can't make an invalid move, now can we? ^_^"),
    WORD_NOT_VALID_ERROR("<WORD>? That's not a word! ^_^"),
    WORD_SCORE_MESSAGE("Word <WORD> scored <WORDPOINTS> points."),
    BONUS_SCORE_MESSAGE("You used all seven letter tiles! 50 bonus points scored."),
    REFILL_RACK_MESSAGE("Added new letters <LETTERS> to the rack.");

    private final String message;

    private Announcement(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}
