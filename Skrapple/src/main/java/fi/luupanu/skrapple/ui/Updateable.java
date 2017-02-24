/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.ui;

/**
 * An interface to handle updating.
 *
 * @author panu
 */
public interface Updateable {

    /**
     * Update with a message string.
     *
     * @param message a message string
     */
    void update(String message);
}
