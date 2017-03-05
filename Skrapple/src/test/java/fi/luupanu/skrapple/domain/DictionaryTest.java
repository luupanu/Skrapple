package fi.luupanu.skrapple.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.List;
import org.junit.Before;
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
    public void setUp() throws IOException {
        d = new Dictionary("kotus-wordlist-fi");
        words = d.getWordList();
    }

    @Test
    public void wordListContains84043Words() {
        assertEquals(84043, words.size());
    }

    @Test
    public void getWordListReturnsEmptyListWhenFileNotFound() throws IOException {
        Dictionary dic = new Dictionary("drumpf");
        assertEquals(0, dic.getWordList().size());
    }
}
