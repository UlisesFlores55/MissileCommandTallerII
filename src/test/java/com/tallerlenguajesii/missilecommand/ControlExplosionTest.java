package com.tallerlenguajesii.missilecommand;

import com.tallerlenguajesii.mock.MockControlExplosion;
import com.tallerlenguajesii.mock.MockICBM;
import com.tallerlenguajesii.mock.MockMisilAntiBalistico;
import com.tallerlenguajesii.mock.MockGraphics2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Point2D;

/**
 * Unit tests for the <code>ControlExplosion</code> class.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 9:06:57 PM
 */
public class ControlExplosionTest {

    private static final int DEFENSIVE_OBJECT_Y_COORDINATE = 325;

    private static final int[] CITY_X_COORDINATES = new int[]{60, 100, 140, 240, 280, 320};
    private static final int[] MISSILE_BASE_X_COORDINATES = new int[]{15, 185, 355};

    private MockControlExplosion mockExplosionController;
    private List<Misil> misils;
    private List<ObjetoDefensivo> objetoDefensivos;

    @Before
    public void setup() {
        misils = new ArrayList<Misil>();

        objetoDefensivos = new ArrayList<ObjetoDefensivo>();

        for (int xCoordinate : CITY_X_COORDINATES) {
            Ciudad ciudad = new Ciudad(new Point2D.Double(xCoordinate, DEFENSIVE_OBJECT_Y_COORDINATE));
            objetoDefensivos.add(ciudad);
        }

        for (int xCoordinate : MISSILE_BASE_X_COORDINATES) {
            MisilBase misilBase = new MisilBase(new Point2D.Double(xCoordinate, DEFENSIVE_OBJECT_Y_COORDINATE));
            objetoDefensivos.add(misilBase);
        }

        mockExplosionController = new MockControlExplosion(misils, objetoDefensivos);
    }


    @Test
    public void afterInitialisation() {
        assertEquals(0, mockExplosionController.getMissiles().size());
        assertEquals(9, mockExplosionController.getDefensiveObjects().size());
        assertEquals(0, mockExplosionController.numberOfExplosions());

        assertTrue(mockExplosionController.areAllExplosionComplete());
    }

    @Test
    public void addMissile() {
        ICBM icbm = new ICBM(150, objetoDefensivos.get(5), 3);
        misils.add(icbm);
        assertEquals(1, mockExplosionController.getMissiles().size());
    }

    @Test
    public void explodeMissile() {
        MockICBM icbm = new MockICBM(150, objetoDefensivos.get(5), 3);
        icbm.setCurrentCoordinates(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        assertEquals(0, mockExplosionController.numberOfExplosions());
        assertFalse(icbm.isDestroyed());

        mockExplosionController.explodeMissile(icbm);
        assertEquals(1, mockExplosionController.numberOfExplosions());
        assertTrue(icbm.isDestroyed());
    }

    @Test
    public void collisionBetweenMissileAndExplosion() {
        MockGraphics2D mockGraphicsContext = new MockGraphics2D();

        MockICBM icbm = new MockICBM(150, objetoDefensivos.get(5), 3);
        icbm.setCurrentCoordinates(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        MockMisilAntiBalistico abm = new MockMisilAntiBalistico(new Point2D.Double(185, DEFENSIVE_OBJECT_Y_COORDINATE), new Point2D.Double(148, 210), 3);
        abm.setCurrentCoordinates(abm.getTargetCoordinates());
        misils.add(abm);

        mockExplosionController.explodeMissile(abm);
        assertEquals(1, mockExplosionController.numberOfExplosions());

        for (int i = 0; i < 20; i++) {
            mockExplosionController.animateExplosions();
            mockExplosionController.drawExplosions(mockGraphicsContext);
        }

        assertFalse(mockExplosionController.areAllExplosionComplete());

        mockExplosionController.detectCollisions();
        assertTrue(icbm.isDestroyed());
        assertEquals(2, mockExplosionController.numberOfExplosions());
    }

    @Test
    public void removalOfCompletedExplosions() {
        MockGraphics2D mockGraphicsContext = new MockGraphics2D();

        MockICBM icbm = new MockICBM(150, objetoDefensivos.get(5), 3);
        icbm.setCurrentCoordinates(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        MockMisilAntiBalistico abm = new MockMisilAntiBalistico(new Point2D.Double(185, DEFENSIVE_OBJECT_Y_COORDINATE), new Point2D.Double(148, 210), 3);
        abm.setCurrentCoordinates(abm.getTargetCoordinates());
        misils.add(abm);

        mockExplosionController.explodeMissile(abm);
        mockExplosionController.explodeMissile(icbm);

        assertEquals(2, mockExplosionController.numberOfExplosions());

        for (int i = 0; i < 80; i++) {
            mockExplosionController.animateExplosions();
            mockExplosionController.drawExplosions(mockGraphicsContext);
        }

        assertEquals(2, mockExplosionController.numberOfExplosions());

        mockExplosionController.removeCompletedExplosions();
        assertEquals(0, mockExplosionController.numberOfExplosions());        
    }

}

