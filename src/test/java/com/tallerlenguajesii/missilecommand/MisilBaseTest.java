package com.tallerlenguajesii.missilecommand;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import com.tallerlenguajesii.mock.MockMisilBase;

/**
 * Unit tests for the <code>MisilBase</code> class.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 3:06:15 PM
 */
public class MisilBaseTest {

    private final Point2D.Double initialCoordinates = new Point2D.Double(200, 300);
    private final Point2D.Double targetCoordinates = new Point2D.Double(100, 100);

    private MockMisilBase missileBase;

    @Before
    public void setup() {
        missileBase = new MockMisilBase(initialCoordinates);
    }

    @Test
    public void notInitiallyDestroyed() {
        assertFalse(missileBase.isDestroyed());
    }

    @Test
    public void fireMissile() {
        assertEquals(20, missileBase.getNumberOfMissilesRemaining());

        MisilAntiBalistico abm = missileBase.fireMissile(targetCoordinates);
        assertEquals(19, missileBase.getNumberOfMissilesRemaining());
        assertEquals(missileBase.getTopOfTriangle(), abm.getCurrentCoordinates());
    }

    @Test
    public void noMissilesLeft() {
        assertEquals(20, missileBase.getNumberOfMissilesRemaining());

        missileBase.setNumberOfMissilesRemaining(0);

        MisilAntiBalistico abm = missileBase.fireMissile(targetCoordinates);
        assertNull(abm);
        assertEquals(0, missileBase.getNumberOfMissilesRemaining());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void animate() {
        missileBase.animate();
    }

    @Test
    public void destroy() {
        assertFalse(missileBase.isDestroyed());
        missileBase.destroy();
        assertTrue(missileBase.isDestroyed());
    }

}
