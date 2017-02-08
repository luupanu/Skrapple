/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

import fi.luupanu.skrapple.domain.Dictionary;
import java.util.List;

/**
 *
 * @author panu
 */
public class WordChecker {
    /*  TO-DO: a working class WordChecker. WordChecker will check if the
        to-be-added words actually form something meaningful using a dictionary.
    */
    
    private final Dictionary d;
    
    public WordChecker(Dictionary d) {
        this.d = d;
    }
    
    public boolean allWordsExistInDictionary(List<String> words) {
        for (String w : words) {
            if (!d.containsWord(w)) {
                return false;
            }
        }
        return true;
    }
}
