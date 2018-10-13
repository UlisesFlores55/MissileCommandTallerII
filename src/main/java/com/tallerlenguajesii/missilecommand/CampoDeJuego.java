package com.tallerlenguajesii.missilecommand;

import org.apache.log4j.Logger;
import taller2.grafico.Dibujable;
import taller2.grafico.InformacionDibujable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class CampoDeJuego extends JPanel implements Dibujable {

    private final Logger logger = Logger.getLogger(CampoDeJuego.class);

    private static final int CANT_CIUDADES = 6;
    private static final int BASES_CON_MISILES = 3;

    private static final int[] CITY_X_COORDINATES = new int[]{60, 100, 140, 240, 280, 320};
    private static final int[] MISSILE_BASE_X_COORDINATES = new int[]{15, 185, 355};

    private List<ObjetoDefensivo> objetoDefensivos = new ArrayList<ObjetoDefensivo>(CANT_CIUDADES + BASES_CON_MISILES);
    private List<MisilBase> misilBases = new ArrayList<MisilBase>(BASES_CON_MISILES);

    private ControladorJuego controladorJuego;

    public CampoDeJuego() {
        setFocusable(true);
        setBackground(Color.BLACK);

        controladorJuego = new ControladorJuego(this, objetoDefensivos);

        MissileCommandMouseAdapter missileCommandMouseAdapter = new MissileCommandMouseAdapter();
        addMouseListener(missileCommandMouseAdapter);
        addMouseMotionListener(missileCommandMouseAdapter);
        
        addKeyListener(new MissileCommandKeyAdapter(missileCommandMouseAdapter));
    }


    public InformacionDibujable getInformacionDibujable() {
        return new InformacionDibujable(255, 345, 'H');
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        constructDefensiveArea(getParent().getHeight());
        drawLand(graphics2D, getParent().getHeight(), getParent().getWidth());
        drawDefensiveObjects(graphics2D);
        controladorJuego.paint(graphics2D);
    }

    private void drawDefensiveObjects(Graphics2D graphics2D) {
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
            objetoDefensivo.dibujar(graphics2D);
        }
    }

    private void drawLand(Graphics2D graphics2D, int parentHeight, int parentWidth) {
        graphics2D.setPaint(Color.GREEN);
        graphics2D.fill(new Rectangle2D.Double(0, parentHeight - 5, parentWidth, 5));
    }

    private void constructDefensiveArea(int parentHeight) {
        if (objetoDefensivos.size() == 0) {
            for (int xCoordinate : CITY_X_COORDINATES) {
                Ciudad ciudad = new Ciudad(new Point2D.Double(xCoordinate, parentHeight - 3));
                objetoDefensivos.add(ciudad);
            }

            for (int xCoordinate : MISSILE_BASE_X_COORDINATES) {
                MisilBase misilBase = new MisilBase(new Point2D.Double(xCoordinate, parentHeight - 3));
                misilBases.add(misilBase);
                objetoDefensivos.add(misilBase);
            }
        }
    }

    //Entrada de teclado
    private class MissileCommandKeyAdapter extends KeyAdapter {

        private final MissileCommandMouseAdapter mouseAdapter;

        public MissileCommandKeyAdapter(CampoDeJuego.MissileCommandMouseAdapter mouseAdapter) {
            this.mouseAdapter = mouseAdapter;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            MisilAntiBalistico abm = null;
            switch (key) {
                case 'a':
                case 'A':
                    if (!misilBases.get(0).estaDestruida()) {
                        controladorJuego.fireMissile(misilBases.get(0), mouseAdapter.getMouseCoordinates());
                    }
                    break;
                case 's':
                case 'S':
                    if (!misilBases.get(1).estaDestruida()) {
                        controladorJuego.fireMissile(misilBases.get(1), mouseAdapter.getMouseCoordinates());
                    }
                    break;
                case 'd':
                case 'D':
                    if (!misilBases.get(2).estaDestruida()) {
                        controladorJuego.fireMissile(misilBases.get(2), mouseAdapter.getMouseCoordinates());
                    }
                    break;
                case ' ':
                    controladorJuego.startGame();
                    break;
            }
        }
    }

     //Entrada Mouse
    private class MissileCommandMouseAdapter extends MouseAdapter {

        private double mouseCoorX;
        private double mouseCoorY;

        @Override
        public void mouseEntered(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        public void mouseMoved(MouseEvent e) {
            mouseCoorX = e.getPoint().getX();
            mouseCoorY = e.getPoint().getY();
        }

        public Point2D.Double getMouseCoordinates() {
            return new Point2D.Double(mouseCoorX, mouseCoorY);
        }
    }
}
