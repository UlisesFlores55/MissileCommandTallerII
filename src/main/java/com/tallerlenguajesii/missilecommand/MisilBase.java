package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;
import java.awt.*;
import java.awt.geom.Point2D;


public class MisilBase extends ObjetoDefensivo {

    private final Logger logger = Logger.getLogger(MisilBase.class);

    private static final int ABM_SPEED = 5;

    protected static final int MISSILE_BASE_HEIGHT = 50;
    protected static final int MISSILE_BASE_WIDTH = 30;
    private static final int INITIAL_NUMBER_OF_MISSILES = 20;

    protected final int[] xCoordinates;
    protected final int[] yCoordinates;
    private final Rectangle limites;
    private final TipoObjetoDefensivo type = TipoObjetoDefensivo.MISSILE_BASE;

    private int cantidadMisiles = INITIAL_NUMBER_OF_MISSILES;

    public MisilBase(Point2D.Double bottomLeftCoordinates) {
        super(new Point2D.Double(bottomLeftCoordinates.getX() + MISSILE_BASE_WIDTH / 2, bottomLeftCoordinates.getY() - MISSILE_BASE_HEIGHT / 2));

        if (logger.isDebugEnabled()) {
            logger.debug("Misil Base created, coordenadas: " + coordenadas);
        }

        xCoordinates = new int[]{(int) bottomLeftCoordinates.getX(), (int) (bottomLeftCoordinates.getX() + MISSILE_BASE_WIDTH / 2), (int) (bottomLeftCoordinates.getX() + MISSILE_BASE_WIDTH)};
        yCoordinates = new int[]{(int) bottomLeftCoordinates.getY(), (int) (bottomLeftCoordinates.getY() - MISSILE_BASE_HEIGHT / 2), (int) bottomLeftCoordinates.getY()};

        limites = new Rectangle((int) bottomLeftCoordinates.getX(), ((int) bottomLeftCoordinates.getY()) - MISSILE_BASE_HEIGHT, MISSILE_BASE_WIDTH, MISSILE_BASE_HEIGHT);
    }

    public MisilAntiBalistico fireMissile(Point2D.Double targetCoordinates) {
        if (cantidadMisiles == 0) {
            logger.info("Misil base no tiene mas misiles.");
            return null;
        }

        cantidadMisiles--;

        if (logger.isDebugEnabled()) {
            logger.debug("Misil fired, base has " + cantidadMisiles + " misils remaining");
        }

        Point2D.Double topOfTriangle = new Point2D.Double(xCoordinates[1], yCoordinates[1]);
        return new MisilAntiBalistico(topOfTriangle, targetCoordinates, ABM_SPEED);
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
            graphicsContext.fill(new Polygon(xCoordinates, yCoordinates, 3));
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
        cantidadMisiles = INITIAL_NUMBER_OF_MISSILES;
    }

    public TipoObjetoDefensivo getTipo() {
        return type;
    }
}
