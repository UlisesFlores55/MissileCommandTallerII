package com.tallerlenguajesii.missilecommand;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Point2D;

public class ControladorJuegoTest {

    private static final int OBJETO_DEFENSIVO_COOR_Y = 325;
    private CampoDeJuego campoDeJuego;
    private List<ObjetoDefensivo> objetosDefensivos;
    private List<MisilBase> misilBases;

    @Before
    public void setup() {
        campoDeJuego = mock(CampoDeJuego.class);
        objetosDefensivos = mock(ArrayList.class);
        misilBases = mock(ArrayList.class);
    }

    @Test
    public void estadoInicio() {
        ControladorJuego controlador = new ControladorJuego(this.campoDeJuego, this.objetosDefensivos);
        assertFalse(controlador.isGameInProgress());
        assertEquals(0, controlador.misils.size());
    }

    @Test
    public void fireMissile() {
        ControladorJuego controlador = new ControladorJuego(this.campoDeJuego, this.objetosDefensivos);
        when(misilBases.get(eq(0))).thenReturn(new MisilBase(new Point2D.Double(60, OBJETO_DEFENSIVO_COOR_Y)));
        assertEquals(0, controlador.misils.size());

        controlador.fireMissile(misilBases.get(0), new Point2D.Double(200,200));
        assertEquals(1, controlador.misils.size());
    }

}
