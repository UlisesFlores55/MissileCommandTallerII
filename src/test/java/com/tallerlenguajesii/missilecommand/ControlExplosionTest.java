package com.tallerlenguajesii.missilecommand;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Point2D;

public class ControlExplosionTest {

    private static final int DEFENSIVE_OBJECT_Y_COORDINATE = 325;

    private ControlExplosion mockExplosionController;
    private List<Misil> misils;
    private List<ObjetoDefensivo> objetoDefensivos;
    private Graphics2D mockGraphicsContext = mock(Graphics2D.class);

    @Before
    public void setup() {
        misils = new ArrayList<Misil>();
        objetoDefensivos = new ArrayList<ObjetoDefensivo>();
        objetoDefensivos.add(new Ciudad(new Point2D.Double()));
        this.mockExplosionController = new ControlExplosion(this.misils, this.objetoDefensivos);
    }


    @Test
    public void despuesDeInstanciar() {
        assertEquals(0, mockExplosionController.misiles.size());
        assertEquals(1, mockExplosionController.objetoDefensivo.size());
        assertEquals(0, mockExplosionController.explosiones.size());

        assertTrue(mockExplosionController.todasExplosionesCompletadas());
    }

    @Test
    public void debeAgregarMisil() {
        mockExplosionController = new ControlExplosion(this.misils, this.objetoDefensivos);
        ICBM icbm = new ICBM(150, objetoDefensivos.get(0), 3);
        this.misils.add(icbm);

        assertEquals(1, mockExplosionController.misiles.size());
    }

    @Test
    public void debeExplotarMisil() {
        ICBM icbm = new ICBM(150, objetoDefensivos.get(0), 3);
        icbm.coordendasActuales.setLocation(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        assertEquals(0, mockExplosionController.explosiones.size());
        assertFalse(icbm.estaDestruido());

        mockExplosionController.explotarMisil(icbm);
        assertEquals(1, mockExplosionController.explosiones.size());
        assertTrue(icbm.estaDestruido());
    }

    @Test
    public void debeExplotarCuandoCoisionaContraElMisil() {
        ICBM icbm = new ICBM(150, objetoDefensivos.get(0), 3);
        icbm.coordendasActuales.setLocation(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        MisilAntiBalistico abm = new MisilAntiBalistico(new Point2D.Double(185, DEFENSIVE_OBJECT_Y_COORDINATE), new Point2D.Double(148, 210), 3);
        abm.coordendasActuales.setLocation(abm.getCoordendasObjetivo());
        misils.add(abm);

        mockExplosionController.explotarMisil(abm);
        assertEquals(1, mockExplosionController.explosiones.size());

        for (int i = 0; i < 20; i++) {
            mockExplosionController.animarExplosiones();
            mockExplosionController.dibujarExplosiones(mockGraphicsContext);
        }

        assertFalse(mockExplosionController.todasExplosionesCompletadas());

        mockExplosionController.detectarChoques();
        assertTrue(icbm.estaDestruido());
        assertEquals(2, mockExplosionController.explosiones.size());
    }

    @Test
    public void removalOfCompletedExplosions() {
        ICBM icbm = new ICBM(150, objetoDefensivos.get(0), 3);
        icbm.coordendasActuales.setLocation(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        MisilAntiBalistico abm = new MisilAntiBalistico(new Point2D.Double(185, DEFENSIVE_OBJECT_Y_COORDINATE), new Point2D.Double(148, 210), 3);
        abm.coordendasActuales.setLocation(abm.getCoordendasObjetivo());
        misils.add(abm);

        mockExplosionController.explotarMisil(abm);
        mockExplosionController.explotarMisil(icbm);

        assertEquals(2, mockExplosionController.explosiones.size());

        for (int i = 0; i < 80; i++) {
            mockExplosionController.animarExplosiones();
            mockExplosionController.dibujarExplosiones(mockGraphicsContext);
        }

        assertEquals(2, mockExplosionController.explosiones.size());

        mockExplosionController.eliminarExplosionesCompletadas();
        assertEquals(0, mockExplosionController.explosiones.size());
    }

}

