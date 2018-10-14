package com.tallerlenguajesii.missilecommand;

import java.awt.Graphics2D;
import java.awt.Rectangle;

// Elementos basicos que requiere el juego
public interface ElementoJuego {
    void dibujar(Graphics2D graphicsContext);

    void animar();

    void destruir();

    Rectangle getLimites();
}
