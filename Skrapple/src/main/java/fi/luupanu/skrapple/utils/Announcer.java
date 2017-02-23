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
 *
 * @author panu
 */
public class Announcer {
    
    private final SkrappleGame s;
    
    public Announcer(SkrappleGame s) {
        this.s = s;
    }
    
    public String announce(Announcement a) {
        return parseDefaultMessage(a.getMessage()) + "\n";
    }
    
    public String announce(Announcement a, Word word) {
        return parseMessageWithWord(a.getMessage(), word) + "\n";
    }
    
    public String announce(Announcement a, List<Letter> letters) {
        return parseMessageWithListOfLetters(a.getMessage(), letters) + "\n";
    }
    
    public String addIndentation(String message) {
        return "  >" + message;
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
