package com.tallerlenguajesii.mock;

import com.tallerlenguajesii.missilecommand.CampoDeJuego;


import java.awt.*;

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
