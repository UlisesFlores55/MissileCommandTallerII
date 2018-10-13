package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.*;

public abstract class Misil implements ElementoJuego {

    private final Logger logger = Logger.getLogger(Misil.class);

    protected final Point2D.Double initialCoordinates;
    protected final int generatedBlastRadius;

    protected Point2D.Double currentCoordinates;
    protected MisilEstado misilEstado = MisilEstado.IN_FLIGHT;

    public Misil(Point2D.Double initialCoordinates, int generatedBlastRadius) {
        this.initialCoordinates = initialCoordinates;
        this.currentCoordinates = initialCoordinates;
        this.generatedBlastRadius = generatedBlastRadius;
    }

    public void draw(Graphics2D graphics2D) {
        switch (misilEstado) {
            case IN_FLIGHT:
                drawInFlight(graphics2D);
                break;
            case REACHED_TARGET:
                logger.warn("Attempting to draw a missile that has already reached its target.");
                break;
            case DESTROYED:
                logger.warn("Attempting to draw a missile that has already been destroyed.");
                break;
            default:
                logger.warn("Unexpected Misil Status: " + misilEstado);
        }
    }

    private void drawInFlight(Graphics2D graphics2D) {
        Line2D.Double line = new Line2D.Double(initialCoordinates, currentCoordinates);
        graphics2D.setColor(getTrailColor());
        graphics2D.draw(line);
    }

    public boolean hasReachedTarget() {
        return misilEstado == MisilEstado.REACHED_TARGET;
    }

    public boolean isDestroyed() {
        return misilEstado == MisilEstado.DESTROYED;
    }

    public void destroy() {        
        misilEstado = MisilEstado.DESTROYED;
    }

    public int getGeneratedBlastRadius() {
        return generatedBlastRadius;
    }

    public Point2D.Double getInitialCoordinates() {
        return initialCoordinates;
    }

    public Point2D.Double getCurrentCoordinates() {
        return currentCoordinates;
    }

    public Point getLocation() {
        Point point = new Point();
        point.setLocation(currentCoordinates.getX(), currentCoordinates.getY());
        return point;
    }

    public Rectangle getBounds() {
        throw new UnsupportedOperationException();
    }

    public abstract Point2D.Double getTargetCoordinates();

    protected abstract Color getTrailColor();

}
