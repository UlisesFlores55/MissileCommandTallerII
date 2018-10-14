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
    protected List<ObjetoDefensivo> objetoDefensivos;

    public ControlExplosion(List<Misil> misiles, List<ObjetoDefensivo> objetoDefensivos) {
        this.misiles = misiles;
        this.objetoDefensivos = objetoDefensivos;
    }

    public boolean estanTodasLasExplosionesCompletas() {
        for (Explosion explosion : explosiones) {
            if (!explosion.isComplete()) {
                return false;
            }
        }
        return true;
    }

    public void eliminarExplosionesCompletadas() {
        Iterator<Explosion> iterator = explosiones.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isComplete()) {
                iterator.remove();
            }
        }
    }

    public void animarExplosiones() {
        for (Explosion explosion : explosiones) {
            if (!explosion.isComplete()) {
                explosion.animar();
            }
        }
    }

    public void drawExplosions(Graphics2D graphics2D) {
        for (Explosion explosion : explosiones) {
            if (!explosion.isComplete()) {
                explosion.dibujar(graphics2D);
            }
        }
    }

    public void detectarChoque() {
        List<Explosion> newExplosions = new ArrayList<Explosion>();

        for (Explosion explosion : explosiones) {
            if (explosion.isComplete()) {
                continue;
            }

            Rectangle explosionBounds = explosion.getLimites();
            if (explosionBounds == null) {
                continue;
            }

            for (Misil misil : misiles) {
                if (!misil.estaDestruido() && explosionBounds.contains(misil.getLocation())) {
                    explodeMissile(misil, newExplosions);
                }
            }

            for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
                if (!objetoDefensivo.estaDestruida() && explosionBounds.intersects(objetoDefensivo.getLimites())) {
                    objetoDefensivo.destruir();
                }
            }
        }

        if (newExplosions.size() > 0) {
            explosiones.addAll(newExplosions);
        }
    }

    public void explodeMissile(Misil misil) {
        explodeMissile(misil, explosiones);
    }

    private void explodeMissile(Misil misil, List<Explosion> explosionsList) {
        if (logger.isDebugEnabled()) {
            logger.debug("Misil exploded at coordenadas: " + misil.getCurrentCoordinates());
        }

        Explosion explosion = new Explosion(misil.getCurrentCoordinates(), misil.getGeneratedBlastRadius());
        explosionsList.add(explosion);
        misil.destruir();
    }

    public void completarExplosiones() {
        for (Explosion explosion : explosiones) {
            explosion.destruir();
        }
    }
}
