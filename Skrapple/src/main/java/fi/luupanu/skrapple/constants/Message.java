/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.constants;

/**
 * Message is an abstract interface for returning messages.
 *
 * @author panu
 * @param <T> a generic object
 */
public interface Message<T> {

    /**
     * Get a message contained within the class.
     *
     * @return a generic object
     */
    public T getMessage();
}
