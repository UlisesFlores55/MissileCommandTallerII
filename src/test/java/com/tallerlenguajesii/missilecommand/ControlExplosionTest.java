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
        assertEquals(0, mockExplosionController.misiles.size());
        assertEquals(1, mockExplosionController.objetoDefensivos.size());
        assertEquals(0, mockExplosionController.explosiones.size());

        assertTrue(mockExplosionController.estanTodasLasExplosionesCompletas());
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
        mockExplosionController = new ControlExplosion(this.misils, this.objetoDefensivos);
        ICBM icbm = new ICBM(150, objetoDefensivos.get(0), 3);
        icbm.currentCoordinates.setLocation(new Point2D.Double(150, 212.5));
        misils.add(icbm);

        assertEquals(0, mockExplosionController.explosiones.size());
        assertFalse(icbm.estaDestruido());

        mockExplosionController.explodeMissile(icbm);
        assertEquals(1, mockExplosionController.explosiones.size());
        assertTrue(icbm.estaDestruido());
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
        assertEquals(1, mockExplosionController.explosiones.size());

        for (int i = 0; i < 20; i++) {
            mockExplosionController.animarExplosiones();
            mockExplosionController.drawExplosions(mockGraphicsContext);
        }

        assertFalse(mockExplosionController.estanTodasLasExplosionesCompletas());

        mockExplosionController.detectarChoque();
        assertTrue(icbm.estaDestruido());
        assertEquals(2, mockExplosionController.explosiones.size());
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

        assertEquals(2, mockExplosionController.explosiones.size());

        for (int i = 0; i < 80; i++) {
            mockExplosionController.animarExplosiones();
            mockExplosionController.drawExplosions(mockGraphicsContext);
        }

        assertEquals(2, mockExplosionController.explosiones.size());

        mockExplosionController.eliminarExplosionesCompletadas();
        assertEquals(0, mockExplosionController.explosiones.size());
    }

}

