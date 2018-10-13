package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;

/**
 * Manages the creation, animation and drawing of <code>Explosions</code>.  Also manages the detection of
 * collisions between explosions and other game elements such as misils, cities and missile bases.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 8:47:33 PM
 */
public class ControlExplosion {

    private final Logger logger = Logger.getLogger(ControlExplosion.class);

    protected List<Explosion> explosions = new ArrayList<Explosion>();
    protected List<Misil> misils;
    protected List<ObjetoDefensivo> objetoDefensivos;

    /**
     * NB. The misils and defensive objects lists are shared between a number of objects, i.e.
     * there state is changed in objects other than this one.
     *
     * @param misils          a list of misils
     * @param objetoDefensivos  a list of defensive OBJECTS
     */
    public ControlExplosion(List<Misil> misils, List<ObjetoDefensivo> objetoDefensivos) {
        this.misils = misils;
        this.objetoDefensivos = objetoDefensivos;
    }

    /**
     * @return Whether or not all the currently listed <code>Explosion</code>s complete.
     */
    public boolean areAllExplosionComplete() {
        for (Explosion explosion : explosions) {
            if (!explosion.isComplete()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes all completed explosions from the internal list of current
     * explostions.
     */
    public void removeCompletedExplosions() {
        Iterator<Explosion> iterator = explosions.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isComplete()) {
                iterator.remove();
            }
        }
    }

    /**
     * Animate each <code>Explosion</code> currently in the internal list. i.e. expand those
     * explosions that are expanding and contract those that are contracting.
     */
    public void animateExplosions() {
        for (Explosion explosion : explosions) {
            if (!explosion.isComplete()) {
                explosion.animate();
            }
        }
    }

    /**
     * Draw the current state of all incomplete <code>Explosions</code> currently in the internal
     * list.
     *
     * @param graphics2D
     */
    public void drawExplosions(Graphics2D graphics2D) {
        for (Explosion explosion : explosions) {
            if (!explosion.isComplete()) {
                explosion.draw(graphics2D);
            }
        }
    }

    /**
     * Have any of the current <code>Explosions</code> come into contact with any of the misils
     * or defensive objects currently on screen?  Where there is contact, the missile or defensive
     * object is destroyed, and in the case of misils, a secondary explosion is generated.
     */
    public void detectCollisions() {
        List<Explosion> newExplosions = new ArrayList<Explosion>();

        for (Explosion explosion : explosions) {
            if (explosion.isComplete()) {
                continue;
            }

            Rectangle explosionBounds = explosion.getBounds();
            if (explosionBounds == null) {
                continue;
            }

            for (Misil misil : misils) {
                if (!misil.isDestroyed() && explosionBounds.contains(misil.getLocation())) {
                    explodeMissile(misil, newExplosions);
                }
            }

            for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
                if (!objetoDefensivo.isDestroyed() && explosionBounds.intersects(objetoDefensivo.getBounds())) {
                    objetoDefensivo.destroy();
                }
            }
        }

        if (newExplosions.size() > 0) {
            explosions.addAll(newExplosions);
        }
    }

    /**
     * Generates an <code>Explosion</code> from the given misil and adds it to the internal list of
     * <code>Explosions</code>.
     *
     * @param misil   the misil to be exploded
     */
    public void explodeMissile(Misil misil) {
        explodeMissile(misil, explosions);
    }

    /**
     * Generates an <code>Explosion</code> from the given misil and adds it to the given list of
     * <code>Explosions</code>.
     *
     * @param misil
     * @param explosionsList
     */
    private void explodeMissile(Misil misil, List<Explosion> explosionsList) {
        if (logger.isDebugEnabled()) {
            logger.debug("Misil exploded at coordinates: " + misil.getCurrentCoordinates());
        }

        Explosion explosion = new Explosion(misil.getCurrentCoordinates(), misil.getGeneratedBlastRadius());
        explosionsList.add(explosion);
        misil.destroy();
    }

    public void completeAllExplosions() {
        for (Explosion explosion : explosions) {
            explosion.destroy();
        }
    }
}
