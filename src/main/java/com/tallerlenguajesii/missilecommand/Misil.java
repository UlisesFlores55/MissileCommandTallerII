package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.*;

// Clase abstracta sobre la cual se basan los misiles
public abstract class Misil implements ElementoJuego {

    private final Logger logger = Logger.getLogger(Misil.class);

    protected final Point2D.Double coordenadasIniciales;
    protected final int radioExplosionGenerado;

    protected Point2D.Double coordendasActuales;
    protected MisilEstado misilEstado = MisilEstado.EN_VUELO;

    public Misil(Point2D.Double coordenadasIniciales, int radioExplosionGenerado) {
        this.coordenadasIniciales = coordenadasIniciales;
        this.coordendasActuales = coordenadasIniciales;
        this.radioExplosionGenerado = radioExplosionGenerado;
    }

    // Dibujar dependiendo del estado del misil
    public void dibujar(Graphics2D graphics2D) {
        switch (misilEstado) {
            case EN_VUELO:
                dibujarEnVuelo(graphics2D);
                break;
            case OBJETIVO_ALCANZADO:
                logger.warn("Intentando dibujar un misil que ya ha alcanzado su objetivo");
                break;
            case DESTRUIDO:
                logger.warn("Intentando dibujar un misil que ya ha sido destruido");
                break;
            default:
                logger.warn("Estado del misil inesperado: " + misilEstado);
        }
    }

    // Dibuja el rastro del misil
    private void dibujarEnVuelo(Graphics2D graphics2D) {
        Line2D.Double linea = new Line2D.Double(coordenadasIniciales, coordendasActuales);
        graphics2D.setColor(getColorDelRastro());
        graphics2D.draw(linea);
    }

    public boolean alcanzoObjetivo() {
        return misilEstado == MisilEstado.OBJETIVO_ALCANZADO;
    }

    public boolean estaDestruido() {
        return misilEstado == MisilEstado.DESTRUIDO;
    }

    public void destruir() {
        misilEstado = MisilEstado.DESTRUIDO;
    }

    public int getRadioGeneradoPorExplosion() {
        return radioExplosionGenerado;
    }

    public Point2D.Double getCoordenadasIniciales() {
        return coordenadasIniciales;
    }

    public Point2D.Double getCoordenadasActuales() {
        return coordendasActuales;
    }

    public Point getLugarActual() {
        Point punto = new Point();
        punto.setLocation(coordendasActuales.getX(), coordendasActuales.getY());
        return punto;
    }

    public Rectangle getLimites() {
        throw new UnsupportedOperationException();
    }

    public abstract Point2D.Double getCoordendasObjetivo();

    protected abstract Color getColorDelRastro();

}
