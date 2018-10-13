package com.tallerlenguajesii.missilecommand;

import com.tallerlenguajesii.mock.MockCampoDeJuego;
import com.tallerlenguajesii.mock.MockControladorJuego;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.awt.geom.Point2D;

/**
 * Unit tests for the <code>ControladorJuego</code> class.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 11:12:37 PM
 */
public class ControladorJuegoTest {

    private static final int DEFENSIVE_OBJECT_Y_COORDINATE = 325;

    private static final int[] CITY_X_COORDINATES = new int[]{60, 100, 140, 240, 280, 320};
    private static final int[] MISSILE_BASE_X_COORDINATES = new int[]{15, 185, 355};

    private MockCampoDeJuego gameArea;
    private MockControladorJuego gameController;
    private List<ObjetoDefensivo> objetoDefensivos;
    private List<MisilBase> misilBases = new ArrayList<MisilBase>(MISSILE_BASE_X_COORDINATES.length);

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
            misilBases.add(misilBase);
        }

        gameController = new MockControladorJuego(gameArea, objetoDefensivos);
    }

    @Test
    public void initialState() {
        assertFalse(gameController.isGameInProgress());
        assertEquals(0, gameController.numberOfMissiles()); 
    }

    @Test
    public void fireMissile() {
        assertEquals(0, gameController.numberOfMissiles());

        gameController.fireMissile(misilBases.get(0), new Point2D.Double(200,200));
        assertEquals(1, gameController.numberOfMissiles()); 
    }

    @Test
    public void runASingleLevel() {
        // ...
    }
}
