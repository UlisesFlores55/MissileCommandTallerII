package com.tallerlenguajesii.missilecommand;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Point2D;

public class ControladorJuegoTest {

    private static final int OBJETO_DEFENSIVO_COOR_Y = 325;
    private CampoDeJuego campoDeJuego;
    private List<ObjetoDefensivo> objetosDefensivos;
    private List<MisilBase> misilBases;
    ControladorJuego controlador;
    private Graphics2D graphics2D;

    @Before
    public void setup() {
        campoDeJuego = mock(CampoDeJuego.class);
        objetosDefensivos = mock(ArrayList.class);
        misilBases = mock(ArrayList.class);
        graphics2D = mock(Graphics2D.class);
        controlador = new ControladorJuego(this.campoDeJuego, this.objetosDefensivos);
    }

    @Test
    public void debeDibujarEstadisiticas() {
        this.controlador.dibujar(this.graphics2D);
        verify(this.graphics2D, times(3)).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    public void noDebeHaberMisilesCargados() {
        assertFalse(controlador.isJuegoEnProgreso());
        assertEquals(0, controlador.misiles.size());
    }

    @Test
    public void debeDispararMisil() {
        when(misilBases.get(eq(0))).thenReturn(new MisilBase(new Point2D.Double(60, OBJETO_DEFENSIVO_COOR_Y)));
        assertEquals(0, controlador.misiles.size());

        controlador.dispararMisil(misilBases.get(0), new Point2D.Double(200,200));
        assertEquals(1, controlador.misiles.size());
    }

}
