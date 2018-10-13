package com.tallerlenguajesii.missilecommand;

import com.tallerlenguajesii.mock.MockICBM;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.*;

/**
 * Unit tests for the <code>ICBM</code> test.
 * 
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 2:27:40 PM
 */
public class ICBMTest {

    private final int gameAreaHeight = 350;
    private final int gameAreaWidth = 400;

    private final Ciudad targetCiudad = new Ciudad(new Point2D.Double(120, gameAreaHeight - 5));

    private MockICBM icbm;

    @Before
    public void setUp() {
        icbm = new MockICBM(50, targetCiudad, 1);
    }

    @Test
    public void createMissile() {
        assertFalse(icbm.hasReachedTarget());
        assertEquals(Color.RED, icbm.getTrailColor());
        assertEquals(15, icbm.getGeneratedBlastRadius());
    }

    @Test
    public void yIncrementIsPositive() {
        assertTrue(icbm.getYIncrement() > 0);
    }

    @Test
    public void positiveIncrement() {
        assertTrue(icbm.getXIncrement() >= 0.0);
    }

    @Test
    public void negativeIncrement() {
        MockICBM icbm2 = new MockICBM(250, targetCiudad, 1);
        assertTrue(icbm2.getXIncrement() <= 0.0);
    }

    @Test
    public void moveMissile() {
        Point2D.Double initialCoordinates = icbm.getInitialCoordinates();
        icbm.animate();
        assertTrue(initialCoordinates.getX() < icbm.getCurrentCoordinates().getX());
        assertTrue(initialCoordinates.getY() < icbm.getCurrentCoordinates().getY());
    }

    @Test
    public void reachedTarget() {
        for (int i = 0; i< targetCiudad.coordinates.getY(); i++) {
            icbm.animate();
        }
        assertTrue(icbm.hasReachedTarget());
    }

    @Test
    public void destroy() {
        assertFalse(icbm.isDestroyed());
        icbm.destroy();
        assertTrue(icbm.isDestroyed());
    }

    @Test (expected=UnsupportedOperationException.class)
    public void testBounds() {
        Rectangle bounds = icbm.getBounds();
    }
}
