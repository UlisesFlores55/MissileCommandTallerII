package com.tallerlenguajesii.missilecommand;

import com.tallerlenguajesii.mock.MockCampoDeJuego;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the <code>GeneradorArmasEnemigas</code> class.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 10:51:58 PM
 */
public class GeneradorArmasEnemigasTest {

    private static final int DEFENSIVE_OBJECT_Y_COORDINATE = 325;

    private static final int[] CITY_X_COORDINATES = new int[]{60, 100, 140, 240, 280, 320};
    private static final int[] MISSILE_BASE_X_COORDINATES = new int[]{15, 185, 355};

    private List<ObjetoDefensivo> objetoDefensivos;
    private GeneradorArmasEnemigas enemyWeaponsFactory;
    private MockCampoDeJuego gameArea;

    @Before
    public void setup() {
        gameArea = new MockCampoDeJuego();

        objetoDefensivos = new ArrayList<ObjetoDefensivo>();

        for (int xCoordinate : CITY_X_COORDINATES) {
            Ciudad ciudad = new Ciudad(new Point2D.Double(xCoordinate, DEFENSIVE_OBJECT_Y_COORDINATE));
            objetoDefensivos.add(ciudad);
        }

        for (int xCoordinate : MISSILE_BASE_X_COORDINATES) {
            MisilBase misilBase = new MisilBase(new Point2D.Double(xCoordinate, DEFENSIVE_OBJECT_Y_COORDINATE));
            objetoDefensivos.add(misilBase);
        }

        enemyWeaponsFactory = new GeneradorArmasEnemigas(gameArea, objetoDefensivos);
    }

    @Test
    public void createIcbm() {
        ICBM icbm = enemyWeaponsFactory.crearMisil(1);
        assertNotNull(icbm);
        assertTrue(icbm.getInitialCoordinates().getX() >= 0);
        assertTrue(icbm.getInitialCoordinates().getX() < gameArea.getParent().getWidth());
    }
}
