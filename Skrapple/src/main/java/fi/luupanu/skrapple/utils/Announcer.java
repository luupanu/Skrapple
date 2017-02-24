/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.utils;

import fi.luupanu.skrapple.constants.Announcement;
import fi.luupanu.skrapple.domain.Letter;
import fi.luupanu.skrapple.domain.Word;
import fi.luupanu.skrapple.logic.SkrappleGame;
import java.util.List;

/**
 * Announcer is used to announce game changes to the players using
 * Announcements.
 *
 * @author panu
 */
public class Announcer {

    private final SkrappleGame s;

    /**
     * Creates a new announcer.
     *
     * @param s SkrappleGame
     */
    public Announcer(SkrappleGame s) {
        this.s = s;
    }

    /**
     * Parse a default announcement. Replaces player name with the current
     * player.
     *
     * @param a the announcement to be made
     * @return parsed announcement
     */
    public String announce(Announcement a) {
        return parseDefaultMessage(a.getMessage()) + "\n";
    }

    /**
     * Replace an announcement with replaceable word elements with the given
     * word.
     *
     * @param a the announcement to be made
     * @param word the word to replace the generics with
     * @return parsed announcement
     */
    public String announce(Announcement a, Word word) {
        return parseMessageWithWord(a.getMessage(), word) + "\n";
    }

    /**
     * Replace an announcement with replaceable letter elements with the given
     * letters.
     *
     * @param a the announcement to be made
     * @param letters a list of letters to replace the generics with
     * @return parsed announcement
     */
    public String announce(Announcement a, List<Letter> letters) {
        return parseMessageWithListOfLetters(a.getMessage(), letters) + "\n";
    }

    /**
     * Optionally prepend a custom indentation to some messages.
     *
     * @param message the message the indentation will be prepended to
     * @return the message with prepended indentation
     */
    public String addIndentation(String message) {
        return "  > " + message;
    }

    private String parseDefaultMessage(String message) {
        message = message.replace("<PLAYER>", s.getGame().getCurrentPlayer().getPlayerName());
        return message;
    }

    private String parseMessageWithWord(String message, Word word) {
        message = message.replace("<WORD>", word.toString());
        message = message.replace("<WORDPOINTS>", String.valueOf(word.getPoints()));
        return message;
    }

    private String parseMessageWithListOfLetters(String message, List<Letter> letters) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < letters.size(); i++) {
            sb.append(letters.get(i).toString());
            if (i < letters.size() - 1) {
                sb.append(", ");
            }
        }
        message = message.replace("<LETTERS>", sb.toString());
        return message;
    }
}
