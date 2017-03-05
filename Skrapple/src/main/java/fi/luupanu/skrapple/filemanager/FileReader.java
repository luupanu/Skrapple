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
 * FileReader is used to read files.
 *
 * @author panu
 */
public class FileReader {

    /**
     * Reads a text file.
     *
     * @param filename the file name
     * @return the read file as a list of strings
     * @throws FileNotFoundException if file not found
     * @throws IOException if something went wrong with I/O
     */
    public List<String> readFile(String filename) throws FileNotFoundException, IOException {
        ArrayList<String> list = new ArrayList<>(84043);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        Scanner s;
        try {
            s = new Scanner(stream);
        } catch (NullPointerException n) {
            return new ArrayList(0);
        }
        while (s.hasNextLine()) {
            list.add(s.nextLine());
        }
        return list;
    }

    /**
     * Reads an image.
     *
     * @param filename the file name
     * @return the read file as an image
     * @throws FileNotFoundException if file not found
     * @throws IOException if something went wrong with I/O
     */
    public Image readImage(String filename) throws FileNotFoundException, IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
        BufferedImage img;
        img = ImageIO.read(stream);
        return img;
    }
}
