/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.filemanager;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * FileReader is used to create a Dictionary from a file.
 *
 * @author panu
 */
public class FileReader {

    /**
     * Reads a text file.
     *
     * @param filename the file name
     * @return the read file as a list of strings
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<String> readFile(String filename) throws FileNotFoundException, IOException {
        ArrayList<String> list = new ArrayList<>(84420);
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
                Scanner s = new Scanner(stream)) {
            while (s.hasNextLine()) {
                list.add(s.nextLine());
            }
        }
        return list;
    }

    /**
     * Reads an image.
     * @param filename the file name
     * @return the read file as an image
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Image readImage(String filename) throws FileNotFoundException, IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        BufferedImage img;
        img = ImageIO.read(stream);
        return img;
    }
}
