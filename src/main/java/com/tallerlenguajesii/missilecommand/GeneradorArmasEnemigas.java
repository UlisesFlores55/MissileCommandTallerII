package com.tallerlenguajesii.missilecommand;

import java.util.Random;
import java.util.List;

public class GeneradorArmasEnemigas {

    private final Random generadorNumerosRandom = new Random();

    private final List<ObjetoDefensivo> objetivos;
    private final CampoDeJuego campoDeJuego;

    public GeneradorArmasEnemigas(CampoDeJuego campoDeJuego, List<ObjetoDefensivo> objetivos) {
        this.campoDeJuego = campoDeJuego;
        this.objetivos = objetivos;
    }

    public ICBM crearMisil(int velocidad) {
        return new ICBM(generarCoordenadaInicialX(), seleccionarObjetivo(), velocidad);
    }

    private ObjetoDefensivo seleccionarObjetivo() {
        int idObjetivo = generadorNumerosRandom.nextInt(objetivos.size() - 1);
        return objetivos.get(idObjetivo);
    }

    public int generarCoordenadaInicialX() {
        return generadorNumerosRandom.nextInt(campoDeJuego.getParent().getWidth() - 1);
    }
}
