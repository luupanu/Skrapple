/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple;

import fi.luupanu.skrapple.core.Letter;
import fi.luupanu.skrapple.core.LetterBag;
import fi.luupanu.skrapple.core.LetterType;

/**
 *
 * @author panu
 */
public class ManualTests {

    public static void main(String[] args) {
        LetterBag bag = new LetterBag();

        printContents(bag);

        System.out.println("\nsize: " + bag.getContents().size() + "\n");

        bag.removeLetter(26);

        printContents(bag);
        
        System.out.println("\nsize: " + bag.getContents().size() + "\n");
    }

    public static String helloWorldZ() {
        return "HelloWorldZ";
    }

    private static void printContents(LetterBag bag) {
        int i = 0;
        for (Letter let : bag.getContents()) {
            System.out.println(i + " " + let.getName());
            i++;
        }
    }
}
