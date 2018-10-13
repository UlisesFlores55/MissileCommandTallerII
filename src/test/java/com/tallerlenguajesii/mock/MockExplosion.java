package com.tallerlenguajesii.mock;

import com.tallerlenguajesii.missilecommand.Explosion;

import java.awt.geom.Point2D;

/**
 * Extends the <code>Explosion</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author Alan Tibbetts
 * @since Feb 21, 2010, 10:55:33 PM
 */
public class MockExplosion extends Explosion {

    public MockExplosion(Point2D.Double centreOfExplosion, int blastRadius) {
        super(centreOfExplosion, blastRadius);
    }

    public int getCurrentRadius() {
        return currentRadius;
    }

    public void setCurrentRadius(int currentRadius) {
        this.currentRadius = currentRadius;
    }

    public boolean isExpanding() {
        return expanding;
    }

    public void setExpanding(boolean expanding) {
        this.expanding = expanding;
    }
}
