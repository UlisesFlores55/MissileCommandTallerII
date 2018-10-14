package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.*;

public class Explosion implements ElementoJuego {

    private final Logger logger = Logger.getLogger(Explosion.class);

    private final Point2D.Double centroDeExplosion;
    private final int radioMaximoDeExplosion;

    private int radioActual = 0;
    private boolean expandiendo = true;
    private boolean completado = false;
    private Ellipse2D.Double explosionActual;

    public Explosion(Point2D.Double centroDeExplosion, int radioMaximoDeExplosion) {
        this.centroDeExplosion = centroDeExplosion;
        this.radioMaximoDeExplosion = radioMaximoDeExplosion;
    }

    public void animar() {
        if (!completado) {
            if (expandiendo) {
                radioActual++;

                if (radioActual == radioMaximoDeExplosion) {
                    expandiendo = false;
                }
            } else {
                radioActual--;
                if (radioActual == 0) {
                    completado = true;
                }
            }
        }
    }

    public void dibujar(Graphics2D graphicsContext) {
        if (!completado) {
            graphicsContext.setPaint(Color.GRAY);
            explosionActual = new Ellipse2D.Double(centroDeExplosion.getX() - radioActual / 2, centroDeExplosion.getY() - radioActual / 2, radioActual, radioActual);
            graphicsContext.fill(explosionActual);
        } else {
            logger.warn("Intentando dibujar una explosion completada");
        }
    }

    public int getRadioMaximoDeExplosion() {
        return radioMaximoDeExplosion;
    }

    public boolean estaCompleta() {
        return completado;
    }

    public Rectangle getLimites() {
        return explosionActual == null ? null : explosionActual.getBounds();
    }

    public void destruir() {
        completado = true;
    }

    public int getRadioActual() {
        return radioActual;
    }

    public void setRadioActual(int radioActual) {
        this.radioActual = radioActual;
    }

    public boolean isExpandiendo() {
        return expandiendo;
    }

    public void setExpandiendo(boolean expandiendo) {
        this.expandiendo = expandiendo;
    }
}
