package com.tallerlenguajesii.mock;

import com.tallerlenguajesii.missilecommand.CampoDeJuego;


import java.awt.*;

/**
 * Extends the <code>CampoDeJuego</code> class to provide additional methods useful within
 * unit tests and the like.
 *
 * @author: Alan Tibbetts
 * @since: Feb 22, 2010, 10:53:08 PM
 */
public class MockCampoDeJuego extends CampoDeJuego {

    private MockGameAreaParent mockGameAreaParent = new MockGameAreaParent();

    @Override
    public Container getParent() {
        return mockGameAreaParent;
    }

    private class MockGameAreaParent extends Container {
        @Override
        public int getWidth() {
            return 400;
        }
    }
}
