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
    private final Point2D.Double coordenadasIzquierdaSuperior;
    private final Rectangle limites;

    public Ciudad(Point2D.Double coordenadasIzquierdaInferior) {
        super(new Point2D.Double(coordenadasIzquierdaInferior.getX() + CIUDAD_PESO / 2, coordenadasIzquierdaInferior.getY() - CIUDAD_ALTURA / 2));

        coordenadasIzquierdaSuperior = new Point2D.Double(coordenadasIzquierdaInferior.getX(), coordenadasIzquierdaInferior.getY() - CIUDAD_ALTURA);
        limites = new Rectangle((int) coordenadasIzquierdaSuperior.getX(), (int) coordenadasIzquierdaSuperior.getY(), CIUDAD_PESO, CIUDAD_ALTURA);

        if (logger.isDebugEnabled()) {
            logger.debug("Ciudad creada, coordenadas: " + coordenadas);
        }
    }

    public void dibujar(Graphics2D graphicsContext) {
        if (!estaDestruido()) {
            graphicsContext.setPaint(Color.DARK_GRAY);
            graphicsContext.fill(new Rectangle2D.Double(coordenadasIzquierdaSuperior.getX(), coordenadasIzquierdaSuperior.getY(), CIUDAD_PESO, CIUDAD_ALTURA));
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

    public void reiniciar() {
        this.setDestruida(false);
    }

    public TipoObjetoDefensivo getTipo() {
        return tipo;
    }
}
