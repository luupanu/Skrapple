/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.constants;

/**
 * Announcements are generic strings with replaceable elements that are used to
 * announce game events to the player.
 *
 * @author panu
 */
public enum Announcement implements Message {
    WELCOME_MESSAGE("Welcome to Skrapple!"),
    TURN_START_MESSAGE("\nIt's <PLAYER>'s turn."),
    PLAYER_RESIGNED_MESSAGE("<PLAYER> resigned!"),
    PLAYER_EXCHANGED_MESSAGE("<PLAYER> exchanged their letters."),
    LETTERQUEUE_EMPTY_MESSAGE("You didn't place any letters, silly you! ^_^"),
    LETTERQUEUE_NOT_VALID_MESSAGE("Can't make an invalid move, now can we? ^_^"),
    WORD_NOT_VALID_MESSAGE("<WORD>? That's not a word! ^_^"),
    WORD_SCORE_MESSAGE("Word <WORD> scored <WORDPOINTS> points."),
    BONUS_SCORE_MESSAGE("You used all seven letter tiles! 50 bonus points scored."),
    REFILL_RACK_MESSAGE("Added new letters <LETTERS> to the rack."),
    LETTERBAG_EMPTY_MESSAGE("The letter bag is empty!"),
    EXCHANGE_FAILED_MESSAGE("The letter bag has less letters than you're trying to exchange!"),
    EXCHANGE_CANNOT_START_MESSAGE("Are you trying to move and exchange at the same time? That's nitwitted! ^_^"),
    RACK_EMPTY_MESSAGE("<PLAYER> has no letters! The game is over!"),
    GAME_OVER_MESSAGE("\nGood game! ^_^");

    private String message;

    private Announcement(String message) {
        setMessage(message);
    }

    private void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
