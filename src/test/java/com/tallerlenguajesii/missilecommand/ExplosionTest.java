package com.tallerlenguajesii.missilecommand;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.Before;

import java.awt.geom.Point2D;
import java.awt.*;

public class ExplosionTest {

    private Explosion explosion;
    private Graphics2D mockGraphicsContext;

    @Before
    public void configuracionTest() {
        this.explosion = new Explosion(new Point2D.Double(100,100), 30);
        this.mockGraphicsContext = mock(Graphics2D.class);
    }

    @Test
    public void explostionCreated() {
        assertEquals(0, explosion.getRadioActual());
        assertEquals(30, explosion.getRadioMaximoDeExplosion());
        assertNull(explosion.getLimites());
    }

    @Test
    public void expandingExplosion() {
        explosion.animar();
        assertEquals(1, explosion.getRadioActual());
    }

    @Test
    public void contractingExplosion() {
        explosion.setRadioActual(20);
        explosion.setExpandiendo(false);

        explosion.animar();
        assertEquals(19, explosion.getRadioActual());
    }

    @Test
    public void completeExplostion() {
        while (!explosion.estaCompleta()) {
            explosion.animar();
        }
        assertEquals(0, explosion.getRadioActual());
    }

    @Test
    public void destroy() {
        assertFalse(explosion.estaCompleta());
        explosion.destruir();
        assertTrue(explosion.estaCompleta());
    }

    @Test
    public void debeEstarLimitado() {
        for (int i=0; i<10; i++) {
            explosion.animar();
            explosion.dibujar(this.mockGraphicsContext);
        }

        Rectangle bounds = this.explosion.getLimites();
        assertNotNull(bounds);
    }
}
