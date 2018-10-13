package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.*;

/**
 * Represents one of the cities that the player must defend from incoming misils.
 *
 * @author Alan Tibbetts
 * @since Feb 19, 2010, 2:44:32 PM
 */
public class Ciudad extends ObjetoDefensivo {

    private final Logger logger = Logger.getLogger(CampoDeJuego.class);

    private static final int CITY_WIDTH = 20;
    private static final int CITY_HEIGHT = 20;

    private final TipoObjetoDefensivo type = TipoObjetoDefensivo.CITY;
    private final Point2D.Double topLeftCoordinates;
    private final Rectangle bounds;

    /**
     * @param bottomLeftCoordinates   the coordinates of the bottom left corner of this city.
     */
    public Ciudad(Point2D.Double bottomLeftCoordinates) {
        super(new Point2D.Double(bottomLeftCoordinates.getX() + CITY_WIDTH / 2, bottomLeftCoordinates.getY() - CITY_HEIGHT / 2));

        topLeftCoordinates = new Point2D.Double(bottomLeftCoordinates.getX(), bottomLeftCoordinates.getY() - CITY_HEIGHT);
        bounds = new Rectangle((int) topLeftCoordinates.getX(), (int) topLeftCoordinates.getY(), CITY_WIDTH, CITY_HEIGHT);

        if (logger.isDebugEnabled()) {
            logger.debug("Ciudad created, coordinates: " + coordinates);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void draw(Graphics2D graphicsContext) {
        if (!isDestroyed()) {
            graphicsContext.setPaint(Color.DARK_GRAY);
            graphicsContext.fill(new Rectangle2D.Double(topLeftCoordinates.getX(), topLeftCoordinates.getY(), CITY_WIDTH, CITY_HEIGHT));
        }
    }

    /**
     * Not supported.
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
    }

    public TipoObjetoDefensivo getType() {
        return type;
    }
}
