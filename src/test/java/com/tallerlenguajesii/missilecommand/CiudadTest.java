package com.tallerlenguajesii.missilecommand;

import com.tallerlenguajesii.missilecommand.Ciudad;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.*;

/**
 * Unit tests for the <code>Ciudad</code> class.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 3:09:28 PM
 */
public class CiudadTest {

    private Ciudad ciudad;

    @Before
    public void setup() {
        ciudad = new Ciudad(new Point2D.Double(20, 300));
    }

    @Test
    public void notInitiallyDestroyed() {
        assertFalse(ciudad.isDestroyed());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void animate() {
        ciudad.animate();
    }

    @Test
    public void destroyCity() {
        assertFalse(ciudad.isDestroyed());
        ciudad.destroy();
        assertTrue(ciudad.isDestroyed());
    }

    @Test
    public void testBounds() {
        Rectangle bounds = ciudad.getBounds();
        assertNotNull(bounds);
        assertEquals(20, (int) bounds.getHeight());
        assertEquals(20, (int) bounds.getWidth());
        
        Point point = new Point(25, 295);
        assertTrue(bounds.contains(point));

        point = new Point(10,10);
        assertFalse(bounds.contains(point));
    }
}
