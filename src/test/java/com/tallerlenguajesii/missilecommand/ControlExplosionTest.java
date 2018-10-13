package com.tallerlenguajesii.missilecommand;

import com.tallerlenguajesii.mock.MockGraphics2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Point2D;

public class ControlExplosionTest {

    private static final int DEFENSIVE_OBJECT_Y_COORDINATE = 325;

    private ControlExplosion mockExplosionController;
    private List<Misil> misils;
    private List<ObjetoDefensivo> objetoDefensivos;

    @Before
    public void setup() {
        misils = new ArrayList<Misil>();
        objetoDefensivos = new ArrayList<ObjetoDefensivo>();
        objetoDefensivos.add(new Ciudad(new Point2D.Double()));
    }


    @Test
    public void despuesDeInstanciar() {
        mockExplosionController = new ControlExplosion(this.misils, this.objetoDefensivos);
        assertEquals(0, mockExplosionController.misils.size());
        assertEquals(1, mockExplosionController.objetoDefensivos.size());
        assertEquals(0, mockExplosionController.explosions.size());

        assertTrue(mockExplosionController.areAllExplosionComplete());
    }

    @Test
    public void debeAgregarMisil() {
        mockExplosionController = new ControlExplosion(this.misils, this.objetoDefensivos);
        ICBM icbm = new ICBM(150, objetoDefensivos.get(0), 3);
        this.misils.add(icbm);

        assertEquals(1, mockExplosionController.misils.size());
    }

    @Test
    public void debeExplotarMisil() {
        mockExplosionController = new ControlExplosion(this.misils, this.objetoDefensivos);
        ICBM icbm = new ICBM(150, objetoDefensivos.get(0), 3);
        icbm.currentCoordinates.setLocation(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        assertEquals(0, mockExplosionController.explosions.size());
        assertFalse(icbm.isDestroyed());

        mockExplosionController.explodeMissile(icbm);
        assertEquals(1, mockExplosionController.explosions.size());
        assertTrue(icbm.isDestroyed());
    }

    @Test
    public void debeExplotarCuandoCoisionaContraElMisil() {
        mockExplosionController = new ControlExplosion(this.misils, this.objetoDefensivos);

        MockGraphics2D mockGraphicsContext = new MockGraphics2D();

        ICBM icbm = new ICBM(150, objetoDefensivos.get(0), 3);
        icbm.currentCoordinates.setLocation(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        MisilAntiBalistico abm = new MisilAntiBalistico(new Point2D.Double(185, DEFENSIVE_OBJECT_Y_COORDINATE), new Point2D.Double(148, 210), 3);
        abm.currentCoordinates.setLocation(abm.getTargetCoordinates());
        misils.add(abm);

        mockExplosionController.explodeMissile(abm);
        assertEquals(1, mockExplosionController.explosions.size());

        for (int i = 0; i < 20; i++) {
            mockExplosionController.animateExplosions();
            mockExplosionController.drawExplosions(mockGraphicsContext);
        }

        assertFalse(mockExplosionController.areAllExplosionComplete());

        mockExplosionController.detectCollisions();
        assertTrue(icbm.isDestroyed());
        assertEquals(2, mockExplosionController.explosions.size());
    }

    @Test
    public void removalOfCompletedExplosions() {
        mockExplosionController = new ControlExplosion(this.misils, this.objetoDefensivos);
        MockGraphics2D mockGraphicsContext = new MockGraphics2D();

        ICBM icbm = new ICBM(150, objetoDefensivos.get(0), 3);
        icbm.currentCoordinates.setLocation(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        MisilAntiBalistico abm = new MisilAntiBalistico(new Point2D.Double(185, DEFENSIVE_OBJECT_Y_COORDINATE), new Point2D.Double(148, 210), 3);
        abm.currentCoordinates.setLocation(abm.getTargetCoordinates());
        misils.add(abm);

        mockExplosionController.explodeMissile(abm);
        mockExplosionController.explodeMissile(icbm);

        assertEquals(2, mockExplosionController.explosions.size());

        for (int i = 0; i < 80; i++) {
            mockExplosionController.animateExplosions();
            mockExplosionController.drawExplosions(mockGraphicsContext);
        }

        assertEquals(2, mockExplosionController.explosions.size());

        mockExplosionController.removeCompletedExplosions();
        assertEquals(0, mockExplosionController.explosions.size());
    }

}

