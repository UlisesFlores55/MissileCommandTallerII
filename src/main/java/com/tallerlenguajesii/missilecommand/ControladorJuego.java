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
    protected List<Misil> misils = new ArrayList<Misil>();

    private ControlExplosion controlExplosion;
    private GeneradorArmasEnemigas generadorArmasEnemigas;
    private boolean gameInProgress = false;

    public ControladorJuego(CampoDeJuego campoDeJuego, List<ObjetoDefensivo> objetoDefensivos) {
        this.campoDeJuego = campoDeJuego;
        this.objetoDefensivos = objetoDefensivos;

        controlExplosion = new ControlExplosion(misils, objetoDefensivos);
        generadorArmasEnemigas = new GeneradorArmasEnemigas(campoDeJuego, objetoDefensivos);
    }

    public void paint(Graphics2D graphics2D) {
        controlExplosion.drawExplosions(graphics2D);
        drawMissiles(graphics2D);
    }

    private void drawMissiles(Graphics2D graphics2D) {
        for (Misil misil : misils) {
            if (misil.hasReachedTarget() && !misil.isDestroyed()) {
                controlExplosion.explodeMissile(misil);
            } else if (!misil.isDestroyed()) {
                misil.draw(graphics2D);
            }
        }
    }

    private boolean allMissilesDestroyed() {
        if (misils.size() == 0) {
            return false;
        }

        for (Misil misil : misils) {
            if (!misil.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    public void fireMissile(MisilBase misilBase, Point2D.Double mouseCoordinates) {
        if (!allMissilesDestroyed()) {
            MisilAntiBalistico missile = misilBase.fireMissile(mouseCoordinates);
            if (missile != null) {
                misils.add(missile);
            }
        }
    }

    public void run() {

        if (logger.isDebugEnabled()) {
            logger.debug("Executing the ICBM Thread");
        }

        gameInProgress = true;

        long beforeTime, timeDiff, sleep;
        long totalTime = 0;

        beforeTime = System.currentTimeMillis();

        int icbmSpeed = 1;
        int maximumMissiles = 10;

        while (true) {

            controlExplosion.detectCollisions();

            for (Misil misil : misils) {
                if (!misil.isDestroyed()) {
                    misil.animate();
                }
            }

            controlExplosion.animateExplosions();

            campoDeJuego.repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            totalTime += DELAY;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();

            if (misils.size() < maximumMissiles && totalTime % 500 == 0) {
                misils.add(generadorArmasEnemigas.createMissile(icbmSpeed));
            }

            controlExplosion.removeCompletedExplosions();

            if (allMissilesDestroyed() && controlExplosion.areAllExplosionComplete()) {
                resetForNewLevel();
                // increase difficulty...
                icbmSpeed = icbmSpeed == 3 ? icbmSpeed : icbmSpeed + 1;
                maximumMissiles = maximumMissiles == 30 ? maximumMissiles : maximumMissiles + 5;
            }

            if (allCitiesDestoyed()) {
                for (Misil misil : misils) {
                    misil.destroy();
                }
                controlExplosion.completeAllExplosions();
                campoDeJuego.repaint();
                break;
            }
        }

        gameInProgress = false;
    }


    private boolean allCitiesDestoyed() {
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
            if (objetoDefensivo.getType() == TipoObjetoDefensivo.CITY) {
                if (!objetoDefensivo.isDestroyed()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void startGame() {
        if (gameInProgress) {
            return;
        }

        resetGameElements();

        Thread thread = new Thread(this);
        thread.start();
    }

    private void resetGameElements() {
        misils = new ArrayList<Misil>();
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
            objetoDefensivo.reset();
        }
        controlExplosion = new ControlExplosion(misils, objetoDefensivos);
    }

    private void resetForNewLevel() {
        misils = new ArrayList<Misil>();
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
            if (objetoDefensivo.getType() == TipoObjetoDefensivo.MISSILE_BASE) {
                objetoDefensivo.reset();
            }
        }
        controlExplosion = new ControlExplosion(misils, objetoDefensivos);
    }
}
