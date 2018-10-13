package com.tallerlenguajesii.mock;

import com.tallerlenguajesii.missilecommand.CampoDeJuego;
import com.tallerlenguajesii.missilecommand.ControladorJuego;
import com.tallerlenguajesii.missilecommand.ObjetoDefensivo;

import java.util.List;

/**
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 11:21:22 PM
 */
public class MockControladorJuego extends ControladorJuego {

    public MockControladorJuego(CampoDeJuego gameArea, List<ObjetoDefensivo> objetoDefensivos) {
        super(gameArea, objetoDefensivos);
    }

    public int numberOfMissiles() {
        return misils.size();
    }
}
