package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.*;

public class Explosion implements ElementoJuego {

    private final Logger logger = Logger.getLogger(Explosion.class);

    private final Point2D.Double centreOfExplosion;
    private final int maximumBlastRadius;

    private int radioActual = 0;
    private boolean expanding = true;
    private boolean complete = false;
    private Ellipse2D.Double currentExplosion;

    public Explosion(Point2D.Double centreOfExplosion, int maximumBlastRadius) {
        this.centreOfExplosion = centreOfExplosion;
        this.maximumBlastRadius = maximumBlastRadius;
    }

    public void animar() {
        if (!complete) {
            if (expanding) {
                radioActual++;

                if (radioActual == maximumBlastRadius) {
                    expanding = false;
                }
            } else {
                radioActual--;
                if (radioActual == 0) {
                    complete = true;
                }
            }
        }
    }

    public void dibujar(Graphics2D graphicsContext) {
        if (!complete) {
            graphicsContext.setPaint(Color.GRAY);
            currentExplosion = new Ellipse2D.Double(centreOfExplosion.getX() - radioActual / 2, centreOfExplosion.getY() - radioActual / 2, radioActual, radioActual);
            graphicsContext.fill(currentExplosion);
        } else {
            logger.warn("Attempting to dibujar a completed explosion");
        }
    }

    public int getMaximumBlastRadius() {
        return maximumBlastRadius;
    }

    public boolean isComplete() {
        return complete;
    }

    public Rectangle getLimites() {
        return currentExplosion == null ? null : currentExplosion.getBounds();
    }

    public void destruir() {
        complete = true;
    }

    public int getRadioActual() {
        return radioActual;
    }

    public void setRadioActual(int radioActual) {
        this.radioActual = radioActual;
    }

    public boolean isExpanding() {
        return expanding;
    }

    public void setExpanding(boolean expanding) {
        this.expanding = expanding;
    }
}
