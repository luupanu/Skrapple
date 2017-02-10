/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Dictionary;
import fi.luupanu.skrapple.domain.Word;
import java.util.List;

/**
 * WordChecker checks if the to-be-added words actually form something
 * meaningful using a Dictionary.
 *
 * @author panu
 */
public class WordChecker {

    private final Dictionary d;

    public WordChecker(Dictionary d) {
        this.d = d;
    }

    public boolean allWordsExistInDictionary(List<Word> words) {
        for (Word w : words) {
            if (!d.containsWord(w.toString().toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
