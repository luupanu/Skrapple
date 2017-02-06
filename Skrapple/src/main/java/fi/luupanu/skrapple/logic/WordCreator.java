/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.logic;

/**
 *
 * @author panu
 */
public class WordCreator {
    /*  TO-DO: a working class WordCreator. WordCreator takes the LetterQueue
        and tries to form a word or multiple words based on the letter positions.
        
        - Need to check if there is at least one letter that touches
        an existing letter on the board. (Unless first word of the game!)
        - Need to check whether the found letter touches an existing letter
        vertically or horizontally.
        - If the letter touches both vertically & horizontally, both directions
        have a word to be created.
        - Horizontally aligned words are always from left to right.
        - Vertically aligned words are always from top to bottom.
    
        Maybe split to two classes, e.g. "LetterQueueValidator" and
        WordCreator?
    */
}
