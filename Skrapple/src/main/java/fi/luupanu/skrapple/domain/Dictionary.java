/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.filemanager.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * The Dictionary class houses the word list used to evaluate if a word will be
 * approved or not.
 *
 * @author panu
 */
public class Dictionary {

    private final List<String> wordlist;

    /**
     * Loads the word list used by the dictionary.
     *
     * @param filename the file location
     * @throws java.io.FileNotFoundException if file not found
     * @throws java.io.IOException if something went wrong with I/O
     */
    public Dictionary(String filename) throws FileNotFoundException, IOException {
        wordlist = loadWordList(filename);
    }

    private List<String> loadWordList(String filename)
            throws FileNotFoundException, IOException {
        return new FileReader().readFile(filename);
    }

    public List<String> getWordList() {
        return wordlist;
    }

    /**
     * Returns if the dictionary used by the game contains the word provided.
     *
     * @param word the word to be checked
     * @return true if the word is found
     */
    public boolean containsWord(String word) {
        return wordlist.contains(word);
    }
}
