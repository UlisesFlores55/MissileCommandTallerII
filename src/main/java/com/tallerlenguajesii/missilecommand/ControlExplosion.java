package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;

public class ControlExplosion {

    private final Logger logger = Logger.getLogger(ControlExplosion.class);

    protected List<Explosion> explosions = new ArrayList<Explosion>();
    protected List<Misil> misils;
    protected List<ObjetoDefensivo> objetoDefensivos;

    public ControlExplosion(List<Misil> misils, List<ObjetoDefensivo> objetoDefensivos) {
        this.misils = misils;
        this.objetoDefensivos = objetoDefensivos;
    }

    public boolean areAllExplosionComplete() {
        for (Explosion explosion : explosions) {
            if (!explosion.isComplete()) {
                return false;
            }
        }
        return true;
    }

    public void removeCompletedExplosions() {
        Iterator<Explosion> iterator = explosions.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isComplete()) {
                iterator.remove();
            }
        }
    }

    public void animateExplosions() {
        for (Explosion explosion : explosions) {
            if (!explosion.isComplete()) {
                explosion.animar();
            }
        }
    }

    public void drawExplosions(Graphics2D graphics2D) {
        for (Explosion explosion : explosions) {
            if (!explosion.isComplete()) {
                explosion.dibujar(graphics2D);
            }
        }
    }

    public void detectCollisions() {
        List<Explosion> newExplosions = new ArrayList<Explosion>();

        for (Explosion explosion : explosions) {
            if (explosion.isComplete()) {
                continue;
            }

            Rectangle explosionBounds = explosion.getLimites();
            if (explosionBounds == null) {
                continue;
            }

            for (Misil misil : misils) {
                if (!misil.isDestroyed() && explosionBounds.contains(misil.getLocation())) {
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
            explosions.addAll(newExplosions);
        }
    }

    public void explodeMissile(Misil misil) {
        explodeMissile(misil, explosions);
    }

    private void explodeMissile(Misil misil, List<Explosion> explosionsList) {
        if (logger.isDebugEnabled()) {
            logger.debug("Misil exploded at coordenadas: " + misil.getCurrentCoordinates());
        }

        Explosion explosion = new Explosion(misil.getCurrentCoordinates(), misil.getGeneratedBlastRadius());
        explosionsList.add(explosion);
        misil.destruir();
    }

    public void completeAllExplosions() {
        for (Explosion explosion : explosions) {
            explosion.destruir();
        }
    }
}
