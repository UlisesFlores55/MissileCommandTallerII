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
    protected MisilEstado misilEstado = MisilEstado.EN_VUELO;

    public Misil(Point2D.Double initialCoordinates, int generatedBlastRadius) {
        this.initialCoordinates = initialCoordinates;
        this.currentCoordinates = initialCoordinates;
        this.generatedBlastRadius = generatedBlastRadius;
    }

    public void dibujar(Graphics2D graphics2D) {
        switch (misilEstado) {
            case EN_VUELO:
                drawInFlight(graphics2D);
                break;
            case ALCANZO_OBJETIVO:
                logger.warn("Attempting to dibujar a missile that has already reached its target.");
                break;
            case DESTRUIDO:
                logger.warn("Attempting to dibujar a missile that has already been destruida.");
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
        return misilEstado == MisilEstado.ALCANZO_OBJETIVO;
    }

    public boolean estaDestruido() {
        return misilEstado == MisilEstado.DESTRUIDO;
    }

    public void destruir() {
        misilEstado = MisilEstado.DESTRUIDO;
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

    public Rectangle getLimites() {
        throw new UnsupportedOperationException();
    }

    public abstract Point2D.Double getTargetCoordinates();

    protected abstract Color getTrailColor();

}
