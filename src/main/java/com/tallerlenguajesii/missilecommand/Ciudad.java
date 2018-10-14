package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Ciudad extends ObjetoDefensivo {

    private final Logger logger = Logger.getLogger(CampoDeJuego.class);

    private static final int CIUDAD_PESO = 20;
    private static final int CIUDAD_ALTURA = 20;

    private final TipoObjetoDefensivo tipo = TipoObjetoDefensivo.CIUDAD;
    private final Point2D.Double coorIzquierdaInferior;
    private final Rectangle limites;

    // Se crea la ciudad con sus coordenadas correspondientes
    public Ciudad(Point2D.Double coordenadaIzquierdaInferior) {
        super(new Point2D.Double(coordenadaIzquierdaInferior.getX() + CIUDAD_PESO / 2, coordenadaIzquierdaInferior.getY() - CIUDAD_ALTURA / 2));

        coorIzquierdaInferior = new Point2D.Double(coordenadaIzquierdaInferior.getX(), coordenadaIzquierdaInferior.getY() - CIUDAD_ALTURA);
        limites = new Rectangle((int) coorIzquierdaInferior.getX(), (int) coorIzquierdaInferior.getY(), CIUDAD_PESO, CIUDAD_ALTURA);

        if (logger.isDebugEnabled()) {
            logger.debug("Ciudad creada, coordenadas: " + coordenadas);
        }
    }

    // Dibujado de la ciudad
    public void dibujar(Graphics2D graphicsContext) {
        if (!estaDestruida()) {
            graphicsContext.setPaint(Color.DARK_GRAY);
            graphicsContext.fill(new Rectangle2D.Double(coorIzquierdaInferior.getX(), coorIzquierdaInferior.getY(), CIUDAD_PESO, CIUDAD_ALTURA));
        }
    }

    public void animar() {
        throw new UnsupportedOperationException();
    }

    public Rectangle getLimites() {
        return limites;
    }

    public void destruir() {
        setDestruida(true);
    }

    public void reset() {
        destruida = false;
    }

    public TipoObjetoDefensivo getTipo() {
        return tipo;
    }
}
