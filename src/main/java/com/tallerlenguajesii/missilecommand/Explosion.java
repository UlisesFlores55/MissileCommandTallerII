package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.*;

/**
 * Represents a missile explosion.  Explosions expand to a maximum size represented by
 * their blast radius before contracting back to nothing.
 *
 * @author Alan Tibbetts
 * @since Feb 21, 2010, 10:52:59 AM
 */
public class Explosion implements ElementoJuego {

    private final Logger logger = Logger.getLogger(Explosion.class);

    private final Point2D.Double centreOfExplosion;
    private final int maximumBlastRadius;

    protected int currentRadius = 0;
    protected boolean expanding = true;
    private boolean complete = false;
    private Ellipse2D.Double currentExplosion;

    /**
     * @param centreOfExplosion     the centre of the explosion
     * @param maximumBlastRadius    the maximum radius of this explosion
     */
    public Explosion(Point2D.Double centreOfExplosion, int maximumBlastRadius) {
        this.centreOfExplosion = centreOfExplosion;
        this.maximumBlastRadius = maximumBlastRadius;
    }

    /**
     * Expands or contracts the explosion by 1 pixel.
     */
    public void animate() {
        if (!complete) {
            if (expanding) {
                currentRadius++;

                if (currentRadius == maximumBlastRadius) {
                    expanding = false;
                }
            } else {
                currentRadius--;
                if (currentRadius == 0) {
                    complete = true;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param graphicsContext
     */
    public void draw(Graphics2D graphicsContext) {
        if (!complete) {
            graphicsContext.setPaint(Color.GRAY);
            currentExplosion = new Ellipse2D.Double(centreOfExplosion.getX() - currentRadius / 2, centreOfExplosion.getY() - currentRadius / 2, currentRadius, currentRadius);
            graphicsContext.fill(currentExplosion);
        } else {
            logger.warn("Attempting to draw a completed explosion");
        }
    }

    public int getMaximumBlastRadius() {
        return maximumBlastRadius;
    }

    /**
     * Has the explosion completed, i.e. has it expanded to its maximum blast radius and
     * contracted back to nothing.
     *
     * @return
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * {@inheritDoc}
     *
     * Returns null if the explosion has been created but not yet drawn on the screen.
     */
    public Rectangle getBounds() {
        return currentExplosion == null ? null : currentExplosion.getBounds();
    }

    /**
     * {@inheritDoc}
     */
    public void destroy() {
        complete = true;
    }

}
