package com.tallerlenguajesii.missilecommand;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;


public class MisilBaseTest {

    private final Point2D.Double coordenadasIniciales = new Point2D.Double(200, 300);
    private final Point2D.Double coordenadasObjetivo = new Point2D.Double(100, 100);

    @Test
    public void incialmenteNoDestruida() {
        MisilBase misilBase = new MisilBase(coordenadasIniciales);
        assertFalse(misilBase.estaDestruida());
    }

    @Test
    public void dispararMisil() {
        MisilBase misilBase = new MisilBase(coordenadasIniciales);
        assertEquals(20, misilBase.getCantidadMisiles());

        MisilAntiBalistico abm = misilBase.fireMissile(coordenadasObjetivo);
        assertEquals(19, misilBase.getCantidadMisiles());
        assertEquals(misilBase.getCoordenadas(), abm.getCurrentCoordinates());
    }

    @Test
    public void sinMisilesRestantes() {
        MisilBase misilBase = new MisilBase(coordenadasIniciales);
        assertEquals(20, misilBase.getCantidadMisiles());

        misilBase.setCantidadMisiles(0);

        MisilAntiBalistico abm = misilBase.fireMissile(coordenadasObjetivo);
        assertNull(abm);
        assertEquals(0, misilBase.getCantidadMisiles());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void animar() {
        MisilBase misilBase = new MisilBase(coordenadasIniciales);
        misilBase.animar();
    }

    @Test
    public void destruir() {
        MisilBase misilBase = new MisilBase(coordenadasIniciales);
        assertFalse(misilBase.estaDestruida());
        misilBase.destruir();
        assertTrue(misilBase.estaDestruida());
    }

}
