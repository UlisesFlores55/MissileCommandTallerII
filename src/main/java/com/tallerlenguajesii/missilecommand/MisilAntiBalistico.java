package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.*;

public class MisilAntiBalistico extends Misil {

    private final Logger logger = Logger.getLogger(MisilAntiBalistico.class);

    private static final int RADIO_ESTALLIDO = 30;

    private final Point2D.Double targetCoordinates;

    protected double xIncrement;
    protected double yDecrement;

    public MisilAntiBalistico(Point2D.Double initialCoordinates, Point2D.Double targetCoordinates, int speed) {
        super(initialCoordinates, RADIO_ESTALLIDO);
        this.targetCoordinates = targetCoordinates;
        calculateIncrements(speed);
    }

    private void calculateIncrements(int speed) {
        yDecrement = speed;
        xIncrement = (targetCoordinates.getX() - this.initialCoordinates.getX()) / (initialCoordinates.getY() - targetCoordinates.getY()) * speed;
    }

    public void animar() {
        if (misilEstado == MisilEstado.EN_VUELO) {
            currentCoordinates = new Point2D.Double(currentCoordinates.getX() + xIncrement, currentCoordinates.getY() - yDecrement);

            if (currentCoordinates.getY() <= targetCoordinates.getY()) {
                misilEstado = MisilEstado.ALCANZO_OBJETIVO;
            }
        }
    }

    @Override
    public Point2D.Double getTargetCoordinates() {
        return targetCoordinates;
    }

    @Override
    protected Color getTrailColor() {
        return Color.BLUE;
    }
}
