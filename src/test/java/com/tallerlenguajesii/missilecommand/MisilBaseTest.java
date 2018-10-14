package com.tallerlenguajesii.missilecommand;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.*;
import java.awt.geom.Point2D;


public class MisilBaseTest {

    private final Point2D.Double coordenadasIniciales = new Point2D.Double(200, 300);
    private final Point2D.Double coordenadasObjetivo = new Point2D.Double(100, 100);
    private MisilBase misilBase;
    private Graphics2D graphics2D = mock(Graphics2D.class);

    @Before
    public void configuracionTest() {
        this.misilBase = new MisilBase(coordenadasIniciales);
    }

    @Test
    public void debeEstarIncialmenteNoDestruida() {
        assertFalse(misilBase.estaDestruido());
    }

    @Test
    public void dispararMisil() {
        assertEquals(20, misilBase.getCantidadMisiles());

        MisilAntiBalistico abm = misilBase.dispararMisil(coordenadasObjetivo);
        assertEquals(19, misilBase.getCantidadMisiles());
        assertEquals(misilBase.getCoordenadas(), abm.getCoordenadasActuales());
    }

    @Test
    public void sinMisilesRestantes() {
        MisilBase misilBase = new MisilBase(coordenadasIniciales);
        assertEquals(20, misilBase.getCantidadMisiles());

        misilBase.setCantidadMisiles(0);

        MisilAntiBalistico abm = misilBase.dispararMisil(coordenadasObjetivo);
        assertNull(abm);
        assertEquals(0, misilBase.getCantidadMisiles());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void debeObtenerExcepcionAlAnimar() {
        this.misilBase.animar();
    }

    @Test
    public void debeDestruir() {
        assertFalse(this.misilBase.estaDestruido());
        this.misilBase.destruir();
        assertTrue(this.misilBase.estaDestruido());
    }

    @Test
    public void debeDibujar() {
        this.misilBase.dibujar(this.graphics2D);
        verify(this.graphics2D, times(1)).setPaint(eq(Color.WHITE));
    }

    @Test
    public void debeResetear() {
        this.misilBase.destruir();
        assertTrue(this.misilBase.estaDestruido());
        this.misilBase.reiniciar();
        assertEquals(20, this.misilBase.getCantidadMisiles());
    }
}
