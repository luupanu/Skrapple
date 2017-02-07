package fi.luupanu.skrapple.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import fi.luupanu.skrapple.domain.Dictionary;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author panu
 */
public class DictionaryTest {
    
    private Dictionary d;
    private List<String> words;

    @Before
    public void setUp() {
        d = new Dictionary();
        words = d.getWordList();
    }
    
    @Test
    public void wordListContains84420Words() {
        assertEquals(84420, words.size());
    }
    
    @Test
    public void canFindWordsFromWordList() {
        assertEquals(true, d.containsWord("öylätti"));
        assertEquals(true, d.containsWord("aakkonen"));
        assertEquals(true, d.containsWord("puutarhaneuvos"));
    }
    
    @Test
    public void cantFindMadeUpWordsFromWordList() {
        assertEquals(false, d.containsWord("donaldtrump"));
        assertEquals(false, d.containsWord("pidili"));
        assertEquals(false, d.containsWord("prumppering"));
    }
}
