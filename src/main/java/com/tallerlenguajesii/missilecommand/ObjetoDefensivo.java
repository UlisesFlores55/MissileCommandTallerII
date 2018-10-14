package com.tallerlenguajesii.missilecommand;

import java.awt.geom.Point2D;

//
public abstract class ObjetoDefensivo implements ElementoJuego {

    protected final Point2D.Double coordenadas;

    // Por defecto el objeto defensivo no esta destruido
    protected boolean destruida = false;

    public ObjetoDefensivo(Point2D.Double coordenadas) {
        this.coordenadas = coordenadas;
    }

    // Obtiene las coordenadas del objeto defensivo
    public Point2D.Double getCoordenadas() {
        return coordenadas;
    }

    // Retorna el estado del objecto defensivo
    public boolean estaDestruida() {
        return destruida;
    }

    public void setDestruida(boolean destruida) {
        this.destruida = destruida;
    }

    public abstract void reset();

    public abstract TipoObjetoDefensivo getTipo();
}
