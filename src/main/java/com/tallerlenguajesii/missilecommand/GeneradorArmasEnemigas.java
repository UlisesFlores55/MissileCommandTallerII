package com.tallerlenguajesii.missilecommand;

import java.util.Random;
import java.util.List;

public class GeneradorArmasEnemigas {

    private final Random randomNumberGenerator = new Random();

    private final List<ObjetoDefensivo> objetivos;
    private final CampoDeJuego campoDeJuego;

    public GeneradorArmasEnemigas(CampoDeJuego campoDeJuego, List<ObjetoDefensivo> objetivos) {
        this.campoDeJuego = campoDeJuego;
        this.objetivos = objetivos;
    }

    public ICBM crearMisil(int speed) {
        return new ICBM(generateInitialXCoordinate(), selectTarget(), speed);
    }

    private ObjetoDefensivo selectTarget() {
        int targetId = randomNumberGenerator.nextInt(objetivos.size() - 1);
        return objetivos.get(targetId);
    }

    public int generateInitialXCoordinate() {
        return randomNumberGenerator.nextInt(campoDeJuego.getParent().getWidth() - 1);
    }
}
