package com.tallerlenguajesii.missilecommand;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.awt.*;

public class MisilAntiBalisticoTest {
    
    private final int gameAreaHeight = 350;
    private final int gameAreaWidth = 400;

    private MisilAntiBalistico abm;
    private final Point2D.Double sourceCoordinates = new Point2D.Double(40, 300);
    private final Point2D.Double targetCoordinates = new Point2D.Double(60, 40);

    @Before
    public void setUp() {
        abm = new MisilAntiBalistico(sourceCoordinates, targetCoordinates, 1);
    }

    @Test
    public void createMissile() {
        assertFalse(abm.alcanzoObjetivo());
        assertEquals(Color.BLUE, abm.getColorDelRastro());
        assertEquals(30, abm.getRadioGeneradoPorExplosion());
    }

    @Test
    public void debeMoverMisil() {
        abm.animar();
        assertTrue(abm.getCoordenadasActuales().getX() > sourceCoordinates.getX());
        assertTrue(abm.getCoordenadasActuales().getY() < sourceCoordinates.getY());
    }

    @Test
    public void debeAlcanzarObjetivo() {
        int verticalDifference = (int) (sourceCoordinates.getY() - targetCoordinates.getY());

        for (int i=0; i<verticalDifference; i++) {
            abm.animar();
        }
        assertTrue(abm.alcanzoObjetivo());
    }

    @Test
    public void debeDestruirse() {
        assertFalse(abm.estaDestruido());
        abm.destruir();
        assertTrue(abm.estaDestruido());
    }

    @Test (expected=UnsupportedOperationException.class)
    public void debeLanzarExcepcionAltratarDeObtenerSusLimitesDeDibujo() {
        abm.getLimites();
    }
}
