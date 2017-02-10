/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.filemanager.FileReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Dictionary class houses the word list used to evaluate if a word will be
 * approved or not.
 *
 * @author panu
 */
public class Dictionary {

    private final List<String> wordlist;

    public Dictionary() {
        wordlist = loadWordList("kotus-wordlist-fi");
    }

    private List<String> loadWordList(String filename) {
        try {
            return new FileReader().readFile(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<String> getWordList() {
        return wordlist;
    }

    public boolean containsWord(String word) {
        return wordlist.contains(word);
    }
}
