package com.tallerlenguajesii.mock;

import com.tallerlenguajesii.missilecommand.ControlExplosion;
import com.tallerlenguajesii.missilecommand.Misil;
import com.tallerlenguajesii.missilecommand.ObjetoDefensivo;
import com.tallerlenguajesii.missilecommand.Explosion;

import java.util.List;

/**
 * Extends the <code>ControlExplosion</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 9:07:47 PM
 */
public class MockControlExplosion extends ControlExplosion {

    public MockControlExplosion(List<Misil> misils, List<ObjetoDefensivo> objetoDefensivos) {
        super(misils, objetoDefensivos);
    }

    public List<Misil> getMissiles() {
        return misils;
    }

    public List<ObjetoDefensivo> getDefensiveObjects() {
        return objetoDefensivos;
    }

    public List<Explosion> getExplosions() {
        return explosions;
    }

    public int numberOfExplosions() {
        return explosions.size();
    }
}
