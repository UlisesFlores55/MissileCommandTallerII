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

// Clase que establece todos los parametros del campo de juego, asi como sus limites, misiles y ciudades
public class CampoDeJuego extends JPanel implements Dibujable {

    private final Logger logger = Logger.getLogger(CampoDeJuego.class);

    private static final int CANT_CIUDADES = 6;
    private static final int BASES_CON_MISILES = 3;

    private static final int[] COORDENADAS_X_CIUDAD = new int[]{60, 100, 140, 240, 280, 320};
    private static final int[] COORDENADAS_X_BASE_MISILES = new int[]{15, 185, 355};

    private List<ObjetoDefensivo> objetoDefensivos = new ArrayList<ObjetoDefensivo>(CANT_CIUDADES + BASES_CON_MISILES);
    private List<MisilBase> misilBases = new ArrayList<MisilBase>(BASES_CON_MISILES);

    private ControladorJuego controladorJuego;

    // Setea el campo de juego
    public CampoDeJuego() {
        setFocusable(true);
        setBackground(Color.BLACK);

        controladorJuego = new ControladorJuego(this, objetoDefensivos);

        MissileCommandAdaptadorMouse missileCommandAdaptadorMouse = new MissileCommandAdaptadorMouse();
        addMouseListener(missileCommandAdaptadorMouse);
        addMouseMotionListener(missileCommandAdaptadorMouse);

        addKeyListener(new MissileCommandAdaptadorTeclado(missileCommandAdaptadorMouse));
    }

    public InformacionDibujable getInformacionDibujable() {
        return new InformacionDibujable(255, 345, 'H');
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        construirAreaDefensiva(getParent().getHeight());
        dibujarBase(graphics2D, getParent().getHeight(), getParent().getWidth());
        dibujarObjetosDefensivos(graphics2D);
        controladorJuego.paint(graphics2D);
    }

    private void dibujarObjetosDefensivos(Graphics2D graphics2D) {
        for (ObjetoDefensivo objetoDefensivo : objetoDefensivos) {
            objetoDefensivo.dibujar(graphics2D);
        }
    }

    private void dibujarBase(Graphics2D graphics2D, int altura, int ancho) {
        graphics2D.setPaint(Color.GREEN);
        graphics2D.fill(new Rectangle2D.Double(0, altura - 5, ancho, 5));
    }

    private void construirAreaDefensiva(int altura) {
        if (objetoDefensivos.size() == 0) {
            for (int xCoordenada : COORDENADAS_X_CIUDAD) {
                Ciudad ciudad = new Ciudad(new Point2D.Double(xCoordenada, altura - 3));
                objetoDefensivos.add(ciudad);
            }

            for (int xCoordenada : COORDENADAS_X_BASE_MISILES) {
                MisilBase misilBase = new MisilBase(new Point2D.Double(xCoordenada, altura - 3));
                misilBases.add(misilBase);
                objetoDefensivos.add(misilBase);
            }
        }
    }

    //Entrada de teclado
    private class MissileCommandAdaptadorTeclado extends KeyAdapter {

        private final MissileCommandAdaptadorMouse mouseAdaptador;

        public MissileCommandAdaptadorTeclado(MissileCommandAdaptadorMouse mouseAdaptador) {
            this.mouseAdaptador = mouseAdaptador;
        }

        @Override
        public void teclaPresionada(KeyEvent e) {
            int tecla = e.getKeyCode();
            MisilAntiBalistico abm = null;
            switch (tecla) {
                case 'a':
                case 'A':
                    if (!misilBases.get(0).estaDestruida()) {
                        controladorJuego.dispararMisil(misilBases.get(0), mouseAdaptador.getCoordenadasMouse());
                    }
                    break;
                case 's':
                case 'S':
                    if (!misilBases.get(1).estaDestruida()) {
                        controladorJuego.dispararMisil(misilBases.get(1), mouseAdaptador.getCoordenadasMouse());
                    }
                    break;
                case 'd':
                case 'D':
                    if (!misilBases.get(2).estaDestruida()) {
                        controladorJuego.dispararMisil(misilBases.get(2), mouseAdaptador.getCoordenadasMouse());
                    }
                    break;
                case ' ':
                    controladorJuego.comenzarJuego();
                    break;
            }
        }
    }

     //Entrada Mouse
    private class MissileCommandAdaptadorMouse extends MouseAdapter {

        private double mouseCoordenadaX;
        private double mouseCoordenadaY;

        @Override
        public void mouseEntrada(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

        @Override
        public void mouseSalida(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        public void mouseDesplazado(MouseEvent e) {
            mouseCoordenadaX = e.getPoint().getX();
            mouseCoordenadaY = e.getPoint().getY();
        }

        public Point2D.Double getCoordenadasMouse() {
            return new Point2D.Double(mouseCoordenadaX, mouseCoordenadaY);
        }
    }
}
