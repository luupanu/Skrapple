/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.filemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author panu
 */
public class FileReader {

    public List<String> readFile(String filename) {
        ArrayList<String> wordlist = new ArrayList<>(84420);
        try (Scanner s = new Scanner(new File(filename))) {
            while (s.hasNextLine()) {
                wordlist.add(s.nextLine());
            }
            s.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wordlist;
    }
}
