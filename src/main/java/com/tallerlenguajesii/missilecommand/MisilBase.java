package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;
import java.awt.*;
import java.awt.geom.Point2D;


public class MisilBase extends ObjetoDefensivo {

    private final Logger logger = Logger.getLogger(MisilBase.class);

    private static final int MAB_VELOCIDAD = 5;

    protected static final int MISIL_BASE_ALTURA = 50;
    protected static final int MISIL_BASE_ANCHO = 30;
    private static final int MISILES_INICIALES = 20;

    protected final int[] xCoordenadas;
    protected final int[] yCoordenadas;
    private final Rectangle limites;
    private final TipoObjetoDefensivo type = TipoObjetoDefensivo.MISSILE_BASE;

    private int cantidadMisiles = MISILES_INICIALES;

    public MisilBase(Point2D.Double bottomLeftCoordinates) {
        super(new Point2D.Double(bottomLeftCoordinates.getX() + MISIL_BASE_ANCHO / 2, bottomLeftCoordinates.getY() - MISIL_BASE_ALTURA / 2));

        if (logger.isDebugEnabled()) {
            logger.debug("Misil Base created, coordenadas: " + coordenadas);
        }

        xCoordenadas = new int[]{(int) bottomLeftCoordinates.getX(), (int) (bottomLeftCoordinates.getX() + MISIL_BASE_ANCHO / 2), (int) (bottomLeftCoordinates.getX() + MISIL_BASE_ANCHO)};
        yCoordenadas = new int[]{(int) bottomLeftCoordinates.getY(), (int) (bottomLeftCoordinates.getY() - MISIL_BASE_ALTURA / 2), (int) bottomLeftCoordinates.getY()};

        limites = new Rectangle((int) bottomLeftCoordinates.getX(), ((int) bottomLeftCoordinates.getY()) - MISIL_BASE_ALTURA, MISIL_BASE_ANCHO, MISIL_BASE_ALTURA);
    }

    public MisilAntiBalistico fireMissile(Point2D.Double targetCoordinates) {
        if (cantidadMisiles == 0) {
            logger.info("Misil base no tiene mas misiles.");
            return null;
        }

        cantidadMisiles--;

        if (logger.isDebugEnabled()) {
            logger.debug("Misil fired, base has " + cantidadMisiles + " misiles remaining");
        }

        Point2D.Double topOfTriangle = new Point2D.Double(xCoordenadas[1], yCoordenadas[1]);
        return new MisilAntiBalistico(topOfTriangle, targetCoordinates, MAB_VELOCIDAD);
    }

    public int getCantidadMisiles() {
        return cantidadMisiles;
    }

    public void setCantidadMisiles(int numberOfMissiles) {
        this.cantidadMisiles = numberOfMissiles;
    }

    public void dibujar(Graphics2D graphicsContext) {
        if (!estaDestruida()) {
            graphicsContext.setPaint(Color.WHITE);
            graphicsContext.fill(new Polygon(xCoordenadas, yCoordenadas, 3));
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
        cantidadMisiles = MISILES_INICIALES;
    }

    public TipoObjetoDefensivo getTipo() {
        return type;
    }
}
