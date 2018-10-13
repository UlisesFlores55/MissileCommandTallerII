package com.tallerlenguajesii.missilecommand;

import com.tallerlenguajesii.mock.MockMisilAntiBalistico;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.*;

/**
 * Unit tests for the <code>MisilAntiBalistico</code> class.
 *
 * @author Alan Tibbetts
 * @since Feb 21, 2010, 10:02:44 PM
 */
public class MisilAntiBalisticoTest {
    
    private final int gameAreaHeight = 350;
    private final int gameAreaWidth = 400;

    private MockMisilAntiBalistico abm;
    private final Point2D.Double sourceCoordinates = new Point2D.Double(40, 300);
    private final Point2D.Double targetCoordinates = new Point2D.Double(60, 40);

    @Before
    public void setUp() {
        abm = new MockMisilAntiBalistico(sourceCoordinates, targetCoordinates, 1);
    }

    @Test
    public void createMissile() {
        assertFalse(abm.hasReachedTarget());
        assertEquals(Color.BLUE, abm.getTrailColor());
        assertEquals(30, abm.getGeneratedBlastRadius());
    }

    @Test
    public void positiveIncrement() {
        assertTrue(abm.getXIncrement() >= 0.0);
    }

    @Test
    public void negativeIncrement() {
        Point2D.Double sourceCoordinates = new Point2D.Double(60, 300);
        Point2D.Double targetCoordinates = new Point2D.Double(40, 40);

        MockMisilAntiBalistico icbm2 = new MockMisilAntiBalistico(sourceCoordinates, targetCoordinates, 1);
        assertTrue(icbm2.getXIncrement() <= 0.0);
    }

    @Test
    public void yDecrementIsPositive() {
        assertTrue(abm.getYDecrement() > 0);
    }

    @Test
    public void moveMissile() {
        abm.animate();
        assertTrue(abm.getCurrentCoordinates().getX() > sourceCoordinates.getX());
        assertTrue(abm.getCurrentCoordinates().getY() < sourceCoordinates.getY());
    }

    @Test
    public void reachedTarget() {
        // Calculate the vertical distance between the source and target
        int verticalDifference = (int) (sourceCoordinates.getY() - targetCoordinates.getY());

        for (int i=0; i<verticalDifference; i++) {
            abm.animate();
        }
        assertTrue(abm.hasReachedTarget());
    }

    @Test
    public void destroy() {
        assertFalse(abm.isDestroyed());
        abm.destroy();
        assertTrue(abm.isDestroyed());
    }

    @Test (expected=UnsupportedOperationException.class)
    public void testBounds() {
        Rectangle bounds = abm.getBounds();
    }
}
