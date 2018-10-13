package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.*;

/**
 * Represents a missile fired by the player toward incoming <code>InterContinentalBallisticMissiles</code>.
 * <code>Anti-Ballistic Missiles</code> originate at one of the three <code>MissileBases</code> and move
 * up the screen toward their target.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 1:56:33 PM
 */
public class MisilAntiBalistico extends Misil {

    private final Logger logger = Logger.getLogger(MisilAntiBalistico.class);

    private static final int BLAST_RADIUS = 30;

    private final Point2D.Double targetCoordinates;

    protected double xIncrement;
    protected double yDecrement;

    /**
     * @param initialCoordinates the orgin of this missile
     * @param targetCoordinates  this missile's target
     * @param speed              the speed at which the missile moves toward its target
     */
    public MisilAntiBalistico(Point2D.Double initialCoordinates, Point2D.Double targetCoordinates, int speed) {
        super(initialCoordinates, BLAST_RADIUS);
        this.targetCoordinates = targetCoordinates;
        calculateIncrements(speed);
    }

    private void calculateIncrements(int speed) {
        yDecrement = speed;
        xIncrement = (targetCoordinates.getX() - this.initialCoordinates.getX()) / (initialCoordinates.getY() - targetCoordinates.getY()) * speed;
    }

    /**
     * Moves this ABM one step closer to its intended target.
     */
    public void animate() {
        if (misilEstado == MisilEstado.IN_FLIGHT) {
            currentCoordinates = new Point2D.Double(currentCoordinates.getX() + xIncrement, currentCoordinates.getY() - yDecrement);

            if (currentCoordinates.getY() <= targetCoordinates.getY()) {
                misilEstado = MisilEstado.REACHED_TARGET;
            }
        }
    }

    /**
     * @return  the coordinates of this ABM's target.
     */
    @Override
    public Point2D.Double getTargetCoordinates() {
        return targetCoordinates;
    }

    /**
     * @return  the colour of the trail left by this ABM as it flies toward its target.
     */
    @Override
    protected Color getTrailColor() {
        return Color.BLUE;
    }
}
