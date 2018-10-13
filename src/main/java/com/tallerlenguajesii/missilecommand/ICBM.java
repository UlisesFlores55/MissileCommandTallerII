package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Represents a missile against which the player must defend his cities and missile bases.
 * <code>InterContinentalBallisticMissiles</code> move from the top of the screen toward their
 * intended objetivo at the bottom of the screen.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 1:54:26 PM
 */
public class ICBM extends Misil {

    private final Logger logger = Logger.getLogger(ICBM.class);

    private static final int RADIO_EXPLOSION = 15;

    private final ObjetoDefensivo objetivo;

    protected double xIncrement;
    protected double yIncrement;

    public ICBM(int initialXCoordinate, ObjetoDefensivo objetivo, int speed) {
        super(new Point2D.Double(initialXCoordinate, 0), RADIO_EXPLOSION);
        this.objetivo = objetivo;
        calculateScreenIncrements(speed);

        if (logger.isDebugEnabled()) {
            logger.debug("Created ICBM with intial coordenadas: " + initialCoordinates +
                    ", objetivo: " + objetivo.getCoordenadas() +
                    ", x increments: " + xIncrement);
        }
    }

    private void calculateScreenIncrements(int speed) {
        yIncrement = speed;
        xIncrement = ((objetivo.getCoordenadas().getX() - this.initialCoordinates.getX()) / 328) * speed;
    }

    /**
     * Move this ICBM one step closer to the bottom of the game area.
     */
    public void animar() {
        if (misilEstado == MisilEstado.IN_FLIGHT) {
            currentCoordinates = new Point2D.Double(currentCoordinates.getX() + xIncrement, currentCoordinates.getY() + yIncrement);

            if (currentCoordinates.getY() >= 308) {
                misilEstado = MisilEstado.REACHED_TARGET;
            }
        }
    }

    /**
     * @return  the coordenadas of this ICBM's objetivo.
     */
    @Override
    public Point2D.Double getTargetCoordinates() {
        return objetivo.getCoordenadas();
    }

     /**
     * @return  the colour of the trail left by this ICBM as it flies toward its objetivo.
     */
    @Override
    protected Color getTrailColor() {
        return Color.RED;
    }
}
