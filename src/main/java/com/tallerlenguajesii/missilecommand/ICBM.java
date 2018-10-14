package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Point2D;

// Representa un misil contra el cual el jugador debe defender sus ciudades
public class ICBM extends Misil {

    private final Logger logger = Logger.getLogger(ICBM.class);
    private static final int RADIO_EXPLOSION = 15;
    private final ObjetoDefensivo objetivo;
    private double xIncrementa;
    private double yIncrementa;

    public ICBM(int coordenadaInicialX, ObjetoDefensivo objetivo, int velocidad) {
        super(new Point2D.Double(coordenadaInicialX, 0), RADIO_EXPLOSION);
        this.objetivo = objetivo;
        calcularIncrementosDePantalla(velocidad);

        if (logger.isDebugEnabled()) {
            logger.debug("ICBM creado con coordenadas: " + coordenadasIniciales +
                    ", objetivo: " + objetivo.getCoordenadas() +
                    ", x incrementa: " + xIncrementa);
        }
    }

    private void calcularIncrementosDePantalla(int velocidad) {
        yIncrementa = velocidad;
        xIncrementa = ((objetivo.getCoordenadas().getX() - this.coordenadasIniciales.getX()) / 328) * velocidad;
    }

    // Mueve el ICBM cada vez mas cerca al fondo de la pantalla donde se encuentran las ciudades
    public void animar() {
        if (this.getMisilEstado() == MisilEstado.EN_VUELO) {
            this.setCoordendasActuales(new Point2D.Double(this.getCoordenadasActuales().getX() + xIncrementa, this.getCoordenadasActuales().getY() + yIncrementa));

            if (this.getCoordenadasActuales().getY() >= 290) {
                this.setMisilEstado(MisilEstado.OBJETIVO_ALCANZADO);
            }
        }
    }

    // Devuelve las coordenadas del objetivo del ICBM
    @Override
    public Point2D.Double getCoordendasObjetivo() {
        return objetivo.getCoordenadas();
    }

     // El color del rastro del misil
    @Override
    protected Color getColorDelRastro() {
        return Color.RED;
    }
}
