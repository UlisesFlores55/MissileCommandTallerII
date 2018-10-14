package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;

public class ControlExplosion {

    private final Logger logger = Logger.getLogger(ControlExplosion.class);

    protected List<Explosion> explosiones = new ArrayList<Explosion>();
    protected List<Misil> misiles;
    protected List<ObjetoDefensivo> objetoDefensivo;
    private List<Misil> misilesDestruidos = new ArrayList<Misil>();

    public ControlExplosion(List<Misil> misiles, List<ObjetoDefensivo> objetoDefensivo) {
        this.misiles = misiles;
        this.objetoDefensivo = objetoDefensivo;
    }

    public boolean todasExplosionesCompletadas() {
        for (Explosion explosion : explosiones) {
            if (!explosion.estaCompleta()) {
                return false;
            }
        }
        return true;
    }

    public void eliminarExplosionesCompletadas() {
        Iterator<Explosion> iterator = explosiones.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().estaCompleta()) {
                iterator.remove();
            }
        }
    }

    public void animarExplosiones() {
        for (Explosion explosion : explosiones) {
            if (!explosion.estaCompleta()) {
                explosion.animar();
            }
        }
    }

    public void dibujarExplosiones(Graphics2D graphics2D) {
        for (Explosion explosion : explosiones) {
            if (!explosion.estaCompleta()) {
                explosion.dibujar(graphics2D);
            }
        }
    }

    public void detectarChoques() {
        List<Explosion> nuevasExplosiones = new ArrayList<Explosion>();

        for (Explosion explosion : explosiones) {
            if (explosion.estaCompleta()) {
                continue;
            }

            Rectangle limitesDeExplosion = explosion.getLimites();
            if (limitesDeExplosion == null) {
                continue;
            }

            for (Misil misil : misiles) {
                if (!misil.estaDestruido() && limitesDeExplosion.contains(misil.getLugarActual())) {
                    explotarMisil(misil, nuevasExplosiones);
                }
            }

            for (ObjetoDefensivo objetoDefensivo : objetoDefensivo) {
                if (!objetoDefensivo.estaDestruida() && limitesDeExplosion.intersects(objetoDefensivo.getLimites())) {
                    objetoDefensivo.destruir();
                }
            }
        }

        if (nuevasExplosiones.size() > 0) {
            explosiones.addAll(nuevasExplosiones);
        }
    }

    public void explotarMisil(Misil misil) {
        explotarMisil(misil, explosiones);
    }

    private void explotarMisil(Misil misil, List<Explosion> listaExplosiones) {
        if (logger.isDebugEnabled()) {
            logger.debug("Misil exploto en coordenadas: " + misil.getCoordenadasActuales());
        }
        if(misil.getColorDelRastro().equals(Color.RED) && misil.getCoordenadasActuales().getY() != 308.0) {
            misilesDestruidos.add(misil);
        }
        Explosion explosion = new Explosion(misil.getCoordenadasActuales(), misil.getRadioGeneradoPorExplosion());
        listaExplosiones.add(explosion);
        misil.destruir();
    }

    public void completarTodasExplosiones() {
        for (Explosion explosion : explosiones) {
            explosion.destruir();
        }
    }

    public List<Misil> getMisilesDestruidos() {
        return misilesDestruidos;
    }

    public void setMisilesDestruidos(List<Misil> misilesDestruidos) {
        this.misilesDestruidos = misilesDestruidos;
    }
}
