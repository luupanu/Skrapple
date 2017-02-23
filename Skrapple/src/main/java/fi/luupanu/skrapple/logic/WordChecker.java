/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Word;
import java.util.ArrayList;
import java.util.List;

/**
 * WordChecker checks if the to-be-added words actually form something
 * meaningful using a Dictionary.
 *
 * @author panu
 */
public class WordChecker {

    private final Dictionary d;

    /**
     * Creates a new WordChecker.
     *
     * @param d the dictionary used by the game
     */
    public WordChecker(Dictionary d) {
        this.d = d;
    }

    /**
     * This method takes a list of Words and checks if all of them were found in
     * the dictionary.
     *
     * @param words a list of words to be checked
     * @return a list of words not found in the dictionary (empty if found all)
     */
    public List<Word> allWordsExistInDictionary(List<Word> words) {
        List<Word> notFound = new ArrayList<>();
        for (Word w : words) {
            if (!d.containsWord(w.toString().toLowerCase())) {
                notFound.add(w);
            }
        }
        return notFound;
    }
}
