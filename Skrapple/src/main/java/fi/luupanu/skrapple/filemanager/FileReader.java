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

/**
 * FileReader is used to create a Dictionary from a file.
 * 
 * @author panu
 */
public class FileReader {

    public List<String> readFile(String filename) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>(84420);
        Scanner s = new Scanner(new File(filename));
            while (s.hasNextLine()) {
                list.add(s.nextLine());
            }
            s.close();
        return list;
    }
}
