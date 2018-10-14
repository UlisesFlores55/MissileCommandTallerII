package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Point2D;

public class ControladorJuego implements Runnable {

    private final Logger logger = Logger.getLogger(ControladorJuego.class);

    private final int DELAY = 50;
    private final CampoDeJuego campoDeJuego;

    private List<ObjetoDefensivo> objetoDefensivos;
    protected List<Misil> misiles = new ArrayList<Misil>();

    private ControlExplosion controlExplosion;
    private GeneradorArmasEnemigas generadorArmasEnemigas;
    private boolean juegoEnProgreso = false;

    public ControladorJuego(CampoDeJuego campoDeJuego, List<ObjetoDefensivo> objetoDefensivos) {
        this.campoDeJuego = campoDeJuego;
        this.objetoDefensivos = objetoDefensivos;

        controlExplosion = new ControlExplosion(misiles, objetoDefensivos);
        generadorArmasEnemigas = new GeneradorArmasEnemigas(campoDeJuego, objetoDefensivos);
    }

    public void paint(Graphics2D graphics2D) {
        controlExplosion.drawExplosions(graphics2D);
        drawMissiles(graphics2D);
    }

    private void drawMissiles(Graphics2D graphics2D) {
        for (Misil misil : misiles) {
            if (misil.hasReachedTarget() && !misil.estaDestruido()) {
                controlExplosion.explodeMissile(misil);
            } else if (!misil.estaDestruido()) {
                misil.dibujar(graphics2D);
            }
        }
    }

    private boolean misilesDestruidos() {
        if (misiles.size() == 0) {
            return false;
        }

        for (Misil misil : misiles) {
            if (!misil.estaDestruido()) {
                return false;
            }
        }
        return true;
    }

    public void dispararMisil(MisilBase misilBase, Point2D.Double mouseCoordinates) {
        if (!misilesDestruidos()) {
            MisilAntiBalistico misilAB = misilBase.fireMissile(mouseCoordinates);
            if (misilAB != null) {
                misiles.add(misilAB);
            }
        }
    }

    public void run() {

        if (logger.isDebugEnabled()) {
            logger.debug("Ejecutando el thread ICBM");
        }

        juegoEnProgreso = true;

        long antesDeTiempo, diferenciaDeTiempo, sleep;
        long tiempoTotal = 0;

        antesDeTiempo = System.currentTimeMillis();

        int velocidadICBM = 1;
        int misilesMaximos = 10;

        while (true) {

            controlExplosion.detectarChoque();

            for (Misil misil : misiles) {
                if (!misil.estaDestruido()) {
                    misil.animar();
                }
            }

            controlExplosion.animarExplosiones();

            campoDeJuego.repaint();

            diferenciaDeTiempo = System.currentTimeMillis() - antesDeTiempo;
            sleep = DELAY - diferenciaDeTiempo;
            tiempoTotal += DELAY;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrumpido");
            }

            antesDeTiempo = System.currentTimeMillis();

            if (misiles.size() < misilesMaximos && tiempoTotal % 500 == 0) {
                misiles.add(generadorArmasEnemigas.crearMisil(velocidadICBM));
            }

            controlExplosion.eliminarExplosionesCompletadas();

            if (misilesDestruidos() && controlExplosion.estanTodasLasExplosionesCompletas()) {
                resetNuevoNivel();

                // subir dificultad...
                velocidadICBM = velocidadICBM == 3 ? velocidadICBM : velocidadICBM + 1;
                misilesMaximos = misilesMaximos == 30 ? misilesMaximos : misilesMaximos + 5;
            }

            if (ciudadesDestruidas()) {
                for (Misil misil : misiles) {
                    misil.destruir();
                }
                controlExplosion.completarExplosiones();
                campoDeJuego.repaint();
                break;
            }
        }

        juegoEnProgreso = false;
    }


    private boolean ciudadesDestruidas() {
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
            if (objetoDefensivo.getTipo() == TipoObjetoDefensivo.CIUDAD) {
                if (!objetoDefensivo.estaDestruida()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isJuegoEnProgreso() {
        return juegoEnProgreso;
    }

    public void comenzarJuego() {
        if (juegoEnProgreso) {
            return;
        }

        resetElementosDeJuego();

        Thread thread = new Thread(this);
        thread.start();
    }

    private void resetElementosDeJuego() {
        misiles = new ArrayList<Misil>();
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
            objetoDefensivo.reset();
        }
        controlExplosion = new ControlExplosion(misiles, objetoDefensivos);
    }

    private void resetNuevoNivel() {
        misiles = new ArrayList<Misil>();
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
            if (objetoDefensivo.getTipo() == TipoObjetoDefensivo.MISIL_BASE) {
                objetoDefensivo.reset();
            }
        }
        controlExplosion = new ControlExplosion(misiles, objetoDefensivos);
    }
}
