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
    private List<ObjetoDefensivo> objetoDefensivo;
    protected List<Misil> misiles = new ArrayList<Misil>();
    private ControlExplosion controlExplosion;
    private GeneradorArmasEnemigas generadorArmasEnemigas;
    private boolean juegoEnProgreso = false;
    private int puntuacionGeneral = 0;
    private int nivel = 1;

    public ControladorJuego(CampoDeJuego campoDeJuego, List<ObjetoDefensivo> objetoDefensivo) {
        this.campoDeJuego = campoDeJuego;
        this.objetoDefensivo = objetoDefensivo;

        controlExplosion = new ControlExplosion(misiles, objetoDefensivo);
        generadorArmasEnemigas = new GeneradorArmasEnemigas(campoDeJuego, objetoDefensivo);
    }

    public void pintar(Graphics2D graphics2D) {
        controlExplosion.dibujarExplosiones(graphics2D);
        dibujarMisiles(graphics2D);
        graficarEstadisticas(graphics2D);
    }

    private void graficarEstadisticas(Graphics2D graphics2D) {
        int misilesActuales = this.campoDeJuego.getMisilBases().stream().mapToInt(MisilBase::getCantidadMisiles).sum();
        graphics2D.drawString(String.format("Misiles en Bases: %d", misilesActuales), 20, 35);
        graphics2D.drawString(String.format("Puntuacion: %d", puntuacionTotal()), 20, 55);
        graphics2D.drawString(String.format("Nivel: %d", this.nivel), 20, 75);
    }

    private int puntuacionTotal() {
        return this.puntuacionGeneral + this.puntuacionActual();
    }
    private int puntuacionActual() {
        return 25 * Math.toIntExact(this.controlExplosion.getMisilesDestruidos().stream().filter(Misil::estaDestruido).count());
    }

    private void dibujarMisiles(Graphics2D graphics2D) {
        for (Misil misil : misiles) {
            if (misil.alcanzoObjetivo() && !misil.estaDestruido()) {
                controlExplosion.explotarMisil(misil);
            } else if (!misil.estaDestruido()) {
                misil.dibujar(graphics2D);
            }
        }
    }

    private boolean todosMisilesDestruidos() {
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

    public void dispararMisil(MisilBase misilBase, Point2D.Double mouseCoordenadas) {
        if (!todosMisilesDestruidos()) {
            MisilAntiBalistico misilAB = misilBase.dispararMisil(mouseCoordenadas);
            if (misilAB != null) {
                misiles.add(misilAB);
            }
        }
    }

    public void run() {

        if (logger.isDebugEnabled()) {
            logger.debug("Ejecutando el Thread ICBM");
        }

        juegoEnProgreso = true;

        long antesDeTiempo, diferenciaDeTiempo, sleep;
        long tiempoTotal = 0;

        antesDeTiempo = System.currentTimeMillis();

        int velocidadICBM = 1;
        int misilesMaximos = 10;

        while (true) {

            controlExplosion.detectarChoques();

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

            if (todosMisilesDestruidos() && controlExplosion.todasExplosionesCompletadas()) {
                this.puntuacionGeneral = this.puntuacionTotal();
                this.resetNuevoNivel();
                // subir dificultad...
                velocidadICBM = velocidadICBM == 3 ? velocidadICBM : velocidadICBM + 1;
                misilesMaximos = misilesMaximos == 30 ? misilesMaximos : misilesMaximos + 5;
            }

            if (todasCiudadesDestruidas()) {
                for (Misil misil : misiles) {
                    misil.destruir();
                }
                controlExplosion.completarTodasExplosiones();
                campoDeJuego.repaint();
                break;
            }
        }

        juegoEnProgreso = false;
    }


    private boolean todasCiudadesDestruidas() {
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivo) {
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

        resetElementosJuego();

        Thread thread = new Thread(this);
        thread.start();
    }

    private void resetElementosJuego() {
        misiles = new ArrayList<Misil>();
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivo) {
            objetoDefensivo.reset();
        }
        controlExplosion = new ControlExplosion(misiles, objetoDefensivo);
    }

    private void resetNuevoNivel() {
        this.nivel++;
        this.misiles = new ArrayList<Misil>();
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivo) {
            if (objetoDefensivo.getTipo() == TipoObjetoDefensivo.MISIL_BASE) {
                objetoDefensivo.reset();
            }
        }
        controlExplosion = new ControlExplosion(misiles, objetoDefensivo);
    }
}
