package com.tallerlenguajesii.missilecommand;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.awt.geom.Point2D;
import java.awt.*;

public class CiudadTest {

    private Ciudad ciudad;

    @Before
    public void configuracionTest() {
        ciudad = new Ciudad(new Point2D.Double(20, 300));
    }

    @Test
    public void debeIniciarSinDestruirse() {
        assertFalse(ciudad.estaDestruido());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void debeArrojarExepcionAlAnimarCiudad() {
        ciudad.animar();
    }

    @Test
    public void debeDestruirCiudad() {
        assertFalse(ciudad.estaDestruido());
        ciudad.destruir();
        assertTrue(ciudad.estaDestruido());
    }

    @Test
    public void debeEstarEntreLosLimites() {
        Rectangle bounds = ciudad.getLimites();
        assertNotNull(bounds);
        assertEquals(20, (int) bounds.getHeight());
        assertEquals(20, (int) bounds.getWidth());
        
        Point point = new Point(25, 295);
        assertTrue(bounds.contains(point));

        point = new Point(10,10);
        assertFalse(bounds.contains(point));
    }
}
