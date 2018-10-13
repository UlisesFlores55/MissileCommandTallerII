package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Represents one of the missile bases that the player must defend from incoming misils.  A missile base
 * starts each level with a finite number of misils (INITIAL_NUMBER_OF_MISSILES) which the player can use
 * to destroy incoming misils.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 2:44:47 PM
 */
public class MisilBase extends ObjetoDefensivo {

    private final Logger logger = Logger.getLogger(MisilBase.class);

    private static final int ABM_SPEED = 5;

    protected static final int MISSILE_BASE_HEIGHT = 50;
    protected static final int MISSILE_BASE_WIDTH = 30;
    private static final int INITIAL_NUMBER_OF_MISSILES = 20;

    protected final int[] xCoordinates;
    protected final int[] yCoordinates;
    private final Rectangle bounds;
    private final TipoObjetoDefensivo type = TipoObjetoDefensivo.MISSILE_BASE;

    private int numberOfMissiles = INITIAL_NUMBER_OF_MISSILES;

    /**
     * @param bottomLeftCoordinates   the coordinates of the bottom left corner of this missile base.
     */
    public MisilBase(Point2D.Double bottomLeftCoordinates) {
        super(new Point2D.Double(bottomLeftCoordinates.getX() + MISSILE_BASE_WIDTH / 2, bottomLeftCoordinates.getY() - MISSILE_BASE_HEIGHT / 2));

        if (logger.isDebugEnabled()) {
            logger.debug("Misil Base created, coordinates: " + coordinates);
        }

        xCoordinates = new int[]{(int) bottomLeftCoordinates.getX(), (int) (bottomLeftCoordinates.getX() + MISSILE_BASE_WIDTH / 2), (int) (bottomLeftCoordinates.getX() + MISSILE_BASE_WIDTH)};
        yCoordinates = new int[]{(int) bottomLeftCoordinates.getY(), (int) (bottomLeftCoordinates.getY() - MISSILE_BASE_HEIGHT / 2), (int) bottomLeftCoordinates.getY()};

        bounds = new Rectangle((int) bottomLeftCoordinates.getX(), ((int) bottomLeftCoordinates.getY()) - MISSILE_BASE_HEIGHT, MISSILE_BASE_WIDTH, MISSILE_BASE_HEIGHT);
    }

    /**
     * If there are any remaining in this base, an anti-balistic missile will be fired at the
     * supplied target coordinates.
     *
     * @param targetCoordinates
     * @return
     */
    public MisilAntiBalistico fireMissile(Point2D.Double targetCoordinates) {
        if (numberOfMissiles == 0) {
            logger.info("Misil base has no more misils.");
            return null;
        }

        numberOfMissiles--;

        if (logger.isDebugEnabled()) {
            logger.debug("Misil fired, base has " + numberOfMissiles + " misils remaining");
        }

        Point2D.Double topOfTriangle = new Point2D.Double(xCoordinates[1], yCoordinates[1]);
        return new MisilAntiBalistico(topOfTriangle, targetCoordinates, ABM_SPEED);
    }

    public int getNumberOfMissilesRemaining() {
        return numberOfMissiles;
    }

    public void setNumberOfMissilesRemaining(int numberOfMissiles) {
        this.numberOfMissiles = numberOfMissiles;
    }

    public void draw(Graphics2D graphicsContext) {
        if (!isDestroyed()) {
            graphicsContext.setPaint(Color.WHITE);
            graphicsContext.fill(new Polygon(xCoordinates, yCoordinates, 3));
        }
    }

    /**
     * {@inheritDoc}
     */
    public void animate() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        setDestroyed(true);
    }

    /**
     * {@inheritDoc}
     */
    public void reset() {
        destroyed = false;
        numberOfMissiles = INITIAL_NUMBER_OF_MISSILES;
    }

    public TipoObjetoDefensivo getType() {
        return type;
    }
}
