/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.luupanu.skrapple.core;

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
public class PlayerTest {

    private Player p;

    @Before
    public void setUp() {
        p = new Player("");
    }

    @Test
    public void whenPlayerIsCreatedSheHasZeroPoints() {
        assertEquals(0, p.getPlayerPoints());
    }

    @Test
    public void addingPointsWorks() {
        p.addPoints(150);
        assertEquals(150, p.getPlayerPoints());
    }

    @Test
    public void substractingPointsWorks() {
        p.addPoints(-150);
        assertEquals(-150, p.getPlayerPoints());
    }
}
