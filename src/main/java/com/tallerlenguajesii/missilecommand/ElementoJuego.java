package com.tallerlenguajesii.missilecommand;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface ElementoJuego {

    void dibujar(Graphics2D graphicsContext);

    void animar();

    void destruir();

    Rectangle getLimites();
}
