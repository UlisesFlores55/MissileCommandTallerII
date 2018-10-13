package com.tallerlenguajesii.mock;

import com.tallerlenguajesii.missilecommand.ICBM;
import com.tallerlenguajesii.missilecommand.ObjetoDefensivo;

import java.awt.geom.Point2D;

/**
 * Extends the <code>ICBM</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 2:32:36 PM
 */
public class MockICBM extends ICBM {

    public MockICBM(int initialXCoordinate, ObjetoDefensivo target, int speed) {
        super(initialXCoordinate, target, speed);
    }

    public double getXIncrement() {
        return xIncrement;
    }

    public double getYIncrement() {
        return yIncrement;
    }

    public void setCurrentCoordinates(Point2D.Double currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }
}
