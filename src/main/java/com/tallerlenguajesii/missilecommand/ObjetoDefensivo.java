package com.tallerlenguajesii.missilecommand;

import java.awt.geom.Point2D;

public abstract class ObjetoDefensivo implements ElementoJuego {

    protected final Point2D.Double coordenadas;

    protected boolean destruida = false;

    public ObjetoDefensivo(Point2D.Double coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Point2D.Double getCoordenadas() {
        return coordenadas;
    }

    public boolean estaDestruida() {
        return destruida;
    }

    public void setDestruida(boolean destruida) {
        this.destruida = destruida;
    }

    public abstract void reset();

    public abstract TipoObjetoDefensivo getTipo();
}
