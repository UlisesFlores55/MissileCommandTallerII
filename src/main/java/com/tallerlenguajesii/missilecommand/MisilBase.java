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
    private final TipoObjetoDefensivo tipo = TipoObjetoDefensivo.MISIL_BASE;

    private int cantidadMisiles = MISILES_INICIALES;

    public MisilBase(Point2D.Double coordenadasIzquierdaInferior) {
        super(new Point2D.Double(coordenadasIzquierdaInferior.getX() + MISIL_BASE_ANCHO / 2, coordenadasIzquierdaInferior.getY() - MISIL_BASE_ALTURA / 2));

        if (logger.isDebugEnabled()) {
            logger.debug("Misil Base creado, coordenadas: " + coordenadas);
        }

        xCoordenadas = new int[]{(int) coordenadasIzquierdaInferior.getX(), (int) (coordenadasIzquierdaInferior.getX() + MISIL_BASE_ANCHO / 2), (int) (coordenadasIzquierdaInferior.getX() + MISIL_BASE_ANCHO)};
        yCoordenadas = new int[]{(int) coordenadasIzquierdaInferior.getY(), (int) (coordenadasIzquierdaInferior.getY() - MISIL_BASE_ALTURA / 2), (int) coordenadasIzquierdaInferior.getY()};

        limites = new Rectangle((int) coordenadasIzquierdaInferior.getX(), ((int) coordenadasIzquierdaInferior.getY()) - MISIL_BASE_ALTURA, MISIL_BASE_ANCHO, MISIL_BASE_ALTURA);
    }

    public MisilAntiBalistico dispararMisil(Point2D.Double coordenadasObjetivo) {
        if (cantidadMisiles == 0) {
            logger.info("La base no tiene mas misiles.");
            return null;
        }

        cantidadMisiles--;

        if (logger.isDebugEnabled()) {
            logger.debug("Misil disparado, la base tiene " + cantidadMisiles + " misiles restantes");
        }

        Point2D.Double cimaDelTriangulo = new Point2D.Double(xCoordenadas[1], yCoordenadas[1]);
        return new MisilAntiBalistico(cimaDelTriangulo, coordenadasObjetivo, MAB_VELOCIDAD);
    }

    public int getCantidadMisiles() {
        return cantidadMisiles;
    }

    public void setCantidadMisiles(int numeroMisiles) {
        this.cantidadMisiles = numeroMisiles;
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
        return tipo;
    }
}
