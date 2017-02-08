/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.domain;

import fi.luupanu.skrapple.filemanager.FileReader;
import java.util.List;

/**
 *
 * @author panu
 */
public class Dictionary {
    
    private final List<String> wordlist;
    
    public Dictionary() {
        this.wordlist = new FileReader().readFile("kotus-wordlist-fi");
    }
    
    public List<String> getWordList() {
        return wordlist;
    }
    
    public boolean containsWord(String word) {
        return wordlist.contains(word);
    }
}
