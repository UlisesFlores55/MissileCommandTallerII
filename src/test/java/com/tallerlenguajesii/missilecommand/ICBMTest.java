package com.tallerlenguajesii.missilecommand;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.*;

public class ICBMTest {

    private final int gameAreaHeight = 350;
    private final int gameAreaWidth = 400;

    private final Ciudad targetCiudad = new Ciudad(new Point2D.Double(120, gameAreaHeight - 5));

    private ICBM icbm;

    @Before
    public void setUp() {
        icbm = new ICBM(50, targetCiudad, 1);
    }

    @Test
    public void debeCrearMisil() {
        assertFalse(icbm.hasReachedTarget());
        assertEquals(Color.RED, icbm.getTrailColor());
        assertEquals(15, icbm.getGeneratedBlastRadius());
    }


    @Test
    public void debeMoverElMisil() {
        Point2D.Double initialCoordinates = icbm.getInitialCoordinates();
        icbm.animar();
        assertTrue(initialCoordinates.getX() < icbm.getCurrentCoordinates().getX());
        assertTrue(initialCoordinates.getY() < icbm.getCurrentCoordinates().getY());
    }

    @Test
    public void debeAlcanzarElObjetivo() {
        for (int i = 0; i< targetCiudad.coordenadas.getY(); i++) {
            icbm.animar();
        }
        assertTrue(icbm.hasReachedTarget());
    }

    @Test
    public void destruir() {
        assertFalse(icbm.isDestroyed());
        icbm.destruir();
        assertTrue(icbm.isDestroyed());
    }

    @Test (expected=UnsupportedOperationException.class)
    public void debeLanzarExcepcionAlObtenerLimitesDeDibujo() {
        icbm.getLimites();
    }
}
