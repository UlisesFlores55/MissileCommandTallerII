package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.awt.*;

// Clase que representa los misiles Antibalisticos
public class MisilAntiBalistico extends Misil {

    private final Logger logger = Logger.getLogger(MisilAntiBalistico.class);

    private static final int RADIO_ESTALLIDO = 30;

    private final Point2D.Double coordendasObjetivo;

    protected double xIncrementa;
    protected double yDecrementa;

    public MisilAntiBalistico(Point2D.Double coordenadasIniciales, Point2D.Double coordendasObjetivo, int velocidad) {
        super(coordenadasIniciales, RADIO_ESTALLIDO);
        this.coordendasObjetivo = coordendasObjetivo;
        calcularIncrementos(velocidad);
    }

    private void calcularIncrementos(int velocidad) {
        yDecrementa = velocidad;
        xIncrementa = (coordendasObjetivo.getX() - this.coordenadasIniciales.getX()) / (coordenadasIniciales.getY() - coordendasObjetivo.getY()) * velocidad;
    }

    public void animar() {
        if (misilEstado == MisilEstado.EN_VUELO) {
            coordendasActuales = new Point2D.Double(coordendasActuales.getX() + xIncrementa, coordendasActuales.getY() - yDecrementa);

            if (coordendasActuales.getY() <= coordendasObjetivo.getY()) {
                misilEstado = MisilEstado.OBJETIVO_ALCANZADO;
            }
        }
    }

    @Override
    public Point2D.Double getCoordendasObjetivo() {
        return coordendasObjetivo;
    }

    @Override
    protected Color getColorDelRastro() {
        return Color.BLUE;
    }
}
