package com.tallerlenguajesii.missilecommand;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GeneradorArmasEnemigasTest {

    private static final int OBJETO_DEFENSIVO_COOR_Y = 325;

    private static final int[] COORDENADAS_X_CIUDAD = new int[]{60, 100, 140, 240, 280, 320};
    private static final int[] COORDENADAS_Y_BASES = new int[]{15, 185, 355};

    private List<ObjetoDefensivo> objetoDefensivos;
    private GeneradorArmasEnemigas armasEnemigas;
    private CampoDeJuego area;
    private Container container;

    @Before
    public void setup() {
        this.area = mock(CampoDeJuego.class);
        this.container = mock(Container.class);

        this.objetoDefensivos = new ArrayList<ObjetoDefensivo>();

        for (int xCoordinate : COORDENADAS_X_CIUDAD) {
            Ciudad ciudad = new Ciudad(new Point2D.Double(xCoordinate, OBJETO_DEFENSIVO_COOR_Y));
            objetoDefensivos.add(ciudad);
        }

        for (int xCoordinate : COORDENADAS_Y_BASES) {
            MisilBase misilBase = new MisilBase(new Point2D.Double(xCoordinate, OBJETO_DEFENSIVO_COOR_Y));
            objetoDefensivos.add(misilBase);
        }

        armasEnemigas = new GeneradorArmasEnemigas(area, objetoDefensivos);
    }

    @Test
    public void debeCrearICBM() {
        when(this.container.getWidth()).thenReturn(400);
        when(this.area.getParent()).thenReturn(this.container);
        ICBM icbm = this.armasEnemigas.crearMisil(1);
        assertNotNull(icbm);
        assertTrue(icbm.getCoordenadasIniciales().getX() >= 0);
        assertTrue(icbm.getCoordenadasIniciales().getX() < area.getParent().getWidth());
    }
}
