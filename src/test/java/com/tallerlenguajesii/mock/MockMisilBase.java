package com.tallerlenguajesii.mock;

import com.tallerlenguajesii.missilecommand.MisilBase;

import java.awt.geom.Point2D;

/**
 * Extends the <code>MisilBase</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author Alan Tibbetts
 * @since Feb 21, 2010, 10:17:52 PM
 */
public class MockMisilBase extends MisilBase {

    public MockMisilBase(Point2D.Double bottomLeftCoordinates) {
        super(bottomLeftCoordinates);
    }

    public Point2D.Double getTopOfTriangle() {
        return new Point2D.Double(xCoordinates[1], yCoordinates[1]);
    }
}
